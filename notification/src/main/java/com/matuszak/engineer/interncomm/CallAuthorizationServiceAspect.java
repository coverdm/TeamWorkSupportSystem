package com.matuszak.engineer.interncomm;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Log
@RequiredArgsConstructor
public class CallAuthorizationServiceAspect {

    private final SecurityAuthenticationChecker securityAuthenticationChecker;

    @Before("execution(* com.matuszak.engineer.boundary.web.*.* (..))")
    public void authorization(){
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        securityAuthenticationChecker.checkAuthentication(request.getHeader("Authorization")).getStatusCodeValue();
    }
}
