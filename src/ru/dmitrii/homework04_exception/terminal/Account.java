package ru.dmitrii.homework04_exception.terminal;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class Account {
    private int account;
    private int pin;
    private int balance;
    private volatile boolean blocked = false;
    private final AtomicInteger timer = new AtomicInteger(0);

    public Account(int account, int pin, int balance) throws IOException {
        this.account = account;
        if (balance < 0) throw new IOException("Баланс не может меньше 0");
        this.balance = balance;
        if ((int) (Math.log10(pin) + 1) == 4) this.pin = pin;
        else throw new IOException("Пин код  не 4 цифры");
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) throws IOException {
        if ((int) (Math.log10(pin) + 1) == 4) this.pin = pin;
        else throw new IOException("Пин код не 4 цифры");
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public int getTimer() {
        return timer.get();
    }

    public void setBlocked() {
        System.out.println("Таймер " + timer + blocked);
        Thread t1 = new Thread(() -> {
            System.out.println("Запуск");
            this.blocked = true;
            this.timer.set(10000000);
            for (int i = timer.get(); i < 0; i--) {
                timer.decrementAndGet();
            }
            System.out.println("Вышли");
            this.blocked = false;
        });
        t1.start();
    }
}
