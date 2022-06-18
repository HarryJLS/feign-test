package com.cloud.feigntest.demo_test.exchange_topic_test;

import java.util.Scanner;

import com.cloud.feigntest.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

// 发消息给交换机
public class EmitLogTopic {

    // 交换机的名称
    public static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        // channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String data = scanner.next();
            String[] split = data.split(",");
            channel.basicPublish(EXCHANGE_NAME, split[1], null, split[0].getBytes("UTF-8"));
            System.out.println("生产者发出消息：" + split[0]);
        }
    }
}
