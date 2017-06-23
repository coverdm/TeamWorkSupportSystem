package com.matuszak.projects.secure;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dawid on 03.04.17.
 */
public class JWTLogin extends GenericFilterBean{

    private static final String SECRET_KEY = "MyOwnSecretKey";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String authorization = request.getHeader(AUTHORIZATION_HEADER);

        if(isValidAuthorization(authorization)){
            HttpServletErrorHandler((HttpServletResponse) servletResponse);
        }else {

            String token = extractToken(authorization);

            Claims claims = extractClaims(token);

            List<SimpleGrantedAuthority> grantedAuthorityList = new ArrayList<>();

            List<String> authorities = (List<String>) claims.get("claims");

            for(String role:authorities)
                grantedAuthorityList.add(new SimpleGrantedAuthority(role));

            User user = new User(claims.getSubject(), "", grantedAuthorityList);

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user,"", grantedAuthorityList));
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private String extractToken(String authorization) {
        return authorization.substring(7);
    }

    private void HttpServletErrorHandler(HttpServletResponse servletResponse) throws IOException {
        servletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private boolean isValidAuthorization(String authString){
        return authString == null || !authString.startsWith(TOKEN_PREFIX) ? true : false;
    }

}
