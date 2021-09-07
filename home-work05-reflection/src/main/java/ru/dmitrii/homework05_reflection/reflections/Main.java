package ru.dmitrii.homework05_reflection.reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        TestImpl test = new TestImpl();
        System.out.println("Parent: "+ test.getClass().getSuperclass().getSimpleName());
        Arrays.stream(test.getClass().getInterfaces()).forEach(n -> System.out.println("Interfaces: " + n.getSimpleName()));
        Method[] methods = test.getClass().getMethods();
        Method[] methods1 = test.getClass().getDeclaredMethods();
        System.out.println("Methods: ");
        for (Method m : methods) {
            System.out.println("Name method: " + m.getName() +
                    ", modifier: " + m.getModifiers() + ". parametrs: " + Arrays.toString(m.getParameterTypes()));
        }
        System.out.println("-----------------------------");
        System.out.println("Declared methods: ");
        for (Method m : methods1) {
            System.out.println("Name method: " + m.getName() +
                    ", modifier: " + m.getModifiers() + ". parametrs: " + Arrays.toString(m.getParameterTypes()));
        }
        System.out.println("-----------------------------");
        for (Method m : methods) {
            if (m.getName().startsWith("get")) System.out.println("Method Get - " + m.getName());
        }
        System.out.println("-----------------------------");
        Field[] fields = test.getClass().getFields();
        System.out.println("Fields: ");
        for (Field f : fields) {
            System.out.println("Name field: " + f.getName() +
                    ", modifier: " + f.getModifiers());
            try {
                if (f.getModifiers()==25 && !f.getName().equals(f.get(test)))
                    System.out.println("Ошибка значения поля - " + f.getName());;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
