
package ejercicio6clase9;

class Node<E> {
    E value;
    Node<E> next;

    Node(E value) {
        this.value = value;
        this.next = null;
    }
}

public class ImplementacionLinkedList<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size = 0;

    public void add(E element) {
        Node<E> newNode = new Node<>(element);
        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public E poll() {
        if (head == null) {
            return null;
        }
        E value = head.value;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return value;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}
