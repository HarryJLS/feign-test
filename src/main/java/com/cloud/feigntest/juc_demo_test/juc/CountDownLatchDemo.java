package com.cloud.feigntest.juc_demo_test.juc;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(6);

        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "号同学离开了教室");
                latch.countDown();
            }, String.valueOf(i)).start();

        }

        latch.await();
        System.out.println("班长锁门走了");
    }
}
