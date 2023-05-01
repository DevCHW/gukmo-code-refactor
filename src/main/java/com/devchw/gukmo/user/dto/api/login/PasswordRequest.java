package com.devchw.gukmo.user.dto.api.login;

import lombok.Data;

@Data
public class PasswordRequest {
    private Long memberId;
    private String password;
}
