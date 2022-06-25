package com.cloud.feigntest.juc_demo_test.queue;

public class BlockQueueTest {

    public static void main(String[] args) {

        BlockQueue blockQueue = new BlockQueue(5);

        new Thread(() -> {
            for(int i =0;i < 100; i++) {
                blockQueue.add(i);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "写入：" + i);
            }
        }, "aa").start();

        new Thread(() -> {
            for(;;) {
                int result = blockQueue.take();
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "取出：" + result);
            }
        }, "bb").start();
    }
}
