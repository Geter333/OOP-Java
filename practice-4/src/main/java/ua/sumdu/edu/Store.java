package ua.sumdu.edu;

import java.util.ArrayList;

public class Store {
    private ArrayList<StoreItem> inventory;

    public Store() {
        this.inventory = new ArrayList<>();
    }

    public void addNewPhone(Phone ph, int quantity) {
        for (StoreItem item : inventory) {
            if (item.getPhone().equals(ph)) {
                item.addQuantity(quantity);
                return;
            }
        }
        inventory.add(new StoreItem(ph, quantity));
    }

    public ArrayList<StoreItem> getInventory() {
        return inventory;
    }

    public ArrayList<Phone> searchByBrand(String brand) {
        ArrayList<Phone> result = new ArrayList<>();
        for (StoreItem item : inventory) {
            if (item.getPhone().getBrand().equalsIgnoreCase(brand)) {
                result.add(item.getPhone());
            }
        }
        return result;
    }

    public ArrayList<Phone> searchByOsType(OsType osType) {
        ArrayList<Phone> result = new ArrayList<>();
        for (StoreItem item : inventory) {
            if (item.getPhone().getOsType() == osType) {
                result.add(item.getPhone());
            }
        }
        return result;
    }

    public ArrayList<Phone> searchByPriceRange(double min, double max) {
        ArrayList<Phone> result = new ArrayList<>();
        for (StoreItem item : inventory) {
            Phone p = item.getPhone();
            if (p.getPrice() >= min && p.getPrice() <= max) {
                result.add(p);
            }
        }
        return result;
    }
}