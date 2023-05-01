package com.devchw.gukmo.user.dto.api.member;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MemberInfoRequest {
    @NotEmpty(message = "유저이름은 필수값입니다.")
    private String username;

    @NotEmpty(message = "닉네임은 필수값입니다.")
    private String nickname;

    @NotEmpty
    private String emailAccept;

    @NotEmpty
    private String fileName;
}
