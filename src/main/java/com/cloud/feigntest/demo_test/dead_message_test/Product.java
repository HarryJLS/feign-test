package com.cloud.feigntest.demo_test.dead_message_test;

import com.cloud.feigntest.utils.RabbitMqUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;

/**
 * 死信队列之生产者
 */
public class Product {

    // 普通交换机的名称
    public static final String NORMAL_EXCHANGE = "normal_exchange";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

//        // 死信消息 设置TTL time to live 单位ms
//        AMQP.BasicProperties properties =
//                new AMQP.BasicProperties().builder().expiration("10000").build();

        for (int i = 0; i < 10; i++) {
            String message = "info" + i;
            channel.basicPublish(NORMAL_EXCHANGE, "zhangsan", null, message.getBytes(StandardCharsets.UTF_8));
        }
    }
}
