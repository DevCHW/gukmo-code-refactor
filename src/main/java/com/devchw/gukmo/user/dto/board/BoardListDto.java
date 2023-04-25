package com.devchw.gukmo.user.dto.board;

import com.devchw.gukmo.entity.board.Board;
import com.devchw.gukmo.entity.board.BoardHashtag;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class BoardListDto {
    private Long id;     	        //게시글번호
    private String nickname;        //작성자닉네임
    private String firstCategory;   //첫번째 카테고리
    private String secondCategory;  //두번째 카테고리
    private String subject;         //제목
    private String content;         //내용
    private LocalDateTime writeDate;       //작성일자
    private Long views;             //조회수
    private String profileImage;    //작성자 프로필이미지
    private Long writerPoint; 	//작성자 활동점수
    private int commentCount;		//댓글수
    private List<BoardHashtag> hashtags = new ArrayList<>();   //해시태그 리스트
    private int likeCount;  //좋아요 수

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
                        int commentCount,
                        List<BoardHashtag> hashtags,
                        int likeCount) {
        this.id = id;
        this.nickname = nickname;
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.subject = subject;
        this.content = content;
        this.writeDate = writeDate;
        this.views = views;
        this.profileImage = profileImage;
        this.writerPoint = writerPoint;
        this.commentCount = commentCount;
        this.hashtags = hashtags;
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
//        this.write_date = board.getCreateDate();
        this.views = board.getViews();
        this.profileImage = board.getMember().getProfileImage();
        this.commentCount = board.getComments().size();

        return this;
    }
}
