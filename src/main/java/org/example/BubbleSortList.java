package org.example;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class BubbleSortList {

    private ArrayList<Double> list;

    public BubbleSortList(int n) {
        list = new ArrayList<>();
        if (n >= 0) {
            fillListWithRandomNumbers(n);
        }
    }

    private void fillListWithRandomNumbers(int n) {
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            list.add(random.nextDouble() * 2001 - 1000);  // числа в диапазоне [-1000, 1000]
        }
    }

    public void bubbleSort() {
        int size = list.size();
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                if (list.get(j) > list.get(j + 1)) {
                    double temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }

    public void printList() {
        System.out.println(list);
    }

    public static void execute(Scanner scanner) {
        System.out.print("\n    Задание 2.\n");
        System.out.print("Введите количество элементов в списке: ");
        int n = scanner.nextInt();
        System.out.print(n);
        if (n < 0) {
            System.out.println("Размер списка не может быть отрицательным.");
        } else {
            BubbleSortList bubbleSortList = new BubbleSortList(n);
            System.out.println("Исходный список: ");
            bubbleSortList.printList();
            bubbleSortList.bubbleSort();
            System.out.println("Отсортированный список: ");
            bubbleSortList.printList();
        }
    }
}
