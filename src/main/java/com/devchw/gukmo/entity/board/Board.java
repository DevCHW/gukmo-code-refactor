package com.devchw.gukmo.entity.board;

import com.devchw.gukmo.entity.member.Member;
import lombok.*;
import org.hibernate.annotations.BatchSize;
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
@Builder
@Getter
@DynamicInsert
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn // 하위 테이블의 구분 컬럼 생성(default = DTYPE)
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member; //작성자

    @OneToMany(mappedBy = "board")
    private List<Comments> comments = new ArrayList<>(); //댓글

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<BoardHashtag> boardHashtags = new ArrayList<>(); //해시태그

    private String subject; //글제목

    private String content; //글내용

    private String firstCategory; //첫번째 카테고리

    private String secondCategory; //두번째 카테고리

    @OneToMany(mappedBy = "board")
    private List<BoardLike> boardLikes; //좋아요

    @ColumnDefault("0")
    private Long views; //조회수

    @ColumnDefault("sysdate")
    private LocalDateTime writeDate;

    private int getCommentCount(List<Comments> comments) {
        return comments.size();
    }
}
