package org.robin.lock8;

import java.util.concurrent.TimeUnit;

/**
 * 2，sendEmail休眠3秒后，先执行sendEmail还是sendSMS
 * 答案：sendEmail
 * 被synchronized修饰的方法，锁的对象是方法的调用者，所以说这里两个方法调用的对象是同一个。先调用的先执行。
 */
public class LockDemo2 {

    public static void main(String[] args) throws InterruptedException {
        Phone2 phone = new Phone2();

        new Thread(() -> {
            try {
                phone.sendEmail();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();

        TimeUnit.SECONDS.sleep(2);

        new Thread(() -> {
            phone.sendSMS();
        }, "B").start();
    }
}

class Phone2 {

    public synchronized void sendEmail() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        System.out.println("sendEmail");
    }

    public synchronized void sendSMS() {
        System.out.println("sendSMS");
    }
}
