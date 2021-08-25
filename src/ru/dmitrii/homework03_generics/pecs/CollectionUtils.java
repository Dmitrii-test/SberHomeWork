package ru.dmitrii.homework03_generics.pecs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CollectionUtils  {
    public static <T> void addAll(List<? extends T> source, List<? super T> destination) {
        destination.addAll(source);
    }

    public static <T> List <T> newArrayList() {
        return new ArrayList<T>();
    }

    public static <T> int indexOf( List <? extends T> source, T t) {
        return source.indexOf(t);
    }

    public static <T> List <? extends T> limit(List <? extends T> source, int size) {
        List<T> list = new ArrayList<>(size);
        list.addAll(source);
        return list;
    }

    public static <T> void add(List<? super T> source,  T t) {

    }

    public static <T> void removeAll(List<T> removeFrom, List<T> c2) { }

    public static <T> boolean containsAll(List<T> c1, List<T> c2) {
        return false;
    }

    public static <T> boolean containsAny(List <T> c1, List <T> c2) {
        return false;
    }

    public static <T> List<T> range(List<T> list, T min, T max) {
        return list;
    }

    public static <T> List <T> range(List<T> list, T min, T max, Comparator comparator) {
        return list;
    }
}


