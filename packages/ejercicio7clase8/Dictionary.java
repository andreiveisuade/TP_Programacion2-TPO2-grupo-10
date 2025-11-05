package ejercicio7clase8;

import ejercicio7clase8.ABBTDA;


// ============================================ 
// IMPLEMENTACIÓN DICCIONARIO MÚLTIPLE (EFICIENTE) 
// ============================================ 
/**
 * Implementación de un Diccionario Múltiple eficiente que asocia una clave con un conjunto de valores.
 * Utiliza un Árbol ABB ({@link ABBTDA}) para almacenar las claves, donde cada clave apunta a otro
 * Árbol ABB que contiene los valores asociados a esa clave. Esto permite una alta eficiencia
 * en las operaciones de búsqueda, inserción y eliminación.
 * @param <K> Tipo de la clave, debe ser comparable.
 * @param <V> Tipo de los valores, deben ser comparables.
 */
public class Dictionary<K extends Comparable<K>, V extends Comparable<V>> 
        implements DictionaryTDA<K, V> { 
    
    private ABBTDA<Entry<K, ABBTDA<V>>> entries;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize() {
        entries = new ABB<>();
        entries.initialize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("No se permite clave nula en el diccionario.");
        }
        Entry<K, ABBTDA<V>> searchEntry = new Entry<>(key, null);
        Entry<K, ABBTDA<V>> entry = null;
        IteratorTDA<Entry<K, ABBTDA<V>>> it = entries.iterator();
        while (it.hasNext()) {
            Entry<K, ABBTDA<V>> currentEntry = it.next();
            if (currentEntry.getKey().equals(key)) {
                entry = currentEntry;
                break;
            }
        }
        
        if (entry != null) {
            entry.getValue().insert(value);
        } else {
            ABBTDA<V> newABB = new ABB<>();
            newABB.initialize();
            newABB.insert(value);
            entries.insert(new Entry<>(key, newABB));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IterableTDA<V> get(K key) {
        if (key == null) {
            throw new NullPointerException("No se permite clave nula para obtener valores del diccionario.");
        }
        Entry<K, ABBTDA<V>> entry = null;
        IteratorTDA<Entry<K, ABBTDA<V>>> it = entries.iterator();
        while (it.hasNext()) {
            Entry<K, ABBTDA<V>> currentEntry = it.next();
            if (currentEntry.getKey().equals(key)) {
                entry = currentEntry;
                break;
            }
        }
        if (entry != null) {
            return entry.getValue();
        } else {
            return new EmptyIterable<>();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IterableTDA<V> remove(K key) {
        if (key == null) {
            throw new NullPointerException("No se permite clave nula para eliminar del diccionario.");
        }
        Entry<K, ABBTDA<V>> searchEntry = new Entry<>(key, null);
        Entry<K, ABBTDA<V>> entry = null;
        IteratorTDA<Entry<K, ABBTDA<V>>> it = entries.iterator();
        while (it.hasNext()) {
            Entry<K, ABBTDA<V>> currentEntry = it.next();
            if (currentEntry.getKey().equals(key)) {
                entry = currentEntry;
                break;
            }
        }
        if (entry != null) {
            entries.remove(searchEntry);
            return entry.getValue();
        } else {
            return new EmptyIterable<>();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V remove(K key, V value) {
        if (key == null) {
            throw new NullPointerException("No se permite clave nula para eliminar del diccionario.");
        }
        Entry<K, ABBTDA<V>> entry = null;
        IteratorTDA<Entry<K, ABBTDA<V>>> it = entries.iterator();
        while (it.hasNext()) {
            Entry<K, ABBTDA<V>> currentEntry = it.next();
            if (currentEntry.getKey().equals(key)) {
                entry = currentEntry;
                break;
            }
        }
        if (entry != null) {
            return entry.getValue().remove(value);
        } else {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsKey(K key) {
        return entries.contains(new Entry<>(key, null));
    }

}
