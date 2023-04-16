package com.devchw.gukmo.entity.member;

import lombok.*;

import javax.persistence.*;

/**
 * 교육기관 회원 엔티티
 * Member 테이블에서 기본키를 내려받는다(1:1 식별관계)
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class AcademyMember {

    @Id
    private Long id;

    @MapsId //Member.member_id와 매핑
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String academy_name;    //교육기관명

    private String company_num; //사업자등록번호

    private int tel;    //전화번호

    private String homepage;    //홈페이지url
}
