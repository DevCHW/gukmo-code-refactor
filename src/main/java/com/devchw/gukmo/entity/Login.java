package com.devchw.gukmo.entity;

import com.devchw.gukmo.entity.member.Member;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
public class Login {

    @Id @GeneratedValue
    @Column(name = "login_id")
    private Long id; //PK

    private LocalDateTime loginDate; //로그인 일자

    private String loginIp; //로그인 IP

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;  //FK
}
