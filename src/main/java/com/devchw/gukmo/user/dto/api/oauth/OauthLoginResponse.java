package com.devchw.gukmo.user.dto.api.oauth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OauthLoginResponse {
    private String redirectURL;
}
