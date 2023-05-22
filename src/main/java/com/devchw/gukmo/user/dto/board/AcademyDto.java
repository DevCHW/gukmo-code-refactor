package com.devchw.gukmo.user.dto.board;

import com.devchw.gukmo.entity.board.Academy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcademyDto {
    private String representativeName; //대표자 이름
    private String address; //주소
    private String phone; //전화번호
    private String homepage;     //홈페이지URL
    private String academyImage;    //학원 이미지

    public AcademyDto toDto(Academy academy) {
        return AcademyDto.builder()
                .representativeName(academy.getRepresentativeName())
                .address(academy.getAddress())
                .phone(academy.getPhone())
                .homepage(academy.getHomepage())
                .academyImage(academy.getAcademyImage())
                .build();
    }
}
