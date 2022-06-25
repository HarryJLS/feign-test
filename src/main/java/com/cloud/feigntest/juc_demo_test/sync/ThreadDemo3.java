package com.cloud.feigntest.juc_demo_test.sync;


class ShareResourse {
    private int flag = 0;

    public synchronized void  aMethod() throws InterruptedException{
        while (flag != 0) {
            this.wait();
        }
        for (int i = 0; i < 5; i++) {
            System.out.println(1);
        }
        flag=1;
        this.notifyAll();
    }
    public synchronized void bMethod() throws InterruptedException{
        while (flag != 1) {
            this.wait();
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(2);
        }
        flag=2;
        this.notifyAll();
    }
    public synchronized void cMethod() throws InterruptedException{
        while (flag != 2) {
            this.wait();
        }
        for (int i = 0; i < 15; i++) {
            System.out.println(3);
        }
        flag=0;
        this.notifyAll();
    }

}
public class ThreadDemo3 {

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
