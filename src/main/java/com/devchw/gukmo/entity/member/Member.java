package com.devchw.gukmo.entity.member;

import com.devchw.gukmo.entity.login.Oauth;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 회원 엔티티
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@DynamicInsert  //insert 시 명시해주지 않아도 기본값 적용되도록 하기.
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;    //회원 식별자

    private String nickname;    //회원 닉네임

    private String username;    //회원 이름

    @ColumnDefault("'user.PNG'")
    private String profileImage;    //프로필 이미지

    private String email;   //이메일

    @ColumnDefault("0")
    private Long point;  //포인트

    @ColumnDefault("sysdate")
    private LocalDateTime joinDate; //가입일자

    @Enumerated(EnumType.STRING)
    private EmailAccept emailAccept;    //이메일수신동의 여부 YES, NO

    @Enumerated(EnumType.STRING)
    private UserRole userRole;    // ADMIN, MEMBER, ACADEMY

    @OneToOne(mappedBy = "member")
    private AcademyMember academyMember;

    @OneToMany(mappedBy = "member")
    private List<Oauth> oauths = new ArrayList<>();

    public enum EmailAccept {
        YES, NO
    }

    public enum UserRole {
        ADMIN, MEMBER, ACADEMY
    }
}
