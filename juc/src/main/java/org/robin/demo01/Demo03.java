package org.robin.demo01;

/**
 * synchronized版
 * 目的：有两个线程A，B，还有个值初始为0
 * 实现两个线程交替执行，对该变量+1，-1。交替10次
 * 传统的wait和notify不能实现精准唤醒通知
 */
public class Demo03 {

    public static void main(String[] args) {
        Data data = new Data();

        // +1
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        // +1
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();

        // -1
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();

        // -1
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "D").start();
    }
}

/**
 * 资源类
 * 线程之间的通信：判断、执行、通知
 */
class Data {

    private int number = 0;

    /**
     * +1
     */
    public synchronized void increment() throws InterruptedException {
        // 1，判断是否需要等待
        if (number > 0) {
            this.wait();
        }
        // 2，执行
        number++;
        System.out.println(Thread.currentThread().getName() + "\t" + number);
        // 3，通知，唤醒所有线程
        this.notifyAll();
    }

    /**
     * -1
     */
    public synchronized void decrement() throws InterruptedException {
        // 1，判断是否需要等待
        if (number == 0) {
            this.wait();
        }
        // 2，执行
        number--;
        System.out.println(Thread.currentThread().getName() + "\t" + number);
        // 3，通知，唤醒所有线程
        this.notifyAll();
    }
}
