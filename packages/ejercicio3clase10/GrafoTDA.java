package ejercicio3clase10;

import java.util.List;
import java.util.Set;

/**
 * Interfaz que define el Tipo de Dato Abstracto (TDA) para un Grafo.
 * Permite la gestión de vértices y aristas, así como operaciones específicas
 * como la identificación de vértices aislados y vértices puente.
 * @param <E> El tipo de dato que representará los vértices del grafo.
 */
public interface GrafoTDA<E> {
    /**
     * Inicializa el grafo, dejándolo vacío y listo para ser usado.
     */
    void inicializar();

    /**
     * Agrega un vértice al grafo.
     * Si el vértice ya existe, la operación no tiene efecto.
     * @param vertice El valor del vértice a agregar.
     * @throws IllegalArgumentException si el vértice es nulo.
     */
    void agregarVertice(E vertice);

    /**
     * Elimina un vértice del grafo, junto con todas las aristas que lo tienen como origen o destino.
     * @param vertice El valor del vértice a eliminar.
     * @throws IllegalArgumentException si el vértice es nulo, el grafo está vacío o el vértice no existe.
     */
    void eliminarVertice(E vertice);

    /**
     * Agrega una arista entre dos vértices con un peso específico.
     * Si la arista ya existe, actualiza su peso.
     * @param origen El vértice de origen de la arista.
     * @param destino El vértice de destino de la arista.
     * @param peso El peso de la arista (debe ser positivo).
     * @throws IllegalArgumentException si el peso no es positivo o si alguno de los vértices no existe.
     */
    void agregarArista(E origen, E destino, int peso);

    /**
     * Elimina una arista específica entre dos vértices.
     * Si la arista no existe, la operación no tiene efecto.
     * @param origen El vértice de origen de la arista.
     * @param destino El vértice de destino de la arista.
     */
    void eliminarArista(E origen, E destino);

    /**
     * Verifica si existe una arista entre dos vértices.
     * @param origen El vértice de origen.
     * @param destino El vértice de destino.
     * @return true si la arista existe, false en caso contrario.
     */
    boolean existeArista(E origen, E destino);

    /**
     * Obtiene el peso de una arista entre dos vértices.
     * @param origen El vértice de origen.
     * @param destino El vértice de destino.
     * @return El peso de la arista.
     * @throws IllegalArgumentException si el vértice origen no existe o la arista no existe.
     */
    int pesoArista(E origen, E destino);

    /**
     * Obtiene una lista con todos los vértices presentes en el grafo.
     * @return Una {@link List} de los valores de los vértices.
     */
    List<E> vertices();
    
    // Métodos adicionales del ejercicio 3

    /**
     * Identifica y retorna un conjunto de vértices que están aislados en el grafo.
     * Un vértice es aislado si no tiene aristas salientes ni entrantes.
     * @return Un {@link Set} de vértices aislados.
     */
    Set<E> verticesAislados();

    /**
     * Identifica y retorna un conjunto de vértices que actúan como "puente" entre un origen y un destino.
     * Un vértice 'p' es puente si existe una arista (origen -> p) y una arista (p -> destino).
     * @param origen El vértice de origen.
     * @param destino El vértice de destino.
     * @return Un {@link Set} de vértices que son puente entre origen y destino.
     * @throws IllegalArgumentException si el vértice origen o destino no existen.
     */
    Set<E> verticesPuente(E origen, E destino);
}
