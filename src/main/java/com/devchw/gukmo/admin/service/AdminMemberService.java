package com.devchw.gukmo.admin.service;

import com.devchw.gukmo.admin.dto.api.member.MemberListDto;
import com.devchw.gukmo.admin.repository.AdminMemberRepository;
import com.devchw.gukmo.entity.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminMemberService {
    private final AdminMemberRepository adminMemberRepository;

    public List<Long> findIncreaseStats() {
        return adminMemberRepository.findIncreaseStats();
    }

    /** 관리자 회원내역 조회 */
    public List<MemberListDto> findAllMemberList(int start, int length, MultiValueMap<String, String> formData) {
        List<Member> findMembers = adminMemberRepository.findAll();
        return findMembers.stream().map(m -> new MemberListDto().toDto(m)).collect(Collectors.toList());
    }
}
