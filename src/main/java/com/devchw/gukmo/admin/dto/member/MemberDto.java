package com.devchw.gukmo.admin.dto.member;

import com.devchw.gukmo.entity.member.Member;
import com.devchw.gukmo.entity.member.Member.EmailAccept;
import com.devchw.gukmo.entity.member.Member.Status;
import com.devchw.gukmo.entity.member.Member.UserRole;
import com.devchw.gukmo.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private String userId;
    private String profileImage;
    private String nickname;
    private String username;
    private String email;
    private Long point;
    private EmailAccept emailAccept;
    private Status status;
    private String joinDate;
    private UserRole userRole;
    private String academyName;
    private String companyNum;
    private String tel;
    private String homepage;

    /**
     * Entity -> Dto
     */
    public MemberDto toDto(Member member) {
        String userId = null;
        String academyName = null;
        String companyNum = null;
        String tel = null;
        String homepage = null;
        if(member.getLogin() != null) userId = member.getLogin().getUserId();
        if(member.getAcademyMember() != null) {
            academyName = member.getAcademyMember().getAcademyName();
            companyNum = member.getAcademyMember().getCompanyNum();
            tel = member.getAcademyMember().getTel();
            homepage = member.getAcademyMember().getHomepage();
        }

        return MemberDto.builder()
                .id(member.getId())
                .profileImage(member.getProfileImage())
                .nickname(member.getNickname())
                .username(member.getUsername())
                .userId(userId)
                .email(member.getEmail())
                .point(member.getPoint())
                .emailAccept(member.getEmailAccept())
                .status(member.getStatus())
                .academyName(academyName)
                .companyNum(companyNum)
                .tel(tel)
                .homepage(homepage)
                .joinDate(DateUtil.formatLocalDateTimeToString(member.getJoinDate()))
                .userRole(member.getUserRole())
                .build();
    }
}
