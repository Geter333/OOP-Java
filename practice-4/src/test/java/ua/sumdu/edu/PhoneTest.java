package ua.sumdu.edu;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PhoneTest {
    @Test
    void shouldThrowExceptionWhenInvalidValueInSetter() {
        Phone phone = new Phone("Apple", "iPhone 15", 256, 999.99);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            phone.setPrice(-100.0);
        });
        assertEquals("Ціна не може бути від'ємною.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenInvalidConstructorData() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Phone("", "Galaxy S24", 256, 800.0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Phone("Samsung", "Galaxy S24", -10, 800.0);
        });
    }
}