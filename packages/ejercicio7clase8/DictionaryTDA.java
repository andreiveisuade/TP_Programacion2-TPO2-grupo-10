package ejercicio7clase8;

public interface DictionaryTDA<K extends Comparable<K>, V> {
    void initialize();
    void put(K key, V value);
    IterableTDA<V> get(K key);
    IterableTDA<V> remove(K key);
    V remove(K key, V value);
    boolean containsKey(K key);
}
