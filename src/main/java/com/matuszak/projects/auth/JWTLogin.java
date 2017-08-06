package com.matuszak.projects.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
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
import java.util.List;

public class JWTLogin extends GenericFilterBean {

    private static final String SECRET_KEY = "MyOwnSecretKey";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String authorization = request.getHeader(AUTHORIZATION_HEADER);

        if (!isAuthorizationHeaderValid(authorization)) {
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        else{
            String token = extractToken(authorization);

            Claims claims = extractClaims(token);

            List<GrantedAuthority> grantedAuthorities = AuthorityUtils.createAuthorityList(extractAuthorities(claims));

            User user = new User(claims.getSubject(), "", grantedAuthorities);

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, "", grantedAuthorities));
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isAuthorizationHeaderValid(String authorization) {
        return authorization != null || authorization.startsWith(TOKEN_PREFIX);
    }

    private String extractAuthorities(Claims claims) {
        return (String) claims.get("claims");
    }

    private Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private String extractToken(String authorization) {
        return authorization.substring(7);
    }
}
