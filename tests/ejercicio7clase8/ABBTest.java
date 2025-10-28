package ejercicio7clase8;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import ejercicio7clase8.ABB;

class ABBTest {

    private ABB<Integer> abb;

    @BeforeEach
    void setUp() {
        abb = new ABB<>();
        abb.initialize();
    }

    @Test
    void testInsertAndContains() {
        // Act
        abb.insert(10);
        abb.insert(5);
        abb.insert(15);

        // Assert
        assertTrue(abb.contains(10));
        assertTrue(abb.contains(5));
        assertTrue(abb.contains(15));
        assertFalse(abb.contains(100));
    }

    @Test
    void testInOrder() {
        // Arrange
        abb.insert(10);
        abb.insert(5);
        abb.insert(15);
        abb.insert(3);
        abb.insert(7);

        // Act
        String inOrderResult = abb.inOrder();

        // Assert
        assertEquals("3 5 7 10 15", inOrderResult);
    }

    @Test
    void testRemoveLeafNode() {
        // Arrange
        abb.insert(10);
        abb.insert(5);

        // Act
        abb.remove(5);

        // Assert
        assertFalse(abb.contains(5));
        assertTrue(abb.contains(10));
        assertEquals("10", abb.inOrder());
    }

    @Test
    void testRemoveNodeWithOneChild() {
        // Arrange
        abb.insert(10);
        abb.insert(5);
        abb.insert(3);

        // Act
        abb.remove(5);

        // Assert
        assertFalse(abb.contains(5));
        assertTrue(abb.contains(10));
        assertTrue(abb.contains(3));
        assertEquals("3 10", abb.inOrder());
    }

    @Test
    void testRemoveNodeWithTwoChildren() {
        // Arrange
        abb.insert(10);
        abb.insert(5);
        abb.insert(15);
        abb.insert(12);
        abb.insert(17);

        // Act
        abb.remove(15);

        // Assert
        assertFalse(abb.contains(15));
        assertTrue(abb.contains(10));
        assertTrue(abb.contains(5));
        assertTrue(abb.contains(12));
        assertTrue(abb.contains(17));
        assertEquals("5 10 12 17", abb.inOrder());
    }

    @Test
    void testRemoveRoot() {
        // Arrange
        abb.insert(10);
        abb.insert(5);
        abb.insert(15);

        // Act
        abb.remove(10);

        // Assert
        assertFalse(abb.contains(10));
        assertTrue(abb.contains(5));
        assertTrue(abb.contains(15));
        assertEquals("5 15", abb.inOrder());
    }

    @Test
    void testInsertNull() {
        assertThrows(NullPointerException.class, () -> {
            abb.insert(null);
        }, "Insertar null debería lanzar NullPointerException.");
    }

    @Test
    void testContainsNull() {
        abb.insert(10);
        assertThrows(NullPointerException.class, () -> {
            abb.contains(null);
        }, "Buscar null debería lanzar NullPointerException.");
    }

    @Test
    void testRemoveNull() {
        abb.insert(10);
        assertThrows(NullPointerException.class, () -> {
            abb.remove(null);
        }, "Eliminar null debería lanzar NullPointerException.");
    }

    @Test
    void testInOrderEmpty() {
        assertEquals("", abb.inOrder(), "El recorrido in-order de un ABB vacío debería ser una cadena vacía.");
    }
}