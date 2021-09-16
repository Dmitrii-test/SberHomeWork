package ru.dmitrii.streams;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Streams<T> {
    private List<T> list;
    private Iterator<T> iterator;
    private Predicate<? super T> pridicate;
    private Function<? super T, ? extends T> mapper;

    public Streams(List<T> list) {
        iterator = Spliterators.iterator(list.spliterator());
        this.list = list;
    }

    public static <T> Streams<T> of(List<T> list) {
        return new Streams<T>(list);
    }

    public Streams<T> filter(Predicate<? super T> predicate) {
        this.pridicate = predicate;
        return this;
    }

    public Streams <T> transform(Function<? super T, ? extends T> mapper) {
        this.mapper = mapper;
        return this;
    }

    public <R> Map <R, T>  toMap(Function<? super T, ? extends R> key, Function<? super T, ? extends T> value) {
        return list.stream().filter(pridicate).map(mapper).collect(Collectors.toMap(key, value));
    }
}
