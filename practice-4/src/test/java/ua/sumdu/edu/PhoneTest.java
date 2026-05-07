package ua.sumdu.edu;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PhoneTest {

    @Test
    void shouldThrowInvalidFieldValueExceptionWhenInvalidValueInSetter() {
        Phone phone = new BasicPhone("Apple", "iPhone 15", 256, 999.99, OsType.IOS);
        Exception exception = assertThrows(InvalidFieldValueException.class, () -> {
            phone.setPrice(-100.0);
        });
        assertEquals("Ціна не може бути від'ємною.", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidFieldValueExceptionWhenInvalidConstructorData() {
        assertThrows(InvalidFieldValueException.class, () -> {
            new BasicPhone("", "Galaxy S24", 256, 800.0, OsType.ANDROID);
        });

        assertThrows(InvalidFieldValueException.class, () -> {
            new BasicPhone("Samsung", "Galaxy S24", -10, 800.0, OsType.ANDROID);
        });
    }
}