package ejercicio3clase10;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// ============================================
// IMPLEMENTACIÓN: GRAFO CON LISTAS DE ADYACENCIA
// ============================================
public class GrafoListas<E> implements GrafoTDA<E> {
    private NodoVertice<E> cabeza;
    private int cantidadVertices;

    @Override
    public void inicializar() {
        this.cabeza = null;
        this.cantidadVertices = 0;
    }

    // ============================================
    // OPERACIONES BÁSICAS DE VÉRTICES
    // ============================================

    @Override
    public void agregarVertice(E vertice) {
        // Complejidad: O(n) donde n = cantidad de vértices
        // Necesitamos verificar que no exista
        
        if (vertice == null) {
            throw new IllegalArgumentException("El vértice no puede ser null");
        }

        // Verificar si ya existe - O(n)
        if (buscarVertice(vertice) != null) {
            return; // Ya existe, no hacer nada
        }

        // Agregar al inicio - O(1)
        NodoVertice<E> nuevoNodo = new NodoVertice<>(vertice);
        nuevoNodo.setSiguiente(cabeza);
        cabeza = nuevoNodo;
        cantidadVertices++;
    }

    @Override
    public void eliminarVertice(E vertice) {
        // Complejidad: O(n + m) donde:
        // n = cantidad de vértices
        // m = cantidad total de aristas en el grafo
        
        if (cabeza == null) {
            return;
        }

        // Caso especial: eliminar la cabeza
        if (cabeza.getValor().equals(vertice)) {
            cabeza = cabeza.getSiguiente();
            cantidadVertices--;
            // Eliminar aristas que apuntan a este vértice - O(n×k)
            eliminarAristasHacia(vertice);
            return;
        }

        // Buscar el nodo anterior al que queremos eliminar - O(n)
        NodoVertice<E> actual = cabeza;
        while (actual.getSiguiente() != null) {
            if (actual.getSiguiente().getValor().equals(vertice)) {
                actual.setSiguiente(actual.getSiguiente().getSiguiente());
                cantidadVertices--;
                // Eliminar aristas que apuntan a este vértice
                eliminarAristasHacia(vertice);
                return;
            }
            actual = actual.getSiguiente();
        }
    }

    /**
     * Elimina todas las aristas que tienen como destino el vértice dado.
     * Complejidad: O(n×k) donde n = vértices, k = promedio de aristas por vértice
     */
    private void eliminarAristasHacia(E vertice) {
        NodoVertice<E> v = cabeza;
        while (v != null) {
            // Para cada vértice, eliminar aristas hacia el vértice dado
            NodoArista<E> aristaActual = v.getAdyacentes();
            NodoArista<E> aristaAnterior = null;

            while (aristaActual != null) {
                if (aristaActual.getDestino().equals(vertice)) {
                    if (aristaAnterior == null) {
                        v.setAdyacentes(aristaActual.getSiguiente());
                    } else {
                        aristaAnterior.setSiguiente(aristaActual.getSiguiente());
                    }
                    aristaActual = aristaActual.getSiguiente();
                } else {
                    aristaAnterior = aristaActual;
                    aristaActual = aristaActual.getSiguiente();
                }
            }
            v = v.getSiguiente();
        }
    }

    // ============================================
    // OPERACIONES BÁSICAS DE ARISTAS
    // ============================================

    @Override
    public void agregarArista(E origen, E destino, int peso) {
        // Complejidad: O(n + k) donde:
        // n = cantidad de vértices (buscar nodo origen)
        // k = cantidad de aristas del vértice origen (verificar duplicado)
        
        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser positivo");
        }

        NodoVertice<E> nodoOrigen = buscarVertice(origen); // O(n)
        NodoVertice<E> nodoDestino = buscarVertice(destino); // O(n)

        if (nodoOrigen == null || nodoDestino == null) {
            throw new IllegalArgumentException("Ambos vértices deben existir");
        }

        // Verificar si la arista ya existe - O(k)
        NodoArista<E> arista = nodoOrigen.getAdyacentes();
        while (arista != null) {
            if (arista.getDestino().equals(destino)) {
                // La arista ya existe, actualizar peso
                arista.setPeso(peso);
                return;
            }
            arista = arista.getSiguiente();
        }

        // Agregar nueva arista al inicio de la lista - O(1)
        NodoArista<E> nuevaArista = new NodoArista<>(destino, peso);
        nuevaArista.setSiguiente(nodoOrigen.getAdyacentes());
        nodoOrigen.setAdyacentes(nuevaArista);
    }

    @Override
    public void eliminarArista(E origen, E destino) {
        // Complejidad: O(n + k) donde:
        // n = buscar vértice origen
        // k = recorrer aristas del origen
        
        NodoVertice<E> nodoOrigen = buscarVertice(origen); // O(n)
        
        if (nodoOrigen == null) {
            return;
        }

        NodoArista<E> aristaActual = nodoOrigen.getAdyacentes();
        NodoArista<E> aristaAnterior = null;

        // Buscar y eliminar la arista - O(k)
        while (aristaActual != null) {
            if (aristaActual.getDestino().equals(destino)) {
                if (aristaAnterior == null) {
                    nodoOrigen.setAdyacentes(aristaActual.getSiguiente());
                } else {
                    aristaAnterior.setSiguiente(aristaActual.getSiguiente());
                }
                return;
            }
            aristaAnterior = aristaActual;
            aristaActual = aristaActual.getSiguiente();
        }
    }

    @Override
    public boolean existeArista(E origen, E destino) {
        // Complejidad: O(n + k)
        
        NodoVertice<E> nodoOrigen = buscarVertice(origen); // O(n)
        
        if (nodoOrigen == null) {
            return false;
        }

        // Buscar en la lista de aristas - O(k)
        NodoArista<E> arista = nodoOrigen.getAdyacentes();
        while (arista != null) {
            if (arista.getDestino().equals(destino)) {
                return true;
            }
            arista = arista.getSiguiente();
        }
        
        return false;
    }

    @Override
    public int pesoArista(E origen, E destino) {
        // Complejidad: O(n + k)
        
        NodoVertice<E> nodoOrigen = buscarVertice(origen); // O(n)
        
        if (nodoOrigen == null) {
            throw new IllegalArgumentException("El vértice origen no existe");
        }

        // Buscar la arista - O(k)
        NodoArista<E> arista = nodoOrigen.getAdyacentes();
        while (arista != null) {
            if (arista.getDestino().equals(destino)) {
                return arista.getPeso();
            }
            arista = arista.getSiguiente();
        }

        throw new IllegalArgumentException("La arista no existe");
    }

    @Override
    public List<E> vertices() {
        // Complejidad: O(n)
        
        List<E> listaVertices = new ArrayList<>();
        NodoVertice<E> actual = cabeza;
        
        while (actual != null) {
            listaVertices.add(actual.getValor());
            actual = actual.getSiguiente();
        }
        
        return listaVertices;
    }

    // ============================================
    // MÉTODOS ADICIONALES DEL EJERCICIO 3
    // ============================================

    /**
     * Ejercicio 3.1: Obtener el conjunto de vértices aislados en G.
     * 
     * Un vértice v es AISLADO si:
     * - No tiene aristas salientes (su lista de adyacencia está vacía)
     * - No tiene aristas entrantes (ningún otro vértice apunta a él)
     * 
     * Complejidad: O(n×k) donde:
     * n = cantidad de vértices
     * k = promedio de aristas por vértice
     * 
     * Desglose:
     * 1. Primera pasada: marcar vértices con aristas salientes - O(n)
     * 2. Segunda pasada: marcar vértices con aristas entrantes - O(n×k)
     * 3. Tercera pasada: recolectar aislados - O(n)
     * Total: O(n) + O(n×k) + O(n) = O(n×k)
     */
    @Override
    public Set<E> verticesAislados() {
        Set<E> aislados = new HashSet<>();
        Set<E> tienenSalientes = new HashSet<>();
        Set<E> tienenEntrantes = new HashSet<>();

        NodoVertice<E> v = cabeza;

        // Primera pasada: identificar vértices con aristas SALIENTES - O(n)
        while (v != null) {
            if (v.getAdyacentes() != null) {
                tienenSalientes.add(v.getValor());
            }
            v = v.getSiguiente();
        }

        // Segunda pasada: identificar vértices con aristas ENTRANTES - O(n×k)
        v = cabeza;
        while (v != null) {
            NodoArista<E> arista = v.getAdyacentes();
            while (arista != null) {
                tienenEntrantes.add(arista.getDestino());
                arista = arista.getSiguiente();
            }
            v = v.getSiguiente();
        }

        // Tercera pasada: recolectar vértices que NO tienen ni salientes ni entrantes - O(n)
        v = cabeza;
        while (v != null) {
            E valor = v.getValor();
            if (!tienenSalientes.contains(valor) && !tienenEntrantes.contains(valor)) {
                aislados.add(valor);
            }
            v = v.getSiguiente();
        }

        return aislados;
    }

    /**
     * Ejercicio 3.2: Obtener vértices puente entre v1 y v2.
     * 
     * Un vértice p es PUENTE entre origen (o) y destino (d) si:
     * - Existe una arista (o → p)
     * - Existe una arista (p → d)
     * 
     * Complejidad: O(k₁×k₂) donde:
     * k₁ = cantidad de aristas salientes de origen
     * k₂ = cantidad de aristas salientes de cada candidato
     * 
     * En el peor caso: O(n²) si el grafo es completo
     * En la práctica: O(k×k) que suele ser mucho menor
     * 
     * Desglose:
     * 1. Buscar vértice origen - O(n)
     * 2. Para cada vecino p de origen - O(k₁)
     *    - Buscar vértice p - O(n)
     *    - Verificar si p→destino existe - O(k₂)
     * Total: O(n) + O(k₁ × (n + k₂))
     * 
     * OPTIMIZACIÓN: Usando HashMap para vértices sería O(k₁×k₂)
     */
    @Override
    public Set<E> verticesPuente(E origen, E destino) {
        Set<E> puentes = new HashSet<>();

        NodoVertice<E> nodoOrigen = buscarVertice(origen); // O(n)
        
        if (nodoOrigen == null) {
            throw new IllegalArgumentException("El vértice origen no existe");
        }

        // Verificar que el destino exista
        if (buscarVertice(destino) == null) { // O(n)
            throw new IllegalArgumentException("El vértice destino no existe");
        }

        // Recorrer todos los vecinos del origen - O(k₁)
        NodoArista<E> aristaDesdeOrigen = nodoOrigen.getAdyacentes();
        
        while (aristaDesdeOrigen != null) {
            E candidatoPuente = aristaDesdeOrigen.getDestino();
            
            // Verificar si existe arista desde el candidato al destino - O(n + k₂)
            if (existeArista(candidatoPuente, destino)) {
                puentes.add(candidatoPuente);
            }
            
            aristaDesdeOrigen = aristaDesdeOrigen.getSiguiente();
        }

        return puentes;
    }

    // ============================================
    // MÉTODOS AUXILIARES
    // ============================================

    /**
     * Busca un nodo vértice por su valor.
     * Complejidad: O(n) donde n = cantidad de vértices
     */
    private NodoVertice<E> buscarVertice(E vertice) {
        NodoVertice<E> actual = cabeza;
        while (actual != null) {
            if (actual.getValor().equals(vertice)) {
                return actual;
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    /**
     * Cuenta el grado de salida de un vértice (cantidad de aristas salientes).
     * Complejidad: O(k) donde k = aristas del vértice
     */
    private int gradoSalida(NodoVertice<E> nodo) {
        int grado = 0;
        NodoArista<E> arista = nodo.getAdyacentes();
        while (arista != null) {
            grado++;
            arista = arista.getSiguiente();
        }
        return grado;
    }

    // ============================================
    // MÉTODOS DE VISUALIZACIÓN
    // ============================================

    public void imprimirGrafo() {
        System.out.println("\n------------------------------------------------");
        System.out.println("|      GRAFO - LISTAS DE ADYACENCIA         |");
        System.out.println("------------------------------------------------\n");

        if (cabeza == null) {
            System.out.println("El grafo está vacío.\n");
            return;
        }

        NodoVertice<E> v = cabeza;
        while (v != null) {
            System.out.printf("%s → ", v.getValor());
            
            NodoArista<E> arista = v.getAdyacentes();
            if (arista == null) {
                System.out.println("(sin aristas)");
            } else {
                List<String> aristas = new ArrayList<>();
                while (arista != null) {
                    aristas.add(String.format("%s(%d)", arista.getDestino(), arista.getPeso()));
                    arista = arista.getSiguiente();
                }
                System.out.println(String.join(", ", aristas));
            }
            
            v = v.getSiguiente();
        }
        System.out.println();
    }

    public void imprimirEstadisticas() {
        System.out.println("------------------------------------------------");
        System.out.println("|       ESTADÍSTICAS DEL GRAFO              |");
        System.out.println("------------------------------------------------");
        System.out.println("Cantidad de vértices: " + cantidadVertices);
        
        int totalAristas = 0;
        int gradoMax = 0;
        int gradoMin = Integer.MAX_VALUE;
        
        NodoVertice<E> v = cabeza;
        while (v != null) {
            int grado = gradoSalida(v);
            totalAristas += grado;
            gradoMax = Math.max(gradoMax, grado);
            if (cantidadVertices > 0) {
                gradoMin = Math.min(gradoMin, grado);
            }
            v = v.getSiguiente();
        }
        
        System.out.println("Cantidad de aristas: " + totalAristas);
        if (cantidadVertices > 0) {
            System.out.println("Grado máximo de salida: " + gradoMax);
            System.out.println("Grado mínimo de salida: " + gradoMin);
                        System.out.printf("Grado promedio: %.2f\n", (double) totalAristas / cantidadVertices);        }
        System.out.println();
    }
}
