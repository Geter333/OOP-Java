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
}