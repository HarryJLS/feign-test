package com.cloud.feigntest.juc_demo_test.pool;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo1 {

    public static void main(String[] args) {
        // 一池五线程
        ExecutorService threadPool1 = Executors.newFixedThreadPool(5);

        ExecutorService threadPool2 = Executors.newSingleThreadExecutor();

        ExecutorService threadPool3 = Executors.newCachedThreadPool();

        try {
            for (int i = 0; i < 20; i++) {
                // 执行
                threadPool3.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "办理业务");
                });

            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            threadPool1.shutdown();
        }



    }
}
