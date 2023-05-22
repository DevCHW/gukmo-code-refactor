package com.devchw.gukmo.entity.board;

import com.devchw.gukmo.entity.member.Member;
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

    /** 정보 수정 */
    public void changeAcademyInfo(String representativeName,
                                  String address,
                                  String phone,
                                  String homepage,
                                  String academyImage,
                                  String subject,
                                  String content) {
        this.representativeName = representativeName;
        this.address = address;
        this.phone = phone;
        this.homepage = homepage;
        this.academyImage = academyImage;
        super.changeBoardInfo(subject, content);
    }

    public void changeAcademyInfo(String representativeName,
                                  String address,
                                  String phone,
                                  String homepage,
                                  String subject,
                                  String content) {
        this.representativeName = representativeName;
        this.address = address;
        this.phone = phone;
        this.homepage = homepage;
        super.changeBoardInfo(subject, content);
    }
}
