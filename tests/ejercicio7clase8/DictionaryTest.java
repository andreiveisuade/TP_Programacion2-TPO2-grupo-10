package ejercicio7clase8;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class DictionaryTest {

    private Dictionary<String, Integer> dictionary;

    @BeforeEach
    void setUp() {
        dictionary = new Dictionary<>();
        dictionary.initialize();
    }

    @Test
    void testPutAndGetSingleValue() {
        // Arrange
        String key = "frutas";
        int value = 10;

        // Act
        dictionary.put(key, value);
        ABBTDA<Integer> abb = dictionary.get(key);

        // Assert
        assertNotNull(abb, "El ABB no debería ser nulo después de insertar un elemento.");
        assertTrue(abb.contains(value), "El ABB debería contener el valor insertado.");
        assertEquals("10", abb.inOrder(), "El recorrido in-order debería mostrar el valor insertado.");
    }

    @Test
    void testPutMultipleValuesForKey() {
        // Arrange
        String key = "numeros";

        // Act
        dictionary.put(key, 20);
        dictionary.put(key, 10);
        dictionary.put(key, 30);
        ABBTDA<Integer> abb = dictionary.get(key);

        // Assert
        assertNotNull(abb);
        assertTrue(abb.contains(10));
        assertTrue(abb.contains(20));
        assertTrue(abb.contains(30));
        assertEquals("10 20 30", abb.inOrder(), "El recorrido in-order debería mostrar los valores ordenados.");
    }

    @Test
    void testGetNonExistentKey() {
        // Act
        ABBTDA<Integer> abb = dictionary.get("no_existe");

        // Assert
        assertNull(abb, "El ABB debería ser nulo para una clave que no existe.");
    }

    @Test
    void testRemoveValue() {
        // Arrange
        dictionary.put("letras", 100);
        dictionary.put("letras", 200);

        // Act
        dictionary.remove("letras", 100);
        ABBTDA<Integer> abb = dictionary.get("letras");

        // Assert
        assertNotNull(abb);
        assertFalse(abb.contains(100), "El valor 100 debería haber sido eliminado.");
        assertTrue(abb.contains(200));
    }

    @Test
    void testRemoveKey() {
        // Arrange
        dictionary.put("a_eliminar", 1);
        dictionary.put("a_eliminar", 2);

        // Act
        dictionary.remove("a_eliminar");

        // Assert
        assertNull(dictionary.get("a_eliminar"), "La clave 'a_eliminar' debería haber sido eliminada.");
    }

    @Test
    void testRemoveNonExistentValue() {
        // Arrange
        dictionary.put("colores", 1);
        dictionary.put("colores", 2);

        // Act
        dictionary.remove("colores", 3); // Intentar eliminar un valor que no existe
        ABBTDA<Integer> abb = dictionary.get("colores");

        // Assert
        assertNotNull(abb);
        assertTrue(abb.contains(1));
        assertTrue(abb.contains(2));
        assertEquals("1 2", abb.inOrder(), "El ABB no debería cambiar si se intenta eliminar un valor inexistente.");
    }

    @Test
    void testRemoveNonExistentKey() {
        // Arrange
        dictionary.put("existente", 1);

        // Act
        dictionary.remove("no_existente"); // Intentar eliminar una clave que no existe

        // Assert
        assertNotNull(dictionary.get("existente"), "La clave existente no debería ser afectada.");
        assertNull(dictionary.get("no_existente"), "La clave no existente debería seguir siendo nula.");
    }

    @Test
    void testPutAfterRemoveKey() {
        // Arrange
        dictionary.put("temporal", 1);
        dictionary.remove("temporal");

        // Act
        dictionary.put("temporal", 5);
        ABBTDA<Integer> abb = dictionary.get("temporal");

        // Assert
        assertNotNull(abb);
        assertTrue(abb.contains(5));
        assertEquals("5", abb.inOrder());
    }

    @Test
    void testRemoveLastValueFromKey() {
        // Arrange
        dictionary.put("single", 100);

        // Act
        dictionary.remove("single", 100);
        ABBTDA<Integer> abb = dictionary.get("single");

        // Assert
        assertNotNull(abb, "El ABB debería existir aunque esté vacío.");
        assertFalse(abb.contains(100), "El valor 100 debería haber sido eliminado.");
        assertEquals("", abb.inOrder(), "El ABB debería estar vacío.");
    }

    @Test
    void testPutNullKey() {
        assertThrows(NullPointerException.class, () -> {
            dictionary.put(null, 10);
        }, "Insertar una clave nula debería lanzar NullPointerException.");
    }

    @Test
    void testPutNullValue() {
        // Arrange
        String key = "testKey";
        dictionary.put(key, 10);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            dictionary.put(key, null);
        }, "Insertar un valor nulo en el diccionario debería lanzar NullPointerException.");
    }

    @Test
    void testGetNullKey() {
        assertThrows(NullPointerException.class, () -> {
            dictionary.get(null);
        }, "Obtener con clave nula debería lanzar NullPointerException.");
    }

    @Test
    void testRemoveNullKey() {
        assertThrows(NullPointerException.class, () -> {
            dictionary.remove(null);
        }, "Eliminar con clave nula debería lanzar NullPointerException.");
    }

    @Test
    void testRemoveNullValue() {
        // Arrange
        dictionary.put("key", 10);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            dictionary.remove("key", null);
        }, "Eliminar un valor nulo del ABB de Integer debería lanzar NullPointerException.");
    }

    @Test
    void testPrintEmptyDictionary() {
        // Act & Assert
        assertDoesNotThrow(() -> dictionary.printDictionary(), "printDictionary en diccionario vacío no debería lanzar excepción.");
    }

    @Test
    void testPrintDictionaryWithEmptyABBs() {
        // Arrange
        dictionary.put("key1", 1);
        dictionary.remove("key1", 1);
        dictionary.put("key2", 2);
        dictionary.remove("key2", 2);

        // Act & Assert
        assertDoesNotThrow(() -> dictionary.printDictionary(), "printDictionary con ABBs vacíos no debería lanzar excepción.");
    }

    @Test
    void testPutConValoresOrdenadosParaMismaClave() {
        // Arrange
        String key = "test";
        for (int i = 1; i <= 100; i++) {
            dictionary.put(key, i);
        }

        // Act
        ABBTDA<Integer> abb = dictionary.get(key);

        // Assert
        assertNotNull(abb);
        StringBuilder expectedInOrder = new StringBuilder();
        for (int i = 1; i <= 100; i++) {
            expectedInOrder.append(i).append(" ");
        }
        assertEquals(expectedInOrder.toString().trim(), abb.inOrder());
    }

    @Test
    void testRemoverTodosLosValoresDeUnaClave() {
        // Arrange
        String key = "colores";
        dictionary.put(key, 1);
        dictionary.put(key, 2);
        dictionary.put(key, 3);

        // Act
        dictionary.remove(key, 1);
        dictionary.remove(key, 2);
        dictionary.remove(key, 3);

        ABBTDA<Integer> abb = dictionary.get(key);

        // Assert
        assertNotNull(abb, "El ABB debería existir aunque esté vacío.");
        assertTrue(abb.inOrder().isEmpty(), "El ABB debería estar vacío.");
        assertTrue(dictionary.containsKey(key), "La clave debería seguir existiendo en el diccionario.");
    }
}
