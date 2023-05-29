package com.devchw.gukmo.admin.service;

import com.devchw.gukmo.admin.dto.api.member.MemberListDto;
import com.devchw.gukmo.admin.dto.member.DataTableMemberFormDto;
import com.devchw.gukmo.admin.repository.AdminMemberRepository;
import com.devchw.gukmo.entity.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
        int end = start+length;
        DataTableMemberFormDto form = new DataTableMemberFormDto().toDto(formData);
        List<Member> findMembers = adminMemberRepository.findAllMemberList(start, end, form);
        return findMembers.stream().map(m -> new MemberListDto().toDto(m)).collect(Collectors.toList());
    }

    /** 관리자 회원내역 조회 */
    public long findAllMemberListTotal(MultiValueMap<String, String> formData) {
        DataTableMemberFormDto form = new DataTableMemberFormDto().toDto(formData);
        return adminMemberRepository.findAllMemberListTotal(form);
    }
}
