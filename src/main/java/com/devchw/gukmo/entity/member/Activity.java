package com.devchw.gukmo.entity.member;

import com.devchw.gukmo.entity.board.Board;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
public class Activity {
    @Id @GeneratedValue
    @Column(name = "activity_id")
    private Long id;

    private String userid;

    private LocalDateTime activityDate;

    private String subject;

    private String detailCategory;

    private ActivityDivision division;

    private String nickname;

    private Board board;
}
