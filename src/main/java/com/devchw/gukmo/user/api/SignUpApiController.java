package com.devchw.gukmo.user.api;

import com.devchw.gukmo.config.RecaptchaConfig;
import com.devchw.gukmo.user.repository.LoginRepository;
import com.devchw.gukmo.user.repository.MemberRepository;
import com.devchw.gukmo.user.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SignUpApiController {

    private final LoginRepository loginRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    /**
     * 아이디 존재여부 검사
     * @return 존재한다면 return true, 존재하지않는다면 return false
     */
    @GetMapping("/api/member/userIdExistCheck")
    public Boolean userIdExistCheck(@RequestParam String userId) {
            return loginRepository.existsByUserId(userId);
    }

    /**
     * 이메일 존재여부 검사
     * @return 존재한다면 return true, 존재하지않는다면 return false
     */
    @GetMapping("/api/member/emailExistCheck")
    public Boolean emailExistCheck(@RequestParam String email) {
        return memberRepository.existsByEmail(email);
    }

    /**
     * 닉네임 존재여부 검사
     * @return 존재한다면 return true, 존재하지않는다면 return false
     */
    @GetMapping("/api/member/nicknameExistCheck")
    public Boolean nicknameExistCheck(@RequestParam String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    /**
     * 회원가입이메일 인증코드 전송
     */
    @PostMapping(value="/api/member/sendEmailCertificationCode")
    public Map<String, Object> sendEmailCertificationCode(@RequestParam String email) {
        return memberService.sendEmail(email);
    }
}
