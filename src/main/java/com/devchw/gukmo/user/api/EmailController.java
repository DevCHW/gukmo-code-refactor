package com.devchw.gukmo.user.api;

import com.devchw.gukmo.config.response.BaseResponse;
import com.devchw.gukmo.config.response.BaseResponseStatus;
import com.devchw.gukmo.user.dto.api.member.SendEmailResponse;
import com.devchw.gukmo.user.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
public class EmailController {

    private final EmailService emailService;

    /** 이메일 인증코드 전송 */
    @PostMapping("/certificationCode")
    public BaseResponse<SendEmailResponse> sendEmailCertificationCode(@RequestParam String email) {
        return new BaseResponse<>(emailService.sendEmail(email));
    }
}
