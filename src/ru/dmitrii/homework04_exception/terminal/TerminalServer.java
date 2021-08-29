package ru.dmitrii.homework04_exception.terminal;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TerminalServer {
    private Map<Integer, Account> accounts = new HashMap<>();

    {
        try {
            accounts.put(576, new Account(576, 5428, 1500));
            accounts.put(577, new Account(577, 4222, 1150));
            accounts.put(578, new Account(578, 1328, 100));
            accounts.put(579, new Account(579, 9429, 500));
        } catch (IOException e) {
            System.out.println("Ошибка заполнения счетов" + e);
        }
    }

    public boolean checkAccount(int acc) {
        return accounts.containsKey(acc);
    }

    public boolean checkPin(int acc, int pin) {
        return accounts.get(acc).getPin() == pin;
    }

    public boolean checkBlock(int acc) {
        if (accounts.get(acc).isBlocked())
            try {
                throw new AccountIsLockedException();
            } catch (AccountIsLockedException e) {
                System.out.println("Счёт забокирован");
                System.out.println("Оставщееся время блокировки: " + accounts.get(acc));
            }
        return true;
    }

    public void blockAcc(int acc) {
        accounts.get(acc).setBlocked();
    }

}
