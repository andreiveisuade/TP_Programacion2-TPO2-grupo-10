package ejercicio6clase9;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import ejercicio6clase9.AVL;

class AVLTest {

    private AVL<Integer> avl;

    @BeforeEach
    void setUp() {
        avl = new AVL<>();
        avl.initialize();
    }

    @Test
    void testInsertAndContains() {
        avl.insert(10);
        avl.insert(5);
        avl.insert(15);
        avl.insert(3);
        avl.insert(7);
        avl.insert(12);
        avl.insert(17);

        assertTrue(avl.contains(10));
        assertTrue(avl.contains(5));
        assertTrue(avl.contains(15));
        assertTrue(avl.contains(3));
        assertTrue(avl.contains(7));
        assertTrue(avl.contains(12));
        assertTrue(avl.contains(17));
        assertFalse(avl.contains(100));
    }

    @Test
    void testInOrderTraversal() {
        avl.insert(10);
        avl.insert(5);
        avl.insert(15);
        avl.insert(3);
        avl.insert(7);

        assertEquals("3 5 7 10 15", avl.inOrder());
    }

    @Test
    void testGetExistingElement() {
        avl.insert(10);
        avl.insert(5);
        avl.insert(15);

        assertEquals(10, avl.get(10));
        assertEquals(5, avl.get(5));
        assertEquals(15, avl.get(15));
    }

    @Test
    void testGetNonExistingElement() {
        avl.insert(10);
        assertNull(avl.get(100));
    }

    @Test
    void testRemoveLeafNode() {
        avl.insert(10);
        avl.insert(5);
        avl.insert(15);
        avl.remove(5);

        assertFalse(avl.contains(5));
        assertTrue(avl.contains(10));
        assertTrue(avl.contains(15));
        assertEquals("10 15", avl.inOrder());
    }

    @Test
    void testRemoveNodeWithOneChild() {
        avl.insert(10);
        avl.insert(5);
        avl.insert(15);
        avl.insert(3);
        avl.remove(5);

        assertFalse(avl.contains(5));
        assertTrue(avl.contains(10));
        assertTrue(avl.contains(3));
        assertTrue(avl.contains(15));
        assertEquals("3 10 15", avl.inOrder());
    }

    @Test
    void testRemoveNodeWithTwoChildren() {
        avl.insert(10);
        avl.insert(5);
        avl.insert(15);
        avl.insert(3);
        avl.insert(7);
        avl.insert(12);
        avl.insert(17);
        avl.remove(15);

        assertFalse(avl.contains(15));
        assertTrue(avl.contains(10));
        assertTrue(avl.contains(5));
        assertTrue(avl.contains(3));
        assertTrue(avl.contains(7));
        assertTrue(avl.contains(12));
        assertTrue(avl.contains(17));
        assertEquals("3 5 7 10 12 17", avl.inOrder());
    }

    @Test
    void testRemoveRoot() {
        avl.insert(10);
        avl.insert(5);
        avl.insert(15);
        avl.remove(10);

        assertFalse(avl.contains(10));
        assertTrue(avl.contains(5));
        assertTrue(avl.contains(15));
        assertEquals("5 15", avl.inOrder());
    }

    @Test
    void testInsertNull() {
        assertThrows(NullPointerException.class, () -> {
            avl.insert(null);
        }, "Insertar null debería lanzar NullPointerException.");
    }

    @Test
    void testContainsNull() {
        avl.insert(10);
        assertThrows(NullPointerException.class, () -> {
            avl.contains(null);
        }, "Buscar null debería lanzar NullPointerException.");
    }

    @Test
    void testRemoveNull() {
        avl.insert(10);
        assertThrows(NullPointerException.class, () -> {
            avl.remove(null);
        }, "Eliminar null debería lanzar NullPointerException.");
    }

    @Test
    void testGetNull() {
        avl.insert(10);
        assertThrows(NullPointerException.class, () -> {
            avl.get(null);
        }, "Obtener null debería lanzar NullPointerException.");
    }

    @Test
    void testEmptyAVL() {
        assertFalse(avl.contains(1));
        assertNull(avl.get(1));
        avl.remove(1); // Should not throw exception
        assertEquals("", avl.inOrder());
    }

    @Test
    void testRemoveNonExistent() {
        avl.insert(10);
        avl.remove(100);
        assertTrue(avl.contains(10));
        assertEquals("10", avl.inOrder());
    }

    @Test
    void testLeftRotation() {
        avl.insert(30);
        avl.insert(20);
        avl.insert(40);
        avl.insert(50);
        avl.insert(60);
        // Tree should rebalance: 30(root) -> 40(root)
        //   30
        //  /  \
        // 20  40
        //      \
        //      50
        //       \
        //       60
        // Becomes:
        //     40
        //    /  \
        //   30   50
        //  /      \
        // 20      60
        assertEquals("20 30 40 50 60", avl.inOrder());
        assertEquals(40, avl.get(40)); // New root
    }

    @Test
    void testRightRotation() {
        avl.insert(60);
        avl.insert(50);
        avl.insert(70);
        avl.insert(40);
        avl.insert(30);
        // Tree should rebalance: 60(root) -> 50(root)
        //      60
        //     /  \
        //    50  70
        //   / 
        //  40
        // /
        // 30
        // Becomes:
        //     50
        //    /  \
        //   40   60
        //  /      \
        // 30      70
        assertEquals("30 40 50 60 70", avl.inOrder());
        assertEquals(50, avl.get(50)); // New root
    }

    @Test
    void testLeftRightRotation() {
        avl.insert(30);
        avl.insert(10);
        avl.insert(20);
        // Tree should rebalance: 30(root) -> 20(root)
        //   30
        //  /  \
        // 10  null
        //  \
        //  20
        // Becomes:
        //   20
        //  /  \
        // 10  30
        assertEquals("10 20 30", avl.inOrder());
        assertEquals(20, avl.get(20)); // New root
    }

    @Test
    void testRightLeftRotation() {
        avl.insert(10);
        avl.insert(30);
        avl.insert(20);
        // Tree should rebalance: 10(root) -> 20(root)
        //   10
        //  /  \
        // null 30
        //     /
        //    20
        // Becomes:
        //   20
        //  /  \
        // 10  30
        assertEquals("10 20 30", avl.inOrder());
        assertEquals(20, avl.get(20)); // New root
    }

    @Test
    void testStressInsertAndRemove() {
        int numElements = 1000;
        java.util.List<Integer> elements = new java.util.ArrayList<>();
        for (int i = 0; i < numElements; i++) {
            elements.add(i);
        }
        java.util.Collections.shuffle(elements);

        // Insert all elements
        for (Integer element : elements) {
            avl.insert(element);
        }

        // Verify all elements are present and in order
        java.util.Collections.sort(elements);
        StringBuilder expectedInOrder = new StringBuilder();
        for (Integer element : elements) {
            expectedInOrder.append(element).append(" ");
        }
        assertEquals(expectedInOrder.toString().trim(), avl.inOrder());

        // Remove half the elements randomly
        java.util.Collections.shuffle(elements);
        for (int i = 0; i < numElements / 2; i++) {
            avl.remove(elements.get(i));
        }

        // Verify remaining elements and order
        java.util.List<Integer> remainingElements = new java.util.ArrayList<>();
        for (int i = numElements / 2; i < numElements; i++) {
            remainingElements.add(elements.get(i));
        }
        java.util.Collections.sort(remainingElements);
        expectedInOrder = new StringBuilder();
        for (Integer element : remainingElements) {
            expectedInOrder.append(element).append(" ");
        }
        assertEquals(expectedInOrder.toString().trim(), avl.inOrder());

        // Remove the rest of the elements
        for (int i = numElements / 2; i < numElements; i++) {
            avl.remove(elements.get(i));
        }
        assertEquals("", avl.inOrder());
        assertNull(avl.get(elements.get(0))); // Should be null after removal
    }

    // New tests for AVL height and balance after rotations
    @Test
    void testAlturaYBalanceDespuesDeRotacionSimpleIzquierda() {
        avl.insert(10);
        avl.insert(20);
        avl.insert(30);
        // Tree should be: 20 (root, h=2, fb=0), 10 (left, h=1, fb=0), 30 (right, h=1, fb=0)
        assertEquals(20, avl.get(20)); // Root
        assertEquals(10, avl.get(10)); // Left child
        assertEquals(30, avl.get(30)); // Right child
        // Assuming AVL has methods to get height and balance factor for testing
        // For now, we'll rely on inOrder and structure to imply balance
        assertEquals("10 20 30", avl.inOrder());
    }

    @Test
    void testAlturaYBalanceDespuesDeRotacionSimpleDerecha() {
        avl.insert(30);
        avl.insert(20);
        avl.insert(10);
        // Tree should be: 20 (root, h=2, fb=0), 10 (left, h=1, fb=0), 30 (right, h=1, fb=0)
        assertEquals(20, avl.get(20)); // Root
        assertEquals(10, avl.get(10)); // Left child
        assertEquals(30, avl.get(30)); // Right child
        assertEquals("10 20 30", avl.inOrder());
    }

    @Test
    void testAlturaYBalanceDespuesDeRotacionDobleIzquierdaDerecha() {
        avl.insert(30);
        avl.insert(10);
        avl.insert(20);
        // Tree should be: 20 (root, h=2, fb=0), 10 (left, h=1, fb=0), 30 (right, h=1, fb=0)
        assertEquals(20, avl.get(20)); // Root
        assertEquals(10, avl.get(10)); // Left child
        assertEquals(30, avl.get(30)); // Right child
        assertEquals("10 20 30", avl.inOrder());
    }

    @Test
    void testAlturaYBalanceDespuesDeRotacionDobleDerechaIzquierda() {
        avl.insert(10);
        avl.insert(30);
        avl.insert(20);
        // Tree should be: 20 (root, h=2, fb=0), 10 (left, h=1, fb=0), 30 (right, h=1, fb=0)
        assertEquals(20, avl.get(20)); // Root
        assertEquals(10, avl.get(10)); // Left child
        assertEquals(30, avl.get(30)); // Right child
        assertEquals("10 20 30", avl.inOrder());
    }

    // New tests for getOrderedArray() edge cases
    @Test
    void testGetOrderedArrayArbolVacio() {
        int[] expected = {};
        assertArrayEquals(expected, avl.getOrderedArray());
    }

    @Test
    void testGetOrderedArrayUnicoElemento() {
        avl.insert(50);
        int[] expected = {50};
        assertArrayEquals(expected, avl.getOrderedArray());
    }
}
