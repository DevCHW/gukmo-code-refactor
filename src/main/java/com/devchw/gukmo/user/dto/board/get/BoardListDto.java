package com.devchw.gukmo.user.dto.board.get;

import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.utils.MyUtil;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardListDto {
    private Long id;     	        //게시글번호
    private String nickname;        //작성자닉네임
    private String firstCategory;   //첫번째 카테고리
    private String secondCategory;  //두번째 카테고리
    private String subject;         //제목
    private String content;         //내용
    private String writeDate;       //작성일자
    private Long views;             //조회수
    private String profileImage;    //작성자 프로필이미지
    private Long writerPoint; 	    //작성자 활동점수
    private Long commentCount;		//댓글수
    private Long likeCount;         //좋아요 수

    @QueryProjection
    public BoardListDto(Long id,
                        String nickname,
                        String firstCategory,
                        String secondCategory,
                        String subject,
                        String content,
                        LocalDateTime writeDate,
                        Long views,
                        String profileImage,
                        Long writerPoint,
                        Long commentCount,
                        Long likeCount) {
        this.id = id;
        this.nickname = nickname;
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.subject = subject;
        this.content = content;
        this.writeDate = MyUtil.calculateTime(writeDate);
        this.views = views;
        this.profileImage = profileImage;
        this.writerPoint = writerPoint;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
    }

    /**
     * Entity -> Dto
     */
    public BoardListDto toDto(Board board) {
        this.id = board.getId();
        this.nickname = board.getMember().getNickname();
        this.firstCategory = board.getFirstCategory();
        this.secondCategory = board.getSecondCategory();
        this.subject = board.getSubject();
        this.content = board.getContent();
        this.writeDate = MyUtil.calculateTime(board.getWriteDate());
        this.views = board.getViews();
        this.profileImage = board.getMember().getProfileImage();
        this.commentCount = board.getCommentCount();
        this.likeCount = board.getLikeCount();

        return this;
    }
}
