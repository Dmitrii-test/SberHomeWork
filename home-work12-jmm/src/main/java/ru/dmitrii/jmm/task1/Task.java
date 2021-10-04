package ru.dmitrii.jmm.task1;

import java.util.concurrent.Callable;

public class Task<T> {

    private final Callable<? extends T> callable;
    private volatile T result;
    private volatile CallException callException;


    public Task(Callable<? extends T> callable) {
        this.callable = callable;
    }

    public T get() {
        synchronized (callable) {
            try {
                System.out.println(Thread.currentThread().getName() + " are waiting");
                callable.wait(10);
                System.out.println(Thread.currentThread().getName() + " run");
            } catch (InterruptedException e) {
                System.out.println("Ошибка ожидания " + e.getMessage());
            }
            if (callException != null) {
                System.out.println("Ошибка из памяти");
                reset();
                throw callException;
            }
            if (result != null) {
                System.out.println("Ответ из памяти");
                reset();
                return result;
            }
            try {
                result = callable.call();
            } catch (Exception e) {
                callException = new CallException("Ошибка расчёта");
                throw callException;
            } finally {
                reset();
            }
        }
        System.out.println(Thread.currentThread().getName() + " выполнил Callable");
        return result;
    }

    private void reset() {
        callable.notify();
    }

}

