package ru.dmitrii.spring;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import ru.dmitrii.spring.streams.LimitInputStream;
import ru.dmitrii.spring.streams.Limiter;

import java.io.*;
import java.net.URL;

@Service
public class Downloader {

    public void downloadFile(URL from, String to) throws IOException {
        File uploadFile = new File(to + FilenameUtils.getName(from.getPath()));
        uploadFile.createNewFile();
        try (BufferedInputStream fileInputStream = new BufferedInputStream(from.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(uploadFile)) {
            byte[] bytes = new byte[1024];
            int read = fileInputStream.read(bytes, 0, 1024);
            while (read != -1) {
                fileOutputStream.write(bytes);
                read = fileInputStream.read(bytes, 0, 1024);
            }
        }
    }


    public void limitDownloadFile(URL from, String to) throws IOException {
        File uploadFile = new File(to + FilenameUtils.getName(from.getPath()));
        boolean newFile = uploadFile.createNewFile();
        try (BufferedInputStream fileInputStream = new BufferedInputStream(from.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(uploadFile)) {
            LimitInputStream limitInputStream = new LimitInputStream(fileInputStream, new Limiter(1024));
            byte[] bytes = new byte[1024];
            int read = limitInputStream.read(bytes, 0, 1024);
            while (read != -1) {
                fileOutputStream.write(bytes);
                read = limitInputStream.read(bytes, 0, 1024);
            }
        }
    }
}
