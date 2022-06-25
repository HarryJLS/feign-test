package com.cloud.feigntest.juc_demo_test.completable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 异步调用，没有返回值
        CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "completableFuture1");
        });
        completableFuture1.get();
        // 异步调用，有返回值
        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "completableFuture2");
//            int i = 1/0;// 人造异常
            return 1024;
        });
        completableFuture2.whenComplete((t, u) -> {
            System.out.println("----t" + t);// 方法的返回值
            System.out.println("------u" + u);// 异常
        }).get();
    }
}
