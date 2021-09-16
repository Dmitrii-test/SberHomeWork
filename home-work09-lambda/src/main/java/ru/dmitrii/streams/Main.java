package ru.dmitrii.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Person> someCollection =
                Arrays.asList(new Person("Piter", 30),
                        new Person("Bob", 20),
                        new Person("Jon", 55),
                        new Person("Tomy", 15));
        Map<String, Person> m = Streams.of(someCollection)
//                .filter(p -> p.getAge() > 20)
                .filter(p -> p.getName().length()>3)
                .transform(p -> new Person(p.getName(),p.getAge() + 30))
                .toMap(p -> p.getName(), p -> p);
        m.forEach((k,v)-> System.out.println("Key: " + k + ",value: " + v));

    }
}
