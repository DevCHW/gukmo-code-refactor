package com.devchw.gukmo.entity.member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id; //PK

    private String nickname; //닉네임

    private String username; //회원이름

    private String profileImage; //프로필이미지

    private String email; //이메일

    private int point; //포인트

    private LocalDateTime joinDate; //가입일자

    @Enumerated(EnumType.STRING)
    private Status emailAccept;  // 이메일 수신 여부(YES, NO)

    @Enumerated(EnumType.STRING)
    private Status kakao; // 카카오로그인 연동여부 (YES, NO)

    @Enumerated(EnumType.STRING)
    private Status naver; // 네이버로그인 연동여부(YES, NO)

    @Enumerated(EnumType.STRING)
    private Status google; // 구글로그인 연동여부(YES, NO)

    @Enumerated(EnumType.STRING)
    private Status facebook; // 페이스북로그인 연동여부(YES, NO)

    @Enumerated(EnumType.STRING)
    private Authority authority; // 유저 구분(USER, ADMIN)

    public Member(String nickname, String username, String profileImage, String email, int point, LocalDateTime joinDate, Status emailAccept, Status kakao, Status naver, Status google, Status facebook, Authority authority) {
        this.nickname = nickname;
        this.username = username;
        this.profileImage = profileImage;
        this.email = email;
        this.point = point;
        this.joinDate = joinDate;
        this.emailAccept = emailAccept;
        this.kakao = kakao;
        this.naver = naver;
        this.google = google;
        this.facebook = facebook;
        this.authority = authority;
    }
}
