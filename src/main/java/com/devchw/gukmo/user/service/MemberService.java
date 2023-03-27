package com.devchw.gukmo.user.service;

import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.entity.member.MemberLogin;
import com.devchw.gukmo.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member, MemberLogin memberLogin) {
        memberRepository.save(member, memberLogin);
        return member.getId();
    }

    /**
     * 닉네임 중복 검증
     */
    private void nicknameDuplicateCheck(String nickname) {
        Member findMember = memberRepository.findMemberWithNickname(nickname);
        if(findMember != null) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 단건 조회
     */
    public Member findMember(Long memberId) {
        return memberRepository.findMember(memberId);
    }

    /**
     * 회원 단건 조회(닉네임으로 조회)
     */
    public Member findMemberWithNickname(String nickname) {
        return memberRepository.findMemberWithNickname(nickname);
    }
}
