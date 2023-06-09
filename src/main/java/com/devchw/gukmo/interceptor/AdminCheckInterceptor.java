package com.devchw.gukmo.interceptor;

import com.devchw.gukmo.config.SessionConst;
import com.devchw.gukmo.user.dto.login.LoginMemberDto;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        LoginMemberDto loginMember = (LoginMemberDto)session.getAttribute(SessionConst.LOGIN_MEMBER);
        String userRole = String.valueOf(loginMember.getUserRole());

        if(!userRole.equals("ADMIN")) {    //관리자가 아니라면 홈으로 redirect.
            response.sendRedirect("/");
            return false;
        }
        return true;
    }
}
