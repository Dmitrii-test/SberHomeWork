package ru.dmitrii.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.URL;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("ru.dmitrii.spring");
        context.refresh();
        Downloader downloader = context.getBean(Downloader.class);
        ReaderURL readerURL = context.getBean(ReaderURL.class);
        Queue<URL> strings = readerURL.readURL("D:\\1.txt");
        ExecutorService exec = Executors.newFixedThreadPool(3);
        strings.forEach(n -> exec.submit(() -> {
            try {
                downloader.limitDownloadFile(n, "D:\\1\\");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        try {
            exec.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        exec.shutdown();
    }
}
