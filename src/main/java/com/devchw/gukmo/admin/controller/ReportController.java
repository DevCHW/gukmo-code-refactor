package com.devchw.gukmo.admin.controller;

import com.devchw.gukmo.admin.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/reports")
public class ReportController {

    private final ReportService adminReportService;

    /** 관리자 신고내역 조회 */
    @GetMapping
    public String reportList(Model model) {
        return "report/reportList.tiles2";
    }

    /** 관리자 신고내역 상세조회 */
    @GetMapping("/{id}")
    public String reportDetail(@PathVariable Long id) {
        return "report/reportDetail.tiles2";
    }
}
