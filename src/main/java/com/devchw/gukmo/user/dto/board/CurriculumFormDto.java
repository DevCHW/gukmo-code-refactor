package com.devchw.gukmo.user.dto.board;

import com.devchw.gukmo.entity.board.Curriculum;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.devchw.gukmo.utils.DateUtil.*;
import static com.devchw.gukmo.utils.DateUtil.stringToLocalDateTimeConverter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurriculumFormDto {
    private Long id;
    private Long memberId;
    private String subject;
    private String content;
    @Builder.Default
    private String firstCategory = "국비학원";
    @Builder.Default
    private String secondCategory = "교육과정";
    private String coreTechnology;			        //핵심기술
    private String academyName;			            //학원명
    private String curriculumStartDate;	            //과정시작일자
    private String curriculumEndDate;		        //과정끝일자
    private String recruitmentStartDate;	        //모집시작일
    private String recruitmentEndDate;	            //모집마감일
    private int recruitsCount;			            //모집인원
    private String url;				                //신청URL
    private String hashtags;                        //해시태그

    /**
     * Dto -> Entity
     */
    public Curriculum toEntity(Member writerMember) {
        LocalDateTime curriculumStartDate = stringToLocalDateTimeConverter(this.curriculumStartDate);
        LocalDateTime curriculumEndDate = stringToLocalDateTimeConverter(this.curriculumEndDate);
        LocalDateTime recruitmentStartDate = stringToLocalDateTimeConverter(this.recruitmentStartDate);
        LocalDateTime recruitmentEndDate = stringToLocalDateTimeConverter(this.recruitmentEndDate);
        return Curriculum.builder()
                .subject(subject)
                .content(content)
                .firstCategory(firstCategory)
                .secondCategory(secondCategory)
                .coreTechnology(coreTechnology)
                .academyName(academyName)
                .curriculumStartDate(curriculumStartDate)
                .curriculumEndDate(curriculumEndDate)
                .recruitmentStartDate(recruitmentStartDate)
                .recruitmentEndDate(recruitmentEndDate)
                .recruitsCount(recruitsCount)
                .url(url)
                .recruitmentPeriod(calculateDateGap(recruitmentStartDate, recruitmentEndDate))
                .curriculumPeriod(calculateDateGap(curriculumStartDate, recruitmentEndDate))
                .member(writerMember)
                .build();
    }

    /**
     * Entity -> Dto
     */
    public CurriculumFormDto toDto(Curriculum curriculum) {
        return CurriculumFormDto.builder()
                .id(curriculum.getId())
                .subject(curriculum.getSubject())
                .content(curriculum.getContent())
                .firstCategory(curriculum.getFirstCategory())
                .secondCategory(curriculum.getSecondCategory())
                .coreTechnology(curriculum.getCoreTechnology())
                .academyName(curriculum.getAcademyName())
                .curriculumStartDate(DateUtil.formatLocalDateTimeToString(curriculum.getCurriculumStartDate()))
                .curriculumEndDate(DateUtil.formatLocalDateTimeToString(curriculum.getCurriculumEndDate()))
                .recruitmentStartDate(DateUtil.formatLocalDateTimeToString(curriculum.getRecruitmentStartDate()))
                .recruitmentEndDate(DateUtil.formatLocalDateTimeToString(curriculum.getRecruitmentEndDate()))
                .recruitsCount(curriculum.getRecruitsCount())
                .url(curriculum.getUrl())
                .build();
    }
}
