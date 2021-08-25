package ru.dmitrii.homework02_collections.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ReadFile {

    public static void main(String[] args) {
        ArrayList<String> stringsList = new ArrayList<>();
        String FileName = "C:\\1.txt";
        // Множество номеров строк
        List<Integer> numbers = new ArrayList<>();
        //Создаем мапу с сортировкой по своему Компаратору
        Map<String, Integer> map = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int result = o1.length() - o2.length();

                if (result == 0) result = o1.compareTo(o2);
                return result;
            }
        });
        //Получаем имя файла
        FileName = getFilePath(FileName);

        //В try catch resourse считываем содержимое файла
        try (BufferedReader reader = new BufferedReader(new FileReader(FileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringsList.add(line);
                //Разделяем строку на слова
                String[] words = line.split("[,;:.!?\\s]+");
                Arrays.stream(words)
                        .forEach(n -> map.merge(n.toLowerCase(), 1, Integer::sum));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.forEach((k, v) -> System.out.println("Слово: \"" + k + "\" кол-во повторений: " + v));
        System.out.println("----------------");

        //Реверсируем строки через метод Collections
        Collections.reverse(stringsList);
        stringsList.forEach(System.out::println);
        Collections.reverse(stringsList);
        System.out.println("----------------");

        //Создаем свой итератор с обратным перебором
        Iterator<String> iteratorRevers = new Iterator<>() {
            private int currentIndex = stringsList.size() - 1;

            @Override
            public boolean hasNext() {
                return currentIndex >= 0;
            }

            @Override
            public String next() {
                return stringsList.get(currentIndex--);
            }
        };
        while (iteratorRevers.hasNext()) System.out.println(iteratorRevers.next());

        //Выводим строки заданые пользователем в произв порядке
        readerNumber(stringsList.size(), numbers);
        Collections.shuffle (numbers);
        numbers.forEach(n -> System.out.println("Номер: " + n + " строка: " + stringsList.get(n - 1)));
    }

    /**
     * В try catch resourse получаем номара строк
     */
    private static void readerNumber(int stringsListSize, List<Integer> numbers) {
        try (BufferedReader readerNumber = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Введите номера строк, для выхода введите - exit");
            while (true) {
                String str = readerNumber.readLine();
                if (str.equals("exit")) break;
                try {
                    int tmp = Integer.parseInt(str);
                    if (tmp > stringsListSize - 1 || tmp <= 0) System.out.println("Нет такого номера строки");
                    else numbers.add(tmp);
                } catch (NumberFormatException e) {
                    System.out.println("Не правильно введена цифра" + e);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка ввода номеров " + e);
            e.printStackTrace();
        }
    }

    /**
     * В try catch resourse получаем имя файла и пути
     */
    private static String getFilePath(String fileName) {
        BufferedReader readerFile = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите путь и имя файла:");
        try {
            fileName = readerFile.readLine();
        } catch (IOException e) {
            System.out.println("Ошибка ввода файла: " + e);
        }
        return fileName;
        //Поток не закрываю так-как он используется еще в другом методе
    }
}
