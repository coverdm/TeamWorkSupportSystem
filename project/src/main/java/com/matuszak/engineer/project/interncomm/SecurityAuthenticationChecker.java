package com.matuszak.engineer.project.interncomm;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("auth")
public interface SecurityAuthenticationChecker {

    @RequestMapping(value = "/checkAuthorization/{accessToken}", method = RequestMethod.GET)
    ResponseEntity checkAuthentication(@PathVariable("accessToken") String accessToken);

}
