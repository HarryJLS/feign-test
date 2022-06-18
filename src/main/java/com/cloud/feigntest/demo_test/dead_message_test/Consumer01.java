package com.cloud.feigntest.demo_test.dead_message_test;

import java.util.HashMap;
import java.util.Map;

import com.cloud.feigntest.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * 死信队列 消费者1
 */
public class Consumer01 {

    // 普通交换机的名称
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    // 死信交换机名称
    public static final String DEAD_EXCHANGE = "dead_exchange";
    // 普通队列的名称
    public static final String NORMAL_QUEUE = "normal_queue";
    // 死信队列的名称
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();

        // 声明死信和普通交换机 类型为direct
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);

        // 声明普通队列 正常队列中设置死信队列
        Map<String, Object> argument = new HashMap<>();
        // 设置死信队列
        argument.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        // 设置死信RoutingKey
        argument.put("x-dead-letter-routing-key", "lisi");
//        // 设置队列的最大长度
//        argument.put("x-max-length", 6);

        channel.queueDeclare(NORMAL_QUEUE, false, false, false, argument);
        // 声明死信队列
        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);

        // 绑定普通的交换机与队列
        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, "zhangsan");
        // 绑定死信的交换机与队列
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, "lisi");
        System.out.println("等待接收消息。。。。。。");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String msg = new String(message.getBody(), "UTF-8");
            if (msg.equals("info5")) {
                System.out.println("Consumer01接收的消息是：" + msg + ": 此消息被c1拒绝");
                channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
            } else {
                System.out.println("Consumer01接收的消息" + msg);
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            }

        };
        channel.basicConsume(NORMAL_QUEUE, false, deliverCallback, consumerTag -> {
        });

    }
}
