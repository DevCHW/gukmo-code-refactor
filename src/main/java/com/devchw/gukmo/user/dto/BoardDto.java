package com.devchw.gukmo.user.dto;

import com.devchw.gukmo.entity.board.Board;
import lombok.Data;

@Data
public class BoardDto {


    public BoardDto toDto(Board board) {

        return this;
    }
}
