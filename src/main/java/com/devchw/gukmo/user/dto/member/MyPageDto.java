package com.devchw.gukmo.user.dto.member;

import com.devchw.gukmo.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyPageDto {
    private String username;
    private String nickname;
    private String email;
    private String profileImage;
    private Member.EmailAccept emailAccept;
    private Long loginId;
    private Long point;

    /**
     * Entity -> Dto
     */
    public MyPageDto toDto(Member member) {
        try {
            return MyPageDto.builder()
                    .username(member.getUsername())
                    .nickname(member.getNickname())
                    .email(member.getEmail())
                    .profileImage(member.getProfileImage())
                    .emailAccept(member.getEmailAccept())
                    .loginId(member.getLogin().getId())
                    .point(member.getPoint())
                    .build();
        } catch (NullPointerException e) {  //소셜로그인 시 Login 없을 수 있음.
            return MyPageDto.builder()
                    .username(member.getUsername())
                    .nickname(member.getNickname())
                    .email(member.getEmail())
                    .profileImage(member.getProfileImage())
                    .emailAccept(member.getEmailAccept())
                    .point(member.getPoint())
                    .build();
        }
    }
}
