package ejercicio3clase10;

import java.util.List;
import java.util.Set;

// ============================================
// INTERFAZ TDA GRAFO
// ============================================
public interface GrafoTDA<E> {
    void inicializar();
    void agregarVertice(E vertice);
    void eliminarVertice(E vertice);
    void agregarArista(E origen, E destino, int peso);
    void eliminarArista(E origen, E destino);
    boolean existeArista(E origen, E destino);
    int pesoArista(E origen, E destino);
    List<E> vertices();
    
    // Métodos adicionales del ejercicio 3
    Set<E> verticesAislados();
    Set<E> verticesPuente(E origen, E destino);
}
