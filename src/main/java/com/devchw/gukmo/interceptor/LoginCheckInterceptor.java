package com.devchw.gukmo.interceptor;

import com.devchw.gukmo.config.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String queryString = request.getQueryString();

        if(queryString != null) { // GET 방식일 경우
            requestURI += "?" + queryString;
        }

        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            log.info("미인증 사용자 요청");
            //로그인 중이 아니라면 로그인 페이지로 redirect.
            response.sendRedirect("/login?redirectURL=" + URLEncoder.encode(requestURI));
            return false;
        }
        return true;
    }
}
