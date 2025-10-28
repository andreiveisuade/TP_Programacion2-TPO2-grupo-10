package ejercicio6clase9;

import java.util.ArrayList;

// ============================================
// INTERFAZ TDA AVL
// ============================================
public interface AVLTDA<E extends Comparable<E>> {
    void insert(E element);
    void remove(E element);
    boolean contains(E element);
    E get(E element);
    void initialize();
    String inOrder();
    ArrayList<E> inOrderTraversal();
    void printByLevels();
    int[] getOrderedArray();
    int getHeight();
}