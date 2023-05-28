package com.devchw.gukmo.user.dto.login;

import com.devchw.gukmo.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.devchw.gukmo.entity.member.Member.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginMemberDto {
    private Long id;    //memberId

    private String profileImage;    //프로필이미지

    private UserRole userRole; //권한
    
    private String nickname;    //닉네임
    
    private Long point; //포인트

    /**
     * Entity -> Dto
     */
    public LoginMemberDto toDto(Member member) {
        if(member.getPoint() != null) {
            return LoginMemberDto.builder()
                    .id(member.getId())
                    .profileImage(member.getProfileImage())
                    .userRole(member.getUserRole())
                    .nickname(member.getNickname())
                    .point(member.getPoint())
                    .build();
        } else {
            return LoginMemberDto.builder()
                    .id(member.getId())
                    .profileImage(member.getProfileImage())
                    .userRole(member.getUserRole())
                    .nickname(member.getNickname())
                    .point(0L)
                    .build();
        }

    }
}
