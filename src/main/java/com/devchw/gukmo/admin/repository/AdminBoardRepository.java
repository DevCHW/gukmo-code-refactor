package com.devchw.gukmo.admin.repository;

import com.devchw.gukmo.admin.repository.custom.AdminBoardRepositoryCustom;
import com.devchw.gukmo.entity.board.Board;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminBoardRepository extends JpaRepository<Board, Long>, AdminBoardRepositoryCustom {

    @EntityGraph(attributePaths = {"member"})
    List<Board> findTop3ByFirstCategoryAndWriteDateBetweenOrderByViewsDesc(String firstCategory, LocalDateTime startDatetime, LocalDateTime endDatetime);

    Long countByWriteDateBetween(LocalDateTime startDatetime, LocalDateTime endDatetime);
}
