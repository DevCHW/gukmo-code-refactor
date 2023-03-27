package com.devchw.gukmo.config;

import com.devchw.gukmo.interceptor.AdminCheckInterceptor;
import com.devchw.gukmo.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())    //로그인체크 인터셉터 등록
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/index", "/", "/login", "/logout", "/resources/**", "/*.ico", "/error");

        registry.addInterceptor(new AdminCheckInterceptor())    //관리자체크 인터셉터 등록
                .order(2)
                .addPathPatterns("/admin/**");
    }
}
