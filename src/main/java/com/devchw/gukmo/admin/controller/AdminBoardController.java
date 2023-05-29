package com.devchw.gukmo.admin.controller;

import com.devchw.gukmo.admin.dto.board.NoticeFormDto;
import com.devchw.gukmo.admin.repository.AdminBoardHashtagRepository;
import com.devchw.gukmo.admin.repository.AdminBoardRepository;
import com.devchw.gukmo.admin.service.AdminBoardService;
import com.devchw.gukmo.entity.board.Notice;
import com.devchw.gukmo.exception.BaseException;
import com.devchw.gukmo.user.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.devchw.gukmo.config.response.BaseResponseStatus.NOT_FOUND_BOARD;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/boards")
public class AdminBoardController {

    private final AdminBoardService adminBoardService;
    private final AdminBoardRepository adminBoardRepository;
    private final AdminBoardHashtagRepository adminBoardHashtagRepository;

    /** 공지사항 폼 */
    @GetMapping("/notice/new")
    public String noticeForm() {
        return "board/noticeForm.tiles2";
    }

    /** 공지사항 작성 */
    @PostMapping("/notice/new")
    public String noticeNew(@ModelAttribute NoticeFormDto form, Model model) {
        //글 저장
        Long savedId = adminBoardService.saveNotice(form);
        MessageResponse messageResponse = MessageResponse.builder()
                .loc("/boards/" + savedId)
                .message("공지사항이 등록되었습니다!")
                .build();

        model.addAttribute("messageResponse", messageResponse);
        return "msg.tiles1";
    }

    /** 공지사항 수정 폼 */
    @GetMapping("/notice/edit/{id}")
    public String noticeEditForm(@PathVariable("id") Long id, Model model) {
        Notice findNotice = (Notice) adminBoardRepository.findById(id).orElseThrow(() -> new BaseException(NOT_FOUND_BOARD));
        List<String> tagNames = adminBoardHashtagRepository.findTagNamesByBoardId(id);
        NoticeFormDto noticeFormDto = new NoticeFormDto().toDto(findNotice);
        log.info("noticeFormDto={}", noticeFormDto);

        model.addAttribute("notice", noticeFormDto);
        model.addAttribute("hashtags", tagNames);

        return "board/noticeForm.tiles2";
    }

    /** 공지사항 수정 */
    @PostMapping("/notice/edit/{id}")
    public String noticeEdit(@PathVariable("id") Long id,
                             @ModelAttribute NoticeFormDto form,
                             Model model) {
        Long editedId = adminBoardService.editNotice(id, form);
        MessageResponse messageResponse = MessageResponse.builder()
                .loc("/boards/" + editedId)
                .message("공지사항 수정이 완료되었습니다!")
                .build();

        model.addAttribute("messageResponse", messageResponse);
        return "msg.tiles1";
    }
}
