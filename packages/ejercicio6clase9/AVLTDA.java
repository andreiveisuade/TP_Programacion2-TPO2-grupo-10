package ejercicio6clase9;



/**
 * Interfaz que define el Tipo de Dato Abstracto (TDA) para un Árbol AVL (Adelson-Velskii y Landis).
 * Un AVL es un Árbol Binario de Búsqueda auto-balanceable que garantiza que las operaciones
 * de búsqueda, inserción y eliminación se realicen en tiempo logarítmico (O(log n)).
 * @param <E> El tipo de dato de los elementos que se almacenarán en el árbol, debe ser comparable.
 */
public interface AVLTDA<E extends Comparable<E>> {
    /**
     * Inserta un elemento en el árbol AVL, manteniendo las propiedades de balanceo.
     * @param element El elemento a insertar.
     */
    void insert(E element);

    /**
     * Elimina un elemento del árbol AVL, manteniendo las propiedades de balanceo.
     * @param element El elemento a eliminar.
     */
    void remove(E element);

    /**
     * Verifica si un elemento está presente en el árbol AVL.
     * @param element El elemento a buscar.
     * @return true si el elemento está en el árbol, false en caso contrario.
     */
    boolean contains(E element);

    /**
     * Obtiene un elemento del árbol AVL que sea igual al elemento proporcionado.
     * @param element El elemento a buscar.
     * @return El elemento encontrado en el árbol, o null si no existe.
     */
    E get(E element);

    /**
     * Inicializa el árbol AVL, dejándolo vacío.
     */
    void initialize();

    /**
     * Realiza un recorrido in-order del árbol y retorna una representación en cadena de los elementos.
     * @return Una cadena con los elementos del árbol en orden ascendente.
     */
    String inOrder();

    /**
     * Realiza un recorrido in-order del árbol y retorna los elementos en un ArrayList.
     * @return Un ArrayList con los elementos del árbol en orden ascendente.
     */
    ImplementacionArrayList<E> inOrderTraversal();

    /**
     * Imprime el árbol por niveles (recorrido BFS), mostrando la estructura del árbol.
     */
    void printByLevels();

    /**
     * Obtiene un arreglo ordenado de todos los elementos presentes en el árbol.
     * @return Un arreglo de elementos en orden ascendente.
     */
    int[] getOrderedArray();

    /**
     * Obtiene la altura del árbol AVL.
     * @return La altura del árbol.
     */
    int getHeight();
}