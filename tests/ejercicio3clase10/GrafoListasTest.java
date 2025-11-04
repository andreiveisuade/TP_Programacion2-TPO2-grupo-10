package ejercicio3clase10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class GrafoListasTest {

    private GrafoListas<String> grafo;

    @BeforeEach
    void setUp() {
        grafo = new GrafoListas<>();
    }

    @Test
    @DisplayName("Test: agregarVertice() y vertices()	")
    void testAgregarYObtenerVertices() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        Object[] vertices = grafo.vertices();
        Set<String> verticesSet = new HashSet<>();
        for (Object v : vertices) {
            verticesSet.add((String) v);
        }
        assertEquals(2, vertices.length);
        assertTrue(verticesSet.containsAll(Arrays.asList("A", "B")));
    }

    @Test
    @DisplayName("Test: agregarArista() no actualiza una arista existente")
    void testAgregarAristaNoActualiza() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista("A", "B", 10);
        grafo.agregarArista("A", "B", 20); // This call should do nothing
        assertEquals(10, grafo.pesoArista("A", "B"));
    }

    @Test
    @DisplayName("Test: eliminarVertice() lo elimina y sus aristas")
    void testEliminarVertice() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarArista("A", "B", 5);
        grafo.agregarArista("C", "A", 3);

        grafo.eliminarVertice("A");
        Object[] vertices = grafo.vertices();
        Set<String> verticesSet = new HashSet<>();
        for (Object v : vertices) {
            verticesSet.add((String) v);
        }

        assertFalse(verticesSet.contains("A"));
        assertEquals(2, vertices.length);
        assertFalse(grafo.existeArista("C", "A"));
    }

    @Test
    @DisplayName("Test 3.1: verticesAislados() funciona correctamente")
    void testVerticesAislados() {
        grafo.agregarVertice("A"); // Has outgoing
        grafo.agregarVertice("B"); // Has incoming
        grafo.agregarVertice("C"); // Is isolated
        grafo.agregarVertice("D"); // Is isolated
        grafo.agregarArista("A", "B", 1);

        Set<String> aislados = grafo.verticesAislados();
        assertEquals(2, aislados.size());
        assertTrue(aislados.containsAll(Arrays.asList("C", "D")));
    }

    @Test
    @DisplayName("Test 3.1: verticesAislados() con grafo sin aislados")
    void testVerticesAisladosSinAislados() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista("A", "B", 1);
        assertTrue(grafo.verticesAislados().isEmpty());
    }

    @Test
    @DisplayName("Test 3.2: verticesPuente() funciona correctamente")
    void testVerticesPuente() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarVertice("D");
        grafo.agregarArista("A", "B", 1);
        grafo.agregarArista("A", "C", 1);
        grafo.agregarArista("B", "D", 1);
        grafo.agregarArista("C", "D", 1);

        Set<String> expected = new HashSet<>(Arrays.asList("B", "C"));
        assertEquals(expected, grafo.verticesPuente("A", "D"));
    }

    @Test
    @DisplayName("Test 3.2: verticesPuente() sin puentes")
    void testVerticesPuenteSinPuentes() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("D");
        grafo.agregarArista("A", "B", 1);
        grafo.agregarArista("A", "D", 1); // Direct edge

        assertTrue(grafo.verticesPuente("A", "D").isEmpty());
    }
}