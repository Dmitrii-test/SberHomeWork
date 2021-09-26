package ru.dmitrii.multi2;

public interface ThreadPool {

    /**
     * Метод запускает потоки
     */
    void start();

    /**
     * Метод складывает задание в очередь
     * @param runnable Runnable
     */
    void execute(Runnable runnable);
}
