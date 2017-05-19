package com.matuszak.projects.secure;

import com.matuszak.projects.domain.UserRole;
import com.matuszak.projects.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by dawid on 03.04.17.
 */
public class JWTLogin extends GenericFilterBean{

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String authorization = request.getHeader("Authorization");

        if(authorization == null || !authorization.startsWith("Bearer")){
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }else {

            String token = authorization.substring(7);

            Claims claims = Jwts.parser().setSigningKey("someSicretKey").parseClaimsJws(token).getBody();

//            List<UserRole> roles = (ArrayList<UserRole>) claims.get("claims");
            List<SimpleGrantedAuthority> grantedAuthorityList = new ArrayList<>();
//
//            roles.stream().forEach(e -> logger.info(e.getRole()));
//            logger.info("SIZE: " + roles.size());
//
//            for(UserRole role:roles) {
//                grantedAuthorityList.add(new SimpleGrantedAuthority(role.getRole()));
//                logger.info("ROLE: " + role.getRole());
//}

            grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));


            User user = new User(claims.getSubject(), "", grantedAuthorityList);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user,"", grantedAuthorityList));
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
