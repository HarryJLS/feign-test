package com.cloud.feigntest.demo_test.test;

import com.cloud.feigntest.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

/**
 * @Author jls
 * @Description: 生产者 发送大量的消息
 */
public class Task01 {

    // 队列名称
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
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
        // 从控制台当中接收信息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("发送消息完成：" + message);
        }

    }
}
