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
        ArrayList<Phone> inventory = loadFromJson(FILE_NAME);
        boolean running = true;

        System.out.println("Вітаємо в системі обліку пристроїв (Версія 9 - JSON)!");

        while (running) {
            System.out.println("\n--- ГОЛОВНЕ МЕНЮ ---");
            System.out.println("1. Створити новий об'єкт");
            System.out.println("2. Вивести інформацію про всі об'єкти");
            System.out.println("3. Зберегти дані та завершити роботу");
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
                    saveToJson(inventory, FILE_NAME);
                    System.out.println("Дані успішно збережено у файл. До побачення!");
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

    private static ArrayList<Phone> loadFromJson(String fileName) {
        ArrayList<Phone> list = new ArrayList<>();
        File file = new File(fileName);
        if (!file.exists()) {
            return list;
        }

        try (FileReader reader = new FileReader(file)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            if (jsonElement.isJsonNull() || !jsonElement.isJsonArray()) {
                return list;
            }

            JsonArray jsonArray = jsonElement.getAsJsonArray();
            Gson gson = new Gson();

            for (JsonElement element : jsonArray) {
                JsonObject obj = element.getAsJsonObject();
                if (!obj.has("type")) continue;
                String type = obj.get("type").getAsString();

                Phone p = null;
                switch (type) {
                    case "Phone": p = gson.fromJson(obj, Phone.class); break;
                    case "SmartPhone": p = gson.fromJson(obj, SmartPhone.class); break;
                    case "KeypadPhone": p = gson.fromJson(obj, KeypadPhone.class); break;
                    case "GamingPhone": p = gson.fromJson(obj, GamingPhone.class); break;
                    case "FoldablePhone": p = gson.fromJson(obj, FoldablePhone.class); break;
                }
                if (p != null) {
                    list.add(p);
                }
            }
        } catch (Exception e) {
            System.out.println("Помилка читання JSON: " + e.getMessage());
        }
        return list;
    }

    private static void saveToJson(ArrayList<Phone> list, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(list, writer);
        } catch (Exception e) {
            System.out.println("Помилка запису JSON: " + e.getMessage());
        }
    }
}