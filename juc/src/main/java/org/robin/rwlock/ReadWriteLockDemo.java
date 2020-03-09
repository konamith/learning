package org.robin.rwlock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 独占锁（写锁）：一次只能被一个线程战友
 * 共享锁（读锁）：该锁可以被多个线程占有
 */
public class ReadWriteLockDemo {

    public static void main(String[] args) {
        /*
        // 不能实现我们的要求
        MyCache myCache = new MyCache();

        // 写
        for (int i = 1; i <= 5; i++) {
            final int tempInt = i;
            new Thread(() -> {
                myCache.put(tempInt + "", tempInt + "");
            }, String.valueOf(i)).start();
        }

        // 读
        for (int i = 1; i <= 5; i++) {
            final int tempInt = i;
            new Thread(() -> {
                myCache.read(tempInt+"");
            }, String.valueOf(i)).start();
        }
        */

        // 能实现我们的要求
        MyCacheLock myCacheLock = new MyCacheLock();

        // 写
        for (int i = 1; i <= 5; i++) {
            final int tempInt = i;
            new Thread(() -> {
                myCacheLock.put(tempInt + "", tempInt + "");
            }, String.valueOf(i)).start();
        }

        // 读
        for (int i = 1; i <= 5; i++) {
            final int tempInt = i;
            new Thread(() -> {
                myCacheLock.read(tempInt + "");
            }, String.valueOf(i)).start();
        }
    }

}

class MyCache {

    private volatile Map<String, Object> map = new HashMap();

    /**
     * 读：可以被多个线程同时读
     *
     * @param key
     */
    public void read(String key) {
        System.out.println(Thread.currentThread().getName() + "读取" + key);
        Object obj = map.get(key);
        System.out.println(Thread.currentThread().getName() + "读取结果" + obj);
    }

    /**
     * 写：保证原子性，不应该被打扰
     *
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        System.out.println(Thread.currentThread().getName() + "写入" + key);
        map.put(key, value);
        System.out.println(Thread.currentThread().getName() + "写入OK");
    }
}

class MyCacheLock {

    private volatile Map<String, Object> map = new HashMap<>();

    // 读写锁
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * 读：可以被多个线程同时读
     *
     * @param key
     */
    public void read(String key) {
        // 这些锁一定要匹配，否则就可能导致死锁
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "读取" + key);
            Object obj = map.get(key);
            System.out.println(Thread.currentThread().getName() + "读取结果" + obj);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    /**
     * 写：保证原子性，不应该被打扰
     *
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "写入" + key);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "写入OK");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }
}
