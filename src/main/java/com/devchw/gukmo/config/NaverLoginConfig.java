package com.devchw.gukmo.config;

import com.github.scribejava.core.builder.api.DefaultApi20;

public class NaverLoginConfig extends DefaultApi20 {
    protected NaverLoginConfig(){
    }
    private static class InstanceHolder{
        private static final NaverLoginConfig INSTANCE = new NaverLoginConfig();
    }
    public static NaverLoginConfig instance(){
        return InstanceHolder.INSTANCE;
    }
    @Override
    public String getAccessTokenEndpoint() {
        return "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code";
    }
    @Override
    protected String getAuthorizationBaseUrl() {
        return "https://nid.naver.com/oauth2.0/authorize";
    }
}
