package org.robin.demo01;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock锁+lambda表达式
 */
public class Demo02 {

    public static void main(String[] args) {
        // 1，新建资源类
        Ticket2 ticket2 = new Ticket2();

        // 2，线程操作资源类，所有函数式接口都可以用lambda表达式简化。
        // lambda表达式：(参数) -> {具体代码}
        new Thread(() -> {
            for (int i = 1; i <= 40; i++) {
                ticket2.saleTicket();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 1; i <= 40; i++) {
                ticket2.saleTicket();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 1; i <= 40; i++) {
                ticket2.saleTicket();
            }
        }, "C").start();


    }
}

/**
 * 资源类
 */
class Ticket2 {

    /**
     * 使用Lock，他是一个对象
     * ReentrantLock 可重入锁：回家：大门（卧室门、厕所门...）
     * ReentrantLock 默认是非公平锁
     * 非公平锁：不公平（可以插队，后面的线程可以插队）
     * 公平锁：公平（只能排队，后面的线程无法插队）
     */
    private Lock lock = new ReentrantLock();

    private int number = 30;

    /**
     * 同步锁，有点像厕所 -->close-->
     * synchronized是一个关键字
     */
    public void saleTicket() {
        // 加锁
        lock.lock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出第" + (number--) + "票，还剩" + number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 解锁
            lock.unlock();
        }
    }
}
