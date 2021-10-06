package ru.dmitrii.gof;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game {
    private final static Tractor tractor= new Tractor();
    private final static char[][] field = new char[5][5];

    public static void main(String[] args) {
        repaint();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.println("Введите команду для трактора: ");
                System.out.printf("Направление движения %s %n", tractor.getOrientation().name);
                System.out.println("F - двигаться, T - повернуть, E - стоп");
                String command = reader.readLine();
                if (!command.equalsIgnoreCase("F") && !command.equalsIgnoreCase("T")
                        && !command.equalsIgnoreCase("E")) {
                    System.out.println("Не правильная команда");
                    continue;
                }
                if (command.equalsIgnoreCase("E")) break;
                tractor.move(command);
                repaint();
            }
        } catch (TractorInDitchException e) {
            System.out.println("Достигнута граница поля");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Game over");
    }

    /**
     * Метод изменения координат трактора
     */
    private static void repaint() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (i==tractor.getPositionX() && j == tractor.getPositionY()) field[j][i] = 'X';
                else field[j][i] = 'O';
            }
        }
        print();
    }

    /**
     * Метод отображения поля на экране
     */
    private static void print() {
        for (int i = field.length-1; i >= 0 ; i--) {
            for (char anAnArr : field[i]) {
                System.out.print(anAnArr + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
