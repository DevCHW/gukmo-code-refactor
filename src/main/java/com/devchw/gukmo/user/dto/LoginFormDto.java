package com.devchw.gukmo.user.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginFormDto {

    @NotEmpty
    private String userId;

    @NotEmpty
    private String password;
}
