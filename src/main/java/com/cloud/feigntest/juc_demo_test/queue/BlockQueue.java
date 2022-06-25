package com.cloud.feigntest.juc_demo_test.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// 使用lock和condition实现阻塞队列
public class BlockQueue {

    // 使用数组来模拟队列
    private List<Integer> container = new ArrayList<>();
    // 队列容量
    private volatile int capticy;
    // 当前队列中信息的数量
    private volatile int size = 0;

    // 声明锁
    private Lock lock = new ReentrantLock();
    // 当队列满的时候，进入的等待队列
    Condition isFull = lock.newCondition();
    // 当队列为空的时候，进入的等待队列
    Condition isEmpty = lock.newCondition();

    // 声明构造函数
    public BlockQueue(int capticy) {
        this.capticy = capticy;
    }

    // 定义写入的函数
    public void add(int data) {
        try {
            lock.lock();

            while (size >= capticy) {
                System.out.println("队列当前已满，请稍后重试！！");
                try {
                    isFull.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            size++;
            container.add(data);
            isEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    // 定义取出函数
    public int take() {
        try {
            lock.lock();

            while(size == 0) {
                System.out.println("当前队列为空，请稍后重试");

                try {
                    isEmpty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            int result = container.get(0);
            container.remove(0);
            size--;
            isFull.signal();
            return result;
        } finally {
            lock.unlock();
        }

    }

}
