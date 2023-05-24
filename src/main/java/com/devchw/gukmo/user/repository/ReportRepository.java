package com.devchw.gukmo.user.repository;

import com.devchw.gukmo.entity.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

    Boolean existsByMemberIdAndBoardId(Long memberId, Long boardId);

    Boolean existsByMemberIdAndCommentsId(Long memberId, Long commentsId);
}
