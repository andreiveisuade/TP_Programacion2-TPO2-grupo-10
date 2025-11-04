package ejercicio2clase10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class GrafoMatrizTest {

    private GrafoTDA<String> grafo;

    @BeforeEach
    void setUp() {
        grafo = new GrafoMatriz<>();
        grafo.inicializar(5); // Initialize with a capacity of 5 vertices
    }

    // --- Tests for basic operations ---

    @Test
    @DisplayName("Test: agregarVertice() y vertices()	")
    void testAgregarYObtenerVertices() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        List<String> vertices = grafo.vertices();
        assertEquals(2, vertices.size());
        assertTrue(vertices.containsAll(Arrays.asList("A", "B")));
    }

    @Test
    @DisplayName("Test: agregarVertice() no agrega duplicados")
    void testAgregarVerticeDuplicado() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("A");
        assertEquals(1, grafo.vertices().size());
    }

    @Test
    @DisplayName("Test: agregarVertice() lanza excepción si el grafo está lleno")
    void testAgregarVerticeGrafoLleno() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarVertice("D");
        grafo.agregarVertice("E");
        assertThrows(IllegalStateException.class, () -> grafo.agregarVertice("F"));
    }

    @Test
    @DisplayName("Test: agregarArista(), existeArista() y pesoArista()	")
    void testAgregarYConsultarArista() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista("A", "B", 10);
        assertTrue(grafo.existeArista("A", "B"));
        assertEquals(10, grafo.pesoArista("A", "B"));
    }

    @Test
    @DisplayName("Test: existeArista() devuelve false si no hay arista")
    void testExisteAristaFalso() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        assertFalse(grafo.existeArista("A", "B"));
    }

    @Test
    @DisplayName("Test: eliminarArista() la elimina correctamente")
    void testEliminarArista() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista("A", "B", 10);
        grafo.eliminarArista("A", "B");
        assertFalse(grafo.existeArista("A", "B"));
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

        assertFalse(grafo.vertices().contains("A"));
        assertEquals(2, grafo.vertices().size());
        assertFalse(grafo.existeArista("A", "B"));
        assertFalse(grafo.existeArista("C", "A"));
    }
    
    // --- Tests for exercise-specific methods ---

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
    @DisplayName("Test 2.1: mayorCostoAristasSalientes() con una sola arista")
    void testMayorCostoUnaSolaArista() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista("A", "B", 5);

        assertEquals(5, grafo.mayorCostoAristasSalientes("A"));
    }

    @Test
    @DisplayName("Test 2.1: mayorCostoAristasSalientes() lanza excepción si no hay aristas salientes")
    void testMayorCostoSinAristasSalientes() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista("B", "A", 10); // Arista entrante a A, no saliente

        assertThrows(IllegalStateException.class, () -> grafo.mayorCostoAristasSalientes("A"));
    }

    @Test
    @DisplayName("Test 2.1: mayorCostoAristasSalientes() lanza excepción si el vértice no existe")
    void testMayorCostoVerticeInexistente() {
        assertThrows(IllegalArgumentException.class, () -> grafo.mayorCostoAristasSalientes("Z"));
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
        grafo.agregarArista("A", "B", 1); // A no tiene predecesores

        assertTrue(grafo.predecesores("A").isEmpty());
    }

    @Test
    @DisplayName("Test 2.2: predecesores() con auto-bucle")
    void testPredecesoresConAutoBucle() {
        grafo.agregarVertice("A");
        grafo.agregarArista("A", "A", 1);

        Set<String> expected = new HashSet<>(Arrays.asList("A"));
        assertEquals(expected, grafo.predecesores("A"));
    }

    @Test
    @DisplayName("Test 2.2: predecesores() lanza excepción si el vértice no existe")
    void testPredecesoresVerticeInexistente() {
        assertThrows(IllegalArgumentException.class, () -> grafo.predecesores("Z"));
    }
}
