package com.devchw.gukmo.admin.service;

import com.devchw.gukmo.admin.repository.AdminReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminReportService {
    private final AdminReportRepository adminReportRepository;
}
