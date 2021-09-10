package ru.dmitrii.encrypclassloader;

import java.io.*;

public class EncryptedClassLoader extends ClassLoader {
    private final int key;
    private final File dir;

    public EncryptedClassLoader(int key, File dir, ClassLoader parent) {
        super(parent);
        this.key = key;
        this.dir = dir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> result;
        String pathname = dir + "/" + name + ".crp";
        File f = new File(pathname);
        System.out.println("Файл найден - " + f.exists());
        byte[] classBytes = loadFileAsBytes(f);
        result = defineClass(name, classBytes, 0,classBytes.length);
        System.out.println("Класс "+ result.getName() + " загружен.");
        return result;
    }

    public byte[] loadFileAsBytes(File file) throws ClassNotFoundException {
        if (!file.exists()) throw new ClassNotFoundException("Файл не доступен");
        byte[] result = new byte[(int) file.length()];
        try (FileInputStream f = new FileInputStream(file)) {
            int i=-1;
            int j = 0;
            while((i=f.read())!=-1){
                result[j++] = (byte) (i+key);
//                result[j++] = (byte) (i);
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла " + e.getMessage());
        }
        return result;
    }

    public void cryptedClass (String name) {
        String pathname = dir + "/" + name + ".class";
        File out = new File(dir+"/" + name + ".crp");
        File in = new File(pathname);
        try (FileOutputStream fileOut = new FileOutputStream(out);
             FileInputStream fileIn = new FileInputStream(in)) {
            int i = -1;
            while ((i = fileIn.read()) != -1) {
                fileOut.write(i-key);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Класс " + name + " зашифрован");
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }
}


