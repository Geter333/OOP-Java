package ua.sumdu.edu;

import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

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
    void shouldThrowObjectNotFoundExceptionWhenUpdatingNonExistingObject() {
        Store store = new Store();
        try {
            Phone phone = new BasicPhone("Apple", "iPhone 15", 256, 999.99, OsType.IOS);
            UUID nonExistentUuid = UUID.randomUUID();

            assertThrows(ObjectNotFoundException.class, () -> {
                store.update(nonExistentUuid, phone);
            });
        } catch (InvalidFieldValueException e) {
            fail("Unexpected InvalidFieldValueException was thrown");
        }
    }

    @Test
    void shouldThrowInvalidFieldValueExceptionWhenCreatingObjectWithInvalidData() {
        assertThrows(InvalidFieldValueException.class, () -> {
            new BasicPhone("Apple", "iPhone 15", -256, 999.99, OsType.IOS);
        });
    }
}