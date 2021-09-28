package ru.dmitrii.jmm.task2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.State.*;

public class ExecutionManagerImpl implements ExecutionManager {

    private final List<Pool> threads;
    private final LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private volatile Runnable callback;
    private final AtomicInteger errorCall = new AtomicInteger(0);
    private final AtomicInteger inter = new AtomicInteger(0);

    /**
     * После завершения всех тасков должен выполниться callback (ровно 1 раз)
     *
     * @param number int
     */
    public ExecutionManagerImpl(int number) {
        threads = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            Pool e = new Pool("My-Thread-" + i);
            threads.add(e);
            e.start();
        }
    }

    @Override
    public Context execute(Runnable callback, Runnable... tasks) {
        Collections.addAll(queue, tasks);
        this.callback = callback;
        return new ContextImpl();
    }

    private class ContextImpl implements Context {

        /**
         * Возвращает количество тасков, которые на текущий момент успешно выполнились
         *
         * @return int
         */
        @Override
        public int getCompletedTaskCount() {
            return (int) threads.stream().filter((n) -> n.getState() == TERMINATED).count();
        }

        /**
         * возвращает количество тасков, при выполнении которых произошел Exception
         *
         * @return int
         */
        @Override
        public int getFailedTaskCount() {
            return errorCall.get();
        }

        /**
         * Возвращает количество тасков, которые не были выполены из-за отмены
         *
         * @return int
         */
        @Override
        public int getInterruptedTaskCount() {
            return inter.get();
        }

        /**
         * Отменяет выполнения тасков, которые еще не начали выполняться
         */
        @Override
        public void interrupt() {
            for (Pool p : threads) {
                if (p.getState() == TIMED_WAITING || p.getState() == NEW) {
                    p.interrupt();
                    inter.incrementAndGet();
                }
            }
        }

        /**
         * Вернет true, если все таски были выполнены или отменены, false в противном случае
         *
         * @return boolean
         */
        @Override
        public boolean isFinished() {
            for (Pool p : threads) {
                if (p.getState() == TERMINATED) return true;
            }
            return false;
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
            try {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        queue.wait(50);
                    }
                    task = queue.poll();
                }
                Objects.requireNonNull(task).run();
            } catch (InterruptedException e) {
                errorCall.incrementAndGet();
                interrupt();
            }
            if (callback != null && queue.isEmpty()) {
                callback.run();
                callback = null;
            }
            System.out.printf("Поток %s окончил задачу%n", Thread.currentThread().getName());
        }
    }
}
