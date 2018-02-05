package com.matuszak.engineer;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class CustomFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {

        return true;
    }

    @Override
    public Object run() {

        RequestContext currentContext = RequestContext.getCurrentContext();


        String authorization = currentContext.getRequest().getHeader("Authorization");
        log.info("#################################################################");
        log.info(authorization);
        log.info("#################################################################");
        return null;
    }
}
