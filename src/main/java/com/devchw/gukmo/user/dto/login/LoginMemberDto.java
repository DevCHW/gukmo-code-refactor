package com.devchw.gukmo.user.dto.login;

import com.devchw.gukmo.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginMemberDto {
    private Long id;    //memberId

    private String profileImage;    //프로필이미지

    private Member.UserRole userRole; //권한

    /**
     * Entity -> Dto
     */
    public LoginMemberDto toDto(Member member) {
       return LoginMemberDto.builder()
               .id(member.getId())
               .profileImage(member.getProfileImage())
               .userRole(member.getUserRole())
               .build();
    }
}
