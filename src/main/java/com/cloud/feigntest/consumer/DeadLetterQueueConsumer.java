package com.cloud.feigntest.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @Author jls
 * @Description 队列TTL 消费者
 */
@Slf4j
@Component // 实例化直接
public class DeadLetterQueueConsumer {

    // 接收消息
    @RabbitListener(queues = "QD")
    public void receiveD(Message message, Channel channel) throws Exception {
        String msg = new String(message.getBody());
        log.info("当前时间：{}， 收到死信队列的消息：{}", new Date().toString(), msg);
    }
}
