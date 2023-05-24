package com.devchw.gukmo.user.controller;

import com.devchw.gukmo.entity.advertisement.Advertisement;
import com.devchw.gukmo.entity.board.Academy;
import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.entity.board.Curriculum;
import com.devchw.gukmo.entity.member.AcademyMember;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.exception.BaseException;
import com.devchw.gukmo.user.dto.MessageResponse;
import com.devchw.gukmo.user.dto.advertisement.AdvertisementDto;
import com.devchw.gukmo.user.dto.board.*;
import com.devchw.gukmo.user.dto.board.AcademyFormDto;
import com.devchw.gukmo.user.dto.board.CommunityFormDto;
import com.devchw.gukmo.user.dto.login.LoginMemberDto;
import com.devchw.gukmo.user.dto.member.AcademyMemberDto;
import com.devchw.gukmo.user.repository.BoardHashtagRepository;
import com.devchw.gukmo.user.repository.BoardRepository;
import com.devchw.gukmo.user.repository.MemberRepository;
import com.devchw.gukmo.user.service.AdvertisementService;
import com.devchw.gukmo.user.service.BoardService;
import com.devchw.gukmo.utils.PageBarUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.devchw.gukmo.config.SessionConst.LOGIN_MEMBER;
import static com.devchw.gukmo.config.response.BaseResponseStatus.NOT_FOUND_BOARD;
import static com.devchw.gukmo.config.response.BaseResponseStatus.NOT_FOUND_MEMBER;
import static com.devchw.gukmo.entity.advertisement.Advertisement.Type.*;
import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;
    private final BoardRepository boardRepository;
    private final BoardHashtagRepository boardHashtagRepository;
    private final MemberRepository memberRepository;
    private final AdvertisementService advertisementService;

    /** 게시글 리스트 조회(페이징) */
    @GetMapping()
    public String boards(@ModelAttribute BoardRequestDto boardRequest,
                         Pageable pageable,
                         Model model) {

        List<Long> boardIds = new ArrayList<>();
        int totalPage = 0;
        int totalElements = 0;
        if(!hasText(boardRequest.getFirstCategory()) || boardRequest.getFirstCategory().equals("커뮤니티")) { //커뮤니티
            Page<CommunityListDto> boards = boardRepository.findCommunityList(boardRequest, pageable);
            boardIds = boards.getContent().stream().map(b -> b.getId()).collect(Collectors.toList());
            totalPage = boards.getTotalPages();
            totalElements = (int) boards.getTotalElements();
            model.addAttribute("boards", boards.getContent());  //결과물
        } else if(boardRequest.getFirstCategory().equals("공지사항")) { //공지사항
            Page<NoticeListDto> boards = boardRepository.findNoticeList(boardRequest, pageable);
            boardIds = boards.getContent().stream().map(b -> b.getId()).collect(Collectors.toList());
            totalPage = boards.getTotalPages();
            totalElements = (int) boards.getTotalElements();
            model.addAttribute("boards", boards.getContent());  //결과물
        } else if(boardRequest.getFirstCategory().equals("국비학원")) {
            if(hasText(boardRequest.getSecondCategory()) && boardRequest.getSecondCategory().equals("교육과정")) {   //국비학원-교육과정
                Page<CurriculumListDto> boards = boardRepository.findCurriculumList(boardRequest, pageable);
                boardIds = boards.getContent().stream().map(b -> b.getId()).collect(Collectors.toList());
                totalPage = boards.getTotalPages();
                totalElements = (int) boards.getTotalElements();
                model.addAttribute("boards", boards.getContent());  //결과물
            } else {    //국비학원
                Page<AcademyListDto> boards = boardRepository.findAcademyList(boardRequest, pageable);
                boardIds = boards.getContent().stream().map(b -> b.getId()).collect(Collectors.toList());
                totalPage = boards.getTotalPages();
                totalElements = (int) boards.getTotalElements();
                model.addAttribute("boards", boards.getContent());  //결과물
            }
        }

        List<BoardHashtagDto> hashtags = boardService.findBoardHashtagByBoardId(boardIds).stream()
                .map(BoardHashtagDto::toDto).collect(Collectors.toList());

        // 페이지 바 만들기
        String queryString = createQueryString(boardRequest);
        String url = "/boards";
        String pageBar = PageBarUtil.createPageBar(pageable.getPageNumber(), totalPage, url, queryString);

        model.addAttribute("total", totalElements); //총 갯수
        model.addAttribute("hashtags", hashtags);   //해시태그
        model.addAttribute("boardRequest", boardRequest);
        model.addAttribute("pageBar", pageBar); //페이지 바

        // 카테고리에 따라 return(보여지는 페이지) 달라야 함.

        if(!hasText(boardRequest.getFirstCategory()) || boardRequest.getFirstCategory().equals("커뮤니티")) { //커뮤니티
            return "board/community/communityList.tiles1";
        } else if(boardRequest.getFirstCategory().equals("공지사항")) { //공지사항
            return "board/notice/noticeList.tiles1";
        } else if(boardRequest.getFirstCategory().equals("국비학원")) {
            if(hasText(boardRequest.getSecondCategory()) && boardRequest.getSecondCategory().equals("교육과정")) {   //국비학원-교육과정
                return "board/academy/curriculumList.tiles1";
            } else {    //국비학원
                return "board/academy/academyList.tiles1";
            }
        }
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
    public String board(@PathVariable Long id, HttpSession session, Model model, HttpServletRequest request, HttpServletResponse response) {
        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("boardView")) {
                    oldCookie = cookie;
                }
            }
        }

        //조회 수 증가
        viewCountUp(id, response, oldCookie);

        // 조건에 맞는 게시물 페이지 광고 5개 조회
        LocalDateTime currentDateTime = LocalDateTime.now();
        Set<Advertisement> advertisements = advertisementService.findAdvertisement(currentDateTime, BOARD);
        List<AdvertisementDto> advertisementList = advertisements.stream().map(a -> new AdvertisementDto().toDto(a)).collect(Collectors.toList());

        log.info("조회된 광고 5개={}", advertisementList);
        model.addAttribute("advertisementList", advertisementList);

        //게시글 조회
        log.info("게시글 단건 조회 요청");
        BoardDto boardDto = boardService.findBoardById(id, session);

        if(boardDto.getSecondCategory().equals("국비학원")) {
            Academy findAcademy = (Academy) boardRepository.findById(id).orElseThrow(() -> new BaseException(NOT_FOUND_BOARD));
            AcademyDto academy = new AcademyDto().toDto(findAcademy);
            model.addAttribute("academy", academy);
        } else if(boardDto.getSecondCategory().equals("교육과정")) {
            Curriculum findCurriculum = (Curriculum) boardRepository.findById(id).orElseThrow(() -> new BaseException(NOT_FOUND_BOARD));
            CurriculumDto curriculum = new CurriculumDto().toDto(findCurriculum);
            model.addAttribute("curriculum", curriculum);
        }

        model.addAttribute("board", boardDto);
        return "board/boardDetail.tiles1";
    }

    /** 게시물 조회 수 증가 */
    private void viewCountUp(Long id, HttpServletResponse response, Cookie oldCookie) {
        //게시물 조회 수 증가
        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("[" + id.toString() + "]")) {
                boardService.viewCountUp(id);
                oldCookie.setValue(oldCookie.getValue() + "_[" + id + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(oldCookie);
            }
        } else {
            boardService.viewCountUp(id);
            Cookie newCookie = new Cookie("boardView","[" + id + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
        }
    }

    /** 커뮤니티 작성 폼 */
    @GetMapping("/community/new")
    public String boardForm(@ModelAttribute CommunityFormDto form) {
        return "board/community/communityForm.tiles1";
    }


    /** 국비학원 작성 폼 */
    @GetMapping("/academy/new")
    public String academyForm(HttpSession session, Model model) {
        LoginMemberDto loginMemberDto = (LoginMemberDto) session.getAttribute(LOGIN_MEMBER);
        if(loginMemberDto.getUserRole().equals(Member.UserRole.ACADEMY)) {
            Long memberId = loginMemberDto.getId();
            AcademyMember findAcademyMember = memberRepository.findAcademyMemberById(memberId).orElseThrow(() -> new BaseException(NOT_FOUND_MEMBER)).getAcademyMember();
            AcademyMemberDto academyMember = new AcademyMemberDto().toDto(findAcademyMember);
            log.info("조회한 데이터={}", academyMember);

            model.addAttribute("academyMember", academyMember);
        }
        return "board/academy/academyForm.tiles1";
    }

    /** 교육과정 작성 폼 */
    @GetMapping("/academy/curriculum/new")
    public String curriculumForm(HttpSession session, Model model) {
        LoginMemberDto loginMemberDto = (LoginMemberDto) session.getAttribute(LOGIN_MEMBER);
        if(loginMemberDto.getUserRole().equals(Member.UserRole.ACADEMY)) {
            Long memberId = loginMemberDto.getId();
            AcademyMember findAcademyMember = memberRepository.findAcademyMemberById(memberId).orElseThrow(() -> new BaseException(NOT_FOUND_MEMBER)).getAcademyMember();
            AcademyMemberDto academyMember = new AcademyMemberDto().toDto(findAcademyMember);
            log.info("조회한 데이터={}", academyMember);

            model.addAttribute("academyMember", academyMember);
        }
        return "board/academy/curriculumForm.tiles1";
    }

    /** 커뮤니티 작성 */
    @PostMapping("/community/new")
    public String communityNew(@ModelAttribute CommunityFormDto form, Model model) {
        //글 저장
        Long savedId = boardService.saveCommunity(form);

        MessageResponse messageResponse = MessageResponse.builder()
                .loc("/boards/" + savedId)
                .message("글작성 성공!")
                .build();

        model.addAttribute("messageResponse", messageResponse);
        return "msg.tiles1";
    }

    /** 국비학원 작성 */
    @PostMapping("/academy/curriculum/new")
    public String curriculumNew(@ModelAttribute CurriculumFormDto form, Model model) {
        log.info("CurriculumFormDto={}", form);

        //글 저장
        Long savedId = boardService.saveCurriculum(form);
        MessageResponse messageResponse = MessageResponse.builder()
                .loc("/boards/" + savedId)
                .message("글작성 성공!")
                .build();

        model.addAttribute("messageResponse", messageResponse);
        return "msg.tiles1";
    }

    /** 교육과정 작성 */
    @PostMapping("/academy/new")
    public String academyNew(@ModelAttribute AcademyFormDto form, Model model) {
        log.info("AcademyFormDto={}", form);

        //글 저장
        Long savedId = boardService.saveAcademy(form);
        MessageResponse messageResponse = MessageResponse.builder()
                .loc("/boards/" + savedId)
                .message("글작성 성공!")
                .build();

        model.addAttribute("messageResponse", messageResponse);
        return "msg.tiles1";
    }

    /** 커뮤니티 수정 폼 */
    @GetMapping("/community/edit/{id}")
    public String communityEditForm(@PathVariable("id") Long id, Model model) {
        Board findBoard = boardRepository.findById(id).orElseThrow(() -> new BaseException(NOT_FOUND_BOARD));
        List<String> tagNames = boardHashtagRepository.findTagNamesByBoardId(id);
        CommunityFormDto communityFormDto = new CommunityFormDto().toDto(findBoard);

        log.info("communityEditForm={}", communityFormDto);
        model.addAttribute("board", communityFormDto);
        model.addAttribute("hashtags", tagNames);
        return "board/community/communityForm.tiles1";
    }

    /** 국비학원 수정 폼 */
    @GetMapping("/academy/edit/{id}")
    public String academyEditForm(@PathVariable("id") Long id, Model model) {
        Academy findAcademy = (Academy) boardRepository.findById(id).orElseThrow(() -> new BaseException(NOT_FOUND_BOARD));
        List<String> tagNames = boardHashtagRepository.findTagNamesByBoardId(id);

        AcademyEditFormResponse academy = new AcademyEditFormResponse().toDto(findAcademy, tagNames);

        log.info("수정할 게시물={}", academy);
        model.addAttribute("academy", academy);
        return "board/academy/academyForm.tiles1";
    }

    /** 교육과정 수정 폼 */
    @GetMapping("/academy/curriculum/edit/{id}")
    public String curriculumEditForm(@PathVariable("id") Long id,
                                     Model model) {
        Curriculum findCurriculum = (Curriculum) boardRepository.findById(id).orElseThrow(() -> new BaseException(NOT_FOUND_BOARD));
        List<String> tagNames = boardHashtagRepository.findTagNamesByBoardId(id);
        CurriculumFormDto curriculum = new CurriculumFormDto().toDto(findCurriculum);
        log.info("수정할 게시물={}", curriculum);
        model.addAttribute("curriculum", curriculum);
        model.addAttribute("hashtags", tagNames);
        return "board/academy/curriculumForm.tiles1";
    }

    /** 커뮤니티 수정 */
    @PostMapping("/community/edit/{id}")
    public String communityEdit(@PathVariable("id") Long id,
                                @ModelAttribute CommunityFormDto form,
                                Model model) {

        Long editedId = boardService.editCommunity(id, form);
        MessageResponse messageResponse = MessageResponse.builder()
                .loc("/boards/" + editedId)
                .message("게시글 수정이 완료되었습니다!")
                .build();

        model.addAttribute("messageResponse", messageResponse);
        return "msg.tiles1";
    }

    /** 국비학원 수정 */
    @PostMapping("/academy/edit/{id}")
    public String academyEdit(@PathVariable("id") Long id,
                              @ModelAttribute AcademyFormDto form,
                              Model model) {
        log.info("AcademyFormDto={}", form);
        //글 저장
        Long editedId = boardService.editAcademy(id, form);
        MessageResponse messageResponse = MessageResponse.builder()
                .loc("/boards/" + editedId)
                .message("게시글 수정이 완료되었습니다!")
                .build();

        model.addAttribute("messageResponse", messageResponse);
        return "msg.tiles1";
    }

    /** 교육과정 수정 */
    @PostMapping("/academy/curriculum/edit/{id}")
    public String academyEdit(@PathVariable("id") Long id,
                              @ModelAttribute CurriculumFormDto form,
                              Model model) {
        log.info("CurriculumFormDto={}", form);
        //글 저장
        Long editedId = boardService.editCurriculum(id, form);
        MessageResponse messageResponse = MessageResponse.builder()
                .loc("/boards/" + editedId)
                .message("게시글 수정이 완료되었습니다!")
                .build();

        model.addAttribute("messageResponse", messageResponse);
        return "msg.tiles1";
    }
}
