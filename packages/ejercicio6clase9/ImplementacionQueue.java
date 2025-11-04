
package ejercicio6clase9;

public class ImplementacionQueue<E> {
    private ImplementacionLinkedList<E> list = new ImplementacionLinkedList<>();

    public void add(E element) {
        list.add(element);
    }

    public E poll() {
        return list.poll();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }
}
