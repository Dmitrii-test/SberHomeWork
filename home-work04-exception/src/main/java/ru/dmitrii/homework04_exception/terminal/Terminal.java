package ru.dmitrii.homework04_exception.terminal;

public interface Terminal {

    int getNum(int quantity, int cate);

    void checkPK(int acc);

    void operations(int acc);

    void putMoney(int acc);

    void takeMoney(int acc);

}
