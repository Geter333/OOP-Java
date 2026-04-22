package ua.sumdu.edu;

public class Phone {
    private String type;
    private String brand;
    private String model;
    private int storage;
    private double price;
    private OsType osType;

    public Phone(String brand, String model, int storage, double price, OsType osType) {
        this.type = this.getClass().getSimpleName();
        setBrand(brand);
        setModel(model);
        setStorage(storage);
        setPrice(price);
        setOsType(osType);
    }

    public Phone(Phone other) {
        this(other.brand, other.model, other.storage, other.price, other.osType);
        this.type = other.type;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

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

    public OsType getOsType() { return osType; }

    public void setOsType(OsType osType) {
        if (osType == null) {
            throw new IllegalArgumentException("Тип ОС не може бути null.");
        }
        this.osType = osType;
    }

    @Override
    public String toString() {
        return "Phone [Brand=" + brand + ", Model=" + model + ", OS=" + osType +
                ", Storage=" + storage + "GB, Price=$" + price + "]";
    }
}