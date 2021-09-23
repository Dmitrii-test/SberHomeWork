package ru.dmitrii.multi2;

import java.util.concurrent.LinkedBlockingQueue;

public class FixedThreadPool implements ThreadPool {

    private int number;
    private final Pool[] threads;
    private final LinkedBlockingQueue<Runnable> queue;

    public FixedThreadPool(int number) {
        this.number = number;
        queue = new LinkedBlockingQueue();
        threads = new Pool[number];

        for (int i = 0; i < number; i++) {
            threads[i] = new Pool();
        }
    }

    @Override
    public void start() {
        for (Pool pool : threads) {
            pool.start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        synchronized (queue) {
            System.out.println("Добавлена задача в очередь " + runnable.toString());
            queue.add(runnable);
            queue.notify();
        }
    }

    private class Pool extends Thread {
        public void run() {
            Runnable task;
            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            System.out.println("Ошибка ожидания: " + e.getMessage());
                        }
                    }
                    task = queue.poll();
                }
                task.run();
            }
        }
    }
}
