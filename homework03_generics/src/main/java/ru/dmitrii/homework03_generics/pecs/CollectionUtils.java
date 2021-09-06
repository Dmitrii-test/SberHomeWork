package ru.dmitrii.homework03_generics.pecs;

import java.util.*;

public class CollectionUtils {

    /**
     * Вернуть новый лист
     */
    public static <T> List<T> newArrayList() {
        return new ArrayList<T>();
    }

    /**
     * Вернуть индекс элемента
     */
    public static <T> int indexOf(List<T> source, T t) {
        return source.indexOf(t);
    }

    /**
     * Изменить размер листа на size
     * @return List
     */
    public static <T> List<T> limit(List<T> source, int size) {
        if (source.size()>size) return source;
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size-source.size(); i++) list.add(null);
        list.addAll(0, source);
        return list;
    }

    /**
     * Добавить элемент в список
     * @param source List
     * @param t T
     * @param <T> T
     */
    public static <T> void add(List<? super T> source, T t) {
        source.add(t);
    }

    /**
     * Добавить всё из первого диста во второй
     * @param source List
     * @param destination List
     * @param <T> T
     */
    public static <T> void addAll(List<? extends T> source, List<? super T> destination) {
        destination.retainAll(source);
    }

    /**
     * Удалить из первого листа все элементы второго
     * @param removeFrom List
     * @param c2 List
     * @param <T> T
     */
    public static <T> void removeAll(List<? super T> removeFrom, List<? extends T> c2) {
        removeFrom.removeAll(c2);
    }

    /**
     * True если первый лист содержит все элементы второго
     * @param c1 List
     * @param c2 List
     * @param <T> T
     * @return boolean
     */
    public static <T> boolean containsAll(List<? extends T> c1, List<? extends T> c2) {
        return c1.containsAll(c2);
    }

    /**
     * True если первый лист содержит хотя-бы 1 второго
     * @param c1 List
     * @param c2 List
     * @param <T> T
     * @return boolean
     */
    public static <T> boolean containsAny(List<? extends T> c1, List<? extends T> c2) {
        for (T t : c2) {
            if (c1.contains(t)) return true;
        }
        return false;
    }

    /**
     *  Возвращает лист, содержащий элементы из входного листа в диапазоне от min до max
     * @param list List
     * @param min T
     * @param max T
     * @param <T> T
     * @return List
     */
    public static <T extends Comparable<? super T>> List<T> range(List<T> list, T min, T max) {
        return range(list, min, max, Comparator.naturalOrder());
    }

    /**
     * Возвращает лист, содержащий элементы из входного листа в диапазоне от min до max (+ Comporator)
     * @param list List
     * @param min T
     * @param max T
     * @param comparator Comparator
     * @param <T> T
     * @return List
     */
    public static <T extends Comparable<? super T>> List<T> range
            (List<T> list, T min, T max, Comparator<T> comparator) {
        if (comparator.compare(min,max)>=0) {
            System.out.println("Элемент min не меньше max");
            return list;
        }
        List<T> listResult = list.subList(list.indexOf(min), list.indexOf(max) + 1);
        listResult.sort(comparator);
        return listResult;
    }

    public static void main(String[] args) {
        List<Integer> list = newArrayList();
        add(list, 8);
        add(list, 1);
        add(list, 3);
        add(list, 5);
        add(list, 6);
        add(list, 4);
        System.out.println("Индекс элемента 5 - "+indexOf(list, 5));
        System.out.println("Размер листа - "+list.size());
        List<Integer> limit = limit(list, 10);
        System.out.println("Новый размер листа - "+limit.size());
        System.out.println("Одинаковые ли два листа - "+containsAll(list, limit));
        System.out.println("Есть ли одинаковые элементы в двух листах - "+containsAny(list, limit));
        addAll(list, limit);
        System.out.println(range(list, 3, 6));
        System.out.println(range(list, 3, 6, Comparator.reverseOrder()));
    }
}


