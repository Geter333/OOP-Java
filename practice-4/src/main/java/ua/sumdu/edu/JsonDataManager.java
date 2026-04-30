package ua.sumdu.edu;

import com.google.gson.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class JsonDataManager {

    public void loadFromJson(Store store, String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return;
        }

        try (FileReader reader = new FileReader(file)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            if (jsonElement.isJsonNull() || !jsonElement.isJsonArray()) {
                return;
            }

            JsonArray jsonArray = jsonElement.getAsJsonArray();
            Gson gson = new Gson();

            for (JsonElement element : jsonArray) {
                JsonObject obj = element.getAsJsonObject();

                if (!obj.has("phone") || !obj.has("quantity")) continue;

                int quantity = obj.get("quantity").getAsInt();
                JsonObject phoneObj = obj.getAsJsonObject("phone");

                if (!phoneObj.has("type")) continue;
                String type = phoneObj.get("type").getAsString();

                Phone p = null;
                switch (type) {
                    case "Phone":
                    case "BasicPhone": p = gson.fromJson(phoneObj, BasicPhone.class); break;
                    case "SmartPhone": p = gson.fromJson(phoneObj, SmartPhone.class); break;
                    case "KeypadPhone": p = gson.fromJson(phoneObj, KeypadPhone.class); break;
                    case "GamingPhone": p = gson.fromJson(phoneObj, GamingPhone.class); break;
                    case "FoldablePhone": p = gson.fromJson(phoneObj, FoldablePhone.class); break;
                }
                if (p != null) {
                    store.addNewPhone(p, quantity);
                }
            }
        } catch (Exception e) {
            System.out.println("Помилка читання JSON: " + e.getMessage());
        }
    }

    public void saveToJson(Store store, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(store.getInventory(), writer);
        } catch (Exception e) {
            System.out.println("Помилка запису JSON: " + e.getMessage());
        }
    }
}