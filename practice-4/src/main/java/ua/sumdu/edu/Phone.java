package ua.sumdu.edu;

public class Phone {
    private String model;
    private int storage;
    private double price;

    public Phone(String model, int storage, double price) {
        this.model = model;
        this.storage = storage;
        this.price = price;
    }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public int getStorage() { return storage; }
    public void setStorage(int storage) { this.storage = storage; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    @Override
    public String toString() {
        return "Phone [Model=" + model + ", Storage=" + storage + "GB, Price=$" + price + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return storage == phone.storage &&
                Double.compare(phone.price, price) == 0 &&
                java.util.Objects.equals(model, phone.model);
    }
}