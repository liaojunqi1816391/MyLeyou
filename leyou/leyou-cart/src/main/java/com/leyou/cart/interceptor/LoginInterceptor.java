package com.leyou.cart.interceptor;

import com.leyou.auth.entity.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.cart.config.JwtProperties;
import com.leyou.common.utils.CookieUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    private JwtProperties jwtProperties;
    // 定义一个线程域，存放登录用户
    private static final ThreadLocal<UserInfo> tl = new ThreadLocal<>();

    public LoginInterceptor(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 查询 token
        String token = CookieUtils.getCookieValue(request,jwtProperties.getCookieName());
        if(StringUtils.isBlank(token)){
            // 未登录，返回 401
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        try {
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
            // 将用户信息放入线程域
            tl.set(userInfo);
            return true;
        } catch (Exception e) {
            // 抛出异常，证明未登录，并返回401
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return true;
        }
    }

    public static UserInfo getLoginUser(){
        return tl.get();
    }
}
