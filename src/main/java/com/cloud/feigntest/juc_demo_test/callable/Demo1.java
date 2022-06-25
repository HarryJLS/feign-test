package com.cloud.feigntest.juc_demo_test.callable;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

class MyThread1 implements Runnable {

    @Override
    public void run() {

    }
}
class MyThread2 implements Callable {

    @Override
    public Integer call() throws Exception {
        return 200;
    }
}
public class Demo1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new Thread(new MyThread1(), "AA").start();
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread2());
        new Thread(futureTask, "lucy").start();

        while(futureTask.isDone()) {
            System.out.println("wait.......");
        }
        System.out.println(futureTask.get());



    }
}
