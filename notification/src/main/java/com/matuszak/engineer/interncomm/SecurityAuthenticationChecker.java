package com.matuszak.engineer.interncomm;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("auth")
public interface SecurityAuthenticationChecker {

    @RequestMapping(value = "/{accessToken}/checkAuthorization", method = RequestMethod.GET)
    ResponseEntity checkAuthentication(@PathVariable("accessToken") String accessToken);

    @RequestMapping(value = "/{accessToken}/getAuthenticatedUser", method = RequestMethod.GET)
    String getAuthenticatedUser(@PathVariable("accessToken") String accessToken);
}
