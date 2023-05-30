package com.devchw.gukmo.admin.service;

import com.devchw.gukmo.admin.dto.api.report.DataTableReportFormDto;
import com.devchw.gukmo.admin.dto.api.report.ReportListDto;
import com.devchw.gukmo.admin.repository.AdminReportRepository;
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
public class ReportService {
    private final AdminReportRepository adminReportRepository;

    /** 신고내역 조회 */
    public List<ReportListDto> findAllReportList(int start, int length, MultiValueMap<String, String> formData) {
        int end = length;
        DataTableReportFormDto form = new DataTableReportFormDto().toDto(formData);
        List<Report> findReportList = adminReportRepository.findAllReportList(start, end, form);
        return findReportList.stream().map(r -> new ReportListDto().toDto(r)).collect(Collectors.toList());
    }

    /** 신고내역 총 갯수 */
    public long findAllReportListTotal(MultiValueMap<String, String> formData) {
        DataTableReportFormDto form = new DataTableReportFormDto().toDto(formData);
        return adminReportRepository.findAllReportListTotal(form);
    }

    /** 회원이 신고한 내역 조회 */
    public List<ReportListDto> findAllReportListByMemberId(int start, int length, Long memberId) {
        int end = length;
        List<Report> findReportList = adminReportRepository.findAllReportListByMemberId(start, end, memberId);
        return findReportList.stream().map(r -> new ReportListDto().toDto(r)).collect(Collectors.toList());

    }

    /** 회원이 신고한 내역 카운트 */
    public long countAllReportListByMemberId(Long id) {
        return adminReportRepository.countAllReportListByMemberId(id);
    }
}
