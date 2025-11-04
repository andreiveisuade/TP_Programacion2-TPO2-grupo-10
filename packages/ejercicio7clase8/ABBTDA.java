package ejercicio7clase8;

// ============================================
// INTERFAZ TDA ABB
// ============================================
/**
 * Interfaz para un Tipo de Dato Abstracto (TDA) de Árbol Binario de Búsqueda (ABB).
 * Define las operaciones básicas que debe soportar un ABB.
 * @param <E> Tipo de elementos que almacenará el ABB, deben ser comparables.
 */
public interface ABBTDA<E extends Comparable<E>> {
    /**
     * Inserta un nuevo elemento en el ABB.
     * @param element El elemento a insertar.
     * @throws NullPointerException si el elemento a insertar es nulo.
     */
    void insert(E element);

    /**
     * Elimina un elemento existente del ABB.
     * @param element El elemento a eliminar.
     * @throws NullPointerException si el elemento a eliminar es nulo.
     */
    void remove(E element);

    /**
     * Verifica si un elemento está presente en el ABB.
     * @param element El elemento a buscar.
     * @return true si el elemento está en el ABB, false en caso contrario.
     * @throws NullPointerException si el elemento a buscar es nulo.
     */
    boolean contains(E element);

    /**
     * Inicializa el ABB, dejándolo vacío.
     */
    void initialize();

    /**
     * Realiza un recorrido in-order del ABB y devuelve una cadena con los elementos.
     * @return Una cadena que representa los elementos del ABB en orden ascendente.
     */
    String inOrder();

    /**
     * Obtiene un elemento del ABB.
     * @param element El elemento a buscar.
     * @return El elemento encontrado, o null si no existe.
     */
    E get(E element);

    /**
     * Realiza una acción para cada elemento del árbol en orden in-order.
     * @param action La acción a realizar para cada elemento.
     */
    void forEachInOrder(java.util.function.Consumer<E> action);
}
