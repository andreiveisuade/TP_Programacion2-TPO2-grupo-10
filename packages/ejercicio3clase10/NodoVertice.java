package ejercicio3clase10;

// ============================================
// CLASE NODO VÉRTICE
// ============================================
/**
 * Representa un nodo en el grafo, conteniendo el valor del vértice y una referencia
 * a la lista de aristas salientes (adyacentes).
 * También incluye un campo 'siguiente' que podría ser usado si los vértices se almacenaran en una lista enlazada.
 * @param <E> El tipo de dato del valor del vértice.
 */
class NodoVertice<E> {
    private E valor;
    private NodoArista<E> adyacentes; // Lista de aristas salientes
    private NodoVertice<E> siguiente;

    /**
     * Constructor para crear un nuevo NodoVertice.
     * @param valor El valor que representará este vértice.
     */
    public NodoVertice(E valor) {
        this.valor = valor;
        this.adyacentes = null;
        this.siguiente = null;
    }

    /**
     * Obtiene el valor asociado a este vértice.
     * @return El valor del vértice.
     */
    public E getValor() { return valor; }

    /**
     * Obtiene la primera arista saliente de este vértice (cabeza de la lista de adyacencia).
     * @return El primer {@link NodoArista} saliente, o null si no tiene adyacentes.
     */
    public NodoArista<E> getAdyacentes() { return adyacentes; }

    /**
     * Establece la lista de aristas salientes para este vértice.
     * @param adyacentes El primer {@link NodoArista} de la lista de adyacencia.
     */
    public void setAdyacentes(NodoArista<E> adyacentes) { this.adyacentes = adyacentes; }

    /**
     * Obtiene el siguiente NodoVertice en una posible lista enlazada de vértices.
     * (Nota: Este campo puede no ser utilizado en todas las implementaciones del grafo).
     * @return El siguiente {@link NodoVertice}, o null si es el último.
     */
    public NodoVertice<E> getSiguiente() { return siguiente; }

    /**
     * Establece el siguiente NodoVertice en una posible lista enlazada de vértices.
     * (Nota: Este campo puede no ser utilizado en todas las implementaciones del grafo).
     * @param siguiente El siguiente {@link NodoVertice}.
     */
    public void setSiguiente(NodoVertice<E> siguiente) { this.siguiente = siguiente; }
}
