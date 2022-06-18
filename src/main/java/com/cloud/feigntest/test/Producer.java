package com.cloud.feigntest.test;


import com.cloud.feigntest.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者：发消息
 * */
public class Producer {
    // 队列名称
    public static final String QUEUE_NAME = "hello";

    // 发消息
    public static void main(String[] args) throws Exception {


        // 获取信道
        Channel channel = RabbitMqUtils.getChannel();

        /**
         * 创建一个队列
         * 1.队列名称
         * 2.队列里面的消息是否能持久化（磁盘）， 默认情况消息存储在内存中
         * 3.该队列是否只供一个消费者进行消费 是否进行消息的共享， true可以多个消费者消费，
         * 4.是否自动删除 最后一个消费者端开连接以后，该队最后是否自动删除，true自动删除
         * 5.其他参数 死信消息
         * */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 发消息
        String message = "jlst";

        /**
         * 发送一个消息
         * 1.发送到哪个交换机
         * 2.路由的key值是哪个，本次队列的名称
         * 3.其他参数信息
         * 4.发送消息的消息体
         * */
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("消息发送完毕");


    }
}
