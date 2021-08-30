package ru.dmitrii.homework04_exception.terminal;

public interface TerminalServer {
    boolean checkAccount(int acc);

    boolean checkPin(int acc, int pin);

    boolean checkBlock(int acc);

    void blockAcc(int acc);

    int requestBalance();

    boolean checkGet(int summ);

    void getMoney(int summ);

    void setMoney(int summ);
}
