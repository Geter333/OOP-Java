package ua.sumdu.edu;

public class FoldablePhone extends SmartPhone {
    private int numberOfScreens;

    public FoldablePhone(String brand, String model, int storage, double price, OsType osType, boolean has5G, int numberOfScreens) {
        super(brand, model, storage, price, osType, has5G);
        setNumberOfScreens(numberOfScreens);
    }

    public int getNumberOfScreens() { return numberOfScreens; }

    public void setNumberOfScreens(int numberOfScreens) {
        if (numberOfScreens <= 0) {
            throw new IllegalArgumentException("Кількість екранів повинна бути більшою за 0.");
        }
        this.numberOfScreens = numberOfScreens;
    }

    @Override
    public String toString() {
        return super.toString().replace("SmartPhone [", "FoldablePhone [") +
                ", Screens=" + numberOfScreens + "]";
    }
}