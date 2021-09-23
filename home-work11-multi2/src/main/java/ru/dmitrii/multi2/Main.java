package ru.dmitrii.multi2;

public class Main {

    public static void main(String[] args) {
//        FixedThreadPool fixedThreadPool = new FixedThreadPool(3);
//        fixedThreadPool.start();
//        for (int i = 1; i <= 100; i++) {
//            int finalI = i;
//            fixedThreadPool.execute(() -> {
//                        System.out.println("Запуск задачи № " + finalI + " потоком " + Thread.currentThread().getName());
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("Завершение задачи № " + finalI + " потоком " + Thread.currentThread().getName());
//                    }
//            );
//        }
        ScalableThreadPool scalableThreadPool = new ScalableThreadPool(2, 6);
        scalableThreadPool.start();
        for (int i = 1; i <= 100; i++) {
            int finalI = i;
            scalableThreadPool.execute(() -> {
                        System.out.println("Запуск задачи № " + finalI + " потоком " + Thread.currentThread().getName());
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Завершение задачи № " + finalI + " потоком " + Thread.currentThread().getName());
                    }
            );
        }

    }
}
