package com.devchw.gukmo.user.dto.board;

import com.devchw.gukmo.entity.board.BoardHashtag;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BoardHashtagDto {
    Long boardId;
    String hashtag;

    public static BoardHashtagDto toDto(BoardHashtag boardHashtag) {
        return BoardHashtagDto.builder()
                .boardId(boardHashtag.getBoard().getId())
                .hashtag(boardHashtag.getHashtag().getTagName())
                .build();
    }
}
