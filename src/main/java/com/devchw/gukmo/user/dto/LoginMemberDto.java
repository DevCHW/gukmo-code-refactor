package com.devchw.gukmo.user.dto;

import com.devchw.gukmo.entity.member.Authority;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginMemberDto {
    private Long memberId;
    private Authority authority;
}
