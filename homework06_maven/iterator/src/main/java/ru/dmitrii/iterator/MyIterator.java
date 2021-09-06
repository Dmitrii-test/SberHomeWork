package ru.dmitrii.iterator;

import java.util.List;
import java.util.ListIterator;

public class MyIterator <T> implements ListIterator <T> {
    List<T> list;
    private int pos;

    public MyIterator(List<T> list) {
        if (list==null) throw new NullPointerException("Лист не проинициализирован");
        else this.list = list;
        this.pos = 0;
    }

    public boolean hasNext() {
        return pos < list.size();
    }

    public T next() {
        if (!hasNext())
            throw new IndexOutOfBoundsException("Нет больше элементов");
        else return list.get(pos++);
    }

    public boolean hasPrevious() {
        return pos-1 >= 0;
    }

    public T previous() {
        if (!hasPrevious())
            throw new IndexOutOfBoundsException("Нет предыдущего элемента");
        else return list.get(--pos);
    }

    public int nextIndex() {
        return pos+1;
    }

    public int previousIndex() {
        return pos-1;
    }

    public void remove() {
        if (hasNext()) list.remove(pos);
        else throw new IndexOutOfBoundsException("Нет элемента для удаления");
    }

    public void set(T t) {
        if (pos>=0) list.set(pos, t);
        else throw new IndexOutOfBoundsException("Нет элемента для замены");
    }

    public void add(T t) {
        if (pos>=0) list.add(pos, t);
        else throw new IndexOutOfBoundsException("Нет элементов в листе");
    }
}
