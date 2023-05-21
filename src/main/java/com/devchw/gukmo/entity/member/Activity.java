package com.devchw.gukmo.entity.member;

import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.entity.comment.Comments;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@DynamicInsert
public class Activity {

    @Id @GeneratedValue
    @Column(name = "activity_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "comments_id")
    private Comments comments;

    @Enumerated(EnumType.STRING)
    private Division division;  //활동 구분

    @ColumnDefault("sysdate")
    private LocalDateTime activityDate; //활동일자

    public enum Division {
        BOARD_WRITE, COMMENT_WRITE, BOARD_LIKE, COMMENT_LIKE
    }
}
