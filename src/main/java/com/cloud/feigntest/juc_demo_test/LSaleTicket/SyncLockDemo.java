package com.cloud.feigntest.juc_demo_test.LSaleTicket;

// 可重入锁
public class SyncLockDemo {

    public synchronized void add() {
        add();
    }

    public static void main(String[] args) {

        new SyncLockDemo().add();

//        Object o = new Object();
//        new Thread(() -> {
//            synchronized (o) {
//                System.out.println(Thread.currentThread().getName() + "外层");
//
//                synchronized (o) {
//                    System.out.println(Thread.currentThread().getName() + "中层");
//                }
//            }
//        }, "t1").start();
    }
}
