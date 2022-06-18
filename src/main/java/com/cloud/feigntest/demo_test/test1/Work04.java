package com.cloud.feigntest.demo_test.test1;

import com.cloud.feigntest.utils.RabbitMqUtils;
import com.cloud.feigntest.utils.SleepUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class Work04 {

    public static final String TASK_QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("c2等待接收消息处理时间较长");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            SleepUtils.sleep(30);
            System.out.println("接收到的消息" + new String(message.getBody(), "UTF-8"));

            // 手动应答
            /**
             * 1.消息的标记 tag
             * 2.是否批量应答 false 批量应答信道中的消息
             * */
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        };

        // 取消消息时的回调
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消息消费被中断");
        };

        // 设置预取值
        int prefetchCount = 2;
        channel.basicQos(prefetchCount);
        // 采用手动应答
        boolean autoAck = false;
        channel.basicConsume(TASK_QUEUE_NAME, autoAck, deliverCallback, cancelCallback, null);

    }
}
