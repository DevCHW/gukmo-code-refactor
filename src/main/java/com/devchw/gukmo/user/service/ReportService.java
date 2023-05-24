package com.devchw.gukmo.user.service;

import com.devchw.gukmo.config.response.BaseResponseStatus;
import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.entity.comment.Comments;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.entity.report.Report;
import com.devchw.gukmo.exception.BaseException;
import com.devchw.gukmo.user.dto.api.report.ReportRequest;
import com.devchw.gukmo.user.repository.BoardRepository;
import com.devchw.gukmo.user.repository.CommentsRepository;
import com.devchw.gukmo.user.repository.MemberRepository;
import com.devchw.gukmo.user.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.devchw.gukmo.config.response.BaseResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {
    private final ReportRepository reportRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CommentsRepository commentsRepository;

    /** 게시물 신고 저장 */
    @Transactional
    public Long saveBoardReport(ReportRequest form) {
        Board reportBoard = boardRepository.findById(form.getBoardId()).orElseThrow(() -> new BaseException(NOT_FOUND_BOARD));
        Member reportMember = memberRepository.findById(form.getReporterId()).orElseThrow(() -> new BaseException(NOT_FOUND_MEMBER));
        Report report = form.toEntity(reportBoard, reportMember);
        Report savedReport = reportRepository.save(report);
        return savedReport.getId();
    }

    /** 댓글 신고 저장 */
    @Transactional
    public Long saveCommentReport(ReportRequest form) {
        Comments reportComment = commentsRepository.findById(form.getCommentId()).orElseThrow(() -> new BaseException(NOT_FOUND_COMMENT));
        Member reportMember = memberRepository.findById(form.getReporterId()).orElseThrow(() -> new BaseException(NOT_FOUND_MEMBER));
        Report report = form.toEntity(reportComment, reportMember);
        Report savedReport = reportRepository.save(report);
        return savedReport.getId();
    }

    /** 게시물 신고 존재여부 조회 */
    public Boolean boardReportExist(Long memberId, Long boardId) {
        return reportRepository.existsByMemberIdAndBoardId(memberId, boardId);
    }

    /** 댓글 신고 존재여부 조회 */
    public Boolean commentsReportExist(Long memberId, Long commentId) {
        return reportRepository.existsByMemberIdAndCommentsId(memberId, commentId);
    }
}
