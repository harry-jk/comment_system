package kr.ac.jejunu.harry.interceptor;

import kr.ac.jejunu.harry.annotation.auth.AuthRequired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jhkang on 2016-06-13.
 *
 * refer to: http://deniz.dizman.org/spring-mvc-annotation-based-authentication-checking-interceptors/
 */
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    private final static Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        AuthRequired isAuth = handlerMethod.getMethod().getAnnotation(AuthRequired.class);
        if(isAuth == null) {
            return true;
        }

        logger.info("Auth Interceptor!");
        return super.preHandle(request, response, handler);
    }
}
