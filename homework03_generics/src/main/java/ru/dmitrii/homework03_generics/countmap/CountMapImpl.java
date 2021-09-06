package ru.dmitrii.homework03_generics.countmap;

import java.util.HashMap;
import java.util.Map;

public class CountMapImpl<K> implements CountMap<K, Integer> {

    Map <K,Integer> map = new HashMap<>();

    @Override
    public void add(K k) {
        map.merge(k, 1, Integer::sum);
    }

    @Override
    public int getCount(K k) {
        return map.getOrDefault(k, 0);
    }

    @Override
    public int remove(K k) {
        return map.remove(k);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void addAll(CountMap<? extends K, Integer> source) {
        for (K k : source.toMap().keySet()) {
            map.merge(k, 1, Integer::sum);
        }
    }

    @Override
    public Map<K, Integer> toMap() {
        return  map;
    }

    @Override
    public void toMap(Map<? super K, Integer> destination) {
        destination.clear();
        destination.putAll(map);
    }


    public static void main(String[] args) {
        CountMap<Integer,Integer> map = new CountMapImpl<>();
        map.add(10);
        map.add(10);
        map.add(5);
        map.add(5);
        map.add(10);
        System.out.println("Удалил элемент 5, count был = " + map.remove(5));
        System.out.println("Осталось элементов 5 - " + map.getCount(5)); // 2
        System.out.println("Осталось элементов 10 - " + map.getCount(10)); // 3
        System.out.println( "Размер: " + map.size());
        CountMap<Integer,Integer> map2 = new CountMapImpl<>();
        map.add(10);
        map.add(10);
        map.addAll(map2);
        System.out.println("После добавления 10 count стал = " + map.getCount(10));
        Map<Number,Integer> map3 = new HashMap<>();
        map.toMap(map3);
        System.out.println("В новой мапе 10 - " + map3.get(10));

    }
}
