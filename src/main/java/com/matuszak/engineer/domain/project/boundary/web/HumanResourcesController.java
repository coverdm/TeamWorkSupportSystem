package com.matuszak.engineer.domain.project.boundary.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project/{{uuid}}/human_resources")
@Log
@RequiredArgsConstructor
public class HumanResourcesController {



}
