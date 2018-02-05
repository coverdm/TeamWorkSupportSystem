package com.matuszak.engineer.project;

import com.matuszak.engineer.project.annotation.impl.CascadingMongoEventListener;
import com.matuszak.engineer.project.annotation.impl.TaskCascadeSaveMongoEventListener;
import com.matuszak.engineer.project.annotation.impl.WorkersCascadeSaveMongoEventListener;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class ProjectApplication {

    public static void main(String[] args) {

        SpringApplication.run(ProjectApplication.class, args);

    }

    @Bean
    public CascadingMongoEventListener workerCascadeSaveMongoEventListener(){
        return new WorkersCascadeSaveMongoEventListener();
    }

    @Bean
    public CascadingMongoEventListener taskCascadeSaveMongoEventListener(){
        return new TaskCascadeSaveMongoEventListener();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
