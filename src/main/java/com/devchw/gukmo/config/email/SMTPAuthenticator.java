package com.devchw.gukmo.config.email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

@Configuration
public class SMTPAuthenticator extends Authenticator{
    @Value("${email.address}")
    String sender; //보내는 사람 메일 주소
    @Value("${email.password}")
    String password;    //보내는 사람 메일 비밀번호
    
    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        // Gmail 의 경우 @gmail.com 을 제외한 아이디만 입력한다.(첫번째 파라미터)
        // 두번째 파라미터에는 발급받은 앱 비밀번호를 입력한다.
        return new PasswordAuthentication(sender, password);
    }

}
