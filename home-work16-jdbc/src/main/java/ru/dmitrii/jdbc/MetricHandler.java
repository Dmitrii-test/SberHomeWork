package ru.dmitrii.jdbc;

import ru.dmitrii.jdbc.inter.Cachable;
import ru.dmitrii.jdbc.inter.H2DB;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class MetricHandler implements InvocationHandler {
    private final Object delegate;
    private final Map<Integer, Long> cashmap = new HashMap<>();

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
    public Object invoke(Object proxy, Method method, Object[] args) {
        try {
            if (method.getAnnotation(Cachable.class).value() == H2DB.class) return invokeDB(method, args);
            return method.invoke(delegate, args);
        } catch (InvocationTargetException | IllegalAccessException e) {
            System.out.println("Ошибка вызова метода " + e.getMessage());
        }
        return null;
    }

    /**
     * Метод проверяющий есть ли значение в БД
     * @param method Method
     * @param args Object[]
     * @return Object
     */
    private Object invokeDB(Method method, Object[] args) {
        DataSource.createDb();
        Integer fib = (Integer) args[0];
        if (cashmap.containsKey(fib)) {
            System.out.println("Результат из кэша");
            return cashmap.get(fib);
        }
        Long fibonacyId = DataSource.findFibonacyId((Integer) args[0]);
        if (fibonacyId != -1) {
            System.out.println("Результат взят из БД");
            cashmap.put(fib, fibonacyId);
            return fibonacyId;
        }
        Long invoke = null;
        try {
            invoke = (Long) method.invoke(delegate, args);
            DataSource.createPerson((Integer) args[0], invoke);
            cashmap.put(fib, invoke);
            return invoke;
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.out.println("Ошибка вызова метода " + e.getMessage());
        }
        return invoke;
    }
}
