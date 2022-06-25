package com.cloud.feigntest.juc_demo_test.readwrite;


import java.util.concurrent.locks.ReentrantReadWriteLock;

// 写锁降级
public class Demo1 {

    public static void main(String[] args) {

        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = rwLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();

        writeLock.lock();
        System.out.println("test");

        readLock.lock();
        System.out.println("----read");

        writeLock.unlock();

        readLock.unlock();


    }
}
