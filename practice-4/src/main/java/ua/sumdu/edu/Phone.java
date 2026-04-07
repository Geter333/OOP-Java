package ua.sumdu.edu;

public class Phone {
    private String brand;
    private String model;
    private int storage;
    private double price;

    public Phone(String brand, String model, int storage, double price) {
        setBrand(brand);
        setModel(model);
        setStorage(storage);
        setPrice(price);
    }

    public String getBrand() { return brand; }

    public void setBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            throw new IllegalArgumentException("Бренд не може бути порожнім.");
        }
        this.brand = brand;
    }

    public String getModel() { return model; }

    public void setModel(String model) {
        if (model == null || model.trim().isEmpty()) {
            throw new IllegalArgumentException("Модель не може бути порожньою.");
        }
        this.model = model;
    }

    public int getStorage() { return storage; }

    public void setStorage(int storage) {
        if (storage <= 0) {
            throw new IllegalArgumentException("Об'єм пам'яті повинен бути більшим за 0.");
        }
        this.storage = storage;
    }

    public double getPrice() { return price; }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Ціна не може бути від'ємною.");
        }
        this.price = price;
    }

    @Override
    public String toString() {
        return "Phone [Brand=" + brand + ", Model=" + model + ", Storage=" + storage + "GB, Price=$" + price + "]";
    }
}