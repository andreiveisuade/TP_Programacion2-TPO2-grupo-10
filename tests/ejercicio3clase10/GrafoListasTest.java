package ejercicio3clase10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GrafoListasTest {

    private GrafoTDA<String> grafo;

    @BeforeEach
    void setUp() {
        grafo = new GrafoListas<>();
        grafo.inicializar();
    }

    @Test
    @DisplayName("Test: inicializar() después de agregar elementos")
    void testInicializarAfterAddingElements() {
        grafo.agregarVertice("A");
        grafo.agregarArista("A", "A", 1);
        grafo.inicializar();
        assertTrue(grafo.vertices().isEmpty());
    }
    
    @Test
    @DisplayName("Test: agregarVertice() con valor nulo lanza IllegalArgumentException")
    void testAgregarVerticeNull() {
        assertThrows(IllegalArgumentException.class, () -> grafo.agregarVertice(null));
    }

    @Test
    @DisplayName("Test: agregarVertice() añade un vértice correctamente")
    void testAgregarVerticeSingle() {
        grafo.agregarVertice("A");
        assertTrue(grafo.vertices().contains("A"));
        assertEquals(1, grafo.vertices().size());
    }

    @Test
    @DisplayName("Test: agregarVertice() no agrega vértices duplicados")
    void testAgregarVerticeDuplicado() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("A");
        assertEquals(1, grafo.vertices().size());
    }

    @Test
    @DisplayName("Test: agregarVertice() añade múltiples vértices")
    void testAgregarMultiplesVertices() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        assertTrue(grafo.vertices().containsAll(Arrays.asList("A", "B", "C")));
        assertEquals(3, grafo.vertices().size());
    }

    @Test
    @DisplayName("Test: eliminarVertice() lanza excepción si el grafo está vacío")
    void testEliminarVerticeEmptyGraph() {
        assertThrows(IllegalArgumentException.class, () -> grafo.eliminarVertice("A"));
    }

    @Test
    @DisplayName("Test: eliminarVertice() lanza excepción si el vértice es nulo")
    void testEliminarVerticeNull() {
        grafo.agregarVertice("A");
        assertThrows(IllegalArgumentException.class, () -> grafo.eliminarVertice(null));
    }

    @Test
    @DisplayName("Test: eliminarVertice() lanza excepción si el vértice no existe")
    void testEliminarVerticeNonExistent() {
        grafo.agregarVertice("A");
        assertThrows(IllegalArgumentException.class, () -> grafo.eliminarVertice("B"));
    }

    @Test
    @DisplayName("Test: eliminarVertice() elimina un vértice hoja (sin aristas)")
    void testEliminarVerticeHoja() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.eliminarVertice("B");
        assertFalse(grafo.vertices().contains("B"));
        assertTrue(grafo.vertices().contains("A"));
        assertEquals(1, grafo.vertices().size());
    }

    @Test
    @DisplayName("Test: eliminarVertice() elimina un vértice con aristas salientes")
    void testEliminarVerticeConAristasSalientes() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista("A", "B", 1);
        grafo.eliminarVertice("A");
        assertFalse(grafo.vertices().contains("A"));
        assertTrue(grafo.vertices().contains("B"));
        assertThrows(IllegalArgumentException.class, () -> grafo.pesoArista("A", "B")); // Arista debe desaparecer
    }

    @Test
    @DisplayName("Test: eliminarVertice() elimina un vértice y sus aristas entrantes (complemento)")
    void testEliminarVerticeWithIncomingEdges() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarArista("A", "C", 1);
        grafo.agregarArista("B", "C", 2);
        grafo.eliminarVertice("C");
        assertFalse(grafo.existeArista("A", "C"));
        assertFalse(grafo.existeArista("B", "C"));
    }
    
    @Test
    @DisplayName("Test: eliminarVertice() elimina el único vértice en el grafo")
    void testEliminarUnicoVertice() {
        grafo.agregarVertice("A");
        grafo.eliminarVertice("A");
        assertTrue(grafo.vertices().isEmpty());
    }

    @Test
    @DisplayName("Test: agregarArista() con peso negativo lanza IllegalArgumentException")
    void testAgregarAristaNegativeWeight() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        assertThrows(IllegalArgumentException.class, () -> grafo.agregarArista("A", "B", -1));
    }

    @Test
    @DisplayName("Test: agregarArista() con peso cero lanza IllegalArgumentException")
    void testAgregarAristaZeroWeight() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        assertThrows(IllegalArgumentException.class, () -> grafo.agregarArista("A", "B", 0));
    }

    @Test
    @DisplayName("Test: agregarArista() con origen inexistente lanza IllegalArgumentException")
    void testAgregarAristaNonExistentOrigin() {
        grafo.agregarVertice("B");
        assertThrows(IllegalArgumentException.class, () -> grafo.agregarArista("A", "B", 1));
    }

    @Test
    @DisplayName("Test: agregarArista() con destino inexistente lanza IllegalArgumentException")
    void testAgregarAristaNonExistentDestination() {
        grafo.agregarVertice("A");
        assertThrows(IllegalArgumentException.class, () -> grafo.agregarArista("A", "B", 1));
    }

    @Test
    @DisplayName("Test: agregarArista() la añade correctamente")
    void testAgregarAristaSimple() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista("A", "B", 5);
        assertTrue(grafo.existeArista("A", "B"));
        assertEquals(5, grafo.pesoArista("A", "B"));
    }

    @Test
    @DisplayName("Test: agregarArista() actualiza el peso si la arista ya existe")
    void testAgregarAristaActualizaPeso() { // Existing test, now with DisplayName
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista("A", "B", 5);
        assertEquals(5, grafo.pesoArista("A", "B"));
        grafo.agregarArista("A", "B", 10); // Actualizar
        assertEquals(10, grafo.pesoArista("A", "B"));
    }
    
    @Test
    @DisplayName("Test: agregarArista() permite auto-bucles")
    void testAgregarAristaSelfLoop() {
        grafo.agregarVertice("A");
        grafo.agregarArista("A", "A", 3);
        assertTrue(grafo.existeArista("A", "A"));
        assertEquals(3, grafo.pesoArista("A", "A"));
    }

    @Test
    @DisplayName("Test: eliminarArista() elimina una arista existente")
    void testEliminarAristaExistente() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista("A", "B", 1);
        grafo.eliminarArista("A", "B");
        assertFalse(grafo.existeArista("A", "B"));
    }

    @Test
    @DisplayName("Test: eliminarArista() no hace nada si la arista no existe")
    void testEliminarAristaNonExistent() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista("A", "B", 1);
        grafo.eliminarArista("A", "C"); // No existe
        assertTrue(grafo.existeArista("A", "B")); // La arista original debe seguir
    }
    
    @Test
    @DisplayName("Test: eliminarArista() no hace nada si el origen no existe")
    void testEliminarAristaNonExistentOrigin() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista("A", "B", 1);
        grafo.eliminarArista("C", "B");
        assertTrue(grafo.existeArista("A", "B"));
    }

    @Test
    @DisplayName("Test: existeArista() devuelve true para arista existente")
    void testExisteAristaTrue() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista("A", "B", 1);
        assertTrue(grafo.existeArista("A", "B"));
    }

    @Test
    @DisplayName("Test: existeArista() devuelve false para arista no existente")
    void testExisteAristaFalse() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        assertFalse(grafo.existeArista("A", "B"));
    }
    
    @Test
    @DisplayName("Test: existeArista() devuelve false para origen inexistente")
    void testExisteAristaNonExistentOrigin() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista("A", "B", 1);
        assertFalse(grafo.existeArista("C", "B"));
    }

    @Test
    @DisplayName("Test: pesoArista() devuelve el peso correcto")
    void testPesoAristaCorrecto() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista("A", "B", 10);
        assertEquals(10, grafo.pesoArista("A", "B"));
    }

    @Test
    @DisplayName("Test: pesoArista() lanza excepción si la arista no existe")
    void testPesoAristaNonExistent() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        assertThrows(IllegalArgumentException.class, () -> grafo.pesoArista("A", "B"));
    }
    
    @Test
    @DisplayName("Test: pesoArista() lanza excepción si el origen no existe")
    void testPesoAristaNonExistentOrigin() {
        grafo.agregarVertice("A");
        assertThrows(IllegalArgumentException.class, () -> grafo.pesoArista("B", "A"));
    }

    @Test
    @DisplayName("Test: vertices() devuelve lista vacía para grafo vacío")
    void testVerticesEmpty() {
        assertTrue(grafo.vertices().isEmpty());
    }

    @Test
    @DisplayName("Test: vertices() devuelve todos los vértices agregados")
    void testVerticesMultiple() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        List<String> expected = Arrays.asList("C", "B", "A"); // Order might vary based on implementation
        assertTrue(grafo.vertices().containsAll(expected));
        assertEquals(3, grafo.vertices().size());
    }
    
    @Test
    @DisplayName("Test: vertices() después de agregar y eliminar algunos")
    void testVerticesAfterAddRemove() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.eliminarVertice("B");
        Set<String> expected = new HashSet<>(Arrays.asList("C", "A"));
        assertEquals(expected, new HashSet<>(grafo.vertices()));
    }

    @Test
    @DisplayName("Test: verticesAislados() con grafo vacío")
    void testVerticesAisladosEmptyGraph() {
        assertTrue(grafo.verticesAislados().isEmpty());
    }
    
    @Test
    @DisplayName("Test: verticesAislados() con todos los vértices conectados")
    void testVerticesAisladosAllConnected() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista("A", "B", 1);
        grafo.agregarArista("B", "A", 1);
        assertTrue(grafo.verticesAislados().isEmpty());
    }

    @Test
    @DisplayName("Test: verticesAislados() con vértices que solo tienen aristas salientes")
    void testVerticesAisladosOnlyOutgoing() {
        grafo.agregarVertice("A"); // A -> B
        grafo.agregarVertice("B"); // B -> C
        grafo.agregarVertice("C"); // C es hoja, no tiene salientes
        grafo.agregarVertice("D"); // D es aislado
        grafo.agregarVertice("E"); // E es aislado
        
        grafo.agregarArista("A", "B", 1);
        grafo.agregarArista("B", "C", 1);
        
        Set<String> aislados = grafo.verticesAislados();
        assertEquals(2, aislados.size());
        assertTrue(aislados.contains("D"));
        assertTrue(aislados.contains("E"));
    }

    @Test
    @DisplayName("Test: verticesAislados() con vértices que solo tienen aristas entrantes")
    void testVerticesAisladosOnlyIncoming() {
        grafo.agregarVertice("A"); // B -> A
        grafo.agregarVertice("B");
        grafo.agregarVertice("C"); // A -> C
        grafo.agregarVertice("D"); // D es aislado
        
        grafo.agregarArista("B", "A", 1);
        grafo.agregarArista("A", "C", 1);
        
        // 'B' solo tiene salientes. 'A' tiene entrantes y salientes. 'C' tiene entrantes. 'D' es aislado.
        Set<String> aislados = grafo.verticesAislados();
        assertEquals(1, aislados.size());
        assertTrue(aislados.contains("D"));
    }
    
    @Test
    @DisplayName("Test: verticesAislados() - Escenario Mixto")
    void testVerticesAisladosMixto() {
        grafo.agregarVertice("A"); // A -> B
        grafo.agregarVertice("B"); // C -> B
        grafo.agregarVertice("C"); // C -> B
        grafo.agregarVertice("D"); // D aislado
        grafo.agregarVertice("E"); // E aislado

        grafo.agregarArista("A", "B", 1);
        grafo.agregarArista("C", "B", 1);

        Set<String> aislados = grafo.verticesAislados();
        assertEquals(2, aislados.size());
        assertTrue(aislados.contains("D"));
        assertTrue(aislados.contains("E"));
    }

    @Test
    @DisplayName("Test: verticesPuente() con grafo vacío lanza excepción para origen")
    void testVerticesPuenteEmptyGraph() {
        assertThrows(IllegalArgumentException.class, () -> grafo.verticesPuente("A", "B"));
    }

    @Test
    @DisplayName("Test: verticesPuente() con origen inexistente lanza excepción")
    void testVerticesPuenteNonExistentOrigin() {
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        assertThrows(IllegalArgumentException.class, () -> grafo.verticesPuente("A", "C"));
    }

    @Test
    @DisplayName("Test: verticesPuente() con destino inexistente lanza excepción")
    void testVerticesPuenteNonExistentDestination() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        assertThrows(IllegalArgumentException.class, () -> grafo.verticesPuente("A", "C"));
    }

    @Test
    @DisplayName("Test: verticesPuente() cuando origen y destino son el mismo")
    void testVerticesPuenteSameOriginDestination() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarArista("A", "B", 1);
        grafo.agregarArista("B", "C", 1);
        grafo.agregarArista("C", "A", 1);

        Set<String> puentes = grafo.verticesPuente("A", "A");
        assertTrue(puentes.isEmpty()); // No hay puente entre el mismo vértice
    }

    @Test
    @DisplayName("Test: verticesPuente() con arista directa entre origen y destino (no puente)")
    void testVerticesPuenteDirectEdge() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarArista("A", "B", 1);
        grafo.eliminarArista("A", "B"); // Remove this line to test direct connection
        grafo.agregarArista("A", "C", 1);
        grafo.agregarArista("C", "B", 1);
        
        Set<String> puentes = grafo.verticesPuente("A", "B");
        assertEquals(1, puentes.size());
        assertTrue(puentes.contains("C")); // 'C' es puente A->C->B
    }

    @Test
    @DisplayName("Test: verticesPuente() con múltiples puentes")
    void testVerticesPuenteMultipleBridges() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarVertice("D");
        
        grafo.agregarArista("A", "B", 1);
        grafo.agregarArista("A", "C", 1);
        grafo.agregarArista("B", "D", 1);
        grafo.agregarArista("C", "D", 1);
        
        Set<String> puentes = grafo.verticesPuente("A", "D");
        assertEquals(2, puentes.size());
        assertTrue(puentes.containsAll(Arrays.asList("B", "C")));
    }

    @Test
    @DisplayName("Test: verticesPuente() sin puentes")
    void testVerticesPuenteNoBridges() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista("A", "B", 1); // Direct edge, no bridge
        Set<String> puentes = grafo.verticesPuente("A", "B");
        assertTrue(puentes.isEmpty());
    }
    
    @Test
    @DisplayName("Test: verticesPuente() - Grafo complejo")
    void testVerticesPuenteComplexGraph() {
        grafo.agregarVertice("S"); // Start
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarVertice("D");
        grafo.agregarVertice("E"); // End

        grafo.agregarArista("S", "A", 1);
        grafo.agregarArista("S", "B", 1);
        grafo.agregarArista("A", "C", 1);
        grafo.agregarArista("B", "C", 1);
        grafo.agregarArista("C", "D", 1);
        grafo.agregarArista("D", "E", 1);
        grafo.agregarArista("B", "E", 1); // Direct path B->E

        Set<String> puentes = grafo.verticesPuente("S", "E");
        assertEquals(1, puentes.size());
        assertTrue(puentes.contains("B")); // S->B->E
    }
    
    @Test
    @DisplayName("Test: verticesPuente() - No hay path")
    void testVerticesPuenteNoPath() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarArista("A", "B", 1);
        // No hay arista hacia C
        Set<String> puentes = grafo.verticesPuente("A", "C");
        assertTrue(puentes.isEmpty());
    }

    @Test
    @DisplayName("Test: eliminarVertice() elimina un vértice del medio de una cadena")
    void testEliminarVerticeDelMedioDeLaCadena() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarArista("A", "B", 1);
        grafo.agregarArista("B", "C", 1);

        grafo.eliminarVertice("B");

        assertFalse(grafo.vertices().contains("B"));
        assertTrue(grafo.vertices().contains("A"));
        assertTrue(grafo.vertices().contains("C"));
        assertFalse(grafo.existeArista("A", "B"));
        assertFalse(grafo.existeArista("B", "C"));
        // A -> C no debería existir directamente a menos que se haya agregado
        assertFalse(grafo.existeArista("A", "C"));
    }

    @Test
    @DisplayName("Test: verticesAislados() con un solo vértice aislado")
    void testVerticesAisladosConUnSoloVertice() {
        grafo.agregarVertice("A");
        Set<String> aislados = grafo.verticesAislados();
        assertEquals(1, aislados.size());
        assertTrue(aislados.contains("A"));
    }

    @Test
    @DisplayName("Test: verticesAislados() en un ciclo completo")
    void testVerticesAisladosEnUnCicloCompleto() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarArista("A", "B", 1);
        grafo.agregarArista("B", "C", 1);
        grafo.agregarArista("C", "A", 1);

        Set<String> aislados = grafo.verticesAislados();
        assertTrue(aislados.isEmpty());
    }

    @Test
    @DisplayName("Test: verticesPuente() sin camino posible entre origen y destino")
    void testVerticesPuenteSinCaminoPosible() {
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarVertice("D");
        grafo.agregarArista("A", "B", 1);
        grafo.agregarArista("C", "D", 1);

        Set<String> puentes = grafo.verticesPuente("A", "D");
        assertTrue(puentes.isEmpty());
    }
}
