package com.devchw.gukmo.user.repository.custom;

import com.devchw.gukmo.entity.hashtag.BoardHashtag;
import com.devchw.gukmo.user.dto.board.get.BoardListDto;
import com.devchw.gukmo.user.dto.board.get.BoardRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardRepositoryCustom {
    Page<BoardListDto> findBoardList(BoardRequestDto request, Pageable pageable);


}
