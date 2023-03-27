package com.devchw.gukmo.entity;

import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.entity.board.Comment;
import com.devchw.gukmo.entity.member.Member;
import lombok.Getter;

import javax.persistence.*;

@Getter
public class Like {
    @Id @GeneratedValue
    @Column(name = "like_id")
    private Long id;

    private String type;    //BOARD, COMMENT

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    private Comment comment;
}
