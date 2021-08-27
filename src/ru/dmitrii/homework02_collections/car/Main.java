package ru.dmitrii.homework02_collections.car;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Car[] arrayCars = {
                new Car("Лада", "седан"),
                new Car("Лада", "хэтчбек"),
                new Car("Мерседес", "седан"),
                new Car("Бмв", "кроссовер"),
                new Car("Форд", "хэтчбек"),
                new Car("Пежо", "кроссовер"),
                new Car("Пежо", "хэтчбек"),
                new Car("Тойота", "седан"),
                new Car("Тойота", "кроссовер")
        };
        Map<String, List<String>> mapCar = new HashMap<>();
        // Через стрим заполняем мапу
        Arrays.stream(arrayCars).forEach(car -> {
            if (!mapCar.containsKey(car.getType())) {
                List<String> value = new ArrayList<>();
                value.add(car.getModel());
                mapCar.put(car.getType(), value);
            } else mapCar.get(car.getType()).add(car.getModel());
        });
        mapCar.forEach((k, v) -> System.out.println(k.toUpperCase() + " - " + v));
    }
}


