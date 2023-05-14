package com.devchw.gukmo.user.repository;

import com.devchw.gukmo.entity.board.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

    boolean existsByBoardIdAndMemberId(Long id, Long memberId);

    Long deleteByBoardIdAndMemberId(Long boardId, Long memberId);
}
