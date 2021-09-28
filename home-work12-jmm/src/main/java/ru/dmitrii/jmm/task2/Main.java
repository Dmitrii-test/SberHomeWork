package ru.dmitrii.jmm.task2;

public class Main {
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " выполняет " + this.toString());
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " выполнил " + this.toString());
            }
        };
        Runnable finish = new Runnable() {
            @Override
            public void run() {
                System.out.println("Выполнение завершения!");
            }
        };
        Runnable[] runes = {runnable,runnable,runnable,runnable,runnable};
        ExecutionManager executionManager = new ExecutionManagerImpl(15);
        Context execute = executionManager.execute(finish, runes);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Выполненно - " + execute.getCompletedTaskCount());
        System.out.println("С ошибками - " + execute.getFailedTaskCount());
        execute.interrupt();
        System.out.println("Остановленно - " + execute.getInterruptedTaskCount());
        System.out.println("Все закрыто - " + execute.isFinished());
    }
}
