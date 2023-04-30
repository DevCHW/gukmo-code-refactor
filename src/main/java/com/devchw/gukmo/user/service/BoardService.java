package com.devchw.gukmo.user.service;

import com.devchw.gukmo.entity.board.BoardHashtag;
import com.devchw.gukmo.user.dto.board.BoardListDto;
import com.devchw.gukmo.user.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    /**
     * 게시글 번호에 맞는 해시태그 리스트 조회
     */
    public List<BoardHashtag> findBoardHashtagByBoardId(Page<BoardListDto> boards) {
        // 게시글들 id값 뽑아서 해시태그 조회하기
        List<Long> boardIds = boards.getContent().stream().map(b -> b.getId()).collect(Collectors.toList());
        return boardRepository.findBoardHashtagByBoardId(boardIds);
    }
}
