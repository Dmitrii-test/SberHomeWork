package ru.dmitrii.gc;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        A a;
        List<A> list = new ArrayList<>();
        while (true) {
            a = new A();
            list.add(new A());
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                System.out.println("Ошибка ожидания " + e.getMessage());
            }
        }
    }

    static class A {

    }
}
