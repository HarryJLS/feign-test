package com.cloud.feigntest.demo_test.exchange_fanout_test;

import com.cloud.feigntest.utils.RabbitMqUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * 
 * 消息接收
 */
public class ReceiveLogs01 {

    // 交换机的名称
    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        // 声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        /**
         * 声明一个队列 临时队列
         * 生成一个临时队列、队列的名称是随机的
         * 当消费者断开与队列的连接的时候 队列就自动删除
         * */
        String queueName = channel.queueDeclare().getQueue();
        /**
         * 绑定交换机和队列
         * */
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        System.out.println("等待接收消息，把接收到的消息打印在屏幕上");

        // 接收消息
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("控制台打印接收到的消息： " + new String(message.getBody(), "UTF-8"));
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});

    }
}
