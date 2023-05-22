package com.devchw.gukmo.user.dto.board;

public interface PrevAndNextBoardDto {
    Long getPreviousId();    //이전글번호
    String getPreviousSubject();   //이전글제목
    Long getNextId();    //다음글번호
    String getNextSubject();    //다음글제목
}
