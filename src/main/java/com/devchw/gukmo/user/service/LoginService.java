package com.devchw.gukmo.user.service;

import com.devchw.gukmo.config.response.BaseResponseStatus;
import com.devchw.gukmo.entity.login.Login;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.exception.BaseException;
import com.devchw.gukmo.exception.LoginException;
import com.devchw.gukmo.user.dto.api.login.PasswordRequest;
import com.devchw.gukmo.user.repository.LoginRepository;
import com.devchw.gukmo.user.repository.MemberRepository;
import com.devchw.gukmo.utils.SHA256;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {
    private final LoginRepository loginRepository;
    private final MemberRepository memberRepository;

    /** 로그인 처리 */
    public Member login(String userId, String password) throws LoginException {
        Long id = loginRepository.findByUserId(userId)
                .filter(l -> l.getPassword().equals(SHA256.encrypt(password)))
                .orElseThrow(LoginException::new)
                .getId();

        return memberRepository.findByLoginId(id).orElseThrow(LoginException::new);
    }

    /** 변경하려는 비밀번호가 기존 비밀번호와 같은지 확인(암호화 필요) */
    public boolean EqualsOriginPasswordCheck(Long id, String password) {
        String findPassword = loginRepository.findPasswordById(id);
        return findPassword.equals(password);
    }

    /** 비밀번호 변경 */
    @Transactional
    public void editPassword(Long id, String password) {
        Login login = loginRepository.findById(id).orElseThrow(() -> new BaseException(BaseResponseStatus.BAD_REQUEST));
        login.changePassword(password);
    }
}
