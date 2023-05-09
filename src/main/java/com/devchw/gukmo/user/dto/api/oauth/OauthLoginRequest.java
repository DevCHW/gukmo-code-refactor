package com.devchw.gukmo.user.dto.api.oauth;

import com.devchw.gukmo.entity.login.Oauth;
import com.devchw.gukmo.entity.member.Member;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OauthLoginRequest {

    private String authId;
    private String email;
    private String profileImage;
    private String username;
    private String redirectURL = "/";
    private String type;

    public Member toMemberEntity(String randomNickname) {
        return Member.builder()
                .email(email)
                .username(username)
                .nickname(randomNickname)
                .profileImage(profileImage)
                .build();
    }

    public Oauth toOauthEntity(Member savedMember) {
        return Oauth.builder()
                .authId(authId)
                .type(oauthTypeConverter())
                .member(savedMember)
                .build();
    }

    public Oauth.Type oauthTypeConverter() {
        switch(type) {
            case "KAKAO":
                return Oauth.Type.KAKAO;
            case "NAVER":
                return Oauth.Type.NAVER;
            case "FACEBOOK":
                return Oauth.Type.FACEBOOK;
            case "GOOGLE":
                return Oauth.Type.GOOGLE;
        }
        return null;
    }
}
