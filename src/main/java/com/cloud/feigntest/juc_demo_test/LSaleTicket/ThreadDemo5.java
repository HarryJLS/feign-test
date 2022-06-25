package com.cloud.feigntest.juc_demo_test.LSaleTicket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * list集合线程不安全
 */
public class ThreadDemo5 {

    public static void main(String[] args) {
//        List<String> list = new Vector<>();
//        List<String> list = Collections.synchronizedList(new ArrayList<>());
//        List<String> list = new CopyOnWriteArrayList<>();

//        Set<String> set = new HashSet<>();
//        Set<String> set = new CopyOnWriteArraySet<>();




//        for (int i = 0; i < 100; i++) {
//            new Thread(() -> {
//                set.add(UUID.randomUUID().toString().substring(0,8));
//                System.out.println(set);
//            }, String.valueOf(i)).start();
//        }

        Map<String, String> map = new ConcurrentSkipListMap<>();
        for (int i = 0; i < 100; i++) {
            String key = String.valueOf(i);
            new Thread(() -> {
                map.put(key, UUID.randomUUID().toString().substring(0,8));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }

    }
}
