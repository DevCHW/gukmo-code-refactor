package com.devchw.gukmo.admin.service;

import com.devchw.gukmo.admin.dto.api.penalty.SavePenaltyRequest;
import com.devchw.gukmo.admin.repository.AdminMemberRepository;
import com.devchw.gukmo.admin.repository.AdminPenaltyRepository;
import com.devchw.gukmo.config.response.BaseResponseStatus;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.entity.penalty.Penalty;
import com.devchw.gukmo.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.devchw.gukmo.config.response.BaseResponseStatus.NOT_FOUND_MEMBER;

@Slf4j
@Service
@RequiredArgsConstructor
public class PenaltyService {

    private final AdminPenaltyRepository adminPenaltyRepository;
    private final AdminMemberRepository adminMemberRepository;


    /** 정지내역 등록하기 */
    @Transactional
    public Penalty save(SavePenaltyRequest request) {
        Long memberId = request.getMemberId();
        Member findMember = adminMemberRepository.findById(memberId).orElseThrow(() -> new BaseException(NOT_FOUND_MEMBER));
        Penalty savePenalty = request.toEntity(findMember);
        return adminPenaltyRepository.save(savePenalty);
    }
}
