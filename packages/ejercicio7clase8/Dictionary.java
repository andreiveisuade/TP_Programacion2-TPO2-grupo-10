package ejercicio7clase8;

import ejercicio6clase9.AVL;
import ejercicio6clase9.AVLTDA;

import java.util.ArrayList;

// ============================================ 
// IMPLEMENTACIÓN DICCIONARIO MÚLTIPLE (EFICIENTE) 
// ============================================ 
/**
 * Implementación de un Diccionario Múltiple que asocia una clave con un conjunto de valores
 * almacenados en un Árbol Binario de Búsqueda (ABB). Utiliza un AVL para almacenar las claves,
 * logrando una alta eficiencia en las operaciones.
 * @param <K> Tipo de la clave, debe ser comparable.
 * @param <V> Tipo de los valores, deben ser comparables.
 */
public class Dictionary<K extends Comparable<K>, V extends Comparable<V>> 
        implements DictionaryTDA<K, V> { 
    
    private AVLTDA<Entry<K, ABBTDA<V>>> entries;

    @Override
    public void initialize() {
        entries = new AVL<>();
        entries.initialize();
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("No se permite clave nula en el diccionario.");
        }
        Entry<K, ABBTDA<V>> entry = entries.get(new Entry<>(key, null));
        
        if (entry != null) {
            entry.getValue().insert(value);
        } else {
            ABBTDA<V> newABB = new ABB<>();
            newABB.initialize();
            newABB.insert(value);
            entries.insert(new Entry<>(key, newABB));
        }
    }

    @Override
    public ABBTDA<V> get(K key) {
        if (key == null) {
            throw new NullPointerException("No se permite clave nula para obtener valores del diccionario.");
        }
        Entry<K, ABBTDA<V>> entry = entries.get(new Entry<>(key, null));
        return (entry != null) ? entry.getValue() : null;
    }

    @Override
    public void remove(K key) {
        if (key == null) {
            throw new NullPointerException("No se permite clave nula para eliminar del diccionario.");
        }
        entries.remove(new Entry<>(key, null));
    }

    @Override
    public void remove(K key, V value) {
        if (key == null) {
            throw new NullPointerException("No se permite clave nula para eliminar del diccionario.");
        }
        ABBTDA<V> abb = get(key);
        if (abb != null) {
            abb.remove(value);
        }
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public void printDictionary() {
        ArrayList<Entry<K, ABBTDA<V>>> entryList = entries.inOrderTraversal();
        
        System.out.println("===== DICCIONARIO MÚLTIPLE (Eficiente) =====\n");
        for (Entry<K, ABBTDA<V>> entry : entryList) {
            K key = entry.getKey();
            ABBTDA<V> abb = entry.getValue();
            
            System.out.println("Clave: " + key);
            System.out.println("  Valores (in-order): " + abb.inOrder());
        }
        System.out.println("\n============================================");
    }
}
