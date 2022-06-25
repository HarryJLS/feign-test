package com.cloud.feigntest.juc_demo_test.LSaleTicket;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareResourse {
    private int flag = 0;

    Lock lock = new ReentrantLock();
    Condition c1 = lock.newCondition();
    Condition c2 = lock.newCondition();
    Condition c3 = lock.newCondition();

    public void  aMethod() throws InterruptedException{
        lock.lock();
        try {
            while (flag != 0) {
                c1.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println(1);
            }
            flag = 1;
            c2.signal();
        } finally {
            lock.unlock();
        }
    }
    public void bMethod() throws InterruptedException{
        lock.lock();
        try {
            while (flag != 1) {
                c2.await();
            }
            for (int i = 0; i < 10; i++) {
                System.out.println(2);
            }
            flag = 2;
            c3.signal();
        } finally {
            lock.unlock();
        }
    }
    public void cMethod() throws InterruptedException{
        lock.lock();
        try {
            while (flag != 2) {
                c3.await();
            }
            for (int i = 0; i < 15; i++) {
                System.out.println(3);
            }
            flag = 0;
            c1.signal();
        } finally {
            lock.unlock();
        }
    }

}
public class ThreadDemo4 {

    public static void main(String[] args) {
        ShareResourse shareResourse = new ShareResourse();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    shareResourse.aMethod();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "aa").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    shareResourse.bMethod();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "bb").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    shareResourse.cMethod();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "cc").start();
    }
}
