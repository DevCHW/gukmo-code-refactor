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

    private String authId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member; //Member(N:1)

    @Enumerated(EnumType.STRING)
    private Type type; //소셜로그인 타입

    public enum Type {
        KAKAO, NAVER, FACEBOOK, GOOGLE
    }
}
