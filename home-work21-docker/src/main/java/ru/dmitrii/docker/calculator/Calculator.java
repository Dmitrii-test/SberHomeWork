package ru.dmitrii.docker.calculator;

public interface Calculator {
    /**
     * Расчет факториала числа
     * @param number int
     * @return long
     */
    @Metric
    @Cache(cache = CacheType.RAM)
    long factorial(int number);

    /**
     * Расчёт числа Фибоначчи
     * @param num int
     * @return long
     */
    @Metric
    @Cache(cache = CacheType.FILE)
    long fibonacci(int num);

}
