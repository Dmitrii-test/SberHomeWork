package ru.dmitrii.jmm.task1;

import java.util.concurrent.Callable;

public class Task<T> {

    public Task(Callable<? extends T> callable) {

    }

    public T get() {
        return null;
    }
}

