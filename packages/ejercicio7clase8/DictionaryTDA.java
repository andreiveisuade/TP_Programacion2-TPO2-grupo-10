package ejercicio7clase8;

// ============================================
// INTERFAZ TDA DICCIONARIO MÚLTIPLE
// ============================================
/**
 * Interfaz para un Tipo de Dato Abstracto (TDA) de Diccionario Múltiple.
 * Un diccionario múltiple permite asociar múltiples valores a una misma clave.
 * Los valores asociados a cada clave se gestionan mediante un ABB (Árbol Binario de Búsqueda).
 * @param <K> Tipo de la clave, debe ser comparable.
 * @param <V> Tipo de los valores, deben ser comparables.
 */
public interface DictionaryTDA<K extends Comparable<K>, V extends Comparable<V>> {
    /**
     * Asocia el valor especificado con la clave especificada en este diccionario.
     * Si la clave ya existe, el valor se añade al ABB de valores de esa clave.
     * @param key La clave con la que se asociará el valor.
     * @param value El valor a ser asociado con la clave.
     */
    void put(K key, V value);

    /**
     * Retorna el ABB de valores asociados a la clave especificada.
     * @param key La clave de la que se desean obtener los valores.
     * @return Un ABBTDA que contiene todos los valores asociados a la clave, o null si la clave no existe.
     */
    ABBTDA<V> get(K key);

    /**
     * Elimina la clave especificada y todos sus valores asociados del diccionario.
     * @param key La clave a eliminar.
     */
    void remove(K key);

    /**
     * Elimina un valor específico asociado a una clave específica.
     * Si la clave existe y el valor está en su ABB, lo elimina.
     * @param key La clave de la que se eliminará el valor.
     * @param value El valor a eliminar.
     */
    void remove(K key, V value);

    /**
     * Verifica si el diccionario contiene la clave especificada.
     * @param key La clave a verificar.
     * @return true si el diccionario contiene la clave, false en caso contrario.
     */
    boolean containsKey(K key);

    /**
     * Inicializa el diccionario, dejándolo vacío.
     */
    void initialize();
}
