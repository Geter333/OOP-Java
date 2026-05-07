package ua.sumdu.edu;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PhoneTest {

    @Test
    void shouldThrowInvalidFieldValueExceptionWhenSettingInvalidPrice() {
        assertThrows(InvalidFieldValueException.class, () -> new BasicPhone("Apple", "iPhone 15", 256, -999.99, OsType.IOS));
    }

    @Test
    void shouldThrowInvalidFieldValueExceptionWhenCreatingWithEmptyBrand() {
        assertThrows(InvalidFieldValueException.class, () -> new BasicPhone("", "Galaxy S24", 256, 800.0, OsType.ANDROID));
    }

    @Test
    void shouldThrowInvalidFieldValueExceptionWhenCreatingWithEmptyModel() {
        assertThrows(InvalidFieldValueException.class, () -> new BasicPhone("Samsung", "", 256, 800.0, OsType.ANDROID));
    }

    @Test
    void shouldThrowInvalidFieldValueExceptionWhenCreatingWithInvalidStorage() {
        assertThrows(InvalidFieldValueException.class, () -> new BasicPhone("Samsung", "Galaxy S24", -256, 800.0, OsType.ANDROID));
    }
}