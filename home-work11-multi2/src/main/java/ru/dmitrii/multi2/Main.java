package ru.dmitrii.multi2;

public class Main {

    public static void main(String[] args) {
//        FixedThreadPool fixedThreadPool = new FixedThreadPool(3);
//        fixedThreadPool.start();
//        for (int i = 1; i <= 10; i++) {
//            fixedThreadPool.execute(getRunnable(i));
//        }
//        try {
//            Thread.sleep(200);
//        } catch (InterruptedException e) {
//            System.out.println("Ошибка ожидания " + e.getMessage());
//        }
//        fixedThreadPool.stop();
        ScalableThreadPool scalableThreadPool = new ScalableThreadPool(2, 5);
        scalableThreadPool.start();
        for (int i = 1; i <= 30; i++) {
            scalableThreadPool.execute(getRunnable(i));
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("Ошибка ожидания " + e.getMessage());
        }
        scalableThreadPool.stop();
    }

    /**
     * Экземпляр Runnable
     *
     * @param finalI int
     * @return Runnable
     */
    private static Runnable getRunnable(int finalI) {
        return () -> {
            System.out.println("Запуск задачи № " + finalI + " потоком " + Thread.currentThread().getName());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Завершение задачи № " + finalI + " потоком " + Thread.currentThread().getName());
        };
    }
}
