package ru.dmitrii.concurrent.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceImpl implements Service {

    @Override
    public Long factorial(String string, int n) {
        if (n > 20) throw new IllegalArgumentException(n + " is out of range");
        return (1 > n) ? 1 : n * factorial(string ,n - 1);
    }

    @Override
    public List<String> run(String item, double value, Date date) {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < value; i++) {
            strings.add(item + " =" + i + " дата " + date);
        }
        return strings;
    }


    @Override
    public List<String> work(String item) {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add(item + i);
        }
        return strings;
    }
}
