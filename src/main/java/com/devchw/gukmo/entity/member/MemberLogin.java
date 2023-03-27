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
public class MemberLogin {

    @Id @GeneratedValue
    @Column(name = "memberLogin_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String userId;

    private String password;

    private LocalDateTime update_passwd_date;

    public MemberLogin(Member member, String userId, String password, LocalDateTime update_passwd_date) {
        this.member = member;
        this.userId = userId;
        this.password = password;
        this.update_passwd_date = update_passwd_date;
    }
}
