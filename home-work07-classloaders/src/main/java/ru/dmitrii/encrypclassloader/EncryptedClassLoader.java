package ru.dmitrii.encrypclassloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class EncryptedClassLoader extends ClassLoader {
    private final String key;
    private final File dir;

    public EncryptedClassLoader(String key, File dir, ClassLoader parent) {
        super(parent);
        this.key = key;
        this.dir = dir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> result;
        String pathname = dir + "/" + name + ".class";
        System.out.println(pathname);
        File f = new File(pathname);
        System.out.println(f.exists());
        byte[] classBytes = loadFileAsBytes(f);
        result = defineClass(name, classBytes, 0,classBytes.length);
        return result;
    }

    public static byte[] loadFileAsBytes(File file) throws ClassNotFoundException {
        if (!file.exists()) throw new ClassNotFoundException("Файл не доступен");
        byte[] result = new byte[(int) file.length()];
        try (FileInputStream f = new FileInputStream(file)) {
            f.read(result, 0, result.length);
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла " + e.getMessage());
        }
        return result;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }
}


