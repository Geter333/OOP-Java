package ua.sumdu.edu;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Помилка: Не вказано шлях до файлу конфігурації бази даних.");
            System.out.println("Для запуску використовуйте аргумент, наприклад: app.properties");
            return;
        }

        String configPath = args[0];
        DatabaseManager dbManager = new DatabaseManager(configPath);
        JsonDataManager jsonManager = new JsonDataManager();
        Store store = new Store();
        Scanner scanner = new Scanner(System.in);

        AppRunner app = new AppRunner(scanner, store, dbManager, jsonManager);
        app.run();
    }
}