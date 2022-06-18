package com.cloud.feigntest.test;

import com.cloud.feigntest.utils.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class consumer {
    // 队列名称
    public static final String QUEUE_NAME = "hello";
    // 接收消息
    public static void main(String[] args) throws Exception {

        // 获取信道
        Channel channel = RabbitMqUtils.getChannel();

        // 声明 接收消息
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println(new String(message.getBody()));
        };
        // 取消消息时的回调
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消息消费被中断");
        };

        /**
         * 消费者消费消息
         * 1.消费哪个队列
         * 2.消费成功之后是否要自动应答 true代表自动应答
         * 3.消费者未成功消费的一个回调
         * 4.消费者取消消费的回调
         * */
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);


    }
}
