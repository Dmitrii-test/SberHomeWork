package ru.dmitrii.iterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        MyIterator<Integer> iterator = new MyIterator<>(list);
        while (iterator.hasNext()) {
            System.out.println("Индекс след " + iterator.nextIndex());
            System.out.println(iterator.next());
        }
        while (iterator.hasPrevious()) {
            System.out.println("Индекс пред " + iterator.previousIndex());
            System.out.println(iterator.previous());
        }

    }
}
