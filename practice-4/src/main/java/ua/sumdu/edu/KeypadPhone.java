package ua.sumdu.edu;

public class KeypadPhone extends Phone {
    private boolean hasFlashlight;

    public KeypadPhone(String brand, String model, int storage, double price, OsType osType, boolean hasFlashlight) {
        super(brand, model, storage, price, osType);
        this.hasFlashlight = hasFlashlight;
    }

    public boolean isHasFlashlight() { return hasFlashlight; }

    public void setHasFlashlight(boolean hasFlashlight) { this.hasFlashlight = hasFlashlight; }

    @Override
    public String toString() {
        return "KeypadPhone [Brand=" + getBrand() + ", Model=" + getModel() +
                ", OS=" + getOsType() + ", Storage=" + getStorage() +
                "GB, Price=$" + getPrice() + ", Flashlight=" + (hasFlashlight ? "Yes" : "No") + "]";
    }
}