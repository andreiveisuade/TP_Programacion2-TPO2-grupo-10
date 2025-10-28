package ejercicio7clase8;

// ============================================
// CLASE ENTRY (PAR CLAVE-VALOR)
// ============================================
/**
 * Representa un par clave-valor genérico. Utilizado para almacenar las asociaciones en el diccionario.
 * @param <K> Tipo de la clave.
 * @param <V> Tipo del valor.
 */
public class Entry<K extends Comparable<K>, V> implements Comparable<Entry<K, V>> {
    private K key;
    private V value;

    /**
     * Constructor para crear una nueva entrada con la clave y el valor especificados.
     * @param key La clave de la entrada.
     * @param value El valor asociado a la clave.
     */
    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Obtiene la clave de esta entrada.
     * @return La clave.
     */
    public K getKey() { return key; }

    /**
     * Obtiene el valor de esta entrada.
     * @return El valor.
     */
    public V getValue() { return value; }

    /**
     * Establece un nuevo valor para esta entrada.
     * @param value El nuevo valor a establecer.
     */
    public void setValue(V value) { this.value = value; }

    @Override
    public int compareTo(Entry<K, V> other) {
        return this.key.compareTo(other.key);
    }
}
