package ejercicio7clase8;

// ============================================
// CLASE NODO PARA ABB
// ============================================
/**
 * Representa un nodo individual en un Árbol Binario de Búsqueda (ABB).
 * Cada nodo contiene un valor, y referencias a sus hijos izquierdo y derecho.
 * @param <E> Tipo de dato del valor almacenado en el nodo, debe ser comparable.
 */
class NodeABB<E extends Comparable<E>> {
    private E value;
    private NodeABB<E> left;
    private NodeABB<E> right;

    /**
     * Constructor para crear un nuevo nodo con el valor especificado.
     * Los hijos izquierdo y derecho se inicializan como nulos.
     * @param value El valor a almacenar en este nodo.
     */
    public NodeABB(E value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    /**
     * Obtiene el valor almacenado en este nodo.
     * @return El valor del nodo.
     */
    public E getValue() { return value; }

    /**
     * Establece un nuevo valor para este nodo.
     * @param value El nuevo valor a establecer.
     */
    public void setValue(E value) { this.value = value; }

    /**
     * Obtiene el nodo hijo izquierdo.
     * @return El nodo hijo izquierdo.
     */
    public NodeABB<E> getLeft() { return left; }

    /**
     * Establece el nodo hijo izquierdo.
     * @param left El nodo a establecer como hijo izquierdo.
     */
    public void setLeft(NodeABB<E> left) { this.left = left; }

    /**
     * Obtiene el nodo hijo derecho.
     * @return El nodo hijo derecho.
     */
    public NodeABB<E> getRight() { return right; }

    /**
     * Establece el nodo hijo derecho.
     * @param right El nodo a establecer como hijo derecho.
     */
    public void setRight(NodeABB<E> right) { this.right = right; }
}
