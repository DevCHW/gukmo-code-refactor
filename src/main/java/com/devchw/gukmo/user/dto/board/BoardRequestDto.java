package com.devchw.gukmo.user.dto.board;

import lombok.Data;

@Data
public class BoardRequestDto {
    String firstCategory;   //상위 카테고리
    String secondCategory;  //하위 카테고리
    String keyword;         //검색어
    String sort;            //정렬조건
}
