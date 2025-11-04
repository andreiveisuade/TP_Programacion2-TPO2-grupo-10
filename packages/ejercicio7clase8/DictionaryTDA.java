package ejercicio7clase8;

public interface DictionaryTDA<K extends Comparable<K>, V extends Comparable<V>> {
    void put(K key, V value);
    ABBTDA<V> get(K key); // ← Cambiar AVLTDA por ABBTDA
    void remove(K key);
    void remove(K key, V value);
    boolean containsKey(K key);
    void initialize();
}
