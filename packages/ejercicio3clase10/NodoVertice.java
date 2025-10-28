package ejercicio3clase10;

// ============================================
// CLASE NODO VÉRTICE
// ============================================
class NodoVertice<E> {
    private E valor;
    private NodoArista<E> adyacentes; // Lista de aristas salientes
    private NodoVertice<E> siguiente;

    public NodoVertice(E valor) {
        this.valor = valor;
        this.adyacentes = null;
        this.siguiente = null;
    }

    public E getValor() { return valor; }
    public NodoArista<E> getAdyacentes() { return adyacentes; }
    public void setAdyacentes(NodoArista<E> adyacentes) { this.adyacentes = adyacentes; }
    public NodoVertice<E> getSiguiente() { return siguiente; }
    public void setSiguiente(NodoVertice<E> siguiente) { this.siguiente = siguiente; }
}
