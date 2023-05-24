package com.devchw.gukmo.user.dto.board;

import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.user.dto.comments.CommentsDto;
import com.devchw.gukmo.user.dto.member.WriterDto;
import com.devchw.gukmo.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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


    public BoardDto toDto(Long id, boolean likeExist, Board board, PrevAndNextBoardDto prevAndNextBoardDto, List<String> hashtags, List<CommentsDto> commentsDtoList) {
        return BoardDto.builder()
                .id(id)
                .subject(board.getSubject())
                .content(board.getContent())
                .firstCategory(board.getFirstCategory())
                .secondCategory(board.getSecondCategory())
                .likeExist(likeExist)
                .commentCount(board.getCommentCount())
                .likeCount(board.getLikeCount())
                .views(board.getViews())
                .writeDate(DateUtil.calculateTime(board.getWriteDate()))
                .comments(commentsDtoList)
                .hashtags(hashtags)
                .prevAndNextBoardDto(prevAndNextBoardDto)
                .writer(WriterDto.toWriterDto(board.getMember()))
                .build();
    }
}
