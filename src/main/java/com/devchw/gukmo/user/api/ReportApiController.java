package com.devchw.gukmo.user.api;

import com.devchw.gukmo.config.response.BaseResponse;
import com.devchw.gukmo.exception.BaseException;
import com.devchw.gukmo.user.dto.api.report.ReportRequest;
import com.devchw.gukmo.user.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.devchw.gukmo.config.response.BaseResponseStatus.INTERNAL_SERVER_ERROR;
import static com.devchw.gukmo.config.response.BaseResponseStatus.SUCCESS;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(("/api/v1/report"))
public class ReportApiController {

    private final ReportService reportService;

    /** 게시물 신고 */
    @PostMapping("/boards")
    public BaseResponse<String> boardReport(ReportRequest form) {
        Long savedId = reportService.saveBoardReport(form);

        if(savedId != null) {
            return new BaseResponse<>(SUCCESS);
        }
        else {
            throw new BaseException(INTERNAL_SERVER_ERROR);
        }
    }

    /** 댓글 신고 */
    @PostMapping("/comments")
    public BaseResponse<String> commentReport(ReportRequest form) {
        Long savedId = reportService.saveCommentReport(form);

        if(savedId != null) {
            return new BaseResponse<>(SUCCESS);
        } else {
            throw new BaseException(INTERNAL_SERVER_ERROR);
        }
    }

    /** 해당 게시물 신고한 적 있는지 조회 */
    @GetMapping("/boards/exist")
    public BaseResponse<Boolean> boardReportExist(@RequestParam Long memberId,
                                                 @RequestParam Long boardId) {
        Boolean exist = reportService.boardReportExist(memberId, boardId);
        return new BaseResponse<>(exist);
    }

    /** 해당 댓글 신고한 적 있는지 조회 */
    @GetMapping("/comments/exist")
    public BaseResponse<Boolean> commentReportExist(@RequestParam Long memberId,
                                                 @RequestParam Long commentId) {
        Boolean exist = reportService.commentsReportExist(memberId, commentId);
        return new BaseResponse<>(exist);
    }
}
