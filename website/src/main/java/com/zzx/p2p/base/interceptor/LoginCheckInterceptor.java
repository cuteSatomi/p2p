package com.zzx.p2p.base.interceptor;

import com.zzx.p2p.base.annotation.RequireLogin;
import com.zzx.p2p.base.util.UserContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录检查的拦截器
 * @author zzx
 * @date 2021-02-08 13:41:23
 */
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod method = (HandlerMethod) handler;
            RequireLogin methodAnnotation = method.getMethodAnnotation(RequireLogin.class);
            if(methodAnnotation != null && UserContext.getCurrent() == null){
                response.sendRedirect("/login.html");
                return false;
            }
        }
        return super.preHandle(request, response, handler);
    }
}
