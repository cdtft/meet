package com.cdut.interceptor;

import com.cdut.annotation.RoleRequired;
import com.cdut.entity.user.Role;
import com.cdut.security.JWTUtil;
import com.google.common.collect.Lists;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by king on 2017/10/30.
 */
@Component
public class RoleRequiredInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if (method.getAnnotation(RoleRequired.class) != null) {
            String authorization = request.getHeader("authorization");
            Claims claims = JWTUtil.getClaimsFromToken(authorization);
            if (claims == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
            List<String> roles = Lists.newArrayList();
            roles = (List<String>) (claims.get("ROLE"));
            RoleRequired roleRequired = method.getAnnotation(RoleRequired.class);
            String[] required = roleRequired.role();
            for (String aRequired : required) {
                for (String role : roles) {
                    if (role.equals(aRequired)) {
                        return true;
                    }
                }
            }
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }
}
