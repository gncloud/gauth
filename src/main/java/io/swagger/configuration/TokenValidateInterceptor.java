package io.swagger.configuration;

import io.swagger.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.AccessControlException;
import java.text.ParseException;

public class TokenValidateInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(TokenValidateInterceptor.class);

    private TokenService tokenService;

    public void setTokenService(TokenService tokenService){
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("Authentication");
        if(token == null || "".equals(token)){
            return super.preHandle(request, response, handler);
        }

        try {
            tokenService.isTokenValidate(token);

            return super.preHandle(request, response, handler);
        } catch (Exception e) {
            logger.error("invalid token access", e);
        }

        return false;
    }

}
