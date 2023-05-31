package com.devchw.gukmo.user.dto.board;

import com.devchw.gukmo.entity.board.Academy;
import com.devchw.gukmo.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcademyFormDto {
    private Long memberId;
    private String subject;
    private String content;
    @Builder.Default
    private String firstCategory = "국비학원";
    @Builder.Default
    private String secondCategory = "국비학원";
    private String hashtags;
    private String address;
    private String representativeName;
    private String phone;
    private String originAcademyImage;
    private MultipartFile academyImage;
    private String homepage;

    public Academy toEntity(Member writer, String fileName) {
        return Academy.builder()
                .subject(subject)
                .content(content)
                .firstCategory(firstCategory)
                .secondCategory(secondCategory)
                .address(address)
                .representativeName(representativeName)
                .phone(phone)
                .academyImage(fileName)
                .homepage(homepage)
                .member(writer)
                .build();
    }
}
