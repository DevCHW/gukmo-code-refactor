package com.devchw.gukmo.user.repository.custom;

import com.devchw.gukmo.entity.board.BoardHashtag;
import com.devchw.gukmo.user.dto.board.BoardListDto;
import com.devchw.gukmo.user.dto.board.BoardRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardRepositoryCustom {
    Page<BoardListDto> findBoardList(BoardRequestDto request, Pageable pageable);

    List<BoardHashtag> findBoardHashtagByBoardId(List<Long> boardIdList);
}
