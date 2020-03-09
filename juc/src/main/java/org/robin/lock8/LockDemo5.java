package org.robin.lock8;

import java.util.concurrent.TimeUnit;

/**
 * 5、两个静态同步方法，同一个手机请问先执行sendEmail 还是 sendSMS
 * 答案：sendEmail
 * 只要方法被 static 修饰，锁的对象就是 Class模板对象,这个是全局唯一！所以说这里是同一个锁
 */
public class LockDemo5 {

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            try {
                Phone5.sendEmail();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            Phone5.sendSMS();
        }, "B").start();
    }
}

class Phone5 {

    public static synchronized void sendEmail() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        System.out.println("sendEmail");
    }

    public static synchronized void sendSMS() {
        System.out.println("sendSMS");
    }
}