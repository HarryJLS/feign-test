package com.cloud.feigntest.Controller;


import com.cloud.feigntest.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {

    @Autowired
    private TestService testService;

    @PostMapping(value = "/test")
    public String TV(){

        return testService.print();
    }
}
