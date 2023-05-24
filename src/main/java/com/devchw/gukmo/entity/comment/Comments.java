package com.devchw.gukmo.entity.comment;

import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.entity.member.Activity;
import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.entity.report.Report;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@DynamicInsert
public class Comments {
    @Id @GeneratedValue
    @Column(name = "comments_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content; //댓글내용

    @ColumnDefault("sysdate")
    private LocalDateTime writeDate; //작성일자

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NO'")
    private Blind blind; //블라인드 여부

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Comments parent; //부모댓글번호

    @OneToMany(mappedBy = "parent")
    @OnDelete(action = OnDeleteAction.CASCADE)  //DB에서 처리됨.
    private List<Comments> child = new ArrayList<>();

    @OneToMany(mappedBy = "comments")
    @OnDelete(action = OnDeleteAction.CASCADE) //DB에서 처리됨.
    private List<CommentsLike> commentsLikes = new ArrayList<>();

    @OneToMany(mappedBy = "comments")
    @OnDelete(action = OnDeleteAction.CASCADE) //DB에서 처리됨.
    private List<Activity> activities = new ArrayList<>();

    @ColumnDefault("0")
    private Long likeCount; //좋아요 개수

    @OneToMany(mappedBy = "comments")
    @OnDelete(action = OnDeleteAction.CASCADE) //DB에서 처리됨.
    private List<Report> reports = new ArrayList<>();

    public enum Blind {
        YES, NO
    }

    public void likePlus() {
        likeCount++;
    }
    public void likeMinus() {
        likeCount--;
    }

    public void changeContent(String content) {
        this.content = content;
    }
}
