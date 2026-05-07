package ua.sumdu.edu;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.UUID;

class PhoneTest {

    @Test
    void shouldThrowInvalidFieldValueExceptionWhenSettingInvalidPrice() {
        assertThrows(InvalidFieldValueException.class, () -> {
            Phone phone = new BasicPhone("Apple", "iPhone 15", 256, 999.99, OsType.IOS);
            phone.setPrice(-100.0);
        });
    }

    @Test
    void shouldThrowInvalidFieldValueExceptionWhenCreatingWithInvalidData() {
        assertThrows(InvalidFieldValueException.class, () -> {
            new BasicPhone("", "Galaxy S24", 256, 800.0, OsType.ANDROID);
        });
    }
}

class StoreTest {

    @Test
    void shouldThrowObjectNotFoundExceptionWhenDeletingNonExistingObject() {
        Store store = new Store();
        UUID nonExistentUuid = UUID.randomUUID();

        assertThrows(ObjectNotFoundException.class, () -> {
            store.delete(nonExistentUuid);
        });
    }

    @Test
    void shouldThrowObjectNotFoundExceptionWhenUpdatingNonExistingObject() throws InvalidFieldValueException {
        Store store = new Store();
        UUID nonExistentUuid = UUID.randomUUID();
        Phone phone = new BasicPhone("Apple", "iPhone 15", 256, 999.99, OsType.IOS);

        assertThrows(ObjectNotFoundException.class, () -> {
            store.update(nonExistentUuid, phone);
        });
    }
}
