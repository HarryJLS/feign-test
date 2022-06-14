package com.cloud.feigntest.feignClient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "feign-consumer")
public interface testClient {

    @PostMapping(value = "test")
    public String print();
}
