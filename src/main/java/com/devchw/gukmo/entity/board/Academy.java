package com.devchw.gukmo.entity.board;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Entity;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@DynamicInsert
@Getter
public class Academy extends Board {
    private String representativeName; //대표자 이름
    private String address; //주소
    private String phone; //전화번호
    private String homepage;     //홈페이지URL
    private String academyImage;    //학원 이미지
}
