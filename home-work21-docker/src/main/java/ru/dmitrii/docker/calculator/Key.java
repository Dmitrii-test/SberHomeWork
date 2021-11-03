package ru.dmitrii.docker.calculator;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * Класс для хранения методов и их аргументов
 */
public class Key implements Serializable {
    private String method;
    private Object[] argum;

    public Key(String method, Object[] argum) {
        this.method = method;
        this.argum = argum;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object[] getArgum() {
        return argum;
    }

    public void setArgum(Object[] argum) {
        this.argum = argum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key key = (Key) o;
        return Objects.equals(method, key.method) &&
                Arrays.equals(argum, key.argum);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(method);
        result = 31 * result + Arrays.hashCode(argum);
        return result;
    }
}
