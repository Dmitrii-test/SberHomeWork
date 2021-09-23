package ru.dmitrii.multi2;

public interface ThreadPool {
    void start();
    void execute(Runnable runnable);
}
