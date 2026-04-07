package ua.sumdu.edu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Store myStore = new Store("Sumy Tech Hub");
        Phone lastCreatedPhone = null;
        boolean running = true;

        System.out.println("Вітаємо в системі управління магазином!");

        while (running) {
            System.out.println("\n--- МЕНЮ ---");
            System.out.println("1. Створити новий смартфон");
            System.out.println("2. Скопіювати останній доданий смартфон (Конструктор копіювання)");
            System.out.println("3. Вивести асортимент магазину (Агрегація)");
            System.out.println("4. Показати загальну статистику (Статичний метод)");
            System.out.println("5. Завершити роботу");
            System.out.print("Оберіть дію: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    lastCreatedPhone = createNewPhone(scanner);
                    if (lastCreatedPhone != null) {
                        myStore.addPhoneToInventory(lastCreatedPhone);
                    }
                    break;
                case "2":
                    if (lastCreatedPhone == null) {
                        System.out.println("Спочатку створіть хоча б один смартфон!");
                    } else {
                        Phone copiedPhone = new Phone(lastCreatedPhone);
                        myStore.addPhoneToInventory(copiedPhone);
                        System.out.println("Смартфон успішно скопійовано та додано до складу.");
                    }
                    break;
                case "3":
                    myStore.showInventory();
                    break;
                case "4":
                    System.out.println("\n--- Статистика ---");
                    System.out.println("Загалом створено об'єктів Phone: " + Phone.getPhoneCount());
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

    private static Phone createNewPhone(Scanner scanner) {
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

            Phone phone = new Phone(brand, model, storage, price, osType);
            System.out.println("Успіх! Смартфон створено.");
            return phone;

        } catch (NumberFormatException e) {
            System.out.println("Помилка вводу: Очікувалось числове значення.");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка валідації даних або невідома ОС: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Непередбачувана помилка: " + e.getMessage());
        }
        return null;
    }
}