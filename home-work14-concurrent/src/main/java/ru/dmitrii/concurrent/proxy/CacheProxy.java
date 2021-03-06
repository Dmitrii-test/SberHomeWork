package ru.dmitrii.concurrent.proxy;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import static ru.dmitrii.concurrent.utils.FileHandler.*;

public class CacheProxy implements InvocationHandler {
    private Object delegate;
    private final ConcurrentHashMap<List<?>, List<?>> cacheMap = new ConcurrentHashMap<>();
    private String fileNamePrefix;
    private volatile int listList;
    private volatile Boolean zip;
    private volatile List<?> identityBy;

    public CacheProxy() {
    }


    /**
     * Метод возвращающий подменный обьект прокси
     *
     * @param delegate Object
     * @return Object
     */
    public Object cache(Object delegate) {
        this.delegate = delegate;
        return Proxy.newProxyInstance(delegate.getClass().getClassLoader(),
                delegate.getClass().getInterfaces(), this);
    }


    /**
     * Перехват методов
     *
     * @param proxy  Object
     * @param method Method
     * @param args   Object[]
     * @return Object
     * @throws Throwable Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            if (method.isAnnotationPresent(Cache.class)) {
                CacheType cacheType = method.getAnnotation(Cache.class).cacheType();
                fileNamePrefix = method.getAnnotation(Cache.class).fileNamePrefix();
                listList = method.getAnnotation(Cache.class).listList();
                zip = method.getAnnotation(Cache.class).zip();
                identityBy = getClasses(method, args);
                if (cacheType == CacheType.FILE) {
                    if (method.getReturnType() == List.class) return invokeFileList(method, args);
                    else return invokeFile(method, args);
                }
                if (cacheType == CacheType.IN_MEMORY) {
                    if (method.getReturnType() == List.class) return invokeRamList(method, args);
                    else return invokeRam(method, args).get(0);
                }
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            System.out.println("Ошибка вызова метода " + e.getMessage());
        }
        return method.invoke(delegate, args);
    }


    /**
     * Метод определения идентификации метода
     *
     * @param method Method
     * @param args   Class<?>
     * @return Class<?>
     */
    private List<?> getClasses(Method method, Object[] args) {
        Class<?>[] classes = method.getAnnotation(Cache.class).identityBy();
        List<Object> collect = Arrays.stream(args).filter(u -> Arrays.asList(classes).contains(u.getClass())).collect(Collectors.toList());
        System.out.println("Определяющие обьекты: " + collect);
        return collect;
    }


    /**
     * Для методов помеченных @Cache(CacheType.IN_MEMORY) сохраняем результаты в ОЗУ и если надо берем результаты из него
     *
     * @param method Method
     * @param args   Object[]
     * @return Object

     */
    private List<?> invokeRam(Method method, Object[] args) {
        return cacheMap.compute(identityBy, (key, value) -> {
            if (value != null) {
                System.out.println(Thread.currentThread().getName() + " ответ взял из кэша");
                return value;
            }
            Object rezult = null;
            try {
                rezult = method.invoke(delegate, args);
                System.out.println(Thread.currentThread().getName() + " ответ вычислил");
            } catch (IllegalAccessException | InvocationTargetException e) {
                System.out.println("Ошибка вызова метода " + e.getMessage());
            }
            return Collections.singletonList(rezult);
        });
    }

    /**
     * Для методов помеченных @Cache(CacheType.IN_MEMORY) сохраняем результаты в ОЗУ и если надо берем результаты из него
     *
     * @param method Method
     * @param args   Object[]
     * @return List<?>

     */
    private List<?> invokeRamList(Method method, Object[] args) {
        return cacheMap.compute(identityBy, (key, value) -> {
            if (value != null) {
                System.out.println(Thread.currentThread().getName() + " ответ взял из кэша");
                return value;
            }
            List<?> rezult = null;
            try {
                rezult = (List<?>) method.invoke(delegate, args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                System.out.println("Ошибка вызова метода " + e.getMessage());
            }
            if (listList != 0 && Objects.requireNonNull(rezult).size() > listList)
                rezult = rezult.subList(0, listList - 1);
            return rezult;
        });
    }

    /**
     * Метод проверки наличия файла и создания его
     *
     * @return File
     */
    private synchronized File checkFile() {
        File file;
        if (zip) {
            file = new File(fileNamePrefix + ".zip");
        } else file = new File(fileNamePrefix + ".map");
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
     * Для методов помеченных @Cache(CacheType.File) сохраняем результаты в файл и если надо берем результаты из него
     *
     * @param method Method
     * @param args   Object[]
     * @return Object
     * @throws InvocationTargetException InvocationTargetException
     * @throws IllegalAccessException    IllegalAccessException
     */
    private Object invokeFile(Method method, Object[] args)
            throws InvocationTargetException, IllegalAccessException {
        File file = checkFile();
        Map<List<?>, List<?>> map = new HashMap<>();
        if (file.length() > 0) map = readFile(file, zip);
        if (map.containsKey(identityBy)) {
            System.out.println("Обьект взят из файла");
            List<?> objects = map.get(identityBy);
            return objects.get(0);
        }
        Object rez = method.invoke(delegate, args);
        map.put(identityBy, Collections.singletonList(rez));
        writeObject(map, file, zip);
        return rez;
    }

    /**
     * Метод возвращающий лист из файлов
     *
     * @param method Method
     * @param args   Object[]
     * @return List<?>
     * @throws InvocationTargetException InvocationTargetException
     * @throws IllegalAccessException    IllegalAccessException
     */
    private List<?> invokeFileList(Method method, Object[] args) throws
            InvocationTargetException, IllegalAccessException {
        File file = checkFile();
        Map<List<?>, List<?>> map = new HashMap<>();
        if (file.length() > 0) map = readFileList(file, zip);
        if (map.containsKey(identityBy)) {
            System.out.println("Лист взят из файла");
            return map.get(identityBy);
        }
        List<?> rez = (List<?>) method.invoke(delegate, args);
        map.put(identityBy, rez);
        writeList(map, file, zip);
        return rez;
    }
}
