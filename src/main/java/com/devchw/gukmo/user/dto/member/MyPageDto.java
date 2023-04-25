package com.devchw.gukmo.user.dto.member;

import com.devchw.gukmo.entity.member.Member;
import lombok.Data;

@Data
public class MyPageDto {
    private String username;
    private String nickname;
    private String email;
    private String profileImage;
    private Member.EmailAccept emailAccept;

    /**
     * Entity -> Dto
     */
    public MyPageDto toDto(Member member) {
        this.username = member.getUsername();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.profileImage = member.getProfileImage();
        this.emailAccept = member.getEmailAccept();
        return this;
    }
}
