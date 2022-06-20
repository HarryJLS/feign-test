package com.cloud.feigntest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableWebMvc
public class FeignTestApplication {

    public static void main(String[] args) {

        SpringApplication.run(FeignTestApplication.class, args);
    }

}
