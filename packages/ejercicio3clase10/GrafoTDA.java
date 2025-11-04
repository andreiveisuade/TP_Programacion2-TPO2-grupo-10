package ejercicio3clase10;

public interface GrafoTDA<E> {
    void agregarVertice(E vertice);
    void eliminarVertice(E vertice);
    void agregarArista(E origen, E destino, int peso);
    void eliminarArista(E origen, E destino);
    boolean existeArista(E origen, E destino);
    int pesoArista(E origen, E destino);
    E[] vertices();
}
