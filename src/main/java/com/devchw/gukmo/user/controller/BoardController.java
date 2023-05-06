package com.devchw.gukmo.user.controller;

import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.entity.board.BoardHashtag;
import com.devchw.gukmo.user.dto.board.BoardHashtagDto;
import com.devchw.gukmo.user.dto.board.BoardListDto;
import com.devchw.gukmo.user.dto.board.BoardRequestDto;
import com.devchw.gukmo.user.repository.BoardRepository;
import com.devchw.gukmo.user.service.BoardService;
import com.devchw.gukmo.utils.PageBarUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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

    /** 게시글 리스트 조회(페이징) */
    @GetMapping()
    public String boards(@ModelAttribute BoardRequestDto boardRequest,
                         Pageable pageable,
                         Model model) {

        Page<BoardListDto> boards = boardRepository.findBoardList(boardRequest, pageable);
        List<BoardHashtagDto> hashtags = boardService.findBoardHashtagByBoardId(boards)
                .stream().map(BoardHashtagDto::toDto).collect(Collectors.toList());

        // 페이지 바 만들기
        String queryString = createQueryString(boardRequest);
        String url = "/boards";
        String pageBar = PageBarUtil.createPageBar(pageable.getPageNumber(), boards.getTotalPages(), url, queryString);

        model.addAttribute("boards", boards.getContent());  //결과물
        model.addAttribute("total", boards.getTotalElements()); //총갯수
        model.addAttribute("hashtags", hashtags);   //해시태그
        model.addAttribute("boardRequest", boardRequest);
        model.addAttribute("pageBar", pageBar); //페이지 바
        return "board/communityList.tiles1";
    }

    /** queryString 만들기 */
    private String createQueryString(BoardRequestDto boardRequest) {
        StringBuffer queryStringBuffer = new StringBuffer().append("firstCategory="+ boardRequest.getFirstCategory()).append("&");
        if(StringUtils.hasText(boardRequest.getSecondCategory())) {
            queryStringBuffer.append("secondCategory="+ boardRequest.getSecondCategory()).append("&");
        }
        if(StringUtils.hasText(boardRequest.getKeyword())) {
            queryStringBuffer.append("keyword="+ boardRequest.getKeyword()).append("&");
        }
        if(StringUtils.hasText(boardRequest.getSort())) {
            queryStringBuffer.append("sort="+ boardRequest.getSort());
        }
        String queryString = queryStringBuffer.toString();
        return queryString;
    }

    /** 게시글 단건 조회 */
    @GetMapping("/{id}")
    public String board(@PathVariable Long id, Model model) {
        log.info("게시글 단건 조회 요청");
        Optional<Board> board = boardRepository.findById(id);
        log.info("조회된 게시글 정보 ={}", board);
        return "board/board.tiles1";
    }
}
