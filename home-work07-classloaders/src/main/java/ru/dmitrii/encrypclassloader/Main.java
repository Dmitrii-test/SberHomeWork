package ru.dmitrii.encrypclassloader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String path = "./home-work07-classloaders/Plugins";
        File dir = new File(path);
        EncryptedClassLoader loader = new EncryptedClassLoader(12, dir, null);
        loader.cryptedClass("Test");
        try {
            Class<?> test = loader.findClass("Test");
            Object o = test.getConstructor().newInstance();
            Method method = test.getMethod("print", String.class );
            method.invoke(o, "Привет");
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            System.out.println("Ошибка получения "+ e.getMessage());
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.out.println("Ошибка запуска метода " + e.getMessage());
        } catch (InstantiationException e) {
            System.out.println("Ошибка создания объекта " + e.getMessage());
        }

    }
}
