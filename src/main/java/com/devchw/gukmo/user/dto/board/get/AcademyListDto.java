package com.devchw.gukmo.user.dto.board.get;

import com.devchw.gukmo.utils.DateUtil;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AcademyListDto {
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
    private String representativeName; //대표자 이름
    private String address; //주소
    private String phone; //전화번호
    private String homepage;     //홈페이지URL
    private String academyImage;    //학원 이미지

    @QueryProjection
    public AcademyListDto(Long id,
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
                          Long likeCount,
                          String representativeName,
                          String address,
                          String phone,
                          String homepage,
                          String academyImage) {
        this.id = id;
        this.nickname = nickname;
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.subject = subject;
        this.content = content;
        this.writeDate = DateUtil.calculateTime(writeDate);
        this.views = views;
        this.profileImage = profileImage;
        this.writerPoint = writerPoint;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.representativeName = representativeName;
        this.address = address;
        this.phone = phone;
        this.homepage = homepage;
        this.academyImage = academyImage;
    }
}
