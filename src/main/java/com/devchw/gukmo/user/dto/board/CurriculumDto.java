package com.devchw.gukmo.user.dto.board;

import com.devchw.gukmo.entity.board.Curriculum;
import com.devchw.gukmo.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurriculumDto {
    private String coreTechnology;			        //핵심기술
    private String academyName;			            //학원명
    private String curriculumStartDate;	    //과정시작일자
    private String curriculumEndDate;		//과정끝일자
    private String recruitmentStartDate;	    //모집시작일
    private String recruitmentEndDate;	    //모집마감일
    private int recruitsCount;			        //모집인원
    private String url;				                //신청URL
    private int recruitmentPeriod;		        //모집기간
    private int curriculumPeriod;		        //과정기간

    public CurriculumDto toDto(Curriculum curriculum) {
        return CurriculumDto.builder()
                .coreTechnology(curriculum.getCoreTechnology())
                .academyName(curriculum.getAcademyName())
                .curriculumStartDate(DateUtil.formatLocalDateTimeToString(curriculum.getCurriculumStartDate()))
                .curriculumEndDate(DateUtil.formatLocalDateTimeToString(curriculum.getCurriculumEndDate()))
                .recruitmentStartDate(DateUtil.formatLocalDateTimeToString(curriculum.getRecruitmentStartDate()))
                .recruitmentEndDate(DateUtil.formatLocalDateTimeToString(curriculum.getRecruitmentEndDate()))
                .url(curriculum.getUrl())
                .recruitmentPeriod(curriculum.getRecruitmentPeriod())
                .curriculumPeriod(curriculum.getCurriculumPeriod())
                .build();
    }
}
