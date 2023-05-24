package com.devchw.gukmo.entity.report;

import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.entity.comment.Comments;
import com.devchw.gukmo.entity.member.Member;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@DynamicInsert  //insert 시 명시해주지 않아도 기본값 적용되도록 하기.
public class Report {
    @Id @GeneratedValue
    @Column(name = "report_id")
    private Long id;

    private String simpleReason;

    @Lob
    private String detailReason;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "report_member_id")
    private Member reportMember;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "reported_member_id")
    private Member reportedMember;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "comments_id")
    private Comments comments;

    private LocalDateTime reportDate;

    private ReportType type; //BOARD, COMMENT
    public enum ReportType {
        BOARD, COMMENT
    }
}

