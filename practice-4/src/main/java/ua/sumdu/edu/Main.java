package ua.sumdu.edu;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Main {
    private static final String FILE_NAME = "input.json";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Store store = new Store();
        loadFromJson(store, FILE_NAME);
        boolean running = true;

        System.out.println("Вітаємо в системі обліку пристроїв (Версія 11 - Склад/Store)!");

        while (running) {
            System.out.println("\n--- ГОЛОВНЕ МЕНЮ ---");
            System.out.println("1. Пошук об'єкта");
            System.out.println("2. Додати товар на склад");
            System.out.println("3. Вивести інформацію про всі товари");
            System.out.println("4. Зберегти дані та завершити роботу");
            System.out.print("Оберіть дію: ");

            String mainChoice = scanner.nextLine().trim();

            switch (mainChoice) {
                case "1":
                    handleSearchMenu(scanner, store);
                    break;
                case "2":
                    handleCreationMenu(scanner, store);
                    break;
                case "3":
                    displayInventory(store);
                    break;
                case "4":
                    saveToJson(store, FILE_NAME);
                    System.out.println("Дані успішно збережено у файл. До побачення!");
                    running = false;
                    break;
                default:
                    System.out.println("Помилка: Невідома команда.");
            }
        }
        scanner.close();
    }

    private static void handleSearchMenu(Scanner scanner, Store store) {
        System.out.println("\n--- МЕНЮ ПОШУКУ ---");
        System.out.println("1. Пошук за брендом");
        System.out.println("2. Пошук за операційною системою");
        System.out.println("3. Пошук за діапазоном ціни");
        System.out.println("0. Повернутися до головного меню");
        System.out.print("Оберіть критерій пошуку: ");

        String choice = scanner.nextLine().trim();

        if (choice.equals("0")) {
            return;
        }

        if (store.getInventory().isEmpty()) {
            System.out.println("Склад порожній. Пошук неможливий.");
            return;
        }

        ArrayList<Phone> searchResults = new ArrayList<>();

        try {
            switch (choice) {
                case "1":
                    System.out.print("Введіть назву бренду: ");
                    String brand = scanner.nextLine().trim();
                    searchResults = store.searchByBrand(brand);
                    break;
                case "2":
                    System.out.print("Оберіть ОС (ANDROID, IOS, HARMONY_OS, OTHER): ");
                    String osInput = scanner.nextLine().trim().toUpperCase();
                    OsType osType = OsType.valueOf(osInput);
                    searchResults = store.searchByOsType(osType);
                    break;
                case "3":
                    System.out.print("Введіть мінімальну ціну: ");
                    double minPrice = Double.parseDouble(scanner.nextLine().trim());
                    System.out.print("Введіть максимальну ціну: ");
                    double maxPrice = Double.parseDouble(scanner.nextLine().trim());
                    searchResults = store.searchByPriceRange(minPrice, maxPrice);
                    break;
                default:
                    System.out.println("Помилка: Невідомий критерій пошуку.");
                    return;
            }

            if (searchResults.isEmpty()) {
                System.out.println("\nЖоден об'єкт не відповідає умовам пошуку.");
            } else {
                System.out.println("\n--- Результати пошуку ---");
                for (Phone p : searchResults) {
                    System.out.println(p.toString());
                }
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Помилка вводу даних для пошуку: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Непередбачувана помилка: " + e.getMessage());
        }
    }

    private static void handleCreationMenu(Scanner scanner, Store store) {
        System.out.println("\n--- МЕНЮ ДОДАВАННЯ ТОВАРУ ---");
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
                System.out.print("Введіть кількість одиниць цього товару для додавання: ");
                int quantity = Integer.parseInt(scanner.nextLine().trim());
                if (quantity <= 0) {
                    System.out.println("Помилка: Кількість має бути більшою за нуль.");
                    return;
                }
                store.addNewPhone(newPhone, quantity);
                System.out.println("Успіх! Товар додано на склад.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Помилка вводу: Очікувалось числове значення.");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка валідації даних або невідома ОС: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Непередбачувана помилка: " + e.getMessage());
        }
    }

    private static void displayInventory(Store store) {
        ArrayList<StoreItem> inventory = store.getInventory();
        if (inventory.isEmpty()) {
            System.out.println("\nСклад порожній. Спочатку додайте товари.");
        } else {
            System.out.println("\n--- Всі товари на складі ---");
            for (StoreItem item : inventory) {
                System.out.println(item.toString());
            }
        }
    }

    private static void loadFromJson(Store store, String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return;
        }

        try (FileReader reader = new FileReader(file)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            if (jsonElement.isJsonNull() || !jsonElement.isJsonArray()) {
                return;
            }

            JsonArray jsonArray = jsonElement.getAsJsonArray();
            Gson gson = new Gson();

            for (JsonElement element : jsonArray) {
                JsonObject obj = element.getAsJsonObject();

                if (!obj.has("phone") || !obj.has("quantity")) continue;

                int quantity = obj.get("quantity").getAsInt();
                JsonObject phoneObj = obj.getAsJsonObject("phone");

                if (!phoneObj.has("type")) continue;
                String type = phoneObj.get("type").getAsString();

                Phone p = null;
                switch (type) {
                    case "Phone": p = gson.fromJson(phoneObj, Phone.class); break;
                    case "SmartPhone": p = gson.fromJson(phoneObj, SmartPhone.class); break;
                    case "KeypadPhone": p = gson.fromJson(phoneObj, KeypadPhone.class); break;
                    case "GamingPhone": p = gson.fromJson(phoneObj, GamingPhone.class); break;
                    case "FoldablePhone": p = gson.fromJson(phoneObj, FoldablePhone.class); break;
                }
                if (p != null) {
                    store.addNewPhone(p, quantity);
                }
            }
        } catch (Exception e) {
            System.out.println("Помилка читання JSON: " + e.getMessage());
        }
    }

    private static void saveToJson(Store store, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(store.getInventory(), writer);
        } catch (Exception e) {
            System.out.println("Помилка запису JSON: " + e.getMessage());
        }
    }
}