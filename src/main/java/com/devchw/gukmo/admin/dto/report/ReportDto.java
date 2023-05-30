package com.devchw.gukmo.admin.dto.report;

import com.devchw.gukmo.entity.report.Report;
import com.devchw.gukmo.entity.report.Report.ReportType;
import com.devchw.gukmo.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDto {
    private Long id;
    private ReportType reportType;
    private Long boardId;
    private Long commentId;
    private String nickname;
    private String simpleReason;
    private String detailReason;
    private String reportDate;

    /**
     * Entity -> Dto
     */
    public ReportDto toDto(Report report) {
        Long boardId = report.getBoard() != null ? report.getBoard().getId() : null;
        Long commentId = report.getComments() != null ? report.getComments().getId() : null;
        return ReportDto.builder()
                .id(report.getId())
                .reportType(report.getType())
                .boardId(boardId)
                .commentId(commentId)
                .nickname(report.getMember().getNickname())
                .simpleReason(report.getSimpleReason())
                .detailReason(report.getDetailReason())
                .reportDate(DateUtil.formatLocalDateTimeToString(report.getReportDate()))
                .build();
    }
}
