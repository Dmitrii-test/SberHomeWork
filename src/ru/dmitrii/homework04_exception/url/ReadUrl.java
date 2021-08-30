package ru.dmitrii.homework04_exception.url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ReadUrl {

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String url = "";
        while (true) {
            System.out.println("Введите адрес сайта: ");
            try {
                url = reader.readLine();
            } catch (IOException e) {
                System.out.println("Ошибка ввода адреса");
            }
            try {
                readContent(url);
                return;
            } catch (MalformedURLException e) {
                System.out.println("URL введен не правильно " + e.getMessage());
            } catch (IOException e) {
                System.out.println("URL не доступен " + e.getMessage());
            }
        }
    }

    /**
     * Метод считывания сайта
     * @param url1 String
     */
    public static void readContent(String url1) throws IOException {
        URL url = new URL(url1);
        URLConnection connect = url.openConnection();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connect.getInputStream()))) {
            String inputLine;
            while ((inputLine = reader.readLine()) != null) System.out.println(inputLine);
        } catch (IOException e) {
            System.out.println("Ошибка считывания сайта" + e.getMessage());
        }
        System.out.println("Сайт считан");
    }
}
