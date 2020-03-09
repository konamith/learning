package org.robin.demo01;

/**
 * 传统的synchornized
 * synchornized方法、synchornized块
 *
 */
public class Demo01 {

    public static void main(String[] args) {
        // 1，新建资源类
        Ticket ticket = new Ticket();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=1; i <= 40; i++) {
                    ticket.saleTicket();
                }
            }
        }, "A").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=1; i <= 40; i++) {
                    ticket.saleTicket();
                }
            }
        }, "B").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=1; i <= 40; i++) {
                    ticket.saleTicket();
                }
            }
        }, "C").start();
    }
}

/**
 * 资源类
 */
class Ticket {

    private int number = 30;

    /**
     * 同步锁，有点像厕所 -->close-->
     * synchronized是一个关键字
     */
    public synchronized void saleTicket() {
        if (number > 0) {
            System.out.println(Thread.currentThread().getName() + "卖出第" + (number--) + "票，还剩" + number);
        }
    }
}
