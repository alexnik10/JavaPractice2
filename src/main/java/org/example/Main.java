package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        RandomArray.execute(scanner);
        BubbleSortList.execute(scanner);
        Employee.execute();
        HttpGetExample.execute();
        scanner.nextLine();
        FolderSyncTask.execute(scanner);

        scanner.close();
    }
}