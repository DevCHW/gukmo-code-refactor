package com.devchw.gukmo.admin.repository.custom;

import com.devchw.gukmo.entity.board.Board;

import java.util.List;

public interface AdminBoardRepositoryCustom {
    Long countByWriteBoardCountMax();

    List<Board> findAllByMemberId(Long id, int start, int end);
}
