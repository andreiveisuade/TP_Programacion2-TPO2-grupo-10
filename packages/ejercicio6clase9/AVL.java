package ejercicio6clase9;



/**
 * Implementación de un Árbol AVL (Adelson-Velskii y Landis).
 * Este árbol binario de búsqueda se auto-balancea para asegurar que las operaciones
 * de búsqueda, inserción y eliminación mantengan una complejidad logarítmica (O(log n)).
 * @param <E> El tipo de dato de los elementos que se almacenarán en el árbol, debe ser comparable.
 */
public class AVL<E extends Comparable<E>> implements AVLTDA<E> {
    private NodeAVL<E> root;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize() {
        root = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(E element) {
        if (element == null) {
            throw new NullPointerException("No se permite insertar elementos nulos en el AVL.");
        }
        root = insertRecursive(root, element);
    }

    /**
     * Método auxiliar recursivo para insertar un elemento en el árbol AVL.
     * Realiza la inserción como en un ABB normal y luego aplica las rotaciones necesarias
     * para mantener el balance del árbol.
     * @param node El nodo actual en la recursión.
     * @param element El elemento a insertar.
     * @return La raíz del subárbol modificado.
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(E element) {
        if (element == null) {
            throw new NullPointerException("No se permite eliminar elementos nulos del AVL.");
        }
        root = removeRecursive(root, element);
    }

    /**
     * Método auxiliar recursivo para eliminar un elemento del árbol AVL.
     * Realiza la eliminación como en un ABB normal y luego aplica las rotaciones necesarias
     * para mantener el balance del árbol.
     * @param node El nodo actual en la recursión.
     * @param element El elemento a eliminar.
     * @return La raíz del subárbol modificado.
     */
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

    /**
     * Encuentra el nodo con el valor mínimo en un subárbol dado.
     * @param node La raíz del subárbol donde buscar el mínimo.
     * @return El nodo con el valor mínimo.
     */
    private NodeAVL<E> findMin(NodeAVL<E> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    /**
     * Realiza una rotación simple a la derecha para balancear el árbol.
     * Se aplica cuando hay un desbalance Izquierda-Izquierda.
     *       y                               x
     *      / \     Rotación Derecha        / \
     *     x   T3   – – – – – – – >        T1  y
     *    / \                                  / \
     *   T1  T2                               T2  T3
     * @param y El nodo desbalanceado (raíz del subárbol a rotar).
     * @return La nueva raíz del subárbol después de la rotación.
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
     * Realiza una rotación simple a la izquierda para balancear el árbol.
     * Se aplica cuando hay un desbalance Derecha-Derecha.
     *     x                                y
     *    / \      Rotación Izquierda      / \
     *   T1  y     – – – – – – – >        x   T3
     *      / \                           / \
     *     T2  T3                        T1  T2
     * @param x El nodo desbalanceado (raíz del subárbol a rotar).
     * @return La nueva raíz del subárbol después de la rotación.
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

    /**
     * Obtiene la altura de un nodo. Retorna 0 si el nodo es nulo.
     * @param node El nodo del cual obtener la altura.
     * @return La altura del nodo.
     */
    private int height(NodeAVL<E> node) {
        return (node == null) ? 0 : node.getHeight();
    }

    /**
     * Actualiza la altura de un nodo basándose en las alturas de sus hijos.
     * La altura de un nodo es 1 + el máximo de las alturas de sus hijos.
     * @param node El nodo cuya altura se va a actualizar.
     */
    private void updateHeight(NodeAVL<E> node) {
        if (node != null) {
            node.setHeight(1 + Math.max(height(node.getLeft()), height(node.getRight())));
        }
    }

    /**
     * Calcula el factor de balance de un nodo.
     * El factor de balance se define como: Altura(Subárbol Izquierdo) - Altura(Subárbol Derecho).
     * @param node El nodo del cual calcular el factor de balance.
     * @return El factor de balance del nodo.
     */
    private int getBalance(NodeAVL<E> node) {
        if (node == null) {
            return 0;
        }
        return height(node.getLeft()) - height(node.getRight());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(E element) {
        if (element == null) {
            throw new NullPointerException("No se permite buscar elementos nulos en el AVL.");
        }
        return containsRecursive(root, element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E get(E element) {
        if (element == null) {
            throw new NullPointerException("No se permite obtener elementos nulos del AVL.");
        }
        return getRecursive(root, element);
    }

    /**
     * Método auxiliar recursivo para obtener un elemento del árbol.
     * @param node El nodo actual en la recursión.
     * @param element El elemento a buscar.
     * @return El elemento encontrado, o null si no existe.
     */
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

    /**
     * Método auxiliar recursivo para verificar si un elemento está presente en el árbol.
     * @param node El nodo actual en la recursión.
     * @param element El elemento a buscar.
     * @return true si el elemento está en el árbol, false en caso contrario.
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String inOrder() {
        StringBuilder sb = new StringBuilder();
        inOrderRecursive(root, sb);
        return sb.toString().trim();
    }

    @Override
    public ImplementacionArrayList<E> inOrderTraversal() {
        ImplementacionArrayList<E> elements = new ImplementacionArrayList<>();
        collectInOrder(root, elements);
        return elements;
    }

    /**
     * Método auxiliar recursivo para realizar un recorrido in-order del árbol.
     * @param node El nodo actual en la recursión.
     * @param sb El StringBuilder para acumular los elementos.
     */
    private void inOrderRecursive(NodeAVL<E> node, StringBuilder sb) {
        if (node != null) {
            inOrderRecursive(node.getLeft(), sb);
            sb.append(node.getValue()).append(" ");
            inOrderRecursive(node.getRight(), sb);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void printByLevels() {
        if (root == null) {
            System.out.println("El árbol está vacío.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║      ÁRBOL AVL POR NIVELES                ║");
        System.out.println("╚════════════════════════════════════════════╝");

        ImplementacionQueue<NodeAVL<E>> queue = new ImplementacionQueue<>();
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

    /**
     * {@inheritDoc}
     */
    @Override
    public int[] getOrderedArray() {
        ImplementacionArrayList<E> elements = new ImplementacionArrayList<>();
        collectInOrder(root, elements);
        
        int[] array = new int[elements.size()];
        for (int i = 0; i < elements.size(); i++) {
            array[i] = (Integer) elements.get(i);
        }
        
        return array;
    }

    /**
     * Método auxiliar recursivo para recolectar los elementos del árbol en orden in-order.
     * @param node El nodo actual en la recursión.
     * @param elements La lista donde se acumularán los elementos.
     */
    private void collectInOrder(NodeAVL<E> node, ImplementacionArrayList<E> elements) {
        if (node != null) {
            collectInOrder(node.getLeft(), elements);
            elements.add(node.getValue());
            collectInOrder(node.getRight(), elements);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight() {
        return height(root);
    }

    /**
     * Imprime una representación visual del árbol AVL en formato ASCII.
     * Muestra el valor del nodo, su altura y su factor de balance.
     */
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

    /**
     * Método auxiliar recursivo para imprimir la estructura del árbol en formato ASCII.
     * @param node El nodo actual a imprimir.
     * @param prefix El prefijo de la línea para la indentación.
     * @param isTail true si el nodo es el último hijo de su padre, false en caso contrario.
     */
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
