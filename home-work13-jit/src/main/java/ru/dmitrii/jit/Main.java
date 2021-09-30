package ru.dmitrii.jit;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 100000 ; i++) {
            map.put(i, "value " + i);
        }
    }
}
