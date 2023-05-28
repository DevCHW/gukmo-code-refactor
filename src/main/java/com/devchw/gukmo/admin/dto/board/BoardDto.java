package com.devchw.gukmo.admin.dto.board;

import com.devchw.gukmo.entity.board.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private Long id;
    private String subject;
    private String nickname;
    private String firstCategory;
    private String secondCategory;
    private Long likeCount;
    private Long views;
    private Long commentCount;


    public BoardDto toDto(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .subject(board.getSubject())
                .nickname(board.getMember().getNickname())
                .firstCategory(board.getFirstCategory())
                .secondCategory(board.getSecondCategory())
                .likeCount(board.getLikeCount())
                .views(board.getViews())
                .commentCount(board.getCommentCount())
                .build();
    }
}
