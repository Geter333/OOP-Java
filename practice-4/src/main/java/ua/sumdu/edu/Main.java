package ua.sumdu.edu;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Phone> inventory = new ArrayList<>();
        boolean running = true;

        System.out.println("Вітаємо в системі обліку пристроїв!");

        while (running) {
            System.out.println("\n--- МЕНЮ ---");
            System.out.println("1. Додати звичайний телефон (Phone)");
            System.out.println("2. Додати смартфон (SmartPhone)");
            System.out.println("3. Додати кнопковий телефон (KeypadPhone)");
            System.out.println("4. Вивести всі пристрої (Демонстрація поліморфізму)");
            System.out.println("5. Завершити роботу");
            System.out.print("Оберіть дію: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    Phone p = createPhone(scanner, 1);
                    if (p != null) inventory.add(p);
                    break;
                case "2":
                    Phone sp = createPhone(scanner, 2);
                    if (sp != null) inventory.add(sp);
                    break;
                case "3":
                    Phone kp = createPhone(scanner, 3);
                    if (kp != null) inventory.add(kp);
                    break;
                case "4":
                    displayInventory(inventory);
                    break;
                case "5":
                    System.out.println("Роботу завершено. До побачення!");
                    running = false;
                    break;
                default:
                    System.out.println("Помилка: Невідома команда.");
            }
        }
        scanner.close();
    }

    private static Phone createPhone(Scanner scanner, int type) {
        try {
            System.out.print("Введіть бренд: ");
            String brand = scanner.nextLine();

            System.out.print("Введіть модель: ");
            String model = scanner.nextLine();

            System.out.print("Оберіть ОС (ANDROID, IOS, HARMONY_OS, OTHER): ");
            String osInput = scanner.nextLine().trim().toUpperCase();
            OsType osType = OsType.valueOf(osInput);

            System.out.print("Введіть об'єм пам'яті (ГБ): ");
            int storage = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Введіть ціну: ");
            double price = Double.parseDouble(scanner.nextLine().trim());

            if (type == 1) {
                System.out.println("Успіх! Телефон створено.");
                return new Phone(brand, model, storage, price, osType);
            } else if (type == 2) {
                System.out.print("Чи підтримує 5G? (true/false): ");
                boolean has5G = Boolean.parseBoolean(scanner.nextLine().trim());
                System.out.println("Успіх! Смартфон створено.");
                return new SmartPhone(brand, model, storage, price, osType, has5G);
            } else if (type == 3) {
                System.out.print("Чи є ліхтарик? (true/false): ");
                boolean hasFlashlight = Boolean.parseBoolean(scanner.nextLine().trim());
                System.out.println("Успіх! Кнопковий телефон створено.");
                return new KeypadPhone(brand, model, storage, price, osType, hasFlashlight);
            }

        } catch (NumberFormatException e) {
            System.out.println("Помилка вводу: Очікувалось числове значення.");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка валідації даних або невідома ОС: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Непередбачувана помилка: " + e.getMessage());
        }
        return null;
    }

    private static void displayInventory(ArrayList<Phone> inventory) {
        if (inventory.isEmpty()) {
            System.out.println("Список порожній.");
        } else {
            System.out.println("\n--- Всі пристрої ---");
            for (Phone p : inventory) {
                System.out.println(p.toString());
            }
        }
    }
}