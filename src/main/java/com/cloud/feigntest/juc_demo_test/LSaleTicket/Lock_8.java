package com.cloud.feigntest.juc_demo_test.LSaleTicket;


import java.util.concurrent.TimeUnit;

class Phone {

    public synchronized void sendSMS() throws Exception {

        TimeUnit.SECONDS.sleep(4);
        System.out.println("------sendSMS");
    }

    public synchronized void sendEmail() throws Exception {
        System.out.println("------sendEmail");
    }

    public void getHello() {
        System.out.println("-----sendHello");
    }

}
public class Lock_8 {


    public static void main(String[] args) throws Exception {

        Phone phone = new Phone();
        Phone phone1 = new Phone();

        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "AA").start();

        Thread.sleep(100);

        new Thread(() -> {
            try {
                phone1.sendEmail();
//                phone.getHello();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "BB").start();



    }


}
