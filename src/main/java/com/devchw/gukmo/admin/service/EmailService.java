package com.devchw.gukmo.admin.service;

import com.devchw.gukmo.config.email.GoogleMail;
import com.devchw.gukmo.user.dto.api.member.SendEmailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final GoogleMail mail;

    /**
     * 회원가입이메일 인증코드 전송
     */
    public SendEmailResponse sendEmail(String email, String message) {
        boolean sendMailSuccess = false; // 메일이 정상적으로 전송되었는지 유무를 알아오기 위한 용도

        // 이메일 제목 설정
        String subject = "[국비의모든것] 안녕하세요.국비의모든것 관리자입니다.";

        try {
            mail.sendmail(email, subject, message);
            sendMailSuccess = true;	//메일 전송했음을 기록함.
            message = "메일 전송에 성공하였습니다!";
        } catch(Exception e) {	//메일 전송이 실패한 경우
            sendMailSuccess = false;	//메일 전송 실패했음을 기록함.
            message = "메일 전송에 실패하였습니다. 다시 시도해주세요.";
        }//end of try-catch()----

        SendEmailResponse sendEmailResponse = new SendEmailResponse(sendMailSuccess, message);
        return sendEmailResponse;
    }
}
