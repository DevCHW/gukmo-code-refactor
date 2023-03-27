package com.devchw.gukmo.user.service;

import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.entity.member.MemberLogin;
import com.devchw.gukmo.user.repository.LoginRepository;
import com.devchw.gukmo.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;
    private final MemberRepository memberRepository;

    /**
     * 로그인 시도
     * @return null 로그인 실패
     */
    public Member login(String userId, String password) {
        MemberLogin memberLogin = loginRepository.findByLoginUser(userId, password);
        if(memberLogin != null) {
            return memberLogin.getMember();
        } else {
            return null;
        }
    }
}
