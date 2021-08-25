package ru.dmitrii.homework01_main.sort;

import java.util.Arrays;

public class Sorter {
    public static void main(String[] args) {
        int[] array = {10,2,15,24,29,19,15};
        System.out.println("Неотсортированный массив: " + Arrays.toString(array));
        bubbleSorter(array);
        System.out.println("Массив после сортировки: " + Arrays.toString(array));
        int index = binarySearch(array,19,0, array.length-1);
        System.out.println("Индекс числа 19 равен " + index);
    }


    /**
     * Сортировка пузырьком с защитой от лишнего прохода.
     */
    public static void  bubbleSorter(int[] array){
        boolean isSorted = false;
        // Проверяем необходимость сортировки
        while (!isSorted) {
            isSorted = true;
            for (int i = 1; i < array.length; i++) {
                // Если хоть раз пришлось поменять элементы местами - проходим маассив еще раз
                if (array[i] < array[i - 1]) {
                    int temp = array[i - 1];
                    array[i - 1] = array[i];
                    array[i] = temp;
                    isSorted = false;
                }
            }
        }
    }

    /**
     * Бинарный поиск
     */
    public static int binarySearch(int[] array, int key, int low, int high) {
        // запоминаем индекс центра массива
        int middle = low + (high - low) / 2;
        // если подмассив пустой значит элемент не найден
        if (low > high) {
            return -1;
        }
        // если элемент в центре равняется ключу возвращаем индекс
        if (key == array[middle]) {
            return middle;
        } else if (key < array[middle]) {
            // начинаем поиск в левом подмассиве
            return binarySearch(array, key, low, middle - 1);
        } else {
            // начинаем поиск в правомо помассиве
            return binarySearch(array, key, middle + 1, high);
        }
    }
}
