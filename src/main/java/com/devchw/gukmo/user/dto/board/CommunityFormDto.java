package com.devchw.gukmo.user.dto.board;

import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommunityFormDto {
    private Long id;
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

    /**
     * Entity -> Dto
     */
    public CommunityFormDto toDto(Board board) {
        return CommunityFormDto.builder()
                .id(board.getId())
                .subject(board.getSubject())
                .content(board.getContent())
                .firstCategory(board.getFirstCategory())
                .build();

    }
}
