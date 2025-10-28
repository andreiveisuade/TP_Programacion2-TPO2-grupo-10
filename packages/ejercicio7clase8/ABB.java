package ejercicio7clase8;

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

    @Override
    public void initialize() {
        root = null;
    }

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

    @Override
    public boolean contains(E element) {
        if (element == null) {
            throw new NullPointerException("No se permite buscar elementos nulos en el ABB.");
        }
        return containsRecursive(root, element);
    }

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

    @Override
    public void remove(E element) {
        if (element == null) {
            throw new NullPointerException("No se permite eliminar elementos nulos del ABB.");
        }
        root = removeRecursive(root, element);
    }

    private NodeABB<E> removeRecursive(NodeABB<E> node, E element) {
        if (node == null) {
            return null;
        }

        int comparison = element.compareTo(node.getValue());

        if (comparison < 0) {
            node.setLeft(removeRecursive(node.getLeft(), element));
        } else if (comparison > 0) {
            node.setRight(removeRecursive(node.getRight(), element));
        } else {
            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            }
            if (node.getLeft() == null) {
                return node.getRight();
            }
            if (node.getRight() == null) {
                return node.getLeft();
            }
            
            NodeABB<E> successor = findMin(node.getRight());
            node.setValue(successor.getValue());
            node.setRight(removeRecursive(node.getRight(), successor.getValue()));
        }

        return node;
    }

    private NodeABB<E> findMin(NodeABB<E> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    @Override
    public String inOrder() {
        StringBuilder sb = new StringBuilder();
        inOrderRecursive(root, sb);
        return sb.toString().trim();
    }

    private void inOrderRecursive(NodeABB<E> node, StringBuilder sb) {
        if (node != null) {
            inOrderRecursive(node.getLeft(), sb);
            sb.append(node.getValue()).append(" ");
            inOrderRecursive(node.getRight(), sb);
        }
    }
}
