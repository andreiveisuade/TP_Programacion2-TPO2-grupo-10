package ejercicio7clase8;

public class EmptyIterable<T> implements IterableTDA<T> {

    @Override
    public IteratorTDA<T> iterator() {
        return new IteratorTDA<T>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public T next() {
                throw new RuntimeException("No such element");
            }
        };
    }
}
