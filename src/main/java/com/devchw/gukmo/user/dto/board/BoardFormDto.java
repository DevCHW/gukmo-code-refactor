package com.devchw.gukmo.user.dto.board;

import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.entity.member.Member;
import lombok.Data;

@Data
public class BoardFormDto {
    private Long memberId;
    private String subject;
    private String firstCategory;
    private String secondCategory;
    private String content;
    private String hashtags;


    /**
     * Dto -> Entity
     */
    public Board toEntity(Member member) {
        return Board.builder()
                .subject(subject)
                .content(content)
                .firstCategory(firstCategory)
                .secondCategory(secondCategory)
                .member(member)
                .build();
    }
}
