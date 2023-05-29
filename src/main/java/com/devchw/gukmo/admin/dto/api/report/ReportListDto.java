package com.devchw.gukmo.admin.dto.api.report;

import com.devchw.gukmo.config.response.BaseResponseStatus;
import com.devchw.gukmo.entity.report.Report;
import com.devchw.gukmo.exception.BaseException;
import com.devchw.gukmo.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.devchw.gukmo.entity.report.Report.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportListDto {
    private Long id;
    private ReportType reportType;
    private String nickname;
    private String boardId;
    private String commentsId;
    private String simpleReason;
    private String reportDate;

    public ReportListDto toDto(Report report) {
        if(report.getBoard() != null && report.getComments() != null) {
            return ReportListDto.builder()
                    .id(report.getId())
                    .reportType(report.getType())
                    .nickname(report.getMember().getNickname())
                    .boardId(String.valueOf(report.getBoard().getId()))
                    .commentsId(String.valueOf(report.getComments().getId()))
                    .simpleReason(report.getSimpleReason())
                    .reportDate(DateUtil.formatLocalDateTimeToString(report.getReportDate()))
                    .build();
        } else if(report.getBoard() != null && report.getComments() == null) {
            return ReportListDto.builder()
                    .id(report.getId())
                    .reportType(report.getType())
                    .nickname(report.getMember().getNickname())
                    .boardId(String.valueOf(report.getBoard().getId()))
                    .commentsId("X")
                    .simpleReason(report.getSimpleReason())
                    .reportDate(DateUtil.formatLocalDateTimeToString(report.getReportDate()))
                    .build();
        } else if (report.getBoard() == null && report.getComments() != null) {
            return ReportListDto.builder()
                    .id(report.getId())
                    .reportType(report.getType())
                    .nickname(report.getMember().getNickname())
                    .boardId("X")
                    .commentsId(String.valueOf(report.getComments().getId()))
                    .simpleReason(report.getSimpleReason())
                    .reportDate(DateUtil.formatLocalDateTimeToString(report.getReportDate()))
                    .build();
        }
        throw new BaseException(BaseResponseStatus.INTERNAL_SERVER_ERROR);
    }
}
