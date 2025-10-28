package ejercicio3clase10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// ============================================ 
// IMPLEMENTACIÓN: GRAFO CON LISTAS DE ADYACENCIA
// ============================================ 
public class GrafoListas<E> implements GrafoTDA<E> {
    private Map<E, NodoVertice<E>> verticesMap;

    @Override
    public void inicializar() {
        this.verticesMap = new HashMap<>();
    }

    // ============================================ 
    // OPERACIONES BÁSICAS DE VÉRTICES
    // ============================================ 

    @Override
    public void agregarVertice(E vertice) {
        // Complejidad: O(1) en promedio
        
        if (vertice == null) {
            throw new IllegalArgumentException("El vértice no puede ser null");
        }

        // Verificar si ya existe - O(1)
        if (verticesMap.containsKey(vertice)) {
            return; // Ya existe, no hacer nada
        }

        // Agregar al mapa - O(1)
        NodoVertice<E> nuevoNodo = new NodoVertice<>(vertice);
        verticesMap.put(vertice, nuevoNodo);
    }

    @Override
    public void eliminarVertice(E vertice) {
        // Complejidad: O(n + m) donde:
        // n = cantidad de vértices
        // m = cantidad total de aristas en el grafo
        
        if (vertice == null) {
            throw new IllegalArgumentException("El vértice no puede ser null");
        }

        if (verticesMap.isEmpty()) {
            throw new IllegalArgumentException("El grafo está vacío, no se puede eliminar el vértice.");
        }

        // Verificar si el vértice existe antes de intentar eliminarlo - O(1)
        if (!verticesMap.containsKey(vertice)) {
            throw new IllegalArgumentException("El vértice a eliminar no existe en el grafo.");
        }

        // Eliminar aristas que apuntan a este vértice - O(n×k)
        eliminarAristasHacia(vertice);

        // Eliminar el vértice del mapa - O(1)
        verticesMap.remove(vertice);
    }

    /**
     * Elimina todas las aristas que tienen como destino el vértice dado.
     * Complejidad: O(n×k) donde n = vértices, k = promedio de aristas por vértice
     */
    private void eliminarAristasHacia(E vertice) {
        for (NodoVertice<E> v : verticesMap.values()) {
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
        }
    }

    /**
     * Elimina todas las aristas entrantes a un vértice específico.
     * Útil para "aislar" un vértice sin eliminarlo.
     * Complejidad: O(n×k) donde n = vértices, k = promedio de aristas por vértice
     */
    public void eliminarAristasEntrantes(E vertice) {
        eliminarAristasHacia(vertice);
    }

    // ============================================ 
    // OPERACIONES BÁSICAS DE ARISTAS
    // ============================================ 

    @Override
    public void agregarArista(E origen, E destino, int peso) {
        // Complejidad: O(k) donde:
        // k = cantidad de aristas del vértice origen (verificar duplicado)
        
        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser positivo");
        }

        NodoVertice<E> nodoOrigen = buscarVertice(origen); // O(1)
        NodoVertice<E> nodoDestino = buscarVertice(destino); // O(1)

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
        // Complejidad: O(k) donde:
        // k = recorrer aristas del origen
        
        NodoVertice<E> nodoOrigen = buscarVertice(origen); // O(1)
        
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
        // Complejidad: O(k)
        
        NodoVertice<E> nodoOrigen = buscarVertice(origen); // O(1)
        
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
        // Complejidad: O(k)
        
        NodoVertice<E> nodoOrigen = buscarVertice(origen); // O(1)
        
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
        
        return new ArrayList<>(verticesMap.keySet());
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

        // Primera pasada: identificar vértices con aristas SALIENTES - O(n)
        for (NodoVertice<E> v : verticesMap.values()) {
            if (v.getAdyacentes() != null) {
                tienenSalientes.add(v.getValor());
            }
        }

        // Segunda pasada: identificar vértices con aristas ENTRANTES - O(n×k)
        for (NodoVertice<E> v : verticesMap.values()) {
            NodoArista<E> arista = v.getAdyacentes();
            while (arista != null) {
                tienenEntrantes.add(arista.getDestino());
                arista = arista.getSiguiente();
            }
        }

        // Tercera pasada: recolectar vértices que NO tienen ni salientes ni entrantes - O(n)
        for (NodoVertice<E> v : verticesMap.values()) {
            E valor = v.getValor();
            if (!tienenSalientes.contains(valor) && !tienenEntrantes.contains(valor)) {
                aislados.add(valor);
            }
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
     * Desglose:
     * 1. Buscar vértice origen - O(1)
     * 2. Para cada vecino p de origen - O(k₁)
     *    - Buscar vértice p - O(1)
     *    - Verificar si p→destino existe - O(k₂)
     * Total: O(1) + O(k₁ × (1 + k₂)) = O(k₁×k₂)
     */
    @Override
    public Set<E> verticesPuente(E origen, E destino) {
        Set<E> puentes = new HashSet<>();

        NodoVertice<E> nodoOrigen = buscarVertice(origen); // O(1)
        
        if (nodoOrigen == null) {
            throw new IllegalArgumentException("El vértice origen no existe");
        }

        NodoVertice<E> nodoDestino = buscarVertice(destino); // O(1)
        if (nodoDestino == null) {
            throw new IllegalArgumentException("El vértice destino no existe");
        }

        // Recorrer todos los vecinos del origen - O(k₁)
        NodoArista<E> aristaDesdeOrigen = nodoOrigen.getAdyacentes();
        
        while (aristaDesdeOrigen != null) {
            E candidatoPuente = aristaDesdeOrigen.getDestino();
            
            // Buscar el nodo candidato UNA SOLA VEZ - O(1)
            NodoVertice<E> nodoCandidato = buscarVertice(candidatoPuente);
            
            if (nodoCandidato != null) {
                // Verificar si candidato → destino en O(k₂)
                NodoArista<E> aristaCandidato = nodoCandidato.getAdyacentes();
                while (aristaCandidato != null) {
                    if (aristaCandidato.getDestino().equals(destino)) {
                        puentes.add(candidatoPuente);
                        break; // Salir del while interno
                    }
                    aristaCandidato = aristaCandidato.getSiguiente();
                }
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
     * Complejidad: O(1) en promedio
     */
    private NodoVertice<E> buscarVertice(E vertice) {
        return verticesMap.get(vertice);
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

        if (verticesMap.isEmpty()) {
            System.out.println("El grafo está vacío.\n");
            return;
        }

        for (NodoVertice<E> v : verticesMap.values()) {
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
        }
        System.out.println();
    }

    public void imprimirEstadisticas() {
        System.out.println("------------------------------------------------");
        System.out.println("|       ESTADÍSTICAS DEL GRAFO              |");
        System.out.println("------------------------------------------------");
        System.out.println("Cantidad de vértices: " + verticesMap.size());
        
        int totalAristas = 0;
        int gradoMax = 0;
        int gradoMin = (verticesMap.isEmpty()) ? 0 : Integer.MAX_VALUE;
        
        for (NodoVertice<E> v : verticesMap.values()) {
            int grado = gradoSalida(v);
            totalAristas += grado;
            gradoMax = Math.max(gradoMax, grado);
            if (!verticesMap.isEmpty()) {
                gradoMin = Math.min(gradoMin, grado);
            }
        }
        
        System.out.println("Cantidad de aristas: " + totalAristas);
        if (!verticesMap.isEmpty()) {
            System.out.println("Grado máximo de salida: " + gradoMax);
            System.out.println("Grado mínimo de salida: " + gradoMin);
            System.out.printf("Grado promedio: %.2f\n", (double) totalAristas / verticesMap.size());
        }
        System.out.println();
    }
}