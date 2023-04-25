package com.devchw.gukmo.user.repository.custom;

import com.devchw.gukmo.user.dto.board.BoardListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomBoardRepository {
    Page<BoardListDto> findBoardList(List<String> categories, String keyword, String sort, Pageable pageable);
}
