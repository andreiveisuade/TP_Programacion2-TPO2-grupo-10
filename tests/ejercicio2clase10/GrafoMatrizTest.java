package ejercicio2clase10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class GrafoMatrizTest {

    private GrafoMatriz<String> grafo;

    @BeforeEach
    void setUp() {
        grafo = new GrafoMatriz<>(5);
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
    @DisplayName("Test 2.1: mayorCostoAristasSalientes() funciona correctamente")
    void testMayorCostoAristasSalientes() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarArista("A", "B", 10);
        grafo.agregarArista("A", "C", 20);
        assertEquals(20, grafo.mayorCostoAristasSalientes("A"));
    }

    @Test
    @DisplayName("Test 2.1: mayorCostoAristasSalientes() devuelve -1 si no hay aristas")
    void testMayorCostoSinAristasSalientes() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista("B", "A", 10);
        assertEquals(-1, grafo.mayorCostoAristasSalientes("A"));
    }

    @Test
    @DisplayName("Test 2.2: predecesores() funciona correctamente")
    void testPredecesores() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarVertice("D");
        grafo.agregarArista("A", "D", 1);
        grafo.agregarArista("B", "D", 1);
        grafo.agregarArista("C", "D", 1);
        Set<String> expected = new HashSet<>(Arrays.asList("A", "B", "C"));
        assertEquals(expected, grafo.predecesores("D"));
    }

    @Test
    @DisplayName("Test 2.2: predecesores() devuelve conjunto vacío si no hay")
    void testPredecesoresSinPredecesores() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista("A", "B", 1);
        assertTrue(grafo.predecesores("A").isEmpty());
    }
}