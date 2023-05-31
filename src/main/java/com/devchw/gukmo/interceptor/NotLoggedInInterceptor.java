package com.devchw.gukmo.interceptor;

import com.devchw.gukmo.config.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

@Slf4j
public class NotLoggedInInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        if(session != null && session.getAttribute(SessionConst.LOGIN_MEMBER) != null) {
            log.info("이미 인증된 사용자 요청");
            //로그인중이라면 메인페이지로 redirect
            response.sendRedirect("/");
            return false;
        }

        return true;
    }
}
