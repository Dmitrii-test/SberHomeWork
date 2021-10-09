package ru.dmitrii.jdbc;

import ru.dmitrii.jdbc.inter.Cachable;
import ru.dmitrii.jdbc.inter.H2DB;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MetricHandler implements InvocationHandler {
    private final Object delegate;

    public MetricHandler(Object delegate) {
        this.delegate = delegate;
    }

    /**
     * Метод создающий обьект прокси
     *
     * @return Object
     */
    public Object newInstance() {
        return Proxy.newProxyInstance(delegate.getClass().getClassLoader(),
                delegate.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            if (method.getAnnotation(Cachable.class).value() == H2DB.class) return invokeDB(method, args);
            return (Long) method.invoke(delegate, args);
        } catch (InvocationTargetException | IllegalAccessException e) {
            System.out.println("Ошибка вызова метода " + e.getMessage());
        }
        return null;
    }

    private Object invokeDB(Method method, Object[] args) {
        DataSource.createDb();
        Long fibonacyId = DataSource.findFibonacyId((Integer) args[0]);
        if (fibonacyId != -1) {
            System.out.println("Результат взят из базы");
            return fibonacyId;
        }
        Long invoke = null;
        try {
            invoke = (Long) method.invoke(delegate, args);
            DataSource.createPerson((Integer) args[0], invoke);
            return invoke;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return invoke;
    }
}
