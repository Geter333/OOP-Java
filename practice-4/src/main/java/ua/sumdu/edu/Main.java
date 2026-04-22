package ua.sumdu.edu;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Phone> inventory = new ArrayList<>();
        boolean running = true;

        System.out.println("Вітаємо в системі обліку пристроїв (Версія 8)!");

        while (running) {
            System.out.println("\n--- ГОЛОВНЕ МЕНЮ ---");
            System.out.println("1. Створити новий об'єкт");
            System.out.println("2. Вивести інформацію про всі об'єкти");
            System.out.println("3. Завершити роботу програми");
            System.out.print("Оберіть дію: ");

            String mainChoice = scanner.nextLine().trim();

            switch (mainChoice) {
                case "1":
                    handleCreationMenu(scanner, inventory);
                    break;
                case "2":
                    displayInventory(inventory);
                    break;
                case "3":
                    System.out.println("Роботу завершено. До побачення!");
                    running = false;
                    break;
                default:
                    System.out.println("Помилка: Невідома команда.");
            }
        }
        scanner.close();
    }

    private static void handleCreationMenu(Scanner scanner, ArrayList<Phone> inventory) {
        System.out.println("\n--- МЕНЮ СТВОРЕННЯ ---");
        System.out.println("Оберіть тип пристрою:");
        System.out.println("1. Звичайний телефон (Phone)");
        System.out.println("2. Смартфон (SmartPhone)");
        System.out.println("3. Кнопковий телефон (KeypadPhone)");
        System.out.println("4. Ігровий смартфон (GamingPhone)");
        System.out.println("5. Складаний смартфон (FoldablePhone)");
        System.out.println("0. Повернутися до головного меню");
        System.out.print("Ваш вибір: ");

        String choice = scanner.nextLine().trim();

        if (choice.equals("0")) {
            return;
        }

        try {
            int type = Integer.parseInt(choice);
            if (type < 1 || type > 5) {
                System.out.println("Помилка: Невірний тип пристрою.");
                return;
            }

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

            Phone newPhone = null;

            switch (type) {
                case 1:
                    newPhone = new Phone(brand, model, storage, price, osType);
                    break;
                case 2:
                    System.out.print("Чи підтримує 5G? (true/false): ");
                    boolean has5G = Boolean.parseBoolean(scanner.nextLine().trim());
                    newPhone = new SmartPhone(brand, model, storage, price, osType, has5G);
                    break;
                case 3:
                    System.out.print("Чи є ліхтарик? (true/false): ");
                    boolean hasFlash = Boolean.parseBoolean(scanner.nextLine().trim());
                    newPhone = new KeypadPhone(brand, model, storage, price, osType, hasFlash);
                    break;
                case 4:
                    System.out.print("Чи підтримує 5G? (true/false): ");
                    boolean gaming5G = Boolean.parseBoolean(scanner.nextLine().trim());
                    System.out.print("Введіть частоту оновлення екрана (Гц): ");
                    int refreshRate = Integer.parseInt(scanner.nextLine().trim());
                    newPhone = new GamingPhone(brand, model, storage, price, osType, gaming5G, refreshRate);
                    break;
                case 5:
                    System.out.print("Чи підтримує 5G? (true/false): ");
                    boolean fold5G = Boolean.parseBoolean(scanner.nextLine().trim());
                    System.out.print("Введіть кількість екранів: ");
                    int screens = Integer.parseInt(scanner.nextLine().trim());
                    newPhone = new FoldablePhone(brand, model, storage, price, osType, fold5G, screens);
                    break;
            }

            if (newPhone != null) {
                inventory.add(newPhone);
                System.out.println("Успіх! Пристрій створено та додано до бази.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Помилка вводу: Очікувалось числове значення.");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка валідації даних або невідома ОС: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Непередбачувана помилка: " + e.getMessage());
        }
    }

    private static void displayInventory(ArrayList<Phone> inventory) {
        if (inventory.isEmpty()) {
            System.out.println("\nКолекція порожня. Спочатку додайте об'єкти.");
        } else {
            System.out.println("\n--- Всі пристрої в базі ---");
            for (Phone p : inventory) {
                System.out.println(p.toString());
            }
        }
    }
}