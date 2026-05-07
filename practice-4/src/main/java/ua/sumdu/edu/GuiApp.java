package ua.sumdu.edu;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.UUID;

public class GuiApp extends Application {

    private final Store store = new Store();
    private final JsonDataManager jsonManager = new JsonDataManager();
    private static final String FILE_NAME = "input.json";
    private final TextArea inventoryTextArea = new TextArea();
    private final Label searchResultLabel = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Система обліку телефонів");

        jsonManager.loadFromJson(store, FILE_NAME);
        updateInventoryView();

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // --- Top: Add new phone ---
        VBox addPhoneBox = createAddPhoneBox();
        root.setTop(addPhoneBox);

        // --- Center: Inventory List ---
        VBox inventoryBox = new VBox(10);
        inventoryBox.setPadding(new Insets(10, 0, 10, 0));
        inventoryBox.getChildren().addAll(new Label("Список товарів на складі:"), inventoryTextArea);
        root.setCenter(inventoryBox);

        // --- Bottom: Search by UUID ---
        VBox searchBox = createSearchBox();
        root.setBottom(searchBox);

        Scene scene = new Scene(root, 600, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createAddPhoneBox() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setStyle("-fx-border-color: lightgray; -fx-border-width: 1;");

        Label title = new Label("Додати новий телефон");
        title.setStyle("-fx-font-weight: bold;");

        TextField brandField = new TextField();
        brandField.setPromptText("Бренд");
        TextField modelField = new TextField();
        modelField.setPromptText("Модель");
        TextField storageField = new TextField();
        storageField.setPromptText("Об'єм пам'яті (ГБ)");
        TextField priceField = new TextField();
        priceField.setPromptText("Ціна");
        ComboBox<OsType> osComboBox = new ComboBox<>();
        osComboBox.getItems().setAll(OsType.values());
        osComboBox.setPromptText("Операційна система");

        Button addButton = new Button("Додати");
        Label addStatusLabel = new Label();

        addButton.setOnAction(e -> {
            try {
                String brand = brandField.getText();
                String model = modelField.getText();
                int storage = Integer.parseInt(storageField.getText());
                double price = Double.parseDouble(priceField.getText());
                OsType os = osComboBox.getValue();

                if (os == null) {
                    throw new IllegalArgumentException("Не обрано операційну систему.");
                }

                Phone newPhone = new BasicPhone(brand, model, storage, price, os);
                store.addNewPhone(newPhone, 1); // Adding 1 by default
                jsonManager.saveToJson(store, FILE_NAME);
                updateInventoryView();

                addStatusLabel.setText("Телефон успішно додано!");
                brandField.clear();
                modelField.clear();
                storageField.clear();
                priceField.clear();
                osComboBox.setValue(null);
            } catch (Exception ex) {
                addStatusLabel.setText("Помилка: " + ex.getMessage());
            }
        });

        vbox.getChildren().addAll(title, brandField, modelField, storageField, priceField, osComboBox, addButton, addStatusLabel);
        return vbox;
    }

    private VBox createSearchBox() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.setStyle("-fx-border-color: lightgray; -fx-border-width: 1;");

        Label title = new Label("Пошук за UUID");
        title.setStyle("-fx-font-weight: bold;");

        TextField uuidField = new TextField();
        uuidField.setPromptText("Введіть UUID");
        Button searchButton = new Button("Знайти");

        HBox searchControls = new HBox(10, uuidField, searchButton);
        vbox.getChildren().addAll(title, searchControls, searchResultLabel);

        searchButton.setOnAction(e -> {
            String uuidText = uuidField.getText();
            if (uuidText == null || uuidText.trim().isEmpty()) {
                searchResultLabel.setText("Поле UUID не може бути порожнім.");
                return;
            }
            try {
                UUID uuid = UUID.fromString(uuidText);
                Phone foundPhone = store.searchByUuid(uuid);
                searchResultLabel.setText("Знайдено:\n" + foundPhone.toString());
            } catch (ObjectNotFoundException ex) {
                searchResultLabel.setText(ex.getMessage());
            } catch (IllegalArgumentException ex) {
                searchResultLabel.setText("Помилка: Некоректний формат UUID.");
            }
        });
        return vbox;
    }

    private void updateInventoryView() {
        inventoryTextArea.clear();
        for (StoreItem item : store.getInventory()) {
            Phone phone = item.getPhone();
            inventoryTextArea.appendText(
                    "Модель: " + phone.getModel() + " | UUID: " + phone.getUuid() + "\n"
            );
        }
    }
}