package com.devchw.gukmo.user.service;

import com.devchw.gukmo.config.email.GoogleMail;
import com.devchw.gukmo.user.dto.api.member.SendEmailResponse;
import com.devchw.gukmo.utils.MyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final GoogleMail mail;

    /**
     * 회원가입이메일 인증코드 전송
     */
    public SendEmailResponse sendEmail(String email) {
        boolean sendMailSuccess = false; // 메일이 정상적으로 전송되었는지 유무를 알아오기 위한 용도

        // 이메일 제목 설정
        String subject = "[국비의모든것] 회원가입을 환영합니다.";

        // 이메일인증코드 생성(10자리의 난수)
        String certificationCode = MyUtil.getAuthKey(10);

        // 이메일에 보낼 메세지(계정을찾을수있는 링크 보내주기)
        String message = "<div style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 540px; height: 600px; border-top: 4px solid #208EC9; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">"
                +"<h1 style='margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;'>"
                +"<span style='font-size: 20px; margin: 0 0 10px 3px; font-weight:bold;'>국비의모든것</span><br />"
                +"<span style='color: #208EC9;'>메일인증</span> 안내입니다."
                +"</h1>"
                +"<p style='font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;'>"
                +"안녕하세요.<br />"
                +"국비의모든것에 가입해 주셔서 진심으로 감사드립니다.<br />"
                +"아래 <b style='color: #208EC9;'>인증코드</b> 를 복사하여 회원가입 페이지로 돌아가서 <br/>회원가입을 완료해주세요.<br />"
                +"감사합니다."
                +"</p>"
                + "<div style='border:solid 1px #208EC9; font-size:20px; border-radius:10px; text-align:center; font-weight:bold;'>"
                + "&nbsp;<span style='color:#208EC9;'>인증코드&nbsp;:&nbsp;</span>"
                + "<span style='color: red; text-decoration: none;'>"
                + "<p style='display: inline-block; width: 210px; height: 45px; margin: 30px 5px 40px; line-height: 45px; vertical-align: middle; font-size: 16px;'>"
                + certificationCode
                + "</p>"
                + "</span>"
                + "</div>"
                + "</div>";
        try {
            mail.sendmail(email, subject, message);
            sendMailSuccess = true;	//메일 전송했음을 기록함.

        } catch(Exception e) {	//메일 전송이 실패한 경우
            sendMailSuccess = false;	//메일 전송 실패했음을 기록함.
        }//end of try-catch()----

        SendEmailResponse sendEmailResponse = new SendEmailResponse(sendMailSuccess, certificationCode, message);
        return sendEmailResponse;
    }
}
