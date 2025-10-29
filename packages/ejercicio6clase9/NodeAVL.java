package ejercicio6clase9;

// ============================================
// CLASE NODO AVL
// ============================================
/**
 * Representa un nodo individual dentro de un Árbol AVL.
 * Contiene el valor del nodo, referencias a sus hijos izquierdo y derecho, y su altura.
 * @param <E> El tipo de dato del valor almacenado en el nodo, debe ser comparable.
 */
class NodeAVL<E extends Comparable<E>> {
    private E value;
    private NodeAVL<E> left;
    private NodeAVL<E> right;
    private int height;

    /**
     * Constructor para crear un nuevo NodeAVL.
     * Inicializa el nodo con el valor dado y una altura de 1 (como nodo hoja).
     * @param value El valor a almacenar en el nodo.
     */
    public NodeAVL(E value) {
        this.value = value;
        this.left = null;
        this.right = null;
        this.height = 1; // Un nodo hoja tiene altura 1
    }

    /**
     * Obtiene el valor almacenado en este nodo.
     * @return El valor del nodo.
     */
    public E getValue() { return value; }

    /**
     * Establece un nuevo valor para este nodo.
     * @param value El nuevo valor del nodo.
     */
    public void setValue(E value) { this.value = value; }

    /**
     * Obtiene el hijo izquierdo de este nodo.
     * @return El {@link NodeAVL} hijo izquierdo, o null si no tiene.
     */
    public NodeAVL<E> getLeft() { return left; }

    /**
     * Establece el hijo izquierdo de este nodo.
     * @param left El {@link NodeAVL} que será el nuevo hijo izquierdo.
     */
    public void setLeft(NodeAVL<E> left) { this.left = left; }

    /**
     * Obtiene el hijo derecho de este nodo.
     * @return El {@link NodeAVL} hijo derecho, o null si no tiene.
     */
    public NodeAVL<E> getRight() { return right; }

    /**
     * Establece el hijo derecho de este nodo.
     * @param right El {@link NodeAVL} que será el nuevo hijo derecho.
     */
    public void setRight(NodeAVL<E> right) { this.right = right; }

    /**
     * Obtiene la altura de este nodo en el árbol.
     * La altura es la longitud del camino más largo desde este nodo hasta una hoja.
     * @return La altura del nodo.
     */
    public int getHeight() { return height; }

    /**
     * Establece la altura de este nodo.
     * @param height La nueva altura del nodo.
     */
    public void setHeight(int height) { this.height = height; }
}