package com.devchw.gukmo.entity.login;

import com.devchw.gukmo.entity.member.Member;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

/**
 * 로그인 엔티티
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@DynamicInsert
public class Login {

    @Id @GeneratedValue
    private Long id; //PK

    private String userId; //유저아이디

    private String password; //비밀번호

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'ACTIVE'")
    private Status status; //상태 {REST, SUSPENSION, ACTIVE, WAIT}

    public enum Status {
        REST, SUSPENSION, ACTIVE, WAIT
    }

    /** 비밀번호 수정 */
    public void changePassword(String password) {
        this.password = password;
    }
}
