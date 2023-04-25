package com.devchw.gukmo.user.dto.api.member;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdateMemberInfoReq {
    @NotEmpty
    private String username;
    @NotEmpty
    private String nickname;
    @NotEmpty
    private String emailAccept;
}
