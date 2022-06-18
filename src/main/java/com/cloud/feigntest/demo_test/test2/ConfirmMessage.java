package com.cloud.feigntest.demo_test.test2;

import com.cloud.feigntest.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;

import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 发布模式
 * 1.单个确认 发布速度慢每秒不超过数百条发布消息的吞吐量
 * 2.批量确认
 * 3.异步批量确认
 * */
public class ConfirmMessage {

    // 批量发消息的个数
    public static final int MESSAGE_COUNT = 1000;


    public static void main(String[] args) throws Exception {
        // 1.单个确认
//        ConfirmMessage.publishMessageIndividually();// 发布1000个单独确认消息， 耗时184ms
        // 2.批量确认
//        ConfirmMessage.publishMessageBatch();// 发布1000个批量确认消息， 耗时46ms
        // 3.异步批量确认
        ConfirmMessage.publishMessageAsync();// 发布1000个异步确认消息， 耗时38ms
    }

    // 1.单个确认
    public static void publishMessageIndividually() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        // 队列名称
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, true, false, false,  null);
        // 开启发布确认
        channel.confirmSelect();
        // 开始时间
        long begin = System.currentTimeMillis();
        // 批量发消息
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + "";
            channel.basicPublish("", queueName, null, message.getBytes());
            boolean flag = channel.waitForConfirms();
            if (flag) {
                System.out.println("消息发送成功");
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("发布" + MESSAGE_COUNT + "个单独确认消息， 耗时" + (end - begin) + "ms");
    }

    // 批量发布确认
    public static void publishMessageBatch() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        // 队列名称
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, true, false, false,  null);
        // 开启发布确认
        channel.confirmSelect();
        // 开始时间
        long begin = System.currentTimeMillis();

        // 批量确认消息的大小
        int batchSize = 100;

        // 批量发消息
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + "";
            channel.basicPublish("", queueName, null, message.getBytes());

            if (i % batchSize == 0) {
                //  发布确认
                boolean flag = channel.waitForConfirms();
                if (flag) {
                    System.out.println("消息发送成功");
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("发布" + MESSAGE_COUNT + "个批量确认消息， 耗时" + (end - begin) + "ms");
    }

    public static void publishMessageAsync() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        // 队列名称
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, true, false, false,  null);
        // 开启发布确认
        channel.confirmSelect();


        /**
         * 线程安全有序的一个哈希表  适用于高并发的情况下
         * 1.轻松的将序号与消息进行关联，
         * 2.可以轻松的批量删除条目，只要给到序号
         * 3.支持高并发（多线程）
         */
        ConcurrentSkipListMap<Long, String> concurrentSkipListMap = new ConcurrentSkipListMap<>();

        // 准备消息的监听器 监听哪些消息成功了， 哪些消息失败了
        ConfirmCallback var1 = (deliverTag, multi) -> {
            if (multi) {
                ConcurrentNavigableMap<Long, String> confirm = concurrentSkipListMap.headMap(deliverTag);
                confirm.clear();
            } else {
                // 删除已经确认的消息  剩下的就是未确认的消息
                concurrentSkipListMap.headMap(deliverTag);
            }

            System.out.println("确认的消息" + deliverTag);
        };
        /**
         * 消息的标记
         * 是否为批量确认
         * 不成功的消息确认
         * */
        ConfirmCallback var2 = (deliverTag, multi) -> {
            String message = concurrentSkipListMap.get(deliverTag);
            System.out.println("未确认的消息：" + message + "标记：" + deliverTag);
        };
        channel.addConfirmListener(var1, var2);// 异步通知

        // 开始时间
        long begin = System.currentTimeMillis();

        // 批量发消息
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + "";
            channel.basicPublish("", queueName, null, message.getBytes());

            // 记录所有要发送的消息  消息的总和
            concurrentSkipListMap.put(channel.getNextPublishSeqNo(), message);
        }

        long end = System.currentTimeMillis();
        System.out.println("发布" + MESSAGE_COUNT + "个批量确认消息， 耗时" + (end - begin) + "ms");
    }
}
