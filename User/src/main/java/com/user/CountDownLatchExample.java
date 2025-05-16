package com.user;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {

    public static void main(String[] args) throws InterruptedException {
        // 创建一个 CountDownLatch，初始值为 4
        CountDownLatch latch = new CountDownLatch(4);

        // 创建4个子线程，模拟执行任务
        for (int i = 1; i <= 4; i++) {
            new Thread(new Task(latch, i)).start();
        }

        // 主线程等待，直到 latch 的计数器变为0
        System.out.println("主线程等待子线程完成任务...");
        latch.await(); // 阻塞，直到计数器为0

        // 所有子线程完成后，主线程继续执行
        System.out.println("所有子线程已完成任务，主线程继续执行。");
    }

    // 定义任务，模拟执行任务并计数
    static class Task implements Runnable {
        private final CountDownLatch latch;
        private final int taskId;

        public Task(CountDownLatch latch, int taskId) {
            this.latch = latch;
            this.taskId = taskId;
        }

        @Override
        public void run() {
            try {
                // 模拟子线程执行任务
                System.out.println("子线程 " + taskId + " 正在执行任务...");
                Thread.sleep((long) (Math.random() * 2000)); // 模拟任务执行时间
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("子线程 " + taskId + " 完成任务.");
                latch.countDown(); // 执行完任务后，计数器减1
            }
        }
    }
}