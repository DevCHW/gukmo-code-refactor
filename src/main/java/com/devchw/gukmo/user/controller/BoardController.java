package com.devchw.gukmo.user.controller;

import com.devchw.gukmo.user.dto.MessageResponse;
import com.devchw.gukmo.user.dto.board.get.BoardDto;
import com.devchw.gukmo.user.dto.board.get.BoardHashtagDto;
import com.devchw.gukmo.user.dto.board.get.BoardListDto;
import com.devchw.gukmo.user.dto.board.get.BoardRequestDto;
import com.devchw.gukmo.user.dto.board.post.BoardFormDto;
import com.devchw.gukmo.user.repository.BoardRepository;
import com.devchw.gukmo.user.service.BoardService;
import com.devchw.gukmo.utils.PageBarUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.hasText;

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
        List<BoardHashtagDto> hashtags = boardService.findBoardHashtagByBoardId(boards).stream()
                .map(BoardHashtagDto::toDto).collect(Collectors.toList());

        // 페이지 바 만들기
        String queryString = createQueryString(boardRequest);
        String url = "/boards";
        String pageBar = PageBarUtil.createPageBar(pageable.getPageNumber(), boards.getTotalPages(), url, queryString);

        model.addAttribute("boards", boards.getContent());  //결과물
        model.addAttribute("total", boards.getTotalElements()); //총갯수
        model.addAttribute("hashtags", hashtags);   //해시태그
        model.addAttribute("boardRequest", boardRequest);
        model.addAttribute("pageBar", pageBar); //페이지 바

        // 카테고리에 따라 return(보여지는 페이지) 달라야 함.
        return "board/community/communityList.tiles1";
    }


    /** queryString 만들기 */
    private String createQueryString(BoardRequestDto boardRequest) {
        StringBuffer queryStringBuffer = new StringBuffer().append("firstCategory="+ boardRequest.getFirstCategory()).append("&");
        if(hasText(boardRequest.getSecondCategory())) {
            queryStringBuffer.append("secondCategory="+ boardRequest.getSecondCategory()).append("&");
        }
        if(hasText(boardRequest.getKeyword())) {
            queryStringBuffer.append("keyword="+ boardRequest.getKeyword()).append("&");
        }
        if(hasText(boardRequest.getSort())) {
            queryStringBuffer.append("sort="+ boardRequest.getSort());
        }
        return queryStringBuffer.toString();
    }


    /** 게시글 단건 조회 */
    @GetMapping("/{id}")
    public String board(@PathVariable Long id, HttpSession session, Model model) {
        log.info("게시글 단건 조회 요청");
        BoardDto boardDto = boardService.findBoardById(id, session);

        model.addAttribute("board", boardDto);
        return "board/boardDetail.tiles1";
    }


    /** 커뮤니티 작성 폼 페이지 */
    @GetMapping("/community/new")
    public String boardForm() {
        return "board/community/communityForm.tiles1";
    }


    /** 커뮤니티 글작성 */
    @PostMapping("/community/new")
    public String boardForm(@ModelAttribute BoardFormDto form, Model model) {
        //글 저장
        Long savedId = boardService.save(form);

        MessageResponse messageResponse = MessageResponse.builder()
                .loc("/boards/" + savedId)
                .message("글작성 성공!")
                .build();

        model.addAttribute("messageResponse", messageResponse);
        return "msg.tiles1";
    }
}
