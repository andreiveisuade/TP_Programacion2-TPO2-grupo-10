package ejercicio2clase10;

public class GrafoMatriz<E> implements GrafoTDA<E> {
    private int[][] matrizAdyacencia;
    private E[] etiquetas; // Arreglo de etiquetas para mapear índice → vértice
    private int maxVertices;
    private int cantidadVertices;
    private static final int SIN_ARISTA = 0;

    @SuppressWarnings("unchecked")
    public GrafoMatriz(int maxVertices) {
        this.maxVertices = maxVertices;
        this.cantidadVertices = 0;
        this.matrizAdyacencia = new int[maxVertices][maxVertices];
        this.etiquetas = (E[]) new Object[maxVertices]; // Inicialización del arreglo de etiquetas
        
        for (int i = 0; i < maxVertices; i++) {
            for (int j = 0; j < maxVertices; j++) {
                matrizAdyacencia[i][j] = SIN_ARISTA;
            }
        }
    }

    // Helper para obtener el índice de un vértice
    private int obtenerIndice(E vertice) {
        for (int i = 0; i < cantidadVertices; i++) {
            if (etiquetas[i].equals(vertice)) {
                return i;
            }
        }
        return -1; // Vértice no encontrado
    }

    @Override
    public void agregarVertice(E vertice) {
        if (obtenerIndice(vertice) == -1 && cantidadVertices < maxVertices) {
            etiquetas[cantidadVertices] = vertice;
            cantidadVertices++;
        }
    }

    @Override
    public void eliminarVertice(E vertice) {
        int indice = obtenerIndice(vertice);
        if (indice == -1) {
            return; // El vértice no existe
        }

        // Eliminar todas las aristas relacionadas con el vértice
        for (int i = 0; i < cantidadVertices; i++) {
            matrizAdyacencia[indice][i] = SIN_ARISTA; // Eliminar aristas salientes
            matrizAdyacencia[i][indice] = SIN_ARISTA; // Eliminar aristas entrantes
        }

        // Si no es el último vértice, mover el último vértice a la posición del eliminado
        if (indice < cantidadVertices - 1) {
            int ultimoIndice = cantidadVertices - 1;
            E ultimoVertice = etiquetas[ultimoIndice];

            // Mover la fila del último vértice a la posición del vértice eliminado
            for (int j = 0; j < cantidadVertices; j++) {
                matrizAdyacencia[indice][j] = matrizAdyacencia[ultimoIndice][j];
            }
            // Mover la columna del último vértice a la posición del vértice eliminado
            for (int i = 0; i < cantidadVertices; i++) {
                matrizAdyacencia[i][indice] = matrizAdyacencia[i][ultimoIndice];
            }

            // Actualizar la etiqueta del vértice movido
            etiquetas[indice] = ultimoVertice;
        }
        // Limpiar la última posición (opcional, ya que cantidadVertices se decrementa)
        etiquetas[cantidadVertices - 1] = null;
        cantidadVertices--;
    }

    @Override
    public void agregarArista(E origen, E destino, int peso) {
        int i = obtenerIndice(origen);
        int j = obtenerIndice(destino);
        if (i != -1 && j != -1 && matrizAdyacencia[i][j] == SIN_ARISTA) {
            matrizAdyacencia[i][j] = peso;
        }
    }

    @Override
    public void eliminarArista(E origen, E destino) {
        int i = obtenerIndice(origen);
        int j = obtenerIndice(destino);
        if (i != -1 && j != -1) {
            matrizAdyacencia[i][j] = SIN_ARISTA;
        }
    }

    @Override
    public boolean existeArista(E origen, E destino) {
        int i = obtenerIndice(origen);
        int j = obtenerIndice(destino);
        return i != -1 && j != -1 && matrizAdyacencia[i][j] != SIN_ARISTA;
    }

    @Override
    public int pesoArista(E origen, E destino) {
        int i = obtenerIndice(origen);
        int j = obtenerIndice(destino);
        if (i != -1 && j != -1) {
            return matrizAdyacencia[i][j];
        }
        return SIN_ARISTA; // O lanzar una excepción si los vértices no existen
    }

    @Override
    public E[] vertices() {
        @SuppressWarnings("unchecked")
        E[] array = (E[]) new Object[cantidadVertices];
        for(int i = 0; i < cantidadVertices; i++) {
            array[i] = etiquetas[i];
        }
        return array;
    }

    // Métodos específicos del ejercicio 2
    public int mayorCostoAristasSalientes(E vertice) {
        int indice = obtenerIndice(vertice);
        if (indice == -1) {
            return -1; // Vértice no encontrado
        }
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
            return -1; // Devolvemos -1 para indicar que no hay aristas salientes
        }
        return mayorCosto;
    }

    public E[] predecesores(E vertice) {
        int indiceDestino = obtenerIndice(vertice);
        if (indiceDestino == -1) {
            return (E[]) new Object[0]; // Vértice no encontrado, devuelve un array vacío
        }

        // Usamos un array temporal para almacenar los predecesores
        // y luego lo copiamos a un array del tamaño exacto.
        @SuppressWarnings("unchecked")
        E[] tempPredecesores = (E[]) new Object[cantidadVertices];
        int count = 0;

        for (int i = 0; i < cantidadVertices; i++) {
            if (matrizAdyacencia[i][indiceDestino] != SIN_ARISTA) {
                tempPredecesores[count++] = etiquetas[i];
            }
        }

        // Crear un nuevo array con el tamaño exacto de los predecesores encontrados
        @SuppressWarnings("unchecked")
        E[] result = (E[]) new Object[count];
        System.arraycopy(tempPredecesores, 0, result, 0, count);
        return result;
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
            System.out.printf("% -6s", etiquetas[j]);
        }
        System.out.println();
        for (int i = 0; i < cantidadVertices; i++) {
            System.out.printf("% -6s", etiquetas[i]);
            for (int j = 0; j < cantidadVertices; j++) {
                int peso = matrizAdyacencia[i][j];
                System.out.printf("% -6s", peso == SIN_ARISTA ? "-" : peso);
            }
            System.out.println();
        }
        System.out.println();
    }
}