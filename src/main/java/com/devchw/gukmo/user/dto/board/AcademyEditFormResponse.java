package com.devchw.gukmo.user.dto.board;

import com.devchw.gukmo.entity.board.Academy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcademyEditFormResponse {
    private Long id;    //게시글번호
    private String subject; //글제목
    private String content; //글내용
    private String firstCategory;   //첫번째 카테고리
    private String secondCategory;  //하위 카테고리
    private List<String> hashtags; //해시태그들
    private String representativeName; //대표자 이름
    private String address; //주소
    private String phone; //전화번호
    private String homepage;     //홈페이지URL
    private String academyImage;    //학원 이미지

    public AcademyEditFormResponse toDto(Academy academy, List<String> hashtags) {
        return AcademyEditFormResponse.builder()
                .id(academy.getId())
                .subject(academy.getSubject())
                .content(academy.getContent())
                .firstCategory(academy.getFirstCategory())
                .secondCategory(academy.getSecondCategory())
                .hashtags(hashtags)
                .representativeName(academy.getRepresentativeName())
                .address(academy.getAddress())
                .phone(academy.getPhone())
                .homepage(academy.getHomepage())
                .academyImage(academy.getAcademyImage())
                .build();
    }
}
