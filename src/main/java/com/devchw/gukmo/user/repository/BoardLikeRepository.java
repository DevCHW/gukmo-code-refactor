package com.devchw.gukmo.user.repository;

import com.devchw.gukmo.entity.board.BoardLike;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    boolean existsByBoardIdAndMemberId(Long boardId, Long memberId);

    @EntityGraph(attributePaths = {"member", "board"})
    Optional<BoardLike> findByBoardIdAndMemberId(Long boardId, Long memberId);
}
