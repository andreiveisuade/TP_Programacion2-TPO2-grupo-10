package ejercicio2clase10;

import java.util.List;
import java.util.Set;

public interface GrafoTDA<E> {
    void inicializar(int maxVertices);
    void agregarVertice(E vertice);
    void eliminarVertice(E vertice);
    void agregarArista(E origen, E destino, int peso);
    void eliminarArista(E origen, E destino);
    boolean existeArista(E origen, E destino);
    int pesoArista(E origen, E destino);
    List<E> vertices();
    
    // Métodos específicos del ejercicio 2
    int mayorCostoAristasSalientes(E vertice);
    Set<E> predecesores(E vertice);
}
