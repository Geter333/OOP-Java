package ua.sumdu.edu;

public class KeypadPhone extends Phone {
    private boolean hasFlashlight;

    public KeypadPhone(String brand, String model, int storage, double price, OsType osType, boolean hasFlashlight) throws InvalidFieldValueException {
        super(brand, model, storage, price, osType);
        this.hasFlashlight = hasFlashlight;
    }

    public boolean isHasFlashlight() { return hasFlashlight; }

    public void setHasFlashlight(boolean hasFlashlight) { this.hasFlashlight = hasFlashlight; }

    @Override
    public String toString() {
        return super.toString().replace("Phone [", "KeypadPhone [") +
                ", Flashlight=" + (hasFlashlight ? "Yes" : "No") + "]";
    }
}
