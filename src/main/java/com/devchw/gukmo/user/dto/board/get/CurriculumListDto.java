package com.devchw.gukmo.user.dto.board.get;

import com.devchw.gukmo.utils.DateUtil;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CurriculumListDto {

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
    private String coreTechnology;			//핵심기술
    private String academyName;			    //학원명
    private LocalDateTime curriculumStartDate;	    //과정시작일자
    private LocalDateTime curriculumEndDate;		//과정끝일자
    private LocalDateTime recruitmentStartDate;	//모집시작일
    private LocalDateTime recruitmentEndDate;	    //모집마감일
    private int recruitsCount;			//모집인원
    private String url;				    //신청URL
    private int recruitmentPeriod;		//모집기간
    private int curriculumPeriod;		//과정기간


    @QueryProjection
    public CurriculumListDto(Long id, String nickname, String firstCategory, String secondCategory,
                             String subject, String content, LocalDateTime writeDate, Long views, String profileImage, Long writerPoint, Long commentCount, Long likeCount, String coreTechnology, String academyName, LocalDateTime curriculumStartDate, LocalDateTime curriculumEndDate, LocalDateTime recruitmentStartDate, LocalDateTime recruitmentEndDate, int recruitsCount, String url, int recruitmentPeriod, int curriculumPeriod) {
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
        this.coreTechnology = coreTechnology;
        this.academyName = academyName;
        this.curriculumStartDate = curriculumStartDate;
        this.curriculumEndDate = curriculumEndDate;
        this.recruitmentStartDate = recruitmentStartDate;
        this.recruitmentEndDate = recruitmentEndDate;
        this.recruitsCount = recruitsCount;
        this.url = url;
        this.recruitmentPeriod = recruitmentPeriod;
        this.curriculumPeriod = curriculumPeriod;
    }

    public int getDday() {
        return Integer.parseInt(DateUtil.getDaysFromNow(recruitmentEndDate));
    }
}
