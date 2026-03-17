package ua.sumdu.edu;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Phone> phoneList = new ArrayList<>();

        System.out.println("Введення даних для смартфонів:");

        for (int i = 0; i < 5; i++) {
            System.out.println("\nТелефон #" + (i + 1));
            System.out.print("Модель: ");
            String model = scanner.nextLine();
            System.out.print("Об'єм: ");
            int storage = scanner.nextInt();
            System.out.print("Ціна: ");
            double price = scanner.nextDouble();
            scanner.nextLine();

            phoneList.add(new Phone(model, storage, price));
        }

        for (Phone p : phoneList) {
            System.out.println(p);
        }
        scanner.close();
    }
}