package com.devchw.gukmo.admin.api;

import com.devchw.gukmo.admin.service.EmailService;
import com.devchw.gukmo.config.response.BaseResponse;
import com.devchw.gukmo.user.dto.api.member.SendEmailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/email/")
public class EmailController {

    private final EmailService emailService;

    /** 이메일 인증코드 전송 */
    @PostMapping("/{email}")
    public BaseResponse<SendEmailResponse> sendEmailCertificationCode(@PathVariable String email,
                                                                      @RequestParam String message) {
        return new BaseResponse<>(emailService.sendEmail(email, message));
    }
}
