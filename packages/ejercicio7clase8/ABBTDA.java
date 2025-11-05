package ejercicio7clase8;

public interface ABBTDA<E extends Comparable<E>> extends IterableTDA<E> {
    void initialize();
    void insert(E element);
    E remove(E element);
    boolean contains(E element);
    String inOrder();

    IteratorTDA<E> iterator();
}
