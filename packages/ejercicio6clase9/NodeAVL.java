package ejercicio6clase9;

// ============================================
// CLASE NODO AVL
// ============================================
class NodeAVL<E extends Comparable<E>> {
    private E value;
    private NodeAVL<E> left;
    private NodeAVL<E> right;
    private int height;

    public NodeAVL(E value) {
        this.value = value;
        this.left = null;
        this.right = null;
        this.height = 1; // Un nodo hoja tiene altura 1
    }

    public E getValue() { return value; }
    public void setValue(E value) { this.value = value; }
    public NodeAVL<E> getLeft() { return left; }
    public void setLeft(NodeAVL<E> left) { this.left = left; }
    public NodeAVL<E> getRight() { return right; }
    public void setRight(NodeAVL<E> right) { this.right = right; }
    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }
}