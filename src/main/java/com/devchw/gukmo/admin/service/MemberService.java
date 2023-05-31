package com.devchw.gukmo.admin.service;

import com.devchw.gukmo.admin.dto.api.member.MemberInfoRequest;
import com.devchw.gukmo.admin.dto.api.member.MemberListDto;
import com.devchw.gukmo.admin.dto.api.member.DataTableMemberFormDto;
import com.devchw.gukmo.admin.repository.AdminAcademyMemberRepository;
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
public class MemberService {
    private final AdminMemberRepository adminMemberRepository;
    private final AdminAcademyMemberRepository adminAcademyMemberRepository;

    public List<Long> findIncreaseStats() {
        return adminMemberRepository.findIncreaseStats();
    }

    /** 관리자 회원내역 조회 */
    public List<MemberListDto> findAllMemberList(int start, int length, MultiValueMap<String, String> formData) {
        int end = length;
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

    /** 회원정보 수정 */
    @Transactional
    public Member edit(Long id, MemberInfoRequest request) {
        Member findMember = adminMemberRepository.findWithAcademyMemberById(id).orElseThrow(() -> new BaseException(NOT_FOUND_MEMBER));
        if(findMember.getUserRole().equals(Member.UserRole.ACADEMY) && !request.getUserRole().equals("ACADEMY")) {
            Long academyMemberId = findMember.getAcademyMember().getId();
            adminAcademyMemberRepository.deleteById(academyMemberId);
        }
        findMember.changeMemberInfo(request.getStatus(), request.getUserRole());
        return findMember;
    }
}
