package com.matuszak.projects.auth.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Log
@RequiredArgsConstructor
@Component
public class JwtFilter extends GenericFilterBean {

    private final JwtService jwtService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        Authentication authentication = jwtService.getAuthenticationFromRequest((HttpServletRequest) servletRequest);

        if(authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Participant authenticated");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
