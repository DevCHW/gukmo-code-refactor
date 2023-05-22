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
public class IndexCurriculumListDto {
    private Long id;
    private String academyName;
    private String subject;
    private String curriculumStartDate;
    private String curriculumEndDate;
    private String day;


    public IndexCurriculumListDto toDto(Curriculum curriculum) {
        return IndexCurriculumListDto.builder()
                .id(curriculum.getId())
                .academyName(curriculum.getAcademyName())
                .subject(curriculum.getSubject())
                .curriculumStartDate(DateUtil.formatLocalDateTimeToString(curriculum.getCurriculumStartDate()))
                .curriculumEndDate(DateUtil.formatLocalDateTimeToString(curriculum.getCurriculumStartDate()))
                .day(DateUtil.getDaysFromNow(curriculum.getRecruitmentEndDate()))
                .build();
    }
}
