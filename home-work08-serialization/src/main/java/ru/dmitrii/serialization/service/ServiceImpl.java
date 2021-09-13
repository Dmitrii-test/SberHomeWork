package ru.dmitrii.serialization.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceImpl implements Service {

    @Override
    public double doHardWork(String string, int i) {
        double result=0;
        for (int j = 0; j < i; j++) {
            result += j;
        }
        return result;
    }

    @Override
    public List<String> run(String item, double value, Date date) {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < value; i++) {
            strings.add(item + i + date);
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
