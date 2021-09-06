package ru.dmitrii.homework05_reflection.calculator;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class MetricHandler implements InvocationHandler {
    private final Object delegate;
    private final Map<Key,Long> cacheMap = new HashMap<>();
    private final String path = "./map.txt";


    public MetricHandler(Object delegate) {
        this.delegate = delegate;
    }

    /**
     * Метод создающий обьект прокси
     * @return Object
     */
    public Object newInstance() {
        return Proxy.newProxyInstance(delegate.getClass().getClassLoader(),
                delegate.getClass().getInterfaces(),this);
    }

    /**
     * Перехват методов у делегата
     * @param proxy Object
     * @param method Method
     * @param args Object
     * @return Object
     */
    @Override
    public Long invoke(Object proxy, Method method, Object[] args) {
        try {
            if (method.isAnnotationPresent(Metric.class)) return invokeTimer(method, args);
            if (method.isAnnotationPresent(Cache.class)) {
                if (method.getAnnotation(Cache.class).cache() == CacheType.FILE) return invokeFile(method, args);
                else if (method.getAnnotation(Cache.class).cache() == CacheType.RAM) return invokeRam(method, args);
            }
            return (Long) method.invoke(delegate, args);
        } catch (InvocationTargetException | IllegalAccessException e) {
            System.out.println("Ошибка вызова метода " + e.getMessage());
        }
        return null;
    }

    /**
     * Для методов помеченных @Metric вычисляем время выполнения
     * @param method Method
     * @param args Object[]
     * @return Object
     * @throws InvocationTargetException InvocationTargetException
     * @throws IllegalAccessException IllegalAccessException
     */
    private Long invokeTimer(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        long start = System.nanoTime();
        Long rez;
        if (method.isAnnotationPresent(Cache.class) && method.getAnnotation(Cache.class).cache() == CacheType.RAM)
            rez = invokeRam(method, args);
        else if (method.isAnnotationPresent(Cache.class) && method.getAnnotation(Cache.class).cache() == CacheType.FILE)
            rez = invokeFile(method, args);
        else rez = (Long) method.invoke(delegate, args);
        System.out.printf("Время выполнения метода: %d нс\n", (System.nanoTime() - start));
        return rez;
    }

    /**
     * Для методов помеченных @Cache(CacheType.Ram) сохраняем результаты в ОЗУ и если надо берем результаты из него
     * @param method Method
     * @param args Object[]
     * @return Object
     * @throws InvocationTargetException InvocationTargetException
     * @throws IllegalAccessException IllegalAccessException
     */
    private Long invokeRam(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        Key key = new Key(method.getName(), args);
        if (cacheMap.containsKey(key)) {
            System.out.println("Ответ взят из кэша");
            return cacheMap.get(key);
        }
        Long rez = (Long) method.invoke(delegate, args);
        cacheMap.put(key, rez);
        return rez;
    }

    /**
     * Для методов помеченных @Cache(CacheType.File) сохраняем результаты в файл и если надо берем результаты из него
     * @param method Method
     * @param args Object[]
     * @return Object
     * @throws InvocationTargetException InvocationTargetException
     * @throws IllegalAccessException IllegalAccessException
     */
    private Long invokeFile(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        Map<Key, Long> map = new HashMap<>();
        Key key = new Key(method.getName(), args);
        File file = checkFile();
        if(file.length()>0) map = readFile(map, file);
        if (map.containsKey(key)) {
            System.out.println("Результат взят из файла");
            return map.get(key);
        }
        Long rez = (Long) method.invoke(delegate, args);
        map.put(key, rez);
        writeMap(map, file);
        return rez;
    }

    /**
     * Метод проверки наличия файла и создания его
     * @return File
     */
    private File checkFile() {
        File file = new File(path);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) System.out.println("Файл создан");
            } catch (IOException e) {
                System.out.println("Файл не создан");
            }
        }
        return file;
    }

    /**
     * Метод чтения файла, результат возвращается map
     * @param map Map<Key, Object>
     * @param file File
     * @return Map<Key, Object>
     */
    @SuppressWarnings("unchecked")
    private Map<Key, Long> readFile(Map<Key, Long> map, File file) {
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            map = (Map<Key, Long>) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Ошибка чтения файла " + e.getMessage());
        }
        return map;
    }

    /**
     * Метод записывающий map в файл
     * @param map Map<Key, Long>
     * @param file File
     */
    private void writeMap(Map<Key, Long> map, File file) {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos);) {
            oos.writeObject(map);
            oos.flush();
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл");
        }
    }
}
