package com.matuszak.engineer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.matuszak.engineer")
@EntityScan(basePackages = "com.matuszak.engineer")
public class Application{

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}