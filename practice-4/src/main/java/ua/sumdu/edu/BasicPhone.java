package ua.sumdu.edu;

public class BasicPhone extends Phone {
    public BasicPhone(String brand, String model, int storage, double price, OsType osType) throws InvalidFieldValueException {
        super(brand, model, storage, price, osType);
    }
}
