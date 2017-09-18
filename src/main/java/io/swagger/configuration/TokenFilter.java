package io.swagger.configuration;

import io.swagger.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class TokenFilter implements Filter {

    @Autowired
    private TokenService tokenService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
         // TODO 해더의 있는 토큰 정보 유효성 확인
    }

    @Override
    public void destroy() {

    }
}
