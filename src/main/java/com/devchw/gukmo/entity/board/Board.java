package com.devchw.gukmo.entity.board;

import com.devchw.gukmo.entity.comment.Comments;
import com.devchw.gukmo.entity.hashtag.BoardHashtag;
import com.devchw.gukmo.entity.member.Activity;
import com.devchw.gukmo.entity.member.Member;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
@DynamicInsert
@Inheritance(strategy = InheritanceType.JOINED)
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member; //작성자

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<Comments> comments = new ArrayList<>(); //댓글

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<BoardHashtag> boardHashtags = new ArrayList<>(); //해시태그

    private String subject; //글제목

    @Lob
    private String content; //글내용

    private String firstCategory; //첫번째 카테고리

    private String secondCategory; //두번째 카테고리

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<BoardLike> boardLikes; //좋아요

    @ColumnDefault("0")
    private Long views; //조회수

    @ColumnDefault("0")
    private Long commentCount; //댓글 수

    @ColumnDefault("0")
    private Long likeCount; //좋아요 수

    @ColumnDefault("sysdate")
    private LocalDateTime writeDate;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Activity> activities = new ArrayList<>();

    /** 추천 수 증가 */
    public void likePlus() {
        likeCount++;
    }

    /** 추천 수 감소 */
    public void likeMinus() {
        likeCount--;
    }

    /** 댓글 수 증가 */
    public void commentPlus() {
        commentCount++;
    }

    /** 조회 수 증가 */
    public void views() {
        views++;
    }
}
