package com.devchw.gukmo.user.dto.api.member;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class SendEmailResponse {
    private boolean sendMailSuccess;
    private String certificationCode;
    private String message;

    public SendEmailResponse(boolean sendMailSuccess, String message) {
        this.sendMailSuccess = sendMailSuccess;
        this.message = message;
    }
}
