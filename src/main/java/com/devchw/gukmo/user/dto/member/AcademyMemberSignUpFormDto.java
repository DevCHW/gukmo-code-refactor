package com.devchw.gukmo.user.dto.member;

import com.devchw.gukmo.entity.login.Login;
import com.devchw.gukmo.entity.member.AcademyMember;
import com.devchw.gukmo.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

import static com.devchw.gukmo.entity.member.Member.EmailAccept.*;
import static com.devchw.gukmo.entity.member.Member.UserRole.ACADEMY;
import static com.devchw.gukmo.entity.member.Member.UserRole.MEMBER;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcademyMemberSignUpFormDto {
    @NotEmpty
    private String userid;

    @NotEmpty
    private String passwd;

    @NotEmpty
    private String email;

    @NotEmpty
    private String username;

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String emailAccept;

    @NotEmpty
    private String academyName;    //교육기관명

    @NotEmpty
    private String companyNum; //사업자등록번호

    @NotEmpty
    private String tel;    //전화번호

    @NotEmpty
    private String homepage;    //홈페이지url

    /**
     * Dto -> Entity 변환
     */
    public Member toEntity() {
        Login login = Login.builder()
                .userId(userid)
                .password(passwd)
                .build();
        AcademyMember academyMember = AcademyMember.builder()
                .academyName(academyName)
                .companyNum(companyNum)
                .tel(tel)
                .homepage(homepage)
                .build();

        if(emailAccept.equals("YES")) {
            return Member.builder()
                    .nickname(nickname)
                    .username(username)
                    .email(email)
                    .emailAccept(YES)
                    .userRole(MEMBER)
                    .login(login)
                    .academyMember(academyMember)
                    .userRole(ACADEMY)
                    .build();
        } else {
            return Member.builder()
                    .nickname(nickname)
                    .username(username)
                    .email(email)
                    .emailAccept(NO)
                    .userRole(MEMBER)
                    .login(login)
                    .academyMember(academyMember)
                    .userRole(ACADEMY)
                    .build();
        }
    }
}
