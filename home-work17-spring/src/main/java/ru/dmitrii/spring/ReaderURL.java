package ru.dmitrii.spring;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class ReaderURL {

    public Queue<URL> readURL(String path) {
        Queue<URL> paths = new ConcurrentLinkedQueue<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String inputLine = null;
            while ((inputLine=reader.readLine())!= null)
                paths.offer(new URL(inputLine));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paths;
    }



}
