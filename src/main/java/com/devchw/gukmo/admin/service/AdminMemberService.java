package com.devchw.gukmo.admin.service;

import com.devchw.gukmo.admin.dto.api.member.MemberListDto;
import com.devchw.gukmo.admin.dto.api.member.DataTableMemberFormDto;
import com.devchw.gukmo.admin.repository.AdminMemberRepository;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.stream.Collectors;

import static com.devchw.gukmo.config.response.BaseResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminMemberService {
    private final AdminMemberRepository adminMemberRepository;

    public List<Long> findIncreaseStats() {
        return adminMemberRepository.findIncreaseStats();
    }

    /** 관리자 회원내역 조회 */
    public List<MemberListDto> findAllMemberList(int start, int length, MultiValueMap<String, String> formData) {
        int end = start+length;
        DataTableMemberFormDto form = new DataTableMemberFormDto().toDto(formData);
        List<Member> findMembers = adminMemberRepository.findAllMemberList(start, end, form);
        return findMembers.stream().map(m -> new MemberListDto().toDto(m)).collect(Collectors.toList());
    }

    /** 관리자 회원내역 총 갯수 조회 */
    public long findAllMemberListTotal(MultiValueMap<String, String> formData) {
        DataTableMemberFormDto form = new DataTableMemberFormDto().toDto(formData);
        return adminMemberRepository.findAllMemberListTotal(form);
    }

    /** 닉네임으로 회원 조회 */
    public Member findByNickname(String nickname) {
        return adminMemberRepository.findByNickname(nickname).orElseThrow(() -> new BaseException(NOT_FOUND_MEMBER));
    }
}
