package ru.dmitrii.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.URL;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("ru.dmitrii.spring");
        context.refresh();
        Downloader downloader = context.getBean(Downloader.class);
        ReaderURL readerURL = context.getBean(ReaderURL.class);
        Queue<URL> strings = readerURL.readURL("D:\\1.txt");
        try {
            downloader.limitDownloadFile(strings.peek(), "D:\\1\\");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
