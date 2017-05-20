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

        if(authorization == null || !authorization.startsWith(TOKEN_PREFIX)){
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }else {

            String token = authorization.substring(7);

            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();

            List<SimpleGrantedAuthority> grantedAuthorityList = new ArrayList<>();

            List<String> authorities = (List<String>) claims.get("claims");

            for(String role:authorities)
                grantedAuthorityList.add(new SimpleGrantedAuthority(role));

            User user = new User(claims.getSubject(), "", grantedAuthorityList);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user,"", grantedAuthorityList));
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
