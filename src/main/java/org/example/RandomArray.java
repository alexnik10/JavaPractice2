package org.example;

import java.util.Random;
import java.util.Arrays;
import java.util.Scanner;

public class RandomArray {

    private int[] array;

    public RandomArray(int n) {
        if (n > 0) {
            array = new int[n];
            fillArrayWithRandomNumbers();
        } else {
            array = new int[0];
        }
    }

    private void fillArrayWithRandomNumbers() {
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(2001) - 1000; // числа в диапазоне [-1000, 1000]
        }
    }

    public double calculateAverage() {
        if (array.length == 0) {
            return 0;
        }
        int sum = 0;
        for (int num : array) {
            sum += num;
        }
        return (double) sum / array.length;
    }

    public static void execute(Scanner scanner) {
        System.out.print("    Задание 1.\n");
        System.out.print("Введите количество элементов в массиве: ");
        int n = scanner.nextInt();
        if (n < 0) {
            System.out.println("Размер массива не может быть отрицательным.");
        } else {
            RandomArray randomArray = new RandomArray(n);
            System.out.println("Массив: " + Arrays.toString(randomArray.array));
            System.out.printf("Среднее значение: %.2f\n", randomArray.calculateAverage());
        }
    }

}