package com.devchw.gukmo.config.key;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ApiKey {
    @Value("${kakao.login.javascript_key}")
    private String kakaoLoginJavascriptKey;

    @Value("${google.login.client}")
    private String googleLoginClient;

    @Value("${facebook.login.appId}")
    private String facebookLoginAppId;
}
