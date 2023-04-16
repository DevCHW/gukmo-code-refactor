package com.devchw.gukmo.entity.login;

import com.devchw.gukmo.entity.member.Member;
import lombok.*;

import javax.persistence.*;

/**
 * 로그인 엔티티
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Login {

    @Id @GeneratedValue
    private Long id; //PK

    private String userId; //유저아이디

    private String password; //비밀번호

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private Status status; //상태 {REST, SUSPENSION, ACTIVE, WAIT}

    private enum Status {
        REST, SUSPENSION, ACTIVE, WAIT
    }
}
