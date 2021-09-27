package ru.dmitrii.multi2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Thread.State.RUNNABLE;
import static java.lang.Thread.State.WAITING;

public class ScalableThreadPool implements ThreadPool {
    private final int min;
    private final int max;
    private final List<Pool> threads;
    private final LinkedBlockingQueue<Runnable> queue;
    private int count;
    private final Thread daemon;

    public ScalableThreadPool(int min, int max) {
        this.min = min;
        this.max = max;
        queue = new LinkedBlockingQueue<>();
        threads = new ArrayList<>();
        for (int i = 0; i < min; i++) {
            count = i;
            threads.add(new Pool("Scalable-Thread-" + i));
        }
        daemon = new Thread(daemonRun());
    }

    private Runnable daemonRun() {
        return () -> {
            while (!daemon.isInterrupted()) {
                if (!queue.isEmpty()) addThread();
                else removeThreads();
            }
            System.out.println("Демон остановлен");
        };
    }

    /**
     * Метод запускает потоки
     */
    @Override
    public void start() {
        daemon.start();
        threads.forEach(Pool::start);
    }


    /**
     * Метод останавливает все потоки
     */
    public void stop() {
        daemon.interrupt();
        System.out.println("Остановка потоков");
        threads.forEach(Pool::interrupt);
    }


    /**
     * Метод складывает задание в очередь
     *
     * @param runnable Runnable
     */
    @Override
    public void execute(Runnable runnable) {
        synchronized (queue) {
            System.out.println("Добавлена задача в очередь " + runnable.toString());
            queue.add(runnable);
            queue.notify();
        }
    }

    /**
     * Метод проверяет есть ли потоки в работе
     *
     * @return boolean
     */
    public boolean workThreads() {
        for (Pool pool : threads) {
            if (pool.getState().equals(RUNNABLE)) return true;
        }
        return false;
    }

    /**
     * Метод удаления лишних потоков
     */
    public void removeThreads() {
        if (threads.size() > min) {
            for (Pool pool : threads) {
                if (pool.getState().equals(WAITING)) {
                    pool.interrupt();
                    threads.remove(pool);
                    System.out.println("Удалён лишний поток " + pool.getName());
                    return;
                }
            }
        }
    }

    /**
     * Метод добавления потоков
     */
    private void addThread() {
        if (workThreads() && threads.size() < max) {
            Pool pool = new Pool("Scalable-Thread-" + ++count);
            threads.add(pool);
            pool.start();
            System.out.println("Добавлен новый поток " + pool.getName());
        }
    }


    /**
     * Вложенный класс реализующий потоки
     */
    private class Pool extends Thread {

        public Pool(String name) {
            super(name);
        }

        @Override
        public void run() {
            Runnable task;
            while (!isInterrupted()) {
                try {
                    synchronized (queue) {
                        while (queue.isEmpty()) {
                            queue.wait();
                        }
                        task = queue.poll();
                    }
                    task.run();
                } catch (InterruptedException e) {
                    interrupt();
                }
            }
            System.out.printf("Поток %s остановлен%n", Thread.currentThread().getName());
        }
    }
}
