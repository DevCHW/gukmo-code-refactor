package com.devchw.gukmo.entity.comment;

import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.entity.member.Member;
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

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id")
    @OnDelete(action = OnDeleteAction.CASCADE)  //DB에서 처리됨.
    private Board board;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)  //DB에서 처리됨.
    private Member member;

    private String content; //댓글내용

    @ColumnDefault("sysdate")
    private LocalDateTime writeDate; //작성일자

    @Enumerated(EnumType.STRING)
    private Blind blind; //블라인드 여부

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    @OnDelete(action = OnDeleteAction.CASCADE)  //DB에서 처리됨.
    private Comments parent; //부모댓글번호

    @OneToMany(mappedBy = "parent")
    private List<Comments> child = new ArrayList<>();

    @ColumnDefault("0")
    private Long likeCount; //좋아요 개수

    public enum Blind {
        YES, NO
    }
}