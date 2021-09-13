package ru.dmitrii.serialization.proxy;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.util.*;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class CacheProxy implements InvocationHandler {
    private Object delegate;
    private final Map<List<Object>, List<?>> cacheMapList = new HashMap<>();
    private final Map<List<Object>, Object> cacheMap = new HashMap<List<Object>, Object>();
    private CacheType cacheType;
    private String fileNamePrefix;
    private int listList;
    private Boolean zip;
    private List<Object> identityBy;

    public CacheProxy() {
    }


    /**
     * Метод возвращающий подменный обьект прокси
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
     * @param proxy Object
     * @param method Method
     * @param args Object[]
     * @return Object
     * @throws Throwable Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            if (method.isAnnotationPresent(Cache.class)) {
                cacheType = method.getAnnotation(Cache.class).cacheType();
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
    private List<Object> getClasses(Method method, Object[] args) {
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
        cacheMap.put(identityBy, rezult);
        return rezult;
    }

    /**
     * Для методов помеченных @Cache(CacheType.Ram) сохраняем результаты в ОЗУ и если надо берем результаты из него
     *
     * @param method Method
     * @param args   Object[]
     * @return List<?>
     * @throws InvocationTargetException InvocationTargetException
     * @throws IllegalAccessException    IllegalAccessException
     */
    private List<?> invokeRamList(Method method, Object[] args)
            throws InvocationTargetException, IllegalAccessException {
        if (cacheMapList.containsKey(identityBy)) {
            System.out.println("Ответ взят из кэша Листа");
            return cacheMapList.get(identityBy);
        }
        List<?> rezult = (List<?>) method.invoke(delegate, args);
        if (listList != 0) rezult = rezult.subList(0, listList - 1);
        cacheMapList.put(identityBy, rezult);
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
        Map<List<Object>, Object> map = new HashMap<>();
        if (file.length() > 0) map = readFile(file);
        if (map.containsKey(identityBy)) {
            System.out.println("Результат взят из файла");
            return map.get(identityBy);
        }
        Object rez = method.invoke(delegate, args);
        map.put(identityBy, rez);
        if (zip) writeZipObject(map, file);
        else writeObject(map, file);
        return rez;
    }

    /**
     * Метод возвращающий лист из файлов
     * @param method Method
     * @param args Object[]
     * @return List<?>
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private List<?> invokeFileList(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        File file = checkFile();
        Map<List<Object>, List<?>> map = new HashMap<>();
        if (file.length() > 0) map = readFileList(file);
        if (map.containsKey(identityBy)) {
            System.out.println("Результат взят из файла");
            return map.get(identityBy);
        }
        List<?> rez = (List<?>) method.invoke(delegate, args);
        map.put(identityBy, rez);
        writeList(map, file);
        return rez;
    }


    /**
     * Метод чтения файла, результат возвращается map
     *
     * @param file File
     * @return Map<Key, Object>
     */
    private Map<List<Object>, Object> readFile(File file) {
        Map<List<Object>, Object> map = new HashMap<>();
        if (zip) {
            map = getZipObject(file, map);
        } else {
            map = getFileObject(file, map);
        }
        return map;
    }

    /**
     * Метод чтения листа из  файла
     * @param file File
     * @return Map<List<Object>, List<?>>
     */
    private Map<List<Object>, List<?>> readFileList(File file) {
        Map<List<Object>, List<?>> map = new HashMap<>();
        if (zip) {
            map = getZipList(file, map);
        } else {
            map = getFileList(file, map);
        }
        return map;
    }

    /**
     * Метод получения обьекта из файла
     * @param file File
     * @param map  Map<List<Object>, Object>
     * @return Map<List<Object>
     */
    private Map<List<Object>, Object> getFileObject(File file, Map<List<Object>, Object> map) {
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            map = (Map<List<Object>, Object>) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Ошибка чтения файла " + e.getMessage());
        }
        return map;
    }

    /**
     * Метод получающий обьект из zip
     * @param file File
     * @param map Map<List<Object>, Object>
     * @return Map<List<Object>, Object>
     */
    private Map<List<Object>, Object> getZipObject(File file, Map<List<Object>, Object> map) {
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
             ObjectInputStream ois = new ObjectInputStream(zipInputStream)) {
            map = (Map<List<Object>, Object>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка чтения файла zip" + e.getMessage());
        }
        return map;
    }


    /**
     * Метод получающий лист из файла
     * @param file File
     * @param map Map<List<Object>, List<?>>
     * @return Map<List<Object>, List<?>>
     */
    private Map<List<Object>, List<?>> getFileList(File file, Map<List<Object>, List<?>> map) {
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            map = (Map<List<Object>, List<?>>) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Ошибка чтения файла " + e.getMessage());
        }
        return map;
    }

    /**
     * Метод возвращающий лист из zip
     * @param file File
     * @param map Map<List<Object>, List<?>>
     * @return Map<List<Object>, List<?>>
     */
    private Map<List<Object>, List<?>> getZipList(File file, Map<List<Object>, List<?>> map) {
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
             ObjectInputStream ois = new ObjectInputStream(zipInputStream)) {
            map = (Map<List<Object>, List<?>>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка чтения листа файла zip" + e.getMessage());
        }
        return map;
    }

    /**
     * Метод записывающий map в файл
     *
     * @param map  Map<Key, Long>
     * @param file File
     */
    private void writeObject(Map<List<Object>, Object> map, File file) {
        if (zip) {
            writeZipObject(map, file);
        } else {
            writeFileObject(map, file);
        }
    }

    private void writeFileObject(Map<List<Object>, Object> map, File file) {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos);) {
            oos.writeObject(map);
            oos.flush();
        } catch (IOException e) {
            System.out.println("Ошибка записи Object в файл" + e.getMessage());
        }
    }

    private void writeZipObject(Map<List<Object>, Object> map, File file) {
        try {
            ZipOutputStream outputStream = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream(file.toPath())));
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            out.writeObject(map);

        } catch (IOException e) {
            System.out.println("Ошибка записи Object в файл Zip" + e.getMessage());
        }
    }

    /**
     * Метод записывающий map в файл
     *
     * @param map  Map<Key, Long>
     * @param file File
     */
    private void writeList(Map<List<Object>, List<?>> map, File file) {
        if (zip) {
            writeZipList(map, file);
        } else {
            writeFileList(map, file);
        }
    }

    private void writeFileList(Map<List<Object>, List<?>> map, File file) {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos);) {
            oos.writeObject(map);
            oos.flush();
        } catch (IOException e) {
            System.out.println("Ошибка записи листа в файл");
        }
    }

    private void writeZipList(Map<List<Object>, List<?>> map, File file) {
        try (ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream((file)));
             ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {
            oos.writeObject(map);
            oos.flush();
        } catch (IOException e) {
            System.out.println("Ошибка записи листа в файл Zip");
        }
    }
}
