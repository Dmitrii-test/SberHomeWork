package ru.dmitrii.jdbc.inter;

public class CalculatorImpl implements Calculator{
    @Cachable(H2DB.class)
    public long fibonachi(int n) {
        long a = 0;
        long b = 1;
        for (int i = 2; i <= n; ++i) {
            long next = a + b;
            a = b;
            b = next;
        }
        return b;
    }
}
