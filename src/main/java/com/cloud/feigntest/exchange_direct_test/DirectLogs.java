package com.cloud.feigntest.exchange_direct_test;

import java.util.Scanner;

import com.cloud.feigntest.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

// 发消息给交换机
public class DirectLogs {

    // 交换机的名称
    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        // channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            channel.basicPublish(EXCHANGE_NAME, "error", null, message.getBytes("UTF-8"));
            System.out.println("生产者发出消息：" + message);
        }
    }
}
