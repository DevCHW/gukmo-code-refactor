package com.devchw.gukmo.user.dto.member;

import com.devchw.gukmo.entity.member.AcademyMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcademyMemberDto {
    private Long id;
    private String academyName;    //교육기관명
    private String companyNum; //사업자등록번호
    private String tel;    //전화번호
    private String homepage;    //홈페이지url

    public AcademyMemberDto toDto(AcademyMember academyMember) {
        return AcademyMemberDto.builder()
                .id(academyMember.getId())
                .academyName(academyMember.getAcademyName())
                .companyNum(academyMember.getCompanyNum())
                .tel(academyMember.getTel())
                .homepage(academyMember.getHomepage())
                .build();
    }
}
