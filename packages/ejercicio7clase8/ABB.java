package ejercicio7clase8;

import ejercicio7clase8.IterableTDA;
import ejercicio7clase8.IteratorTDA;

// ============================================
// IMPLEMENTACIÓN ABB
// ============================================
/**
 * Implementación de un Árbol Binario de Búsqueda (ABB) que cumple con la interfaz ABBTDA.
 * Permite almacenar elementos comparables, insertarlos, eliminarlos, buscarlos y recorrerlos en orden.
 * @param <E> Tipo de elementos que almacenará el ABB, deben ser comparables.
 */
public class ABB<E extends Comparable<E>> implements ABBTDA<E> {
    private NodeABB<E> root;

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
            throw new NullPointerException("No se permite insertar elementos nulos en el ABB.");
        }
        if (root == null) {
            root = new NodeABB<>(element);
        } else {
            insertRecursive(root, element);
        }
    }

    /**
     * Método auxiliar recursivo para insertar un elemento en el ABB.
     * @param node El nodo actual en la recursión.
     * @param element El elemento a insertar.
     */
    private void insertRecursive(NodeABB<E> node, E element) {
        int comparison = element.compareTo(node.getValue());
        
        if (comparison < 0) {
            if (node.getLeft() == null) {
                node.setLeft(new NodeABB<>(element));
            } else {
                insertRecursive(node.getLeft(), element);
            }
        } else if (comparison > 0) {
            if (node.getRight() == null) {
                node.setRight(new NodeABB<>(element));
            } else {
                insertRecursive(node.getRight(), element);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(E element) {
        if (element == null) {
            throw new NullPointerException("No se permite buscar elementos nulos en el ABB.");
        }
        return containsRecursive(root, element);
    }

    /**
     * Método auxiliar recursivo para verificar si un elemento está presente en el ABB.
     * @param node El nodo actual en la recursión.
     * @param element El elemento a buscar.
     * @return true si el elemento está en el ABB, false en caso contrario.
     */
    private boolean containsRecursive(NodeABB<E> node, E element) {
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
    public E remove(E element) {
        if (element == null) {
            throw new NullPointerException("No se permite eliminar elementos nulos del ABB.");
        }
        E[] removedElement = (E[]) new Comparable[1]; // Array to hold the removed element
        root = removeRecursive(root, element, removedElement);
        return removedElement[0];
    }

    /**
     * Método auxiliar recursivo para eliminar un elemento del ABB.
     * @param node El nodo actual en la recursión.
     * @param element El elemento a eliminar.
     * @return La raíz del subárbol modificado.
     */
    private NodeABB<E> removeRecursive(NodeABB<E> node, E element, E[] removedElement) {
        if (node == null) {
            return null;
        }

        int comparison = element.compareTo(node.getValue());

        if (comparison < 0) {
            node.setLeft(removeRecursive(node.getLeft(), element, removedElement));
        } else if (comparison > 0) {
            node.setRight(removeRecursive(node.getRight(), element, removedElement));
        } else {
            // Nodo encontrado
            removedElement[0] = node.getValue(); // Store the removed element
            // Caso 1: Nodo sin hijos o con un solo hijo
            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            }
            if (node.getLeft() == null) {
                return node.getRight();
            }
            if (node.getRight() == null) {
                return node.getLeft();
            }
            
            // Caso 2: Nodo con dos hijos
            NodeABB<E> successor = findMin(node.getRight());
            node.setValue(successor.getValue());
            node.setRight(removeRecursive(node.getRight(), successor.getValue(), removedElement));
        }

        return node;
    }

    /**
     * Método auxiliar para encontrar el nodo con el valor mínimo en un subárbol dado.
     * @param node La raíz del subárbol donde buscar el mínimo.
     * @return El nodo con el valor mínimo.
     */
    private NodeABB<E> findMin(NodeABB<E> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
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

    /**
     * Método auxiliar recursivo para realizar un recorrido in-order del ABB.
     * @param node El nodo actual en la recursión.
     * @param sb El StringBuilder para acumular los elementos.
     */
    private void inOrderRecursive(NodeABB<E> node, StringBuilder sb) {
        if (node != null) {
            inOrderRecursive(node.getLeft(), sb);
            sb.append(node.getValue()).append(" ");
            inOrderRecursive(node.getRight(), sb);
        }
    }

    @Override
    public IteratorTDA<E> iterator() {
        return new ABBIterator();
    }

    private class ABBIterator implements IteratorTDA<E> {
        private CustomStack<NodeABB<E>> stack;

        public ABBIterator() {
            stack = new CustomStack<>();
            pushLeft(root);
        }

        private void pushLeft(NodeABB<E> node) {
            while (node != null) {
                stack.push(node);
                node = node.getLeft();
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new RuntimeException("No such element");
            }
            NodeABB<E> node = stack.pop();
            E result = node.getValue();
            pushLeft(node.getRight());
            return result;
        }
    }
}