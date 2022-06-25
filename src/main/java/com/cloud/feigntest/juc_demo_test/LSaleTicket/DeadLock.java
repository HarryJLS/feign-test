package com.cloud.feigntest.juc_demo_test.LSaleTicket;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLock {

    static Object a = new Object();
    static Object b = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (a) {
                System.out.println(Thread.currentThread().getName() + "持有锁a");

                synchronized (b) {
                    System.out.println(Thread.currentThread().getName() + "获取锁b");
                }
            }
        }, "aa").start();

        new Thread(() -> {
            synchronized (b) {
                System.out.println(Thread.currentThread().getName() + "持有锁b");

                synchronized (a) {
                    System.out.println(Thread.currentThread().getName() + "获取锁a");
                }
            }
        }, "bb").start();


    }
}
