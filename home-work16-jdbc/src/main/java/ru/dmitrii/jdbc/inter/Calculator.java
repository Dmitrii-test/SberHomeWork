package ru.dmitrii.jdbc.inter;

import ru.dmitrii.jdbc.inter.Cachable;
import ru.dmitrii.jdbc.inter.H2DB;

public interface Calculator {

    /**
     * Расчёт числа Фибоначчи
     * @param num int
     * @return long
     */

    @Cachable(H2DB.class)
    long fibonachi(int num);

}
