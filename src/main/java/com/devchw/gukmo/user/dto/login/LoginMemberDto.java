package com.devchw.gukmo.user.dto.login;

import com.devchw.gukmo.entity.member.Member;
import lombok.Data;
@Data
public class LoginMemberDto {
    private Long id;    //memberId

    private String profileImage;    //프로필이미지

    private Member.UserRole userRole; //권한
}
