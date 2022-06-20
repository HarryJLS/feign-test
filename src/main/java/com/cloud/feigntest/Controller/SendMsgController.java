package com.cloud.feigntest.Controller;

import java.util.Date;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author jls
 * @Description 发送延迟消息
 **/
@Slf4j
@RestController
@RequestMapping("/ttl")
public class SendMsgController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 开始发消息
    @GetMapping("/sendMsg/{message}")
    public void sendMsg(@PathVariable String message) {
        log.info("当前时间：{}， 发送一条信息给两个TTL队列：{}", new Date().toString(), message);

        rabbitTemplate.convertAndSend("X", "XA", "消息来自ttl为10S的队列");
        rabbitTemplate.convertAndSend("X", "XB", "消息来自ttl为30S的队列");
    }

    // 开始发消息 消息和TTL
    @GetMapping("/sendExpiretionMsg/{message}/{ttlTime}")
    public void sendMsg(@PathVariable String message, @PathVariable String ttlTime) {
        log.info("当前时间：{}， 发送一条时长是{}毫秒信息给TTL队列：{}", new Date().toString(), ttlTime, message);

        rabbitTemplate.convertAndSend("X", "XC", message, msg -> {
            // 设置发送消息的时候 延迟时长
            msg.getMessageProperties().setExpiration(ttlTime);
            return msg;
        });
    }

    // 开始发消息 测试确认

}
