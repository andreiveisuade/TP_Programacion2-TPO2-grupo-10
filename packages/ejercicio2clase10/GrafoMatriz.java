package ejercicio2clase10;

import java.util.*;

public class GrafoMatriz<E> implements GrafoTDA<E> {
    private int[][] matrizAdyacencia;
    private Map<E, Integer> verticeIndice; // Mapeo vértice → índice en matriz
    private List<E> indiceVertice; // Mapeo índice → vértice
    private int maxVertices;
    private int cantidadVertices;
    private static final int SIN_ARISTA = 0;

    @Override
    public void inicializar(int maxVertices) {
        this.maxVertices = maxVertices;
        this.cantidadVertices = 0;
        this.matrizAdyacencia = new int[maxVertices][maxVertices];
        this.verticeIndice = new HashMap<>();
        this.indiceVertice = new ArrayList<>();
        
        // Inicializar matriz con SIN_ARISTA
        for (int i = 0; i < maxVertices; i++) {
            for (int j = 0; j < maxVertices; j++) {
                matrizAdyacencia[i][j] = SIN_ARISTA;
            }
        }
    }

    @Override
    public void agregarVertice(E vertice) {
        if (vertice == null) {
            throw new IllegalArgumentException("El vértice no puede ser null");
        }
        if (verticeIndice.containsKey(vertice)) {
            return; // Ya existe
        }
        if (cantidadVertices >= maxVertices) {
            throw new IllegalStateException("Se alcanzó el máximo de vértices");
        }
        
        verticeIndice.put(vertice, cantidadVertices);
        indiceVertice.add(vertice);
        cantidadVertices++;
    }

    @Override
    public void eliminarVertice(E vertice) {
        if (vertice == null) {
            throw new IllegalArgumentException("El vértice no puede ser null");
        }
        if (!verticeIndice.containsKey(vertice)) {
            throw new IllegalArgumentException("El vértice no existe");
        }
        
        int indice = verticeIndice.get(vertice);
        
        // Eliminar aristas (fila y columna)
        for (int i = 0; i < cantidadVertices; i++) {
            matrizAdyacencia[indice][i] = SIN_ARISTA;
            matrizAdyacencia[i][indice] = SIN_ARISTA;
        }
        
        // Mover la última fila/columna a la posición eliminada
        if (indice < cantidadVertices - 1) {
            int ultimoIndice = cantidadVertices - 1;
            E ultimoVertice = indiceVertice.get(ultimoIndice);
            
            // Mover fila
            for (int j = 0; j < cantidadVertices; j++) {
                matrizAdyacencia[indice][j] = matrizAdyacencia[ultimoIndice][j];
                matrizAdyacencia[ultimoIndice][j] = SIN_ARISTA;
            }
            
            // Mover columna
            for (int i = 0; i < cantidadVertices; i++) {
                matrizAdyacencia[i][indice] = matrizAdyacencia[i][ultimoIndice];
                matrizAdyacencia[i][ultimoIndice] = SIN_ARISTA;
            }
            
            // Actualizar mapeos
            verticeIndice.put(ultimoVertice, indice);
            indiceVertice.set(indice, ultimoVertice);
        }
        
        verticeIndice.remove(vertice);
        indiceVertice.remove(cantidadVertices - 1);
        cantidadVertices--;
    }

    @Override
    public void agregarArista(E origen, E destino, int peso) {
        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser positivo");
        }
        if (!verticeIndice.containsKey(origen) || !verticeIndice.containsKey(destino)) {
            throw new IllegalArgumentException("Ambos vértices deben existir");
        }
        
        int i = verticeIndice.get(origen);
        int j = verticeIndice.get(destino);
        matrizAdyacencia[i][j] = peso;
    }

    @Override
    public void eliminarArista(E origen, E destino) {
        if (!verticeIndice.containsKey(origen) || !verticeIndice.containsKey(destino)) {
            return;
        }
        
        int i = verticeIndice.get(origen);
        int j = verticeIndice.get(destino);
        matrizAdyacencia[i][j] = SIN_ARISTA;
    }

    @Override
    public boolean existeArista(E origen, E destino) {
        if (!verticeIndice.containsKey(origen) || !verticeIndice.containsKey(destino)) {
            return false;
        }
        
        int i = verticeIndice.get(origen);
        int j = verticeIndice.get(destino);
        return matrizAdyacencia[i][j] != SIN_ARISTA;
    }

    @Override
    public int pesoArista(E origen, E destino) {
        if (!existeArista(origen, destino)) {
            throw new IllegalArgumentException("La arista no existe");
        }
        
        int i = verticeIndice.get(origen);
        int j = verticeIndice.get(destino);
        return matrizAdyacencia[i][j];
    }

    @Override
    public List<E> vertices() {
        return new ArrayList<>(indiceVertice.subList(0, cantidadVertices));
    }

    // ============================================ 
    // MÉTODOS REQUERIDOS POR LA CONSIGNA
    // ============================================ 

    /**
     * Ejercicio 2.1: Calcular el mayor de los costos de las aristas salientes.
     * Complejidad: O(n) donde n = cantidad de vértices
     */
    @Override
    public int mayorCostoAristasSalientes(E vertice) {
        if (!verticeIndice.containsKey(vertice)) {
            throw new IllegalArgumentException("El vértice no existe");
        }
        
        int indice = verticeIndice.get(vertice);
        int mayorCosto = -1; // Inicializar con -1 para indicar "sin aristas"
        
        // Recorrer la fila (aristas salientes)
        for (int j = 0; j < cantidadVertices; j++) {
            if (matrizAdyacencia[indice][j] != SIN_ARISTA) {
                mayorCosto = Math.max(mayorCosto, matrizAdyacencia[indice][j]);
            }
        }
        
        if (mayorCosto == -1) {
            throw new IllegalStateException("El vértice no tiene aristas salientes");
        }
        
        return mayorCosto;
    }

    /**
     * Ejercicio 2.2: Obtener el conjunto de predecesores del vértice.
     * Un predecesor es un vértice que tiene una arista hacia el vértice dado.
     * Complejidad: O(n) donde n = cantidad de vértices
     */
    @Override
    public Set<E> predecesores(E vertice) {
        if (!verticeIndice.containsKey(vertice)) {
            throw new IllegalArgumentException("El vértice no existe");
        }
        
        Set<E> predecesores = new HashSet<>();
        int indiceDestino = verticeIndice.get(vertice);
        
        // Recorrer la columna (aristas entrantes)
        for (int i = 0; i < cantidadVertices; i++) {
            if (matrizAdyacencia[i][indiceDestino] != SIN_ARISTA) {
                predecesores.add(indiceVertice.get(i));
            }
        }
        
        return predecesores;
    }

    /**
     * Método auxiliar para imprimir la matriz de adyacencia.
     */
    public void imprimirMatriz() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║      MATRIZ DE ADYACENCIA                 ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
        
        if (cantidadVertices == 0) {
            System.out.println("El grafo está vacío.\n");
            return;
        }
        
        // Encabezado
        System.out.print("      ");
        for (int j = 0; j < cantidadVertices; j++) {
            System.out.printf("% -6s", indiceVertice.get(j));
        }
        System.out.println();
        
        // Filas
        for (int i = 0; i < cantidadVertices; i++) {
            System.out.printf("% -6s", indiceVertice.get(i));
            for (int j = 0; j < cantidadVertices; j++) {
                int peso = matrizAdyacencia[i][j];
                System.out.printf("% -6s", peso == SIN_ARISTA ? "-" : peso);
            }
            System.out.println();
        }
        System.out.println();
    }
}
