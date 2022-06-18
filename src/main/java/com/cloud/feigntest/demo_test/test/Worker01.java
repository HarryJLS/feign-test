package com.cloud.feigntest.demo_test.test;

import com.cloud.feigntest.utils.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @Author jls
 * @Description 工作线程
 * */
public class Worker01 {

    // 队列名称
    public static final String QUEUE_NAME = "hello";

    // 接收消息
    public static void main(String[] args) throws Exception {
        // 获取信道
        Channel channel = RabbitMqUtils.getChannel();

        // 声明 接收消息
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String messages = new String(message.getBody(), "UTF-8");
            System.out.println("接收到消息：" + message);
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
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
        System.out.println("c2等待接收消息。。。。。。");

        Boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, cancelCallback);
    }
}
