package ua.sumdu.edu;

public abstract class Phone implements Comparable<Phone> {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Phone)) return false;
        Phone phone = (Phone) o;
        return storage == phone.storage &&
                Double.compare(phone.price, price) == 0 &&
                brand.equalsIgnoreCase(phone.brand) &&
                model.equalsIgnoreCase(phone.model) &&
                osType == phone.osType;
    }

    @Override
    public int hashCode() {
        int result = brand.toLowerCase().hashCode();
        result = 31 * result + model.toLowerCase().hashCode();
        result = 31 * result + storage;
        long temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + osType.hashCode();
        return result;
    }

    @Override
    public int compareTo(Phone other) {
        int priceComparison = Double.compare(this.price, other.price);

        if (priceComparison == 0) {
            return this.brand.compareToIgnoreCase(other.brand);
        }
        return priceComparison;
    }
}