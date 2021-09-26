package ru.dmitrii.multi2;

import java.util.concurrent.LinkedBlockingQueue;

public class FixedThreadPool implements ThreadPool {

    private int number;
    private final Pool[] threads;
    private final LinkedBlockingQueue<Runnable> queue;

    public FixedThreadPool(int number) {
        this.number = number;
        queue = new LinkedBlockingQueue<>();
        threads = new Pool[number];

        for (int i = 0; i < number; i++) {
            threads[i] = new Pool("Fixed-Thread-"+i);
        }
    }


    /**
     * Метод запускает потоки
     */
    @Override
    public void start() {
        for (Pool pool : threads) {
            pool.start();
        }
        System.out.println("Потоки запущены");
    }

    /**
     * Метод останавливает все потоки
     */
    public void stop() {
        System.out.println("Остановка потоков");
        for (Pool pool : threads) {
            pool.interrupt();
        }
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
