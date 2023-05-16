package com.devchw.gukmo.user.repository;

import com.devchw.gukmo.entity.board.BoardLike;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

    boolean existsByBoardIdAndMemberId(Long id, Long memberId);
    BoardLike findByBoardIdAndMemberId(Long boardId, Long memberId);
}
