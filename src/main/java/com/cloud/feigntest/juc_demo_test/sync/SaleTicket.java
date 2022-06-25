package com.cloud.feigntest.juc_demo_test.sync;

class Ticket {
    // 票数
    private int number = 30;

    // 操作方法：卖票
    public synchronized void sale() {
        // 判断当前是否有票
        if (number > 0) {
            System.out.println(Thread.currentThread().getName() + ":卖出：" + (number--) + "剩下：" + number);
        }
    }
}

public class SaleTicket {



    // 第二步 创建多个线程。调用资源类的操作方法
    public static void main(String[] args) {
        // 创建Ticket对象
        Ticket ticket = new Ticket();
        // 创建三个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 调用卖票方法
                for (int i = 0; i < 40; i++) {
                    ticket.sale();
                }
            }
        }, "aa").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 调用卖票方法
                for (int i = 0; i < 40; i++) {
                    ticket.sale();
                }
            }
        }, "bb").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 调用卖票方法
                for (int i = 0; i < 40; i++) {
                    ticket.sale();
                }
            }
        }, "cc").start();

    }
}
