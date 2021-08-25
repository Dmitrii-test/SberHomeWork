package ru.dmitrii.homework03_generics.countmap;

import java.util.Map;

public interface CountMap <T, K extends Integer> {

    // добавляет элемент в этот контейнер.
    void add(T t);

    //Возвращает количество добавлений данного элемента
    int getCount(T t);

    //Удаляет элемент и контейнера и возвращает количество его добавлений(до удаления)
    int remove(T t);

    //количество разных элементов
    int size();

    /*Добавить все элементы из source в текущий контейнер,
     при совпадении ключей,     суммировать значения */
    void addAll(CountMap<? extends T, K> source);

    /*Вернуть java.util.Map. ключ - добавленный элемент,
      значение - количество его добавлений */
    Map<T, K> toMap();

    //Тот же самый контракт как и toMap(), только всю информацию записать в destination
    void toMap(Map<? super T, K> destination);

}
