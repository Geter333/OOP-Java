package ua.sumdu.edu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.UUID;

public class AppRunner {

    private final Scanner scanner;
    private final Store store;
    private final DatabaseManager dbManager;
    private final JsonDataManager jsonManager;
    private static final String FILE_NAME = "input.json";

    public AppRunner(Scanner scanner, Store store, DatabaseManager dbManager, JsonDataManager jsonManager) {
        this.scanner = scanner;
        this.store = store;
        this.dbManager = dbManager;
        this.jsonManager = jsonManager;
    }

    public void run() {
        jsonManager.loadFromJson(store, FILE_NAME);
        boolean running = true;

        System.out.println("Вітаємо в системі обліку пристроїв (Версія 18 - Custom Exceptions)!");

        while (running) {
            System.out.println("\n--- ГОЛОВНЕ МЕНЮ ---");
            System.out.println("1. Пошук об'єкта");
            System.out.println("2. Додати товар на склад");
            System.out.println("3. Вивести інформацію про всі товари");
            System.out.println("4. Вивести відсортовану інформацію про всі товари");
            System.out.println("5. Модифікувати товар");
            System.out.println("6. Видалити товар");
            System.out.println("7. Зберегти дані та завершити роботу");
            System.out.print("Оберіть дію: ");

            String mainChoice = scanner.nextLine().trim();

            switch (mainChoice) {
                case "1":
                    handleSearchMenu();
                    break;
                case "2":
                    handleCreationMenu();
                    break;
                case "3":
                    displayInventory();
                    break;
                case "4":
                    displaySortedInventory();
                    break;
                case "5":
                    handleModificationMenu();
                    break;
                case "6":
                    handleDeletionMenu();
                    break;
                case "7":
                    jsonManager.saveToJson(store, FILE_NAME);
                    System.out.println("Дані успішно збережено у файл. До побачення!");
                    running = false;
                    break;
                default:
                    System.out.println("Помилка: Невідома команда.");
            }
        }
        scanner.close();
    }

    private void handleSearchMenu() {
        System.out.println("\n--- МЕНЮ ПОШУКУ ---");
        System.out.println("1. Пошук за брендом");
        System.out.println("2. Пошук за операційною системою");
        System.out.println("3. Пошук за діапазоном ціни");
        System.out.println("4. Пошук за UUID");
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

        try {
            switch (choice) {
                case "1":
                    System.out.print("Введіть назву бренду: ");
                    String brand = scanner.nextLine().trim();
                    ArrayList<Phone> searchResultsByBrand = store.searchByBrand(brand);
                    displaySearchResults(searchResultsByBrand);
                    break;
                case "2":
                    System.out.print("Оберіть ОС (ANDROID, IOS, HARMONY_OS, OTHER): ");
                    String osInput = scanner.nextLine().trim().toUpperCase();
                    OsType osType = OsType.valueOf(osInput);
                    ArrayList<Phone> searchResultsByOs = store.searchByOsType(osType);
                    displaySearchResults(searchResultsByOs);
                    break;
                case "3":
                    System.out.print("Введіть мінімальну ціну: ");
                    double minPrice = Double.parseDouble(scanner.nextLine().trim());
                    System.out.print("Введіть максимальну ціну: ");
                    double maxPrice = Double.parseDouble(scanner.nextLine().trim());
                    ArrayList<Phone> searchResultsByPrice = store.searchByPriceRange(minPrice, maxPrice);
                    displaySearchResults(searchResultsByPrice);
                    break;
                case "4":
                    System.out.print("Введіть UUID для пошуку: ");
                    String uuidInput = scanner.nextLine().trim();
                    UUID uuid = UUID.fromString(uuidInput);
                    Phone foundPhone = store.searchByUuid(uuid);
                    System.out.println("\n--- Знайдено об'єкт ---");
                    System.out.println(foundPhone);
                    break;
                default:
                    System.out.println("Помилка: Невідомий критерій пошуку.");
            }
        } catch (ObjectNotFoundException e) {
            System.out.println("Помилка пошуку: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка вводу даних для пошуку: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Непередбачувана помилка: " + e.getMessage());
        }
    }

    private void displaySearchResults(ArrayList<Phone> results) {
        if (results.isEmpty()) {
            System.out.println("\nЖоден об'єкт не відповідає умовам пошуку.");
        } else {
            System.out.println("\n--- Результати пошуку ---");
            for (Phone p : results) {
                System.out.println(p);
            }
        }
    }

    private void handleCreationMenu() {
        System.out.println("\n--- МЕНЮ ДОДАВАННЯ ТОВАРУ ---");
        System.out.println("Оберіть тип пристрою:");
        System.out.println("1. Звичайний телефон (BasicPhone)");
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

            Phone newPhone;

            switch (type) {
                case 1:
                    newPhone = new BasicPhone(brand, model, storage, price, osType);
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
                default:
                    // This case is unreachable due to the check above, but good practice
                    return;
            }

            System.out.print("Введіть кількість одиниць цього товару для додавання: ");
            int quantity = Integer.parseInt(scanner.nextLine().trim());
            if (quantity <= 0) {
                System.out.println("Помилка: Кількість має бути більшою за нуль.");
                return;
            }

            store.addNewPhone(newPhone, quantity);
            System.out.println("Успіх! Товар додано на склад (в пам'ять).");
            System.out.println(newPhone);
            dbManager.savePhone(newPhone, quantity);

        } catch (NumberFormatException e) {
            System.out.println("Помилка вводу: Очікувалось числове значення.");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка валідації даних: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Непередбачувана помилка: " + e.getMessage());
        }
    }

    private void displayInventory() {
        ArrayList<StoreItem> inventory = store.getInventory();
        if (inventory.isEmpty()) {
            System.out.println("\nСклад порожній. Спочатку додайте товари.");
        } else {
            System.out.println("\n--- Всі товари на складі ---");
            for (StoreItem item : inventory) {
                System.out.println(item);
            }
        }
    }

    private void displaySortedInventory() {
        ArrayList<StoreItem> inventory = store.getInventory();
        if (inventory.isEmpty()) {
            System.out.println("\nСклад порожній. Сортування неможливе.");
            return;
        }

        System.out.println("\n--- МЕНЮ СОРТУВАННЯ ---");
        System.out.println("Оберіть критерій сортування:");
        System.out.println("1. За об'ємом пам'яті (від меншого до більшого)");
        System.out.println("2. За ціною (від найдорожчих до найдешевших)");
        System.out.println("3. За назвою моделі (за алфавітом)");
        System.out.println("0. Повернутися в головне меню");
        System.out.print("Ваш вибір: ");

        String choice = scanner.nextLine().trim();
        ArrayList<StoreItem> sortedItems = new ArrayList<>(inventory);
        String sortCriteria;
        Comparator<StoreItem> comparator;

        switch (choice) {
            case "1":
                comparator = Comparator.comparingInt(item -> item.getPhone().getStorage());
                sortCriteria = "ЗА ОБ'ЄМОМ ПАМ'ЯТІ";
                break;
            case "2":
                comparator = (o1, o2) -> Double.compare(o2.getPhone().getPrice(), o1.getPhone().getPrice());
                sortCriteria = "ЗА ЦІНОЮ (ПО СПАДАННЮ)";
                break;
            case "3":
                comparator = (o1, o2) -> o1.getPhone().getModel().compareToIgnoreCase(o2.getPhone().getModel());
                sortCriteria = "ЗА НАЗВОЮ МОДЕЛІ";
                break;
            case "0":
                return;
            default:
                System.out.println("Помилка: Невідомий критерій сортування.");
                return;
        }

        sortedItems.sort(comparator);

        System.out.println("\n--- Всі товари на складі (ВІДСОРТОВАНО " + sortCriteria + ") ---");
        for (StoreItem item : sortedItems) {
            System.out.println(item);
        }
    }

    private void handleModificationMenu() {
        System.out.println("\n--- МЕНЮ МОДИФІКАЦІЇ ТОВАРУ ---");
        if (store.getInventory().isEmpty()) {
            System.out.println("Склад порожній. Модифікація неможлива.");
            return;
        }

        try {
            System.out.print("Введіть UUID телефону для модифікації: ");
            String uuidInput = scanner.nextLine().trim();
            UUID uuidToModify = UUID.fromString(uuidInput);

            Phone phoneToModify = store.searchByUuid(uuidToModify);

            System.out.println("\n--- Знайдено об'єкт ---");
            System.out.println(phoneToModify);

            System.out.println("\nОберіть атрибут для зміни:");
            System.out.println("1. Бренд");
            System.out.println("2. Модель");
            System.out.println("3. Об'єм пам'яті");
            System.out.println("4. Ціна");
            System.out.println("5. ОС");
            System.out.println("0. Скасувати");
            System.out.print("Ваш вибір: ");

            String choice = scanner.nextLine().trim();

            Phone tempPhone = new BasicPhone(phoneToModify.getBrand(), phoneToModify.getModel(),
                                          phoneToModify.getStorage(), phoneToModify.getPrice(),
                                          phoneToModify.getOsType());

            switch (choice) {
                case "1":
                    System.out.print("Введіть новий бренд: ");
                    tempPhone.setBrand(scanner.nextLine());
                    break;
                case "2":
                    System.out.print("Введіть нову модель: ");
                    tempPhone.setModel(scanner.nextLine());
                    break;
                case "3":
                    System.out.print("Введіть новий об'єм пам'яті (ГБ): ");
                    tempPhone.setStorage(Integer.parseInt(scanner.nextLine().trim()));
                    break;
                case "4":
                    System.out.print("Введіть нову ціну: ");
                    tempPhone.setPrice(Double.parseDouble(scanner.nextLine().trim()));
                    break;
                case "5":
                    System.out.print("Оберіть нову ОС (ANDROID, IOS, HARMONY_OS, OTHER): ");
                    tempPhone.setOsType(OsType.valueOf(scanner.nextLine().trim().toUpperCase()));
                    break;
                case "0":
                    System.out.println("Модифікацію скасовано.");
                    return;
                default:
                    System.out.println("Помилка: Невідомий атрибут.");
                    return;
            }

            store.update(uuidToModify, tempPhone);
            System.out.println("Об'єкт успішно модифіковано!");
            System.out.println("Нові дані: " + store.searchByUuid(uuidToModify));

        } catch (ObjectNotFoundException e) {
            System.out.println("Помилка модифікації: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка валідації даних: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Непередбачувана помилка: " + e.getMessage());
        }
    }

    private void handleDeletionMenu() {
        System.out.println("\n--- МЕНЮ ВИДАЛЕННЯ ТОВАРУ ---");
        if (store.getInventory().isEmpty()) {
            System.out.println("Склад порожній. Видалення неможливе.");
            return;
        }

        try {
            System.out.print("Введіть UUID телефону для видалення: ");
            String uuidInput = scanner.nextLine().trim();
            UUID uuidToDelete = UUID.fromString(uuidInput);

            // Search for the phone to confirm existence and show it to the user
            Phone phoneToDelete = store.searchByUuid(uuidToDelete);

            System.out.println("\n--- Знайдено об'єкт ---");
            System.out.println(phoneToDelete);

            System.out.print("Ви впевнені, що хочете видалити цей об'єкт? (yes/no): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();

            if (confirmation.equals("yes")) {
                store.delete(uuidToDelete);
                System.out.println("Об'єкт успішно видалено!");
            } else {
                System.out.println("Видалення скасовано.");
            }
        } catch (ObjectNotFoundException e) {
            System.out.println("Помилка видалення: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: Некоректний формат UUID.");
        } catch (Exception e) {
            System.out.println("Непередбачувана помилка: " + e.getMessage());
        }
    }
}