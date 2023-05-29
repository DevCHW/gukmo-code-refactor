package com.devchw.gukmo.admin.controller;

import com.devchw.gukmo.admin.service.AdminReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/reports")
public class AdminReportController {

    private final AdminReportService adminReportService;
    @GetMapping
    public String reportList(Model model) {
        return "report/reportList.tiles2";
    }
}
