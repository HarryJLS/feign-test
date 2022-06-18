package com.cloud.feigntest.exchange_topic_test;

import com.cloud.feigntest.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * topic类型交换机的消费者声明
 */
public class ReceiveLogsTopic02 {
    public static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();
        // 声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        String queueName = "Q2";

        // 声明一个队列
        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName, EXCHANGE_NAME, "*.*.rabbit");
        channel.queueBind(queueName, EXCHANGE_NAME, "lazy.#");

        // 接收消息
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("ReceiveLogsTopic02控制台打印接收到的消息： " + new String(message.getBody(), "UTF-8")
                    + "绑定键：" + message.getEnvelope().getRoutingKey());
        };
        channel.basicConsume(queueName, deliverCallback, consumerTag -> {
        });
    }
}
