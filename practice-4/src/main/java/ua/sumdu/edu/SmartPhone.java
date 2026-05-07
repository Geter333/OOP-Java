package ua.sumdu.edu;

public class SmartPhone extends Phone {
    private boolean has5G;

    public SmartPhone(String brand, String model, int storage, double price, OsType osType, boolean has5G) throws InvalidFieldValueException {
        super(brand, model, storage, price, osType);
        this.has5G = has5G;
    }

    public boolean isHas5G() { return has5G; }

    public void setHas5G(boolean has5G) { this.has5G = has5G; }

    @Override
    public String toString() {
        return super.toString().replace("Phone [", "SmartPhone [") +
                ", 5G=" + (has5G ? "Yes" : "No") + "]";
    }
}
