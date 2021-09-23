package ru.dmitrii.multi2;

public class ScalableThreadPool implements ThreadPool{
    private final int min;
    private final int max;

    public ScalableThreadPool(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public void start() {

    }

    @Override
    public void execute(Runnable runnable) {

    }
}
