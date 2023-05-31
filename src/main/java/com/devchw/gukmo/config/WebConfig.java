package com.devchw.gukmo.config;

import com.devchw.gukmo.interceptor.AdminCheckInterceptor;
import com.devchw.gukmo.interceptor.LoginCheckInterceptor;
import com.devchw.gukmo.interceptor.NotLoggedInInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())    //로그인체크 인터셉터 등록
                .order(1)
                .addPathPatterns("/**") //로그인체크 할 url 패턴 등록
                .excludePathPatterns("/", "/login", "/members/signUp/**", "/members/tos")
                .excludePathPatterns("/oauth/**", "/api/v1/oauth/**") //소셜로그인
                .excludePathPatterns("/boards", "/boards/*") //게시판 리스트, 게시판 상세보기
                .excludePathPatterns("/api/v1/recaptcha/**") //recaptcha
                .excludePathPatterns("/resources/**"); //자원들

        registry.addInterceptor(new AdminCheckInterceptor())    //관리자체크 인터셉터 등록
                .order(2)
                .addPathPatterns("/admin/**", "/api/v1/admin/**");

        registry.addInterceptor(new NotLoggedInInterceptor())    //로그인중이 아니어야 하는 URL 등록
                .order(3)
                .addPathPatterns("/login", "/members/signUp/**", "/members/tos");
    }
}
