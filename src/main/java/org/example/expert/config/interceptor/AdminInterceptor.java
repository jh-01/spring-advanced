package org.example.expert.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.expert.config.AdminSecure;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.AccessDeniedException;

@Component
public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)) return true;

        System.out.println("AdminInterceptor preHandle: " + request.getRequestURI());

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        AdminSecure adminSecure = handlerMethod.getMethodAnnotation(AdminSecure.class);

        // admin 권한이 필요없는 경우
        if(adminSecure == null) return true;

        HttpSession session = request.getSession();
        if(session == null){
            response.sendRedirect("/my-error");
            return false;
        }

        // 세션 존재 -> 권한 확인
        User user = (User) session.getAttribute("user");
        if(user == null){
            response.sendRedirect("/my-error");
            return false;
        }

        if (adminSecure != null) {
            UserRole requiredRole = adminSecure.role();
            if (requiredRole == UserRole.ADMIN && user.getUserRole() != UserRole.ADMIN) {
                throw new AccessDeniedException("관리자 권한이 필요합니다.");
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
