package com.devchw.gukmo.user.dto.member;

import com.devchw.gukmo.entity.member.Member;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdateInfoRequest {

    private Long id;

    private MultipartFile profileImage;

    private String username;

    @NotEmpty
    private String emailAccept;

    @NotEmpty
    private String nickname;
}
