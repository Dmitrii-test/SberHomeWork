package ru.dmitrii.concurrent.utils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Утилитный класс для работы с файлами
 */
public abstract class FileHandler {

    /**
     * Метод чтения файла, результат возвращается map
     *
     * @param file File
     * @param zip  boolean
     * @return Map<List < ?>, List<?>>
     */
    public static Map<List<?>, List<?>> readFile(File file, boolean zip) {
        Map<List<?>, List<?>> map = new HashMap<>();
        if (!zip) {
            map = getFileObject(file, map);
        } else {
            map = getZipObject(file, map);
        }
        return map;
    }

    /**
     * Метод получения обьекта из файла
     *
     * @param file File
     * @param map  Map<List<?>, List<?>>
     * @return Map<List < ?>, List<?>>
     */
    private static Map<List<?>, List<?>> getFileObject(File file, Map<List<?>, List<?>> map) {
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            map = (Map<List<?>, List<?>>) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Ошибка чтения файла " + e.getMessage());
        }
        return map;
    }

    /**
     * Метод получающий обьект из zip
     *
     * @param file File
     * @param map  Map<List<?>, List<?>>
     * @return Map<List < ?>, List<?>>
     */
    private static synchronized Map<List<?>, List<?>> getZipObject(File file, Map<List<?>, List<?>> map) {
        try (GZIPInputStream inputStream = new GZIPInputStream(new FileInputStream(file));
             ObjectInputStream ois = new ObjectInputStream(inputStream)) {
            map = (Map<List<?>, List<?>>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка чтения файла zip" + e.getMessage());
        }
        return map;
    }


    /**
     * Метод чтения листа из  файла
     *
     * @param file File
     * @return Map<List < ?>, List<?>>
     */
    public static Map<List<?>, List<?>> readFileList(File file, boolean zip) {
        Map<List<?>, List<?>> map = new HashMap<>();
        if (!zip) {
            map = getFileList(file, map);
        } else {
            map = getZipList(file, map);
        }
        return map;
    }


    /**
     * Метод получающий лист из файла
     *
     * @param file File
     * @param map  Map<List<?>, List<?>>
     * @return Map<List < Object>, List<?>>
     */
    private static Map<List<?>, List<?>> getFileList(File file, Map<List<?>, List<?>> map) {
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            map = (Map<List<?>, List<?>>) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Ошибка чтения файла " + e.getMessage());
        }
        return map;
    }

    /**
     * Метод возвращающий лист из zip
     *
     * @param file File
     * @param map  Map<List<?>, List<?>>
     * @return Map<List < ?>, List<?>>
     */
    private static Map<List<?>, List<?>> getZipList(File file, Map<List<?>, List<?>> map) {
        try (GZIPInputStream inputStream = new GZIPInputStream(new FileInputStream(file));
             ObjectInputStream ois = new ObjectInputStream(inputStream)) {
            map = (Map<List<?>, List<?>>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка чтения листа файла zip" + e.getMessage());
        }
        return map;
    }

    /**
     * Метод записывающий обьект
     *
     * @param map  Map<List<?>, List<?>
     * @param file File
     * @param zip  boolean
     */
    public static void writeObject(Map<List<?>, List<?>> map, File file, boolean zip) {
        if (!zip) {
            writeFileObject(map, file);
        } else {
            writeZipObject(map, file);
        }
    }

    /**
     * Метод для записи обьекта в файл
     *
     * @param map  Map<List<?>, List<?>>
     * @param file File
     */
    private static synchronized void writeFileObject(Map<List<?>, List<?>> map, File file) {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(map);
            oos.flush();
        } catch (IOException e) {
            System.out.println("Ошибка записи Object в файл" + e.getMessage());
        }
    }

    /**
     * Метод записывающий обьект в zip
     *
     * @param map  Map<List<?>, List<?>>
     * @param file File
     */
    private static synchronized void writeZipObject(Map<List<?>, List<?>> map, File file) {
        try (GZIPOutputStream outputStream = new GZIPOutputStream(new FileOutputStream(file));
             ObjectOutputStream out = new ObjectOutputStream(outputStream)) {
            out.writeObject(map);
            out.flush();
        } catch (IOException e) {
            System.out.println("Ошибка записи Object в файл Zip" + e.getMessage());
        }
    }

    /**
     * Метод записывающий лист
     *
     * @param map  Map<List<?>, List<?>>
     * @param file File
     * @param zip  boolean
     */
    public static synchronized void writeList(Map<List<?>, List<?>> map, File file, boolean zip) {
        if (!zip) {
            writeFileList(map, file);
        } else {
            writeZipList(map, file);
        }
    }

    /**
     * Метод записывающий лист в файл
     *
     * @param map  Map<List<?>, List<?>>
     * @param file File
     */
    private static void writeFileList(Map<List<?>, List<?>> map, File file) {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos);) {
            oos.writeObject(map);
            oos.flush();
        } catch (IOException e) {
            System.out.println("Ошибка записи листа в файл" + e.getMessage());
        }
    }

    /**
     * Метод записывающий лист в zip
     *
     * @param map  Map<List<?>, List<?>
     * @param file File
     */
    private static synchronized void writeZipList(Map<List<?>, List<?>> map, File file) {
        try (GZIPOutputStream outputStream = new GZIPOutputStream(new FileOutputStream(file));
             ObjectOutputStream out = new ObjectOutputStream(outputStream)) {
            out.writeObject(map);
            out.flush();
        } catch (IOException e) {
            System.out.println("Ошибка записи листа в файл Zip" + e.getMessage());
        }
    }
}
