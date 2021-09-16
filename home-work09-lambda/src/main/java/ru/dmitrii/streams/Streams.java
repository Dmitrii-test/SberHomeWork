package ru.dmitrii.streams;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class Streams<T> {
    private List<T> list;
    Queue<Method> methodQueue = new LinkedList<>();
    Queue<Predicate<? super T>> predicateQueue = new LinkedList<>();
    Queue<Function<? super T, ? extends T>> functionQueue = new LinkedList<>();

    public Streams(List<T> list) {
        this.list = list;
    }

    public static <T> Streams<T> of(List<T> list) {
        return new Streams<T>(list);
    }

    public Streams<T> filter(Predicate<? super T> predicate) {
        predicateQueue.add(predicate);
        try {
            Method method = Streams.class.getDeclaredMethod("filterImp", Predicate.class);
            methodQueue.add(method);
            System.out.println("Метод добавлен " + method.getName());
        } catch (NoSuchMethodException e) {
            System.out.println("Ошибка получения метода " + e.getMessage());
        }
        return this;
    }

    private Streams<T> filterImp(Predicate<? super T> predicate) {
        List<T> result = new ArrayList<>();
        for (T element : list) {
            if (predicate.test(element)) {
                result.add(element);
            }
        }
        list = result;
        return this;
    }

    public Streams<T> transform(Function<? super T, ? extends T> mapper) {
        functionQueue.add(mapper);
        try {
            Method method = this.getClass().getDeclaredMethod("transformImpl", Function.class);
            methodQueue.add(method);
            System.out.println("Метод добавлен " + method.getName());
        } catch (NoSuchMethodException e) {
            System.out.println("Ошибка получения метода " + e.getMessage());
        }
        return this;
    }

    private Streams<T> transformImpl(Function<? super T, ? extends T> mapper) {
        List<T> result = new ArrayList<>();
        for (T t : this.list) {
            result.add(mapper.apply(t));
        }
        this.list = result;
        return this;
    }

    public <R> Map<R, T> toMap(Function<? super T, ? extends R> key, Function<? super T, ? extends T> value) {
        while (!methodQueue.isEmpty()) {
            Method method = methodQueue.poll();
            try {
                if (method.getName().equals("filterImp")) method.invoke(this, predicateQueue.poll());
                else if (method.getName().equals("transformImpl")) method.invoke(this, functionQueue.poll());
            } catch (IllegalAccessException | InvocationTargetException e) {
                System.out.println("Ошибка запуска метода " + e.getMessage());
            }
        }
        Map<R, T> map = new HashMap<>();
        for (T element : list) {
            map.put(key.apply(element), value.apply(element));
        }
        return map;
    }
}
