package ejercicio3clase10;

import java.util.Set;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal que demuestra el funcionamiento de la implementación de un grafo
 * utilizando listas de adyacencia ({@link GrafoListas}).
 * Realiza operaciones de agregación y eliminación de vértices y aristas,
 * y ejecuta los métodos para encontrar vértices aislados y vértices puente.
 */
public class Main {
    /**
     * Método principal que inicia la demostración del grafo.
     * @param args Argumentos de la línea de comandos (no utilizados en esta aplicación).
     */
    public static void main(String[] args) {
        GrafoTDA<String> grafo = new GrafoListas<>();
        grafo.inicializar();

        System.out.println("------------------------------------------------");
        System.out.println("|  GRAFO CON LISTAS DE ADYACENCIA           |");
        System.out.println("|  Ejercicio 3 - Clase 10                   |");
        System.out.println("------------------------------------------------\n");

        // ============================================ 
        // PASO 1: Crear el grafo 
        // ============================================ 
        System.out.println("═══ PASO 1: Agregando vértices ═══");
        String[] vertices = {"A", "B", "C", "D", "E", "F", "G", "H"};
        for (String v : vertices) {
            grafo.agregarVertice(v);
        }
        System.out.println("✓ Vértices agregados: A, B, C, D, E, F, G, H\n");

        // ============================================ 
        // PASO 2: Agregar aristas (diseñadas para demostrar los ejercicios) 
        // ============================================ 
        System.out.println("═══ PASO 2: Agregando aristas ═══");
        
        // Crear conexiones que generen puentes
        grafo.agregarArista("A", "B", 5);
        grafo.agregarArista("A", "C", 3);
        grafo.agregarArista("B", "D", 2);
        grafo.agregarArista("C", "D", 4);
        grafo.agregarArista("D", "E", 6);
        grafo.agregarArista("E", "F", 1);
        
        // G está aislado (sin aristas entrantes ni salientes)
        // H también está aislado
        
        System.out.println("✓ Aristas agregadas\n");

        // Mostrar el grafo
        ((GrafoListas<String>) grafo).imprimirGrafo();
        ((GrafoListas<String>) grafo).imprimirEstadisticas();

        // ============================================ 
        // EJERCICIO 3.1: Vértices Aislados 
        // ============================================ 
        System.out.println("------------------------------------------------");
        System.out.println("|  EJERCICIO 3.1: Vértices Aislados        |");
        System.out.println("------------------------------------------------\n");

        Set<String> aislados = grafo.verticesAislados();
        System.out.println("Vértices aislados: " + aislados);
        
        if (aislados.isEmpty()) {
            System.out.println("(No hay vértices aislados)");
        } else {
            System.out.println("\nExplicación:");
            for (String v : aislados) {
                System.out.println("  - " + v + ": no tiene aristas salientes ni entrantes");
            }
        }

        // ============================================ 
        // EJERCICIO 3.2: Vértices Puente 
        // ============================================ 
        System.out.println("\n------------------------------------------------");
        System.out.println("|  EJERCICIO 3.2: Vértices Puente          |");
        System.out.println("------------------------------------------------\n");

        // Casos de prueba
        String[][] casosPrueba = {
            {"A", "D"},
            {"A", "E"},
            {"B", "F"},
            {"C", "E"},
            {"A", "F"}
        };

        for (String[] caso : casosPrueba) {
            String origen = caso[0];
            String destino = caso[1];
            
            try {
                Set<String> puentes = grafo.verticesPuente(origen, destino);
                System.out.printf("Puentes entre %s y %s: ", origen, destino);
                
                if (puentes.isEmpty()) {
                    System.out.println("(ninguno)");
                } else {
                    System.out.println(puentes);
                    System.out.println("  Explicación:");
                    for (String p : puentes) {
                        System.out.printf("    - %s → %s → %s\n", origen, p, destino);
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.printf("Puentes entre %s y %s: Error - %s\n", 
                                origen, destino, e.getMessage());
            }
            System.out.println();
        }

        // ============================================ 
        // DEMOSTRACIÓN ADICIONAL 
        // ============================================ 
        System.out.println("------------------------------------------------");
        System.out.println("|  DEMOSTRACIÓN: Agregar y Quitar Aristas  |");
        System.out.println("------------------------------------------------\n");

        System.out.println("Agregando aristas para conectar vértices aislados...");
        grafo.agregarArista("G", "H", 10);
        System.out.println("✓ Arista G → H agregada\n");

        ((GrafoListas<String>) grafo).imprimirGrafo();

        System.out.println("Verificando vértices aislados nuevamente:");
        aislados = grafo.verticesAislados();
        System.out.println("Vértices aislados: " + aislados);
        System.out.println("(G ya no está aislado porque tiene arista saliente)");
        System.out.println("(H ya no está aislado porque tiene arista entrante)\n");

        // ============================================ 
        // ANÁLISIS DE COMPLEJIDAD 
        // ============================================ 
        System.out.println("------------------------------------------------");
        System.out.println("|  ANÁLISIS DE COMPLEJIDAD                  |");
        System.out.println("------------------------------------------------");
        System.out.println("verticesAislados(): O(n×k)");
        System.out.println("  - n = cantidad de vértices");
        System.out.println("  - k = promedio de aristas por vértice");
        System.out.println("  - Requiere 3 pasadas por el grafo");
        System.out.println();
        System.out.println("verticesPuente(origen, destino): O(k₁×k₂)");
        System.out.println("  - k₁ = aristas salientes del origen");
        System.out.println("  - k₂ = aristas salientes de cada candidato");
        System.out.println("  - En el peor caso (grafo completo): O(n²)");
        System.out.println("  - En grafos dispersos: mucho mejor que O(n²)");
        System.out.println();
    }
}
