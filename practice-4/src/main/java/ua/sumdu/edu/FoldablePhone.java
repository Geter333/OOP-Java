package ua.sumdu.edu;

public class FoldablePhone extends SmartPhone {
    private int numberOfScreens;

    public FoldablePhone(String brand, String model, int storage, double price, OsType osType, boolean has5G, int numberOfScreens) {
        super(brand, model, storage, price, osType, has5G);
        this.numberOfScreens = numberOfScreens;
    }

    public int getNumberOfScreens() { return numberOfScreens; }

    public void setNumberOfScreens(int numberOfScreens) { this.numberOfScreens = numberOfScreens; }

    @Override
    public String toString() {
        return "FoldablePhone [Brand=" + getBrand() + ", Model=" + getModel() +
                ", OS=" + getOsType() + ", Storage=" + getStorage() +
                "GB, Price=$" + getPrice() + ", 5G=" + (isHas5G() ? "Yes" : "No") +
                ", Screens=" + numberOfScreens + "]";
    }
}