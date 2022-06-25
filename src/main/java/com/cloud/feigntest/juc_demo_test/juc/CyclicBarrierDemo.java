package com.cloud.feigntest.juc_demo_test.juc;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {

    private static final int NUMBER = 7;
    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(NUMBER, () -> {
            System.out.println("***集齐7颗龙珠就可以召唤神龙");
        });

        //  集齐七颗龙珠的过程
        for (int i = 0; i < 7; i++) {
            new Thread(() -> {


                try {
                    System.out.println(Thread.currentThread().getName() + "星龙珠被收集到了");
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i+1)).start();
        }

    }
}
