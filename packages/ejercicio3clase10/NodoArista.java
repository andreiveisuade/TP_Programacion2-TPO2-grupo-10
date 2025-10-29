package ejercicio3clase10;

// ============================================
// CLASE NODO ARISTA
// ============================================
/**
 * Representa un nodo en una lista de adyacencia, que simboliza una arista saliente de un vértice.
 * Contiene el vértice de destino, el peso de la arista y una referencia a la siguiente arista en la lista.
 * @param <E> El tipo de dato del vértice de destino.
 */
class NodoArista<E> {
    private E destino;
    private int peso;
    private NodoArista<E> siguiente;

    /**
     * Constructor para crear un nuevo NodoArista.
     * @param destino El vértice al que apunta esta arista.
     * @param peso El peso o costo asociado a esta arista.
     */
    public NodoArista(E destino, int peso) {
        this.destino = destino;
        this.peso = peso;
        this.siguiente = null;
    }

    /**
     * Obtiene el vértice de destino de esta arista.
     * @return El vértice de destino.
     */
    public E getDestino() { return destino; }

    /**
     * Obtiene el peso de esta arista.
     * @return El peso de la arista.
     */
    public int getPeso() { return peso; }

    /**
     * Establece un nuevo peso para esta arista.
     * @param peso El nuevo peso de la arista.
     */
    public void setPeso(int peso) { this.peso = peso; }

    /**
     * Obtiene el siguiente NodoArista en la lista de adyacencia.
     * @return El siguiente NodoArista, o null si es el último.
     */
    public NodoArista<E> getSiguiente() { return siguiente; }

    /**
     * Establece el siguiente NodoArista en la lista de adyacencia.
     * @param siguiente El siguiente NodoArista.
     */
    public void setSiguiente(NodoArista<E> siguiente) { this.siguiente = siguiente; }
}
