package org.robin.lock8;

import java.util.concurrent.TimeUnit;

/**
 * 3，增加一个普通方法，请问先打印哪个？
 * 答案：sendSMS
 * sendSMS方法没有synchronized修饰，不是同步方法，不受锁的影响
 */
public class LockDemo3 {

    public static void main(String[] args) throws InterruptedException {
        Phone7 phone = new Phone7();

        new Thread(() -> {
            try {
                phone.sendEmail();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            phone.sendSMS();
        }, "B").start();
    }
}

class Phone3 {

    public synchronized void sendEmail() throws InterruptedException {
        TimeUnit.SECONDS.sleep(4);
        System.out.println("sendEmail");
    }

    // 没有synchronized， 没有static就是普通方法
    public void sendSMS() {
        System.out.println("sendSMS");
    }
}