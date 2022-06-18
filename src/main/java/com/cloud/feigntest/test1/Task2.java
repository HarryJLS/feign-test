package com.cloud.feigntest.test1;

import com.cloud.feigntest.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.util.Scanner;

/**
 * @Author jls
 * @Description: 消息手动应答时是不丢失的，放回队列中重新消费
 */
public class Task2 {

    public static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        /**
         * 开启发布确认
         * 1.单个确认 发布速度慢每秒不超过数百条发布消息的吞吐量
         * 2.批量确认
         * 3.异步确认
         * */
        channel.confirmSelect();

        // 声明队列
        /**
         * 创建一个队列
         * 1.队列名称
         * 2.队列里面的消息是否能持久化（磁盘）， 默认情况消息存储在内存中
         * 3.该队列是否只供一个消费者进行消费 是否进行消息的共享， true可以多个消费者消费，
         * 4.是否自动删除 最后一个消费者端开连接以后，该队最后是否自动删除，true自动删除
         * 5.其他参数 死信消息
         * */
        boolean durable = true;// 需要让队列持久化
        channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);

        // 从控制台当中接收信息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            /**
             * 发送一个消息
             * 1.发送到哪个交换机
             * 2.路由的key值是哪个，本次队列的名称
             * 3.其他参数信息，  ******可在这进行消息持久化（保存到磁盘上）
             * 4.发送消息的消息体
             * */
            channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN , message.getBytes("UTF-8"));
            System.out.println("发送消息完成：" + message);
        }
    }
}
