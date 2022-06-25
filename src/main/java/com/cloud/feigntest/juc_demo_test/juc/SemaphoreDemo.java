package com.cloud.feigntest.juc_demo_test.juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

// 6辆汽车挺三个车位的方法
public class SemaphoreDemo {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "车抢到车位");
                    TimeUnit.SECONDS.sleep(3);

                    System.out.println(Thread.currentThread().getName() + "车---离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }

            }, String.valueOf(i)).start();

        }
    }
}
