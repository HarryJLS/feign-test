package com.cloud.feigntest.feignClient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "feign-consumer")
public interface testClient {

    // 测试接口
    @PostMapping(value = "test")
    public String print();
}
