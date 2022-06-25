package com.cloud.feigntest.juc_demo_test.LSaleTicket;

import java.util.concurrent.locks.ReentrantLock;

// 创建资源类
public class Lticket {

    private int number = 30;

    // 创建可重入锁
    private final ReentrantLock lock = new ReentrantLock(true);

    // 卖票方法
    public void sale() {

        lock.lock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + ":卖出：" + (number--) + "剩下：" + number);
            }
        } finally {
            lock.unlock();
        }

    }
}
