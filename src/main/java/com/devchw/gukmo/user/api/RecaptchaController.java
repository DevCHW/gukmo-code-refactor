package com.devchw.gukmo.user.api;

import com.devchw.gukmo.config.RecaptchaConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
public class RecaptchaController {
    /**
     * reCAPTCHA(로봇이아닙니다) 인증하기
     * @return 인증 성공시 0 실패시 1 에러시 -1
     */
    @PostMapping("/api/recaptcha/verify")
    public int verify(@RequestParam String recaptcha) {
        log.info("verify 호출 request={}", recaptcha);

        RecaptchaConfig.setSecret("6LdO7zkjAAAAACYEyYOQ0PquIol3BtmcbcGY9PFI");
        try {
            if(RecaptchaConfig.verify(recaptcha)) {
                return 0; // 성공
            } else {
                return 1; // 실패
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
