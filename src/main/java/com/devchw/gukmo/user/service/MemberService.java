package com.devchw.gukmo.user.service;

import com.devchw.gukmo.config.email.GoogleMail;
import com.devchw.gukmo.entity.login.Login;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.exception.BaseException;
import com.devchw.gukmo.user.dto.api.member.SendEmailResponse;
import com.devchw.gukmo.user.dto.member.MyPageDto;
import com.devchw.gukmo.user.dto.member.SignUpFormDto;
import com.devchw.gukmo.user.repository.LoginRepository;
import com.devchw.gukmo.user.repository.MemberRepository;
import com.devchw.gukmo.utils.MyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static com.devchw.gukmo.config.response.BaseResponseStatus.NOT_FOUND_MEMBER;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final LoginRepository loginRepository;
    private final MemberRepository memberRepository;
    @Transactional
    public void signUp(SignUpFormDto form) {
        Member member = form.toMemberEntity(); //Dto -> 엔티티 변환
        Member saveMember = memberRepository.save(member);
        Login login = form.toLoginEntity(saveMember);
        loginRepository.save(login);
    }

    @Transactional
    public void editEmail(Long id, String email) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new BaseException(NOT_FOUND_MEMBER));
        member.changeMemberInfo(email);
    }
}
