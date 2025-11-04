package ejercicio3clase10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GrafoListas<E> implements GrafoTDA<E> {
    private Map<E, NodoVertice<E>> verticesMap;

    public GrafoListas() {
        this.verticesMap = new HashMap<>();
    }

    @Override
    public void agregarVertice(E vertice) {
        if (!verticesMap.containsKey(vertice)) {
            NodoVertice<E> nuevoNodo = new NodoVertice<>(vertice);
            verticesMap.put(vertice, nuevoNodo);
        }
    }

    @Override
    public void eliminarVertice(E vertice) {
        verticesMap.remove(vertice);
        for (NodoVertice<E> v : verticesMap.values()) {
            eliminarArista(v.getValor(), vertice);
        }
    }

    @Override
    public void agregarArista(E origen, E destino, int peso) {
        if (!existeArista(origen, destino)) {
            NodoVertice<E> nodoOrigen = verticesMap.get(origen);
            NodoArista<E> nuevaArista = new NodoArista<>(destino, peso);
            nuevaArista.setSiguiente(nodoOrigen.getAdyacentes());
            nodoOrigen.setAdyacentes(nuevaArista);
        }
    }

    @Override
    public void eliminarArista(E origen, E destino) {
        NodoVertice<E> nodoOrigen = verticesMap.get(origen);
        if (nodoOrigen != null) {
            NodoArista<E> aristaActual = nodoOrigen.getAdyacentes();
            NodoArista<E> aristaAnterior = null;
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
    }

    @Override
    public boolean existeArista(E origen, E destino) {
        NodoVertice<E> nodoOrigen = verticesMap.get(origen);
        if (nodoOrigen == null) {
            return false;
        }
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
        NodoVertice<E> nodoOrigen = verticesMap.get(origen);
        NodoArista<E> arista = nodoOrigen.getAdyacentes();
        while (arista != null) {
            if (arista.getDestino().equals(destino)) {
                return arista.getPeso();
            }
            arista = arista.getSiguiente();
        }
        return 0; // La precondición es que la arista existe
    }

    @Override
    public E[] vertices() {
        @SuppressWarnings("unchecked")
        E[] array = (E[]) new Object[verticesMap.size()];
        return verticesMap.keySet().toArray(array);
    }

    // Métodos específicos del ejercicio 3
    public Set<E> verticesAislados() {
        Set<E> aislados = new HashSet<>(verticesMap.keySet());
        Set<E> tienenEntrantes = new HashSet<>();

        for (NodoVertice<E> v : verticesMap.values()) {
            if (v.getAdyacentes() != null) {
                aislados.remove(v.getValor()); // No es aislado si tiene salientes
                NodoArista<E> arista = v.getAdyacentes();
                while (arista != null) {
                    tienenEntrantes.add(arista.getDestino());
                    arista = arista.getSiguiente();
                }
            }
        }
        aislados.removeAll(tienenEntrantes);
        return aislados;
    }

    public Set<E> verticesPuente(E origen, E destino) {
        Set<E> puentes = new HashSet<>();
        NodoVertice<E> nodoOrigen = verticesMap.get(origen);
        if (nodoOrigen == null) {
            return puentes; // O lanzar excepción si se prefiere
        }

        NodoArista<E> aristaDesdeOrigen = nodoOrigen.getAdyacentes();
        while (aristaDesdeOrigen != null) {
            E candidatoPuente = aristaDesdeOrigen.getDestino();
            if (existeArista(candidatoPuente, destino)) {
                puentes.add(candidatoPuente);
            }
            aristaDesdeOrigen = aristaDesdeOrigen.getSiguiente();
        }
        return puentes;
    }
    
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
                List<String> aristasStr = new ArrayList<>();
                while (arista != null) {
                    aristasStr.add(String.format("%s(%d)", arista.getDestino(), arista.getPeso()));
                    arista = arista.getSiguiente();
                }
                System.out.println(String.join(", ", aristasStr));
            }
        }
        System.out.println();
    }
}
