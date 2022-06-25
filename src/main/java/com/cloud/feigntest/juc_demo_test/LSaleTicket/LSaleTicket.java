package com.cloud.feigntest.juc_demo_test.LSaleTicket;

import sun.security.krb5.internal.Ticket;

public class LSaleTicket {

    public static void main(String[] args) {
        Lticket lticket = new Lticket();

        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                lticket.sale();
            }
        }, "aa").start();

        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                lticket.sale();
            }
        }, "bb").start();

        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                lticket.sale();
            }
        }, "cc").start();

    }
}
