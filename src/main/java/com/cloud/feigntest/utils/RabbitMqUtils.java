package com.cloud.feigntest.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 此类为连接工厂创建信道的工具类
 */
public class RabbitMqUtils {

    public static Channel getChannel() throws Exception {
        // 创建个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 工厂ip 连接RabbitMQ的队列
        factory.setHost("192.168.5.7");
        // 用户名
        factory.setUsername("admin");
        factory.setPassword("123");
        // 创建连接
        Connection connection = factory.newConnection();
        // 获取信道
        Channel channel = connection.createChannel();
        return channel;
    }
}
