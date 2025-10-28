package ejercicio7clase8;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import ejercicio7clase8.Entry;

class EntryTest {

    @Test
    void testCompareTo() {
        // Arrange
        Entry<String, Integer> entry1 = new Entry<>("apple", 1);
        Entry<String, Integer> entry2 = new Entry<>("banana", 2);
        Entry<String, Integer> entry3 = new Entry<>("apple", 3);

        // Assert
        assertTrue(entry1.compareTo(entry2) < 0, "'apple' should be less than 'banana'");
        assertTrue(entry2.compareTo(entry1) > 0, "'banana' should be greater than 'apple'");
        assertEquals(0, entry1.compareTo(entry3), "'apple' should be equal to 'apple'");
    }

    @Test
    void testNullKeyComparison() {
        // Arrange
        Entry<String, Integer> entryWithNullKey = new Entry<>(null, 1);
        Entry<String, Integer> regularEntry = new Entry<>("test", 2);

        // Assert
        assertThrows(NullPointerException.class, () -> {
            entryWithNullKey.compareTo(regularEntry);
        }, "Comparar un Entry con clave nula debería lanzar NullPointerException.");

        assertThrows(NullPointerException.class, () -> {
            regularEntry.compareTo(entryWithNullKey);
        }, "Comparar con un Entry con clave nula debería lanzar NullPointerException.");
    }

    @Test
    void testNullValue() {
        // Arrange
        Entry<String, Integer> entryWithNullValue = new Entry<>("key", null);
        Entry<String, Integer> regularEntry = new Entry<>("key2", 1);

        // Assert
        assertNotNull(entryWithNullValue.getKey());
        assertNull(entryWithNullValue.getValue());
        // No debería lanzar excepción al crear o acceder a la clave/valor
        // La comparación se basa en la clave, no en el valor
        assertTrue(entryWithNullValue.compareTo(regularEntry) < 0);
    }
}

