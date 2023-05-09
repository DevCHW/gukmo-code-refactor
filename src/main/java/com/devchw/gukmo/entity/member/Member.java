package com.devchw.gukmo.entity.member;

import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.entity.login.Login;
import com.devchw.gukmo.entity.login.Oauth;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

import static javax.persistence.FetchType.LAZY;

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
    private String profileImage;    //저장된 프로필이미지명

    private String email;   //이메일

    @ColumnDefault("0")
    private Long point;  //포인트

    @ColumnDefault("sysdate")
    private LocalDateTime joinDate; //가입일자

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NO'")
    private EmailAccept emailAccept;    //이메일수신동의 여부 YES, NO

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'MEMBER'")
    private UserRole userRole;    // ADMIN, MEMBER, ACADEMY

    @OneToOne(mappedBy = "member", fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
    private AcademyMember academyMember;

    @OneToOne(mappedBy = "member", fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
    private Login login;

    @OneToOne(mappedBy = "member", fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
    private Oauth oauth;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<Board> boards = new ArrayList<>();

    public enum EmailAccept {
        YES, NO
    }

    public enum UserRole {
        ADMIN, MEMBER, ACADEMY
    }

    /**
     * 저장할 프로필이미지 파일명 생성(고유한 값)
     */
    public String generatedUniqueProfileImageName(String profileImageName) {
        return UUID.randomUUID().toString() + "." + profileImageName;
    }

    /** 회원 정보 수정 1.*/
    public void changeMemberInfo(String username, String nickname, String profileImage, EmailAccept emailAccept) {
        this.username = username;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.emailAccept = emailAccept;
    }

    /** 회원 정보 수정 2.*/
    public void changeMemberInfo(String username, String nickname, EmailAccept emailAccept) {
        this.username = username;
        this.nickname = nickname;
        this.emailAccept = emailAccept;
    }

    /** 회원 정보 수정 3.*/
    public void changeMemberInfo(String email) {
        this.email = email;
    }
}
