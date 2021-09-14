package ru.dmitrii.serialization.proxy;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

import static ru.dmitrii.serialization.utils.FileHandler.*;

public class CacheProxy implements InvocationHandler {
    private Object delegate;
    private final Map<List<?>, List<?>> cacheMap = new HashMap<>();
    private String fileNamePrefix;
    private int listList;
    private Boolean zip;
    private List<?> identityBy;

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
                System.out.println(identityBy);
                if (cacheType == CacheType.FILE) {
                    if (method.getReturnType() == List.class) return invokeFileList(method, args);
                    else return invokeFile(method, args);
                }
                if (cacheType == CacheType.IN_MEMORY) {
                    if (method.getReturnType() == List.class) return invokeRamList(method, args);
                    else return invokeRam(method, args);
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
        List<Object> objects = new ArrayList<>();
        Class<?>[] classes = method.getAnnotation(Cache.class).identityBy();
        for (int i = 0; i < classes.length; i++) {
            for (int j = 0; j < args.length; j++) {
                if (classes[i] == args[j].getClass()) {
                    objects.add(args[j]);
                }
            }
        }
        return objects;
    }


    /**
     * Для методов помеченных @Cache(CacheType.Ram) сохраняем результаты в ОЗУ и если надо берем результаты из него
     *
     * @param method Method
     * @param args   Object[]
     * @return Object
     * @throws InvocationTargetException InvocationTargetException
     * @throws IllegalAccessException    IllegalAccessException
     */
    private Object invokeRam(Method method, Object[] args)
            throws InvocationTargetException, IllegalAccessException {
        if (cacheMap.containsKey(identityBy)) {
            System.out.println("Ответ взят из кэша");
            return cacheMap.get(identityBy);
        }
        Object rezult = method.invoke(delegate, args);
        cacheMap.put(identityBy, Collections.singletonList(rezult));
        return rezult;
    }

    /**
     * Для методов помеченных @Cache(CacheType.IN_MEMORY) сохраняем результаты в ОЗУ и если надо берем результаты из него
     *
     * @param method Method
     * @param args   Object[]
     * @return List<?>
     * @throws InvocationTargetException InvocationTargetException
     * @throws IllegalAccessException    IllegalAccessException
     */
    private List<?> invokeRamList(Method method, Object[] args)
            throws InvocationTargetException, IllegalAccessException {
        if (cacheMap.containsKey(identityBy)) {
            System.out.println("Ответ взят из кэша Листа");
            return cacheMap.get(identityBy);
        }
        List<?> rezult = (List<?>) method.invoke(delegate, args);
        if (listList != 0 && rezult.size() > listList) rezult = rezult.subList(0, listList - 1);
        cacheMap.put(identityBy, rezult);
        return rezult;
    }

    /**
     * Метод проверки наличия файла и создания его
     *
     * @return File
     */
    private File checkFile() {
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
     * Для методов помеченных @Cache(CacheType.File) сохраняем результаты в файл и если надо берем результаты из него     *
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
     * @throws IllegalAccessException IllegalAccessException
     */
    private List<?> invokeFileList(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
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
