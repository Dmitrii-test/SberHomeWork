package ru.dmitrii.encrypclassloader;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        String path = "./home-work07-classloaders/Plugins";
        File dir = new File(path);
        EncryptedClassLoader loader = new EncryptedClassLoader("123", dir, null);
        try {
            Class<?> test = loader.findClass("Test");
        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка получения "); e.printStackTrace();
        }

    }
}
