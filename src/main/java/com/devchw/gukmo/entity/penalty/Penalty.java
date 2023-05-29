package com.devchw.gukmo.entity.penalty;

import com.devchw.gukmo.entity.member.Member;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Penalty {
    @Id @GeneratedValue
    @Column(name = "penalty_id")
    private Long id; //PK

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String simpleReason; //간단사유

    private String detailReason; //상세사유

    private int period; //정지기간
}
