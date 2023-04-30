package com.devchw.gukmo.user.controller;

import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.entity.board.BoardHashtag;
import com.devchw.gukmo.user.dto.board.BoardHashtagDto;
import com.devchw.gukmo.user.dto.board.BoardListDto;
import com.devchw.gukmo.user.dto.board.BoardRequestDto;
import com.devchw.gukmo.user.repository.BoardRepository;
import com.devchw.gukmo.user.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;
    private final BoardRepository boardRepository;

    /**
     * 게시글 리스트 조회(페이징)
     */
    @GetMapping()
    public String boards(@ModelAttribute BoardRequestDto boardRequest,
                         Pageable pageable,
                         Model model) {
        Page<BoardListDto> boards = boardRepository.findBoardList(boardRequest, pageable);
        log.info("게시판 리스트 조회, 게시글 수 ={} 정보 ={}", boards.getTotalElements(), boards.getContent());
        List<BoardHashtagDto> hashtags = boardService.findBoardHashtagByBoardId(boards)
                .stream().map(BoardHashtagDto::toDto).collect(Collectors.toList());

        model.addAttribute("boards", boards.getContent());
        model.addAttribute("total", boards.getTotalPages());
        model.addAttribute("hashtags", hashtags);
        model.addAttribute("boardRequest", boardRequest);
        return "board/communityList.tiles1";
    }

    /**
     * 게시글 단건 조회
     */
    @GetMapping("/{id}")
    public String board(@PathVariable Long id, Model model) {
        log.info("게시글 단건 조회 요청");
        Optional<Board> board = boardRepository.findById(id);
        log.info("조회된 게시글 정보 ={}", board);
        return "board/board.tiles1";
    }
}
