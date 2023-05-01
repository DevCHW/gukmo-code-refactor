package com.devchw.gukmo.user.api;

import com.devchw.gukmo.config.RecaptchaConfig;
import com.devchw.gukmo.config.response.BaseResponse;
import com.devchw.gukmo.user.dto.api.member.SendEmailResponse;
import com.devchw.gukmo.user.repository.LoginRepository;
import com.devchw.gukmo.user.repository.MemberRepository;
import com.devchw.gukmo.user.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/signUp")
public class SignUpApiController {

    private final MemberService memberService;
    private final LoginRepository loginRepository;

    /** 회원아이디 존재여부 조회 */
    @GetMapping("/userId/exist")
    public BaseResponse<Boolean> userIdExistCheck(@RequestParam String userId) {
        return new BaseResponse<>(loginRepository.existsByUserId(userId));
    }
}
