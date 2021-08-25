package ru.dmitrii.homework03_generics.pecs;

import java.util.*;

public class CollectionUtils {

    //вернуть новый лист
    public static <T> List<T> newArrayList() {
        return new ArrayList<T>();
    }

    // ернуть индекс элемента
    public static <T> int indexOf(List<? extends T> source, T t) {
        return source.indexOf(t);
    }

    // изменить размер листа на size
    public static <T> List<? extends T> limit(List<? extends T> source, int size) {
        List<T> list = new ArrayList<>(size);
        list.addAll(source);
        return list;
    }

    // добавить элемент в список
    public static <T> void add(List<? super T> source, T t) {
        source.add(t);
    }

    // добавить всё из первого диста во второй
    public static <T> void addAll(List<? extends T> source, List<? super T> destination) {
        destination.addAll(source);
    }

    // удалить из первого листа все элементы второго
    public static <T> void removeAll(List<? super T> removeFrom, List<? extends T> c2) {
        removeFrom.removeAll(c2);
    }

    //true если первый лист содержит все элементы второго
    public static <T> boolean containsAll(List<? extends T> c1, List<? extends T> c2) {
        return c1.containsAll(c2);
    }

    //true если первый лист содержит хотя-бы 1 второго
    public static <T> boolean containsAny(List<T> c1, List<T> c2) {
        for (T t : c2) {
            if (c1.equals(t)) return true;
        }
        return false;
    }

    //Возвращает лист, содержащий элементы из входного листа в диапазоне от min до max.
    public static <T extends Comparable<? super T>> List<T> range(List<T> list, T min, T max) {
        Collections.sort(list);
        return list.subList(list.indexOf(min), list.indexOf(max)+1);
    }

    //Возвращает лист, содержащий элементы из входного листа в диапазоне от min до max (+ Comporator)
    public static <T extends Comparable<? super T>> List<T> range
            (List<T> list, T min, T max, Comparator<T> comparator) {
        list.sort(comparator);
        return list.subList(list.indexOf(min), list.indexOf(max)+1);
    }

    public static void main(String[] args) {
        System.out.println(range(Arrays.asList(8,1,3,5,6, 4), 3, 6, Comparator.naturalOrder()));
    }
}


