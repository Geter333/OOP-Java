package ua.sumdu.edu;

import java.util.ArrayList;

public class Store {
    private String storeName;
    private ArrayList<Phone> inventory;

    public Store(String storeName) {
        if (storeName == null || storeName.trim().isEmpty()) {
            throw new IllegalArgumentException("Назва магазину не може бути порожньою.");
        }
        this.storeName = storeName;
        this.inventory = new ArrayList<>();
    }

    public void addPhoneToInventory(Phone phone) {
        if (phone != null) {
            inventory.add(phone);
        }
    }

    public void showInventory() {
        System.out.println("=== Асортимент магазину: " + storeName + " ===");
        if (inventory.isEmpty()) {
            System.out.println("Склад наразі порожній.");
        } else {
            for (Phone p : inventory) {
                System.out.println(p.toString());
            }
        }
        System.out.println("=========================================");
    }
}