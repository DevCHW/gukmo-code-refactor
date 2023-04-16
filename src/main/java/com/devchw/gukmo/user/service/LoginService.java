package com.devchw.gukmo.user.service;

import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.exception.LoginException;
import com.devchw.gukmo.user.dto.login.LoginMemberDto;
import com.devchw.gukmo.user.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final LoginRepository loginRepository;

    /**
     * 로그인 처리
     */
    public Member login(String userId, String password) throws LoginException {
        return loginRepository.findByUserId(userId)
                .filter(l -> l.getPassword().equals(password))
                .orElseThrow(LoginException::new)
                .getMember();
    }
}
