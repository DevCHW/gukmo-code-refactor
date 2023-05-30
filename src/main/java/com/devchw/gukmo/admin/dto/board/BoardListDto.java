package com.devchw.gukmo.admin.dto.board;

import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardListDto {
    private Long id;
    private String firstCategory;
    private String secondCategory;
    private String subject;
    private String writeDate;

    public BoardListDto toDto(Board board) {
        return BoardListDto.builder()
                .id(board.getId())
                .firstCategory(board.getFirstCategory())
                .secondCategory(board.getSecondCategory())
                .subject(board.getSubject())
                .writeDate(DateUtil.formatLocalDateTimeToString(board.getWriteDate()))
                .build();
    }
}
