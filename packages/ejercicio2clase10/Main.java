package ejercicio2clase10;

import ejercicio3clase10.ImplementacionHashSet;

public class Main {
    public static void main(String[] args) {
        GrafoMatriz<String> grafo = new GrafoMatriz<>(10);

        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║  GRAFO CON MATRIZ DE ADYACENCIA           ║");
        System.out.println("║  Ejercicio 2 - Clase 10                   ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        // Crear grafo de ejemplo
        System.out.println("═══ Agregando vértices ═══");
        String[] vertices = {"A", "B", "C", "D", "E"};
        for (String v : vertices) {
            grafo.agregarVertice(v);
        }
        System.out.println("✓ Vértices agregados: A, B, C, D, E\n");

        System.out.println("═══ Agregando aristas ═══");
        grafo.agregarArista("A", "B", 5);
        grafo.agregarArista("A", "C", 3);
        grafo.agregarArista("A", "D", 7);
        grafo.agregarArista("B", "C", 2);
        grafo.agregarArista("B", "D", 4);
        grafo.agregarArista("C", "E", 6);
        grafo.agregarArista("D", "E", 1);
        System.out.println("✓ Aristas agregadas\n");

        // Mostrar matriz
        grafo.imprimirMatriz();

        // Ejercicio 2.1: Mayor costo de aristas salientes
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║  EJERCICIO 2.1: Mayor Costo Salientes    ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
        
        for (String v : vertices) {
            try {
                int mayorCosto = grafo.mayorCostoAristasSalientes(v);
                if (mayorCosto != -1) {
                    System.out.printf("Vértice %s → Mayor costo: %d\n", v, mayorCosto);
                } else {
                    System.out.printf("Vértice %s → Sin aristas salientes\n", v);
                }
            } catch (Exception e) {
                System.out.printf("Vértice %s → Error al calcular costo\n", v);
            }
        }

        // Ejercicio 2.2: Predecesores
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║  EJERCICIO 2.2: Predecesores              ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
        
        for (String v : vertices) {
            ImplementacionHashSet<String> preds = grafo.predecesores(v);
            System.out.printf("Vértice %s → Predecesores: %s\n", v, preds.isEmpty() ? "(ninguno)" : preds);
        }
    }
}
