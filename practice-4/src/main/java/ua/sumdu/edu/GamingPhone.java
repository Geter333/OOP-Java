package ua.sumdu.edu;

public class GamingPhone extends SmartPhone {
    private int refreshRate;

    public GamingPhone(String brand, String model, int storage, double price, OsType osType, boolean has5G, int refreshRate) {
        super(brand, model, storage, price, osType, has5G);
        setRefreshRate(refreshRate);
    }

    public int getRefreshRate() { return refreshRate; }

    public void setRefreshRate(int refreshRate) {
        if (refreshRate <= 0) {
            throw new IllegalArgumentException("Частота оновлення екрана повинна бути більшою за 0.");
        }
        this.refreshRate = refreshRate;
    }

    @Override
    public String toString() {
        return "GamingPhone [Brand=" + getBrand() + ", Model=" + getModel() +
                ", OS=" + getOsType() + ", Storage=" + getStorage() +
                "GB, Price=$" + getPrice() + ", 5G=" + (isHas5G() ? "Yes" : "No") +
                ", RefreshRate=" + refreshRate + "Hz]";
    }
}