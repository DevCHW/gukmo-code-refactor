package com.devchw.gukmo.admin.dto.api.report;

import com.devchw.gukmo.admin.dto.api.member.DataTableMemberFormDto;
import com.devchw.gukmo.entity.advertisement.Advertisement;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.entity.report.Report;
import com.devchw.gukmo.entity.report.Report.ReportType;
import com.devchw.gukmo.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;

import static com.devchw.gukmo.entity.report.Report.*;
import static org.springframework.util.StringUtils.hasText;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataTableReportFormDto {
    private String id;
    private ReportType reportType;
    private String nickname;
    private String boardId;
    private String commentsId;
    private String simpleReason;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String sort;
    private String direction;

    public DataTableReportFormDto toDto(MultiValueMap<String, String> formData) {
        String id = formData.get("columns[0][search][value]").get(0);
        String strReportType = formData.get("columns[1][search][value]").get(0);
        String nickname = formData.get("columns[2][search][value]").get(0);
        String boardId = formData.get("columns[3][search][value]").get(0);
        String commentsId = formData.get("columns[4][search][value]").get(0);
        String simpleReason = formData.get("columns[5][search][value]").get(0);
        String reportDate = formData.get("columns[6][search][value]").get(0);
        String sort = formData.get("order[0][column]").get(0);
        String direction = formData.get("order[0][dir]").get(0);
        System.out.println("======================================");
        System.out.println("id = " + id);
        System.out.println("strReportType = " + strReportType);
        System.out.println("nickname = " + nickname);
        System.out.println("boardId = " + boardId);
        System.out.println("commentsId = " + commentsId);
        System.out.println("simpleReason = " + simpleReason);
        System.out.println("reportDate = " + reportDate);
        System.out.println("sort = " + sort);
        System.out.println("direction = " + direction);
        System.out.println("======================================");
        switch (sort) {
            case "0":
                sort = "id";
                break;
            case "1":
                sort = "reportType";
                break;
            case "2":
                sort = "nickname";
                break;
            case "3":
                sort = "boardId";
                break;
            case "4":
                sort = "commentsId";
                break;
            case "5":
                sort = "simpleReason";
                break;
            case "6":
                sort = "reportDate";
                break;
            default:
                sort = "reportDate";
                break;
        }

        String startDate = null;
        String endDate = null;
        if(reportDate != null && !reportDate.trim().isEmpty()) {
            String[] startAndEnd = reportDate.split(",");
            startDate = startAndEnd[0];
            endDate = startAndEnd[1];
        }

        ReportType reportType = null;
        if("BOARD".equals(strReportType)) {
            reportType = ReportType.BOARD;
        } else if("COMMENT".equals(strReportType)) {
            reportType = ReportType.COMMENT;
        }

        if(hasText(reportDate)) {
            if(hasText(startDate) && !hasText(endDate)) {
                return DataTableReportFormDto.builder()
                        .id(id)
                        .reportType(reportType)
                        .nickname(nickname)
                        .boardId(boardId)
                        .commentsId(commentsId)
                        .simpleReason(simpleReason)
                        .startDate(DateUtil.stringToLocalDateTimeConverter(startDate))
                        .sort(sort)
                        .direction(direction)
                        .build();
            } else if(!hasText(startDate) && hasText(endDate)) {
                return DataTableReportFormDto.builder()
                        .id(id)
                        .reportType(reportType)
                        .nickname(nickname)
                        .boardId(boardId)
                        .commentsId(commentsId)
                        .simpleReason(simpleReason)
                        .endDate(DateUtil.stringToLocalDateTimeConverter(endDate).plusDays(1))
                        .sort(sort)
                        .direction(direction)
                        .build();
            } else if(!hasText(startDate) && !hasText(endDate)) {
                return DataTableReportFormDto.builder()
                        .id(id)
                        .reportType(reportType)
                        .nickname(nickname)
                        .boardId(boardId)
                        .commentsId(commentsId)
                        .simpleReason(simpleReason)
                        .sort(sort)
                        .direction(direction)
                        .build();
            } else {
                return DataTableReportFormDto.builder()
                        .id(id)
                        .reportType(reportType)
                        .nickname(nickname)
                        .boardId(boardId)
                        .commentsId(commentsId)
                        .simpleReason(simpleReason)
                        .startDate(DateUtil.stringToLocalDateTimeConverter(startDate))
                        .endDate(DateUtil.stringToLocalDateTimeConverter(endDate).plusDays(1))
                        .sort(sort)
                        .direction(direction)
                        .build();
            }
        } else {
            return DataTableReportFormDto.builder()
                    .id(id)
                    .reportType(reportType)
                    .nickname(nickname)
                    .boardId(boardId)
                    .commentsId(commentsId)
                    .simpleReason(simpleReason)
                    .sort(sort)
                    .direction(direction)
                    .build();
        }
    }
}
