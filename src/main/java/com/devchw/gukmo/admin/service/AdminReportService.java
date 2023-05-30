package com.devchw.gukmo.admin.service;

import com.devchw.gukmo.admin.dto.api.advertisement.AdvertisementListDto;
import com.devchw.gukmo.admin.dto.api.advertisement.DataTableAdvertisementFormDto;
import com.devchw.gukmo.admin.dto.api.member.MemberListDto;
import com.devchw.gukmo.admin.dto.api.report.DataTableReportFormDto;
import com.devchw.gukmo.admin.dto.api.report.ReportListDto;
import com.devchw.gukmo.admin.repository.AdminReportRepository;
import com.devchw.gukmo.entity.advertisement.Advertisement;
import com.devchw.gukmo.entity.report.Report;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminReportService {
    private final AdminReportRepository adminReportRepository;

    public List<ReportListDto> findAllReportList(int start, int length, MultiValueMap<String, String> formData) {
        int end = length;
        DataTableReportFormDto form = new DataTableReportFormDto().toDto(formData);
        List<Report> findReportList = adminReportRepository.findAllReportList(start, end, form);
        return findReportList.stream().map(r -> new ReportListDto().toDto(r)).collect(Collectors.toList());
    }

    public long findAllReportListTotal(MultiValueMap<String, String> formData) {
        DataTableReportFormDto form = new DataTableReportFormDto().toDto(formData);
        return adminReportRepository.findAllReportListTotal(form);
    }
}
