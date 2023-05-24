package com.devchw.gukmo.user.dto.api.report;

import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.entity.comment.Comments;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.entity.report.Report;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.devchw.gukmo.entity.report.Report.ReportType.BOARD;
import static com.devchw.gukmo.entity.report.Report.ReportType.COMMENT;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequest {
    private Long boardId;
    private Long commentId;
    private Long reporterId;
    private String simpleReason;
    private String detailReason;


    public Report toEntity(Board board, Member member) {
        return Report.builder()
                .board(board)
                .simpleReason(simpleReason)
                .detailReason(detailReason)
                .member(member)
                .type(BOARD)
                .build();
    }

    public Report toEntity(Comments comments, Member member) {
        return Report.builder()
                .comments(comments)
                .simpleReason(simpleReason)
                .detailReason(detailReason)
                .member(member)
                .type(COMMENT)
                .build();
    }
}
