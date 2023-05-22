package com.devchw.gukmo.user.dto.board;

import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.user.dto.member.WriterDto;
import com.devchw.gukmo.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndexBoardDto {

    private Long id;    //글번호
    private WriterDto writer;   //작성자
    private Long views;     //조회수
    private Long commentCount;  //댓글수
    private Long likeCount; //좋아요 수
    private String subject; //제목
    private String writeDate;   //작성일자

    public IndexBoardDto toDto(Board board) {

        WriterDto writerDto = WriterDto.toWriterDto(board.getMember());

        return IndexBoardDto.builder()
                .id(board.getId())
                .writer(writerDto)
                .commentCount(board.getCommentCount())
                .likeCount(board.getLikeCount())
                .subject(board.getSubject())
                .writeDate(DateUtil.calculateTime(board.getWriteDate()))
                .views(board.getViews())
                .build();
    }
}
