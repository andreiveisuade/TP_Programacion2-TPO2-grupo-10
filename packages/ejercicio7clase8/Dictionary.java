package ejercicio7clase8;

import ejercicio7clase8.ABB;
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
    
    private ABBTDA<Entry<K, ABBTDA<V>>> entries; // ← Cambiar AVLTDA por ABBTDA

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize() {
        entries = new ABB<>(); // ← Cambiar AVL por ABB
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
        Entry<K, ABBTDA<V>> entry = entries.get(new Entry<>(key, null));
        
        if (entry != null) {
            entry.getValue().insert(value);
        } else {
            ABBTDA<V> newABB = new ABB<>(); // ← Cambiar AVL por ABB
            newABB.initialize();
            newABB.insert(value);
            entries.insert(new Entry<>(key, newABB));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ABBTDA<V> get(K key) { // ← Cambiar AVLTDA por ABBTDA
        if (key == null) {
            throw new NullPointerException("No se permite clave nula para obtener valores del diccionario.");
        }
        Entry<K, ABBTDA<V>> entry = entries.get(new Entry<>(key, null));
        return (entry != null) ? entry.getValue() : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(K key) {
        if (key == null) {
            throw new NullPointerException("No se permite clave nula para eliminar del diccionario.");
        }
        entries.remove(new Entry<>(key, null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(K key, V value) {
        if (key == null) {
            throw new NullPointerException("No se permite clave nula para eliminar del diccionario.");
        }
        ABBTDA<V> abb = get(key); // ← Cambiar avl por abb
        if (abb != null) {
            abb.remove(value);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Imprime el contenido del diccionario en la consola, mostrando cada clave y sus valores asociados
     * en orden ascendente.
     */
    public void printDictionary() {
        System.out.println("===== DICCIONARIO MÚLTIPLE (Eficiente) =====\n");
        entries.forEachInOrder(entry -> {
            K key = entry.getKey();
            ABBTDA<V> abb = entry.getValue();
            System.out.println("Clave: " + key);
            System.out.println("  Valores (in-order): " + abb.inOrder());
        });
        System.out.println("\n============================================");
    }
}
