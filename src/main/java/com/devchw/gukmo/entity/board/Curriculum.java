package com.devchw.gukmo.entity.board;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@DynamicInsert
@Getter
public class Curriculum extends Board {

    private String coreTechnology;			        //핵심기술
    private String academyName;			            //학원명
    private LocalDateTime curriculumStartDate;	    //과정시작일자
    private LocalDateTime curriculumEndDate;		//과정끝일자
    private LocalDateTime recruitmentStartDate;	    //모집시작일
    private LocalDateTime recruitmentEndDate;	    //모집마감일
    private int recruitsCount;			        //모집인원
    private String url;				                //신청URL
    private int recruitmentPeriod;		        //모집기간
    private int curriculumPeriod;		        //과정기간


    /** 수정 메소드 */
    public void changeCurriculumInfo(String coreTechnology,
                                     String academyName,
                                     LocalDateTime curriculumStartDate,
                                     LocalDateTime curriculumEndDate,
                                     LocalDateTime recruitmentStartDate,
                                     LocalDateTime recruitmentEndDate,
                                     int recruitsCount,
                                     String url,
                                     String content,
                                     String subject) {
        this.coreTechnology = coreTechnology;
        this.academyName = academyName;
        this.curriculumStartDate = curriculumStartDate;
        this.curriculumEndDate = curriculumEndDate;
        this.recruitmentStartDate = recruitmentStartDate;
        this.recruitmentEndDate = recruitmentEndDate;
        this.url = url;
        this.recruitsCount = recruitsCount;
        super.changeBoardInfo(subject, content);
    }
}
