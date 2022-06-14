package com.cloud.feigntest.service.Impl;

import com.cloud.feigntest.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {


    @Override
    public String print() {
        return "test";
    }
}
