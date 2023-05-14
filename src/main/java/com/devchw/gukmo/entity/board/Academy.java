package com.devchw.gukmo.entity.board;

import lombok.*;

import javax.persistence.Entity;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Academy extends Board {
    private String representativeName; //대표자 이름
    private String address; //주소
    private String phone; //전화번호
    private String jurisdiction; //
    private String homepage;
    private String academyImage;
}
