package ua.sumdu.edu;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {
    private String url;
    private String user;
    private String password;

    public DatabaseManager(String configPath) {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(configPath)) {
            props.load(fis);
            this.url = props.getProperty("db.url");
            this.user = props.getProperty("db.user");
            this.password = props.getProperty("db.password");
        } catch (IOException e) {
            System.out.println("Помилка читання конфігураційного файлу: " + e.getMessage());
        }
    }

    public void savePhone(Phone phone, int quantity) {
        if (url == null || user == null || password == null) {
            System.out.println("Помилка: Немає підключення до БД (невірний конфіг).");
            return;
        }

        String sql = "INSERT INTO phones (type, brand, model, storage, price, os_type, quantity, has_5g, has_flashlight, refresh_rate, number_of_screens) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Заповнення базових полів
            pstmt.setString(1, phone.getType());
            pstmt.setString(2, phone.getBrand());
            pstmt.setString(3, phone.getModel());
            pstmt.setInt(4, phone.getStorage());
            pstmt.setDouble(5, phone.getPrice());
            pstmt.setString(6, phone.getOsType().name());
            pstmt.setInt(7, quantity);

            pstmt.setObject(8, null);
            pstmt.setObject(9, null);
            pstmt.setObject(10, null);
            pstmt.setObject(11, null);

            // Визначення типу та запис специфічних даних
            if (phone instanceof SmartPhone) {
                pstmt.setBoolean(8, ((SmartPhone) phone).isHas5G());
                if (phone instanceof GamingPhone) {
                    pstmt.setInt(10, ((GamingPhone) phone).getRefreshRate());
                } else if (phone instanceof FoldablePhone) {
                    pstmt.setInt(11, ((FoldablePhone) phone).getNumberOfScreens());
                }
            } else if (phone instanceof KeypadPhone) {
                pstmt.setBoolean(9, ((KeypadPhone) phone).isHasFlashlight());
            }

            pstmt.executeUpdate();
            System.out.println("--> [БД] Об'єкт успішно збережено у базу даних!");

        } catch (SQLException e) {
            System.out.println("Помилка бази даних: " + e.getMessage());
        }
    }
}