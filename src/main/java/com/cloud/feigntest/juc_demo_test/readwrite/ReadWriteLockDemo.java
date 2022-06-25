package com.cloud.feigntest.juc_demo_test.readwrite;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


class MyCache {

    private volatile Map<String, Object> map = new HashMap<>();

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    // 放数据
    public void put(String key, Object value) throws InterruptedException {
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "正在写操作" + key);

            TimeUnit.MICROSECONDS.sleep(300);

            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "写完了" + key);
        } finally {
            readWriteLock.writeLock().unlock();
        }


    }

    public Object get(String key) throws InterruptedException {

        readWriteLock.readLock().lock();
        Object result = null;
        try {
            System.out.println(Thread.currentThread().getName() + "正在读取操作" + key);

            TimeUnit.MICROSECONDS.sleep(300);

            result = map.get(key);
            System.out.println(Thread.currentThread().getName() + "取完了" + key);


        } finally {
            readWriteLock.readLock().unlock();
        }
        return result;
    }

}

public class ReadWriteLockDemo {

    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        for (int i = 0; i < 5; i++) {
            final int num = i;
            new Thread(() -> {
                try {
                    myCache.put(num + "", num);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();

        }

        for (int i = 0; i < 5; i++) {
            final int num = i;
            new Thread(() -> {
                try {
                    myCache.get(num + "");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();

        }
    }
}
