package com.devchw.gukmo.admin.api;

import com.devchw.gukmo.admin.dto.DataTableResponse;
import com.devchw.gukmo.admin.dto.api.report.ReportListDto;
import com.devchw.gukmo.admin.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/reports")
public class ReportApiController {

    private final ReportService adminReportService;

    /** 관리자 신고내역 조회 */
    @PostMapping
    public DataTableResponse reports(@RequestBody MultiValueMap<String, String> formData) {
        int draw = Integer.parseInt(formData.get("draw").get(0));
        int start = Integer.parseInt(formData.get("start").get(0));
        int length = Integer.parseInt(formData.get("length").get(0));

        List<ReportListDto> findData = adminReportService.findAllReportList(start, length, formData);
        int total = (int) adminReportService.findAllReportListTotal(formData);

        DataTableResponse data = DataTableResponse.builder()
                .draw(draw)
                .recordsFiltered(total)
                .recordsTotal(total)
                .data(findData)
                .build();
        return data;
    }

    /** 회원의 신고내역 조회 */
    @PostMapping("/members/{id}")
    public DataTableResponse memberReports(@PathVariable Long id,
                                           @RequestBody MultiValueMap<String, String> formData) {
        int draw = Integer.parseInt(formData.get("draw").get(0));
        int start = Integer.parseInt(formData.get("start").get(0));
        int length = Integer.parseInt(formData.get("length").get(0));

        List<ReportListDto> findData = adminReportService.findAllReportListByMemberId(start, length, id);
        int total = (int) adminReportService.countAllReportListByMemberId(id);
        DataTableResponse data = DataTableResponse.builder()
                .draw(draw)
                .recordsFiltered(total)
                .recordsTotal(total)
                .data(findData)
                .build();
        return data;
    }
}
