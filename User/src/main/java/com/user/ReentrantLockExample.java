package com.user;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {

    // 共享资源
    private static int counter = 0;

    // 创建一个 ReentrantLock
    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        // 启动 5 个线程来更新共享的计数器
        Thread[] threads = new Thread[5];
        
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(new CounterTask());
            threads[i].start();
        }

        // 等待所有线程完成
        for (int i = 0; i < 5; i++) {
            threads[i].join();
        }

        // 打印最终的计数器值
        System.out.println("最终计数器的值: " + counter);
    }

    // 任务类，用于对计数器进行加1操作
    static class CounterTask implements Runnable {

        @Override
        public void run() {
            // 尝试获取锁
            lock.lock();
            try {
                // 模拟操作
                System.out.println(Thread.currentThread().getName() + " 正在修改计数器...");
                counter++;
                // 模拟操作的延迟
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 确保释放锁
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + " 完成修改，当前计数器值: " + counter);
            }
        }
    }
}