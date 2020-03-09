package org.robin.lock8;

import java.util.concurrent.TimeUnit;

/**
 * 1，标准的访问情况下，先执行sendEmail还是sendSMS
 * 答案：sendEmail
 * 被synchronized修饰的方法，锁的对象是方法的调用者，所以说这里两个方法调用的对象是同一个。先调用的先执行。
 */
public class LockDemo1 {

    public static void main(String[] args) throws InterruptedException {
        Phone phone = new Phone();

        new Thread(() -> {
            phone.sendEmail();
        }, "A").start();

        TimeUnit.SECONDS.sleep(2);

        new Thread(() -> {
            phone.sendSMS();
        }, "B").start();
    }

}

class Phone {

    public synchronized void sendEmail() {
        System.out.println("sendEmail");
    }

    public synchronized void sendSMS() {
        System.out.println("sendSMS");
    }
}
