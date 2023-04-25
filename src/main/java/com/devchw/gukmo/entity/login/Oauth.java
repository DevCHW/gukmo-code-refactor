package com.devchw.gukmo.entity.login;

import com.devchw.gukmo.entity.member.Member;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

/**
 * OAuth 엔티티
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Oauth {

    @Id @GeneratedValue
    @Column(name = "oauth_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member; //Member(N:1)

    private Type type; //소셜로그인 타입

    private String accessToken; //엑세스 토큰

    public enum Type {
        KAKAO, NAVER, FACEBOOK, GOOGLE
    }
}
