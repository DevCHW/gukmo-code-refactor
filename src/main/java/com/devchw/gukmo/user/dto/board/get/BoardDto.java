package com.devchw.gukmo.user.dto.board.get;

import com.devchw.gukmo.user.dto.comments.CommentsDto;
import com.devchw.gukmo.user.dto.member.WriterDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class BoardDto {
    private Long id;    //게시글번호
    private String subject; //글제목
    private String content; //글내용
    private String firstCategory;   //첫번째 카테고리
    private String secondCategory;  //하위 카테고리
    private boolean likeExist;         //좋아요 여부
    private Long commentCount;       //댓글개수
    private Long likeCount;          //좋아요개수
    private Long views;         // 조회수
    private String writeDate;   //작성일자
    private List<CommentsDto> comments; //댓글목록
    private List<String> hashtags; //해시태그들
    private PrevAndNextBoardDto prevAndNextBoardDto;    //이전글,다음글 Dto
    private WriterDto writer;   //작성자 정보
}
