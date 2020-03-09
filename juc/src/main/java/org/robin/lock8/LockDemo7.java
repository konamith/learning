package org.robin.lock8;

import java.util.concurrent.TimeUnit;

/**
 * 7、一个普通同步方法，一个静态同步方法，只有一个手机，请问先执行sendEmail 还是 sendSMS
 * 答案：sendSMS
 * synchronized 锁的是这个调用的对象， static 锁的是这个类的Class模板， 这里是两个锁！
 */
public class LockDemo7 {

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

class Phone7 {

    public static synchronized void sendEmail() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        System.out.println("sendEmail");
    }

    public synchronized void sendSMS() {
        System.out.println("sendSMS");
    }
}