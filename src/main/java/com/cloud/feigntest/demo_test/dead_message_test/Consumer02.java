package com.cloud.feigntest.demo_test.dead_message_test;

import java.nio.charset.StandardCharsets;

import com.cloud.feigntest.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * 死信队列 消费者2
 */
public class Consumer02 {

    // 死信队列的名称
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();

        System.out.println("等待接收消息。。。。。。");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String msg = new String(message.getBody(), StandardCharsets.UTF_8);
            System.out.println("Consumer02接收的消息" + msg);

        };
        channel.basicConsume(DEAD_QUEUE, true, deliverCallback, consumerTag -> {
        });

    }
}
