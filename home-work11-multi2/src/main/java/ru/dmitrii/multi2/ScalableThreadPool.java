package ru.dmitrii.multi2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Thread.State.RUNNABLE;
import static java.lang.Thread.State.WAITING;

public class ScalableThreadPool implements ThreadPool{
    private final int min;
    private final int max;
    private final List<Pool> threads;
    private final LinkedBlockingQueue<Runnable> queue;

    public ScalableThreadPool(int min, int max) {
        this.min = min;
        this.max = max;
        queue = new LinkedBlockingQueue<>();
        threads = new ArrayList<>();

        for (int i = 0; i < min; i++) {
            threads.add(new Pool());
        }
    }

    @Override
    public void start() {
        threads.forEach(Pool::start);
    }

    @Override
    public void execute(Runnable runnable) {
        synchronized (queue) {
            System.out.println("Добавлена задача в очередь " + runnable.toString());
            queue.add(runnable);
            queue.notify();
        }
    }

    public boolean workThreads () {
        for (Thread thread : threads) {
            if (thread.getState().equals(RUNNABLE)) return true;
        }
        return false;
    }

    public void reduceThreads () {
        for (Thread thread : threads) {
            if (thread.getState().equals(WAITING)) {
                threads.remove(thread);
                System.out.println("Удалён лишний поток");
                return;
            }
        }
    }



    private class Pool extends Thread {
        public void run() {
            Runnable task;
            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        synchronized (threads) {
                            if (threads.size() > min) reduceThreads();
                        }
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            System.out.println("Ошибка ожидания: " + e.getMessage());
                        }
                    }
                    task = queue.poll();
                }
                synchronized (threads) {
                    if (!queue.isEmpty() && workThreads() && threads.size() < max) {
                        Pool pool = new Pool();
                        threads.add(pool);
                        pool.start();
                        System.out.println("Добавлен новый поток");
                    }
                }
                task.run();
            }
        }
    }
}
