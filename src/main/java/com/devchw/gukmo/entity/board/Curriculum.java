package com.devchw.gukmo.entity.board;

import lombok.*;

import javax.persistence.Entity;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Curriculum extends Board {

    private String coreTechnology;			//핵심기술
    private String academyName;			    //학원명
    private String curriculumStartDate;	    //과정시작일자
    private String curriculumEndDate;		//과정끝일자
    private String recruitmentStartDate;	//모집시작일
    private String recruitmentEndDate;	    //모집마감일
    private String recruitsCount;			//모집인원
    private String url;				        //신청URL
    private String recruitmentPeriod;		//모집기간
    private String curriculumPeriod;		//과정기간
}
