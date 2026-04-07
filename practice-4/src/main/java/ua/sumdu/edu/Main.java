package ua.sumdu.edu;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Phone> phoneList = new ArrayList<>();
        boolean running = true;

        System.out.println("Вітаємо в системі обліку смартфонів!");

        while (running) {
            System.out.println("\n--- МЕНЮ ---");
            System.out.println("1. Створити новий смартфон");
            System.out.println("2. Вивести інформацію про всі смартфони");
            System.out.println("3. Завершити роботу");
            System.out.print("Оберіть дію: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    createNewPhone(scanner, phoneList);
                    break;
                case "2":
                    displayPhones(phoneList);
                    break;
                case "3":
                    System.out.println("Роботу завершено. До побачення!");
                    running = false;
                    break;
                default:
                    System.out.println("Помилка: Невідома команда. Будь ласка, введіть 1, 2 або 3.");
            }
        }
        scanner.close();
    }

    private static void createNewPhone(Scanner scanner, ArrayList<Phone> phoneList) {
        try {
            System.out.print("Введіть бренд: ");
            String brand = scanner.nextLine();

            System.out.print("Введіть модель: ");
            String model = scanner.nextLine();

            System.out.print("Введіть об'єм пам'яті (ГБ): ");
            String storageInput = scanner.nextLine();
            int storage = Integer.parseInt(storageInput.trim());

            System.out.print("Введіть ціну: ");
            String priceInput = scanner.nextLine();
            double price = Double.parseDouble(priceInput.trim());

            Phone phone = new Phone(brand, model, storage, price);
            phoneList.add(phone);
            System.out.println("Успіх! Смартфон додано до списку.");

        } catch (NumberFormatException e) {
            System.out.println("Помилка вводу: Очікувалось числове значення для пам'яті або ціни. Спробуйте ще раз.");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка валідації даних: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Виникла непередбачувана помилка: " + e.getMessage());
        }
    }

    private static void displayPhones(ArrayList<Phone> phoneList) {
        if (phoneList.isEmpty()) {
            System.out.println("Список смартфонів порожній.");
        } else {
            System.out.println("\n--- Список ваших смартфонів ---");
            for (Phone p : phoneList) {
                System.out.println(p.toString());
            }
        }
    }
}