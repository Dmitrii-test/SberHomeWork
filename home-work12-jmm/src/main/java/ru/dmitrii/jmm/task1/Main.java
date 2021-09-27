package ru.dmitrii.jmm.task1;

import java.util.concurrent.Callable;

public class Main {
    public static void main(String[] args) {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 10 / 0;
            }
        };
        Task<Integer> task = new Task(callable);
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() ->
                    System.out.println(Thread.currentThread().getName() + " результат " + task.get()));
            thread.start();
        }

    }
}
