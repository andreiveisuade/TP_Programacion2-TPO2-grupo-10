package ejercicio2clase10;

import ejercicio3clase10.ImplementacionArrayList;
import ejercicio3clase10.ImplementacionHashMap;
import ejercicio3clase10.ImplementacionHashSet;

public class GrafoMatriz<E> implements GrafoTDA<E> {
    private int[][] matrizAdyacencia;
    private ImplementacionHashMap<E, Integer> verticeIndice; // Mapeo vértice → índice en matriz
    private ImplementacionArrayList<E> indiceVertice; // Mapeo índice → vértice
    private int maxVertices;
    private int cantidadVertices;
    private static final int SIN_ARISTA = 0;

    public GrafoMatriz(int maxVertices) {
        this.maxVertices = maxVertices;
        this.cantidadVertices = 0;
        this.matrizAdyacencia = new int[maxVertices][maxVertices];
        this.verticeIndice = new ImplementacionHashMap<>();
        this.indiceVertice = new ImplementacionArrayList<>();
        
        for (int i = 0; i < maxVertices; i++) {
            for (int j = 0; j < maxVertices; j++) {
                matrizAdyacencia[i][j] = SIN_ARISTA;
            }
        }
    }

    @Override
    public void agregarVertice(E vertice) {
        if (!verticeIndice.containsKey(vertice) && cantidadVertices < maxVertices) {
            verticeIndice.put(vertice, cantidadVertices);
            indiceVertice.add(vertice);
            cantidadVertices++;
        }
    }

    @Override
    public void eliminarVertice(E vertice) {
        int indice = verticeIndice.get(vertice);
        
        for (int i = 0; i < cantidadVertices; i++) {
            matrizAdyacencia[indice][i] = SIN_ARISTA;
            matrizAdyacencia[i][indice] = SIN_ARISTA;
        }
        
        if (indice < cantidadVertices - 1) {
            int ultimoIndice = cantidadVertices - 1;
            E ultimoVertice = indiceVertice.get(ultimoIndice);
            
            for (int j = 0; j < cantidadVertices; j++) {
                matrizAdyacencia[indice][j] = matrizAdyacencia[ultimoIndice][j];
            }
            
            for (int i = 0; i < cantidadVertices; i++) {
                matrizAdyacencia[i][indice] = matrizAdyacencia[i][ultimoIndice];
            }
            
            verticeIndice.put(ultimoVertice, indice);
            indiceVertice.set(indice, ultimoVertice);
        }
        
        verticeIndice.remove(vertice);
        indiceVertice.remove(cantidadVertices - 1);
        cantidadVertices--;
    }

    @Override
    public void agregarArista(E origen, E destino, int peso) {
        int i = verticeIndice.get(origen);
        int j = verticeIndice.get(destino);
        if (matrizAdyacencia[i][j] == SIN_ARISTA) {
            matrizAdyacencia[i][j] = peso;
        }
    }

    @Override
    public void eliminarArista(E origen, E destino) {
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
        int i = verticeIndice.get(origen);
        int j = verticeIndice.get(destino);
        return matrizAdyacencia[i][j];
    }

    @Override
    public E[] vertices() {
        @SuppressWarnings("unchecked")
        E[] array = (E[]) new Object[cantidadVertices];
        for(int i = 0; i < cantidadVertices; i++) {
            array[i] = indiceVertice.get(i);
        }
        return array;
    }

    // Métodos específicos del ejercicio 2
    public int mayorCostoAristasSalientes(E vertice) {
        int indice = verticeIndice.get(vertice);
        int mayorCosto = -1;
        boolean tieneSalientes = false;
        for (int j = 0; j < cantidadVertices; j++) {
            if (matrizAdyacencia[indice][j] != SIN_ARISTA) {
                tieneSalientes = true;
                if(matrizAdyacencia[indice][j] > mayorCosto) {
                    mayorCosto = matrizAdyacencia[indice][j];
                }
            }
        }
        if (!tieneSalientes) {
            // Opcional: decidir qué hacer si no hay aristas. El PDF no lo especifica.
            // Devolver -1 o lanzar excepción son opciones válidas.
            return -1; // Devolvemos -1 para indicar que no hay aristas salientes
        }
        return mayorCosto;
    }

    public ImplementacionHashSet<E> predecesores(E vertice) {
        ImplementacionHashSet<E> predecesores = new ImplementacionHashSet<>();
        int indiceDestino = verticeIndice.get(vertice);
        for (int i = 0; i < cantidadVertices; i++) {
            if (matrizAdyacencia[i][indiceDestino] != SIN_ARISTA) {
                predecesores.add(indiceVertice.get(i));
            }
        }
        return predecesores;
    }

    public void imprimirMatriz() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║      MATRIZ DE ADYACENCIA                 ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
        if (cantidadVertices == 0) {
            System.out.println("El grafo está vacío.\n");
            return;
        }
        System.out.print("      ");
        for (int j = 0; j < cantidadVertices; j++) {
            System.out.printf("% -6s", indiceVertice.get(j));
        }
        System.out.println();
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