package ru.dmitrii.homework04_exception.terminal;

import javax.security.auth.login.AccountNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TerminalServerImpl implements TerminalServer {
    private final Map<Integer, Account> accounts = new HashMap<>();
    private final Writer writer = new WriterImp();
    private volatile boolean isRun = false;
    private final AtomicInteger timer = new AtomicInteger(0);
    private Account currentAcc;


    {
        try {
            accounts.put(576, new Account(576, 5428, 1500));
            accounts.put(577, new Account(577, 4222, 1150));
            accounts.put(578, new Account(578, 1328, 100));
            accounts.put(579, new Account(579, 9429, 500));
        } catch (IOException e) {
            writer.write ("Ошибка заполнения счетов" + e.getMessage());
        }
    }

    /**
     * Метод проверки наличия счёта
     * @param acc int
     */
    @Override
    public boolean checkAccount(int acc) {
        if (accounts.containsKey(acc)) {
            writer.write("Счёт найден");
            return true;
        }
        else try {
            throw new AccountNotFoundException();
        } catch (AccountNotFoundException e) {
            writer.write("Счёт не найден");
            return false;
        }
    }

    /**
     * Метод проверки пин-кода
     * @param acc int
     * @param pin int
     * @return boolean
     */
    @Override
    public boolean checkPin(int acc, int pin) {
        if (accounts.get(acc).getPin() == pin) {
            currentAcc = accounts.get(acc);
            return true;
        }
        else return false;
    }

    /**
     * Метод проверки блокировки счёта
     * @param acc int
     * @return boolean
     */
    @Override
    public boolean checkBlock(int acc) {
        if (accounts.get(acc).isBlocked())
            try {
                throw new AccountIsLockedException();
            } catch (AccountIsLockedException e) {
                writer.write("Счёт забокирован");
                writer.write(String.format("Оставщееся время блокировки: %d", timer.get()));
                return true;
            }
        return false;
    }

    /**
     * Метод блокировки счёта на 10 сек
     * @param acc int
     */
    @Override
    public void blockAcc(int acc) {
        Account account = accounts.get(acc);
        Thread t1 = new Thread(() -> {
            this.isRun = true;
            account.setBlocked(true);
            this.timer.set(10);
            while (timer.get()>=0) {
                try {
                    Thread.sleep(1000);
                    this.timer.decrementAndGet();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
            account.setBlocked(false);
            this.isRun = false;
        });
        if (!isRun) t1.start();
    }

    /**
     * Метод запроса баланса
     * @return int
     */
    @Override
    public int requestBalance() {
        return currentAcc.getBalance();
    }

    /**
     * Метод проверки суммы перед снятием
     * @param summ int
     * @return boolean
     */
    @Override
    public boolean checkGet(int summ) {
        return summ <= currentAcc.getBalance();
    }

    /**
     * Метод списания денег
     * @param summ int
     */
    @Override
    public void getMoney(int summ) {
        currentAcc.setBalance(currentAcc.getBalance()-summ);
    }

    /**
     * Метод перевода денег
     * @param summ int
     */
    @Override
    public void setMoney(int summ) {
        currentAcc.setBalance(currentAcc.getBalance()+summ);
    }
}
