package ejercicio7clase8;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import ejercicio7clase8.Dictionary;
import ejercicio6clase9.AVLTDA;

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
        AVLTDA<Integer> avl = dictionary.get(key);

        // Assert
        assertNotNull(avl, "El AVL no debería ser nulo después de insertar un elemento.");
        assertTrue(avl.contains(value), "El AVL debería contener el valor insertado.");
        assertEquals("10", avl.inOrder(), "El recorrido in-order debería mostrar el valor insertado.");
    }

    @Test
    void testPutMultipleValuesForKey() {
        // Arrange
        String key = "numeros";

        // Act
        dictionary.put(key, 20);
        dictionary.put(key, 10);
        dictionary.put(key, 30);
        AVLTDA<Integer> avl = dictionary.get(key);

        // Assert
        assertNotNull(avl);
        assertTrue(avl.contains(10));
        assertTrue(avl.contains(20));
        assertTrue(avl.contains(30));
        assertEquals("10 20 30", avl.inOrder(), "El recorrido in-order debería mostrar los valores ordenados.");
    }

    @Test
    void testGetNonExistentKey() {
        // Act
        AVLTDA<Integer> avl = dictionary.get("no_existe");

        // Assert
        assertNull(avl, "El AVL debería ser nulo para una clave que no existe.");
    }

    @Test
    void testRemoveValue() {
        // Arrange
        dictionary.put("letras", 100);
        dictionary.put("letras", 200);

        // Act
        dictionary.remove("letras", 100);
        AVLTDA<Integer> avl = dictionary.get("letras");

        // Assert
        assertNotNull(avl);
        assertFalse(avl.contains(100), "El valor 100 debería haber sido eliminado.");
        assertTrue(avl.contains(200));
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
        AVLTDA<Integer> avl = dictionary.get("colores");

        // Assert
        assertNotNull(avl);
        assertTrue(avl.contains(1));
        assertTrue(avl.contains(2));
        assertEquals("1 2", avl.inOrder(), "El AVL no debería cambiar si se intenta eliminar un valor inexistente.");
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
        AVLTDA<Integer> avl = dictionary.get("temporal");

        // Assert
        assertNotNull(avl);
        assertTrue(avl.contains(5));
        assertEquals("5", avl.inOrder());
    }

    @Test
    void testRemoveLastValueFromKey() {
        // Arrange
        dictionary.put("single", 100);

        // Act
        dictionary.remove("single", 100);
        AVLTDA<Integer> avl = dictionary.get("single");

        // Assert
        assertNotNull(avl, "El AVL debería existir aunque esté vacío.");
        assertFalse(avl.contains(100), "El valor 100 debería haber sido eliminado.");
        assertEquals("", avl.inOrder(), "El AVL debería estar vacío.");
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
        }, "Eliminar un valor nulo del AVL de Integer debería lanzar NullPointerException.");
    }

    @Test
    void testPrintEmptyDictionary() {
        // Act & Assert
        assertDoesNotThrow(() -> dictionary.printDictionary(), "printDictionary en diccionario vacío no debería lanzar excepción.");
        // Further assertions would require capturing System.out, which is complex without external libraries.
        // We assume if it doesn't throw an exception, it's behaving gracefully.
    }

    @Test
    void testPrintDictionaryWithEmptyAVLs() {
        // Arrange
        dictionary.put("key1", 1);
        dictionary.remove("key1", 1);
        dictionary.put("key2", 2);
        dictionary.remove("key2", 2);

        // Act & Assert
        assertDoesNotThrow(() -> dictionary.printDictionary(), "printDictionary con AVLs vacíos no debería lanzar excepción.");
        // Similar to above, detailed output verification would need System.out capture.
    }

    @Test
    void testPutConValoresOrdenadosParaMismaClave() {
        // Arrange
        String key = "test";
        for (int i = 1; i <= 100; i++) {
            dictionary.put(key, i);
        }

        // Act
        AVLTDA<Integer> avl = dictionary.get(key);

        // Assert
        assertNotNull(avl);
        StringBuilder expectedInOrder = new StringBuilder();
        for (int i = 1; i <= 100; i++) {
            expectedInOrder.append(i).append(" ");
        }
        assertEquals(expectedInOrder.toString().trim(), avl.inOrder());
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

        AVLTDA<Integer> avl = dictionary.get(key);

        // Assert
        assertNotNull(avl, "El AVL debería existir aunque esté vacío.");
        assertTrue(avl.inOrder().isEmpty(), "El AVL debería estar vacío.");
        assertTrue(dictionary.containsKey(key), "La clave debería seguir existiendo en el diccionario.");
    }
}