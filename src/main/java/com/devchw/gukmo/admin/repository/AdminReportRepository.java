package com.devchw.gukmo.admin.repository;

import com.devchw.gukmo.admin.repository.custom.AdminReportRepositoryCustom;
import com.devchw.gukmo.entity.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminReportRepository extends JpaRepository<Report, Long>, AdminReportRepositoryCustom {
}
