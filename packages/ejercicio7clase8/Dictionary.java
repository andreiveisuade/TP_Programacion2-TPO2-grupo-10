package ejercicio7clase8;

import ejercicio6clase9.AVL;
import ejercicio6clase9.AVLTDA;

import java.util.ArrayList;

// ============================================ 
// IMPLEMENTACIÓN DICCIONARIO MÚLTIPLE (EFICIENTE) 
// ============================================ 
/**
 * Implementación de un Diccionario Múltiple eficiente que asocia una clave con un conjunto de valores.
 * Utiliza un Árbol AVL ({@link AVLTDA}) para almacenar las claves, donde cada clave apunta a otro
 * Árbol AVL que contiene los valores asociados a esa clave. Esto permite una alta eficiencia
 * en las operaciones de búsqueda, inserción y eliminación.
 * @param <K> Tipo de la clave, debe ser comparable.
 * @param <V> Tipo de los valores, deben ser comparables.
 */
public class Dictionary<K extends Comparable<K>, V extends Comparable<V>> 
        implements DictionaryTDA<K, V> { 
    
    private AVLTDA<Entry<K, AVLTDA<V>>> entries;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize() {
        entries = new AVL<>();
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
        Entry<K, AVLTDA<V>> entry = entries.get(new Entry<>(key, null));
        
        if (entry != null) {
            entry.getValue().insert(value);
        } else {
            AVLTDA<V> newAVL = new AVL<>();
            newAVL.initialize();
            newAVL.insert(value);
            entries.insert(new Entry<>(key, newAVL));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AVLTDA<V> get(K key) {
        if (key == null) {
            throw new NullPointerException("No se permite clave nula para obtener valores del diccionario.");
        }
        Entry<K, AVLTDA<V>> entry = entries.get(new Entry<>(key, null));
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
        AVLTDA<V> avl = get(key);
        if (avl != null) {
            avl.remove(value);
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
        ArrayList<Entry<K, AVLTDA<V>>> entryList = entries.inOrderTraversal();
        
        System.out.println("===== DICCIONARIO MÚLTIPLE (Eficiente) =====\n");
        for (Entry<K, AVLTDA<V>> entry : entryList) {
            K key = entry.getKey();
            AVLTDA<V> avl = entry.getValue();
            
            System.out.println("Clave: " + key);
            System.out.println("  Valores (in-order): " + avl.inOrder());
        }
        System.out.println("\n============================================");
    }
}