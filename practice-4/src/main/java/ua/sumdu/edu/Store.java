package ua.sumdu.edu;

import java.util.ArrayList;
import java.util.UUID;

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

    public Phone searchByUuid(UUID uuid) throws ObjectNotFoundException {
        for (StoreItem item : inventory) {
            if (item.getPhone().getUuid().equals(uuid)) {
                return item.getPhone();
            }
        }
        throw new ObjectNotFoundException("Телефон з UUID " + uuid + " не знайдено.");
    }

    public void delete(UUID uuidToDelete) throws ObjectNotFoundException {
        if (!inventory.removeIf(item -> item.getPhone().getUuid().equals(uuidToDelete))) {
            throw new ObjectNotFoundException("Не вдалося видалити: телефон з UUID " + uuidToDelete + " не знайдено.");
        }
    }

    public void update(UUID uuidToUpdate, Phone newPhoneData) throws ObjectNotFoundException {
        for (StoreItem item : inventory) {
            if (item.getPhone().getUuid().equals(uuidToUpdate)) {
                Phone phoneToUpdate = item.getPhone();
                phoneToUpdate.setBrand(newPhoneData.getBrand());
                phoneToUpdate.setModel(newPhoneData.getModel());
                phoneToUpdate.setStorage(newPhoneData.getStorage());
                phoneToUpdate.setPrice(newPhoneData.getPrice());
                phoneToUpdate.setOsType(newPhoneData.getOsType());
                return;
            }
        }
        throw new ObjectNotFoundException("Не вдалося оновити: телефон з UUID " + uuidToUpdate + " не знайдено.");
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