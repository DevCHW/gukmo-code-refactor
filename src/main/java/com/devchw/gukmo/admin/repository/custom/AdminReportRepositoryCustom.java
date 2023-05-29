package com.devchw.gukmo.admin.repository.custom;

import com.devchw.gukmo.admin.dto.api.report.DataTableReportFormDto;
import com.devchw.gukmo.entity.report.Report;

import java.util.List;

public interface AdminReportRepositoryCustom {
    List<Report> findAllReportList(int start, int end, DataTableReportFormDto form);

    long findAllReportListTotal(DataTableReportFormDto form);
}
