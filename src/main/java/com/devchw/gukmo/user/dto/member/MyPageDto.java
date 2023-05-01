package com.devchw.gukmo.user.dto.member;

import com.devchw.gukmo.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    /**
     * Entity -> Dto
     */
    public static MyPageDto toDto(Member member) {
        return MyPageDto.builder()
                .username(member.getUsername())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .profileImage(member.getProfileImage())
                .emailAccept(member.getEmailAccept())
                .loginId(member.getLogin().getId())
                .build();
    }
}
