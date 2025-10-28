package ejercicio6clase9;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

// ============================================ 
// IMPLEMENTACIÓN AVL
// ============================================ 
public class AVL<E extends Comparable<E>> implements AVLTDA<E> {
    private NodeAVL<E> root;

    @Override
    public void initialize() {
        root = null;
    }

    @Override
    public void insert(E element) {
        if (element == null) {
            throw new NullPointerException("No se permite insertar elementos nulos en el AVL.");
        }
        root = insertRecursive(root, element);
    }

    private NodeAVL<E> insertRecursive(NodeAVL<E> node, E element) {
        // 1. INSERCIÓN NORMAL DE ABB
        if (node == null) {
            return new NodeAVL<>(element);
        }

        int comparison = element.compareTo(node.getValue());
        
        if (comparison < 0) {
            node.setLeft(insertRecursive(node.getLeft(), element));
        } else if (comparison > 0) {
            node.setRight(insertRecursive(node.getRight(), element));
        } else {
            // El elemento ya existe, no hacer nada
            return node;
        }

        // 2. ACTUALIZAR LA ALTURA DEL NODO ANCESTRO
        updateHeight(node);

        // 3. CALCULAR EL FACTOR DE BALANCE
        int balance = getBalance(node);

        // 4. SI EL NODO ESTÁ DESBALANCEADO, HAY 4 CASOS:

        // CASO 1: Rotación Simple a la Derecha (Izquierda-Izquierda)
        if (balance > 1 && element.compareTo(node.getLeft().getValue()) < 0) {
            return rotateRight(node);
        }

        // CASO 2: Rotación Simple a la Izquierda (Derecha-Derecha)
        if (balance < -1 && element.compareTo(node.getRight().getValue()) > 0) {
            return rotateLeft(node);
        }

        // CASO 3: Rotación Doble Izquierda-Derecha
        if (balance > 1 && element.compareTo(node.getLeft().getValue()) > 0) {
            node.setLeft(rotateLeft(node.getLeft()));
            return rotateRight(node);
        }

        // CASO 4: Rotación Doble Derecha-Izquierda
        if (balance < -1 && element.compareTo(node.getRight().getValue()) < 0) {
            node.setRight(rotateRight(node.getRight()));
            return rotateLeft(node);
        }

        return node;
    }

    @Override
    public void remove(E element) {
        if (element == null) {
            throw new NullPointerException("No se permite eliminar elementos nulos del AVL.");
        }
        root = removeRecursive(root, element);
    }

    private NodeAVL<E> removeRecursive(NodeAVL<E> node, E element) {
        if (node == null) {
            return null;
        }

        int comparison = element.compareTo(node.getValue());

        if (comparison < 0) {
            node.setLeft(removeRecursive(node.getLeft(), element));
        } else if (comparison > 0) {
            node.setRight(removeRecursive(node.getRight(), element));
        } else {
            // NODO ENCONTRADO
            // Caso 1: Nodo sin hijos o con un solo hijo
            if (node.getLeft() == null || node.getRight() == null) {
                NodeAVL<E> temp = (node.getLeft() != null) ? node.getLeft() : node.getRight();

                if (temp == null) { // Sin hijos
                    node = null;
                } else { // Un hijo
                    node = temp;
                }
            } else {
                // Caso 2: Nodo con dos hijos
                NodeAVL<E> temp = findMin(node.getRight());
                node.setValue(temp.getValue());
                node.setRight(removeRecursive(node.getRight(), temp.getValue()));
            }
        }

        if (node == null) {
            return null;
        }

        // Actualizar altura y balance
        updateHeight(node);
        int balance = getBalance(node);

        // Rebalanceo
        // Izquierda-Izquierda
        if (balance > 1 && getBalance(node.getLeft()) >= 0) {
            return rotateRight(node);
        }
        // Izquierda-Derecha
        if (balance > 1 && getBalance(node.getLeft()) < 0) {
            node.setLeft(rotateLeft(node.getLeft()));
            return rotateRight(node);
        }
        // Derecha-Derecha
        if (balance < -1 && getBalance(node.getRight()) <= 0) {
            return rotateLeft(node);
        }
        // Derecha-Izquierda
        if (balance < -1 && getBalance(node.getRight()) > 0) {
            node.setRight(rotateRight(node.getRight()));
            return rotateLeft(node);
        }

        return node;
    }

    private NodeAVL<E> findMin(NodeAVL<E> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    // ============================================ 
    // MÉTODOS DE ROTACIÓN
    // ============================================ 

    /**
     * Rotación Simple a la Derecha
     *       y                               x
     *      / \     Rotación Derecha        / \
     *     x   T3   – – – – – – – >        T1  y
     *    / \                                  / \
     *   T1  T2                               T2  T3
     */
    private NodeAVL<E> rotateRight(NodeAVL<E> y) {
        NodeAVL<E> x = y.getLeft();
        NodeAVL<E> T2 = x.getRight();

        // Realizar rotación
        x.setRight(y);
        y.setLeft(T2);

        // Actualizar alturas
        updateHeight(y);
        updateHeight(x);

        return x; // Nueva raíz
    }

    /**
     * Rotación Simple a la Izquierda
     *     x                                y
     *    / \      Rotación Izquierda      / \
     *   T1  y     – – – – – – – >        x   T3
     *      / \                           / \
     *     T2  T3                        T1  T2
     */
    private NodeAVL<E> rotateLeft(NodeAVL<E> x) {
        NodeAVL<E> y = x.getRight();
        NodeAVL<E> T2 = y.getLeft();

        // Realizar rotación
        y.setLeft(x);
        x.setRight(T2);

        // Actualizar alturas
        updateHeight(x);
        updateHeight(y);

        return y; // Nueva raíz
    }

    // ============================================ 
    // MÉTODOS AUXILIARES PARA AVL
    // ============================================ 

    private int height(NodeAVL<E> node) {
        return (node == null) ? 0 : node.getHeight();
    }

    private void updateHeight(NodeAVL<E> node) {
        if (node != null) {
            node.setHeight(1 + Math.max(height(node.getLeft()), height(node.getRight())));
        }
    }

    /**
     * Factor de Balance = Altura(Subárbol Derecho) - Altura(Subárbol Izquierdo)
     * Balance > 1  → Subárbol izquierdo más pesado
     * Balance < -1 → Subárbol derecho más pesado
     */
    private int getBalance(NodeAVL<E> node) {
        if (node == null) {
            return 0;
        }
        return height(node.getLeft()) - height(node.getRight());
    }

    // ============================================ 
    // MÉTODO CONTAINS
    // ============================================ 

    @Override
    public boolean contains(E element) {
        if (element == null) {
            throw new NullPointerException("No se permite buscar elementos nulos en el AVL.");
        }
        return containsRecursive(root, element);
    }

    @Override
    public E get(E element) {
        if (element == null) {
            throw new NullPointerException("No se permite obtener elementos nulos del AVL.");
        }
        return getRecursive(root, element);
    }

    private E getRecursive(NodeAVL<E> node, E element) {
        if (node == null) {
            return null;
        }

        int comparison = element.compareTo(node.getValue());

        if (comparison == 0) {
            return node.getValue();
        } else if (comparison < 0) {
            return getRecursive(node.getLeft(), element);
        } else {
            return getRecursive(node.getRight(), element);
        }
    }

    private boolean containsRecursive(NodeAVL<E> node, E element) {
        if (node == null) {
            return false;
        }

        int comparison = element.compareTo(node.getValue());
        
        if (comparison == 0) {
            return true;
        } else if (comparison < 0) {
            return containsRecursive(node.getLeft(), element);
        } else {
            return containsRecursive(node.getRight(), element);
        }
    }

    // ============================================ 
    // RECORRIDO IN-ORDER (ELEMENTOS ORDENADOS)
    // ============================================ 

    @Override
    public String inOrder() {
        StringBuilder sb = new StringBuilder();
        inOrderRecursive(root, sb);
        return sb.toString().trim();
    }

    @Override
    public ArrayList<E> inOrderTraversal() {
        ArrayList<E> elements = new ArrayList<>();
        collectInOrder(root, elements);
        return elements;
    }

    private void inOrderRecursive(NodeAVL<E> node, StringBuilder sb) {
        if (node != null) {
            inOrderRecursive(node.getLeft(), sb);
            sb.append(node.getValue()).append(" ");
            inOrderRecursive(node.getRight(), sb);
        }
    }

    // ============================================ 
    // RECORRIDO POR NIVELES (BFS)
    // ============================================ 

    @Override
    public void printByLevels() {
        if (root == null) {
            System.out.println("El árbol está vacío.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║      ÁRBOL AVL POR NIVELES                ║");
        System.out.println("╚════════════════════════════════════════════╝");

        Queue<NodeAVL<E>> queue = new LinkedList<>();
        queue.add(root);
        int level = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            System.out.print("Nivel " + level + ": ");

            for (int i = 0; i < levelSize; i++) {
                NodeAVL<E> current = queue.poll();
                
                // Mostrar valor con su factor de balance
                int balance = getBalance(current);
                System.out.print(current.getValue() + "(FB:" + balance + ") ");

                if (current.getLeft() != null) {
                    queue.add(current.getLeft());
                }
                if (current.getRight() != null) {
                    queue.add(current.getRight());
                }
            }
            System.out.println();
            level++;
        }
        System.out.println();
    }

    // ============================================ 
    // OBTENER ARREGLO ORDENADO
    // ============================================ 

    @Override
    public int[] getOrderedArray() {
        ArrayList<E> elements = new ArrayList<>();
        collectInOrder(root, elements);
        
        int[] array = new int[elements.size()];
        for (int i = 0; i < elements.size(); i++) {
            array[i] = (Integer) elements.get(i);
        }
        
        return array;
    }

    private void collectInOrder(NodeAVL<E> node, ArrayList<E> elements) {
        if (node != null) {
            collectInOrder(node.getLeft(), elements);
            elements.add(node.getValue());
            collectInOrder(node.getRight(), elements);
        }
    }

    @Override
    public int getHeight() {
        return height(root);
    }

    // ============================================ 
    // VISUALIZACIÓN GRÁFICA DEL ÁRBOL
    // ============================================ 

    public void printTree() {
        if (root == null) {
            System.out.println("El árbol está vacío.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║      VISUALIZACIÓN DEL ÁRBOL AVL          ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        printTreeRecursive(root, "", true);
        System.out.println();
    }

    private void printTreeRecursive(NodeAVL<E> node, String prefix, boolean isTail) {
        if (node == null) {
            return;
        }

        System.out.println(prefix + (isTail ? "└── " : "├── ") + 
                          node.getValue() + " (h:" + node.getHeight() + 
                          ", FB:" + getBalance(node) + ")");

        if (node.getLeft() != null || node.getRight() != null) {
            if (node.getLeft() != null) {
                printTreeRecursive(node.getLeft(), prefix + (isTail ? "    " : "│   "), 
                                 node.getRight() == null);
            }
            if (node.getRight() != null) {
                printTreeRecursive(node.getRight(), prefix + (isTail ? "    " : "│   "), true);
            }
        }
    }
}
