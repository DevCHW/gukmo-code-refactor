package com.devchw.gukmo.user.api;

import com.devchw.gukmo.config.RecaptchaConfig;
import com.devchw.gukmo.config.response.BaseResponse;
import com.devchw.gukmo.config.response.BaseResponseStatus;
import com.devchw.gukmo.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.devchw.gukmo.config.response.BaseResponseStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestController
@RequestMapping("/api/v1/recaptcha")
public class RecaptchaController {

    /** reCAPTCHA 인증 인증 성공시 0 실패시 1 에러시 -1 */
    @PostMapping("/verify")
    public BaseResponse<Integer> verify(@RequestParam String recaptcha) {
        log.info("verify 요청 request={}", recaptcha);
        int result = -1;
        try {
            if(RecaptchaConfig.verify(recaptcha)) {
                result = 0; // 성공
            } else {
                result = 1; // 실패
            }
            return new BaseResponse<>(result);
        } catch (IOException e) {
            throw new BaseException(INTERNAL_SERVER_ERROR);
        }
    }
}
