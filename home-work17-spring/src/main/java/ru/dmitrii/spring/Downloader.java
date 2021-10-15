package ru.dmitrii.spring;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import ru.dmitrii.spring.streams.LimitInputStream;
import ru.dmitrii.spring.streams.Limiter;

import java.io.*;
import java.net.URL;

@Service
public class Downloader {

    /**
     * Метод загружающий файлы из интернета
     * @param from URL
     * @param to String
     * @throws IOException IOException
     */
    public void downloadFile(URL from, String to) throws IOException {
        File uploadFile = new File(to + FilenameUtils.getName(from.getPath()));
        if (uploadFile.createNewFile()) {
            System.out.printf("Файл %s создал %s%n", uploadFile.getName(), Thread.currentThread().getName());
        }
        try (BufferedInputStream fileInputStream = new BufferedInputStream(from.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(uploadFile)) {
            byte[] bytes = new byte[1024];
            int read;
            while ((read = fileInputStream.read(bytes, 0, 1024)) != -1) {
                fileOutputStream.write(bytes,0,read);
            }
        }
        System.out.printf("Файл %s скачал %s%n", uploadFile.getName(), Thread.currentThread().getName());
    }

    /**
     * Метод загружающий файлы из интернета с огриничением скорости
     * @param from Метод загружающий файлы из интернета
     * @param to String
     * @throws IOException IOException
     */
    public void limitDownloadFile(URL from, String to) throws IOException {
        File uploadFile = new File(to + FilenameUtils.getName(from.getPath()));
        if (uploadFile.createNewFile()) {
            System.out.printf("Файл %s создал %s%n", uploadFile.getName(), Thread.currentThread().getName());
        }
        try (BufferedInputStream fileInputStream = new BufferedInputStream(from.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(uploadFile)) {
            LimitInputStream limitInputStream = new LimitInputStream(fileInputStream, new Limiter(500));
            byte[] bytes = new byte[1024];
            int read;
            while ((read = fileInputStream.read(bytes, 0, 1024)) != -1) {
                fileOutputStream.write(bytes,0,read);
            }
        }
        System.out.printf("Файл %s скачал %s%n", uploadFile.getName(), Thread.currentThread().getName());
    }
}
