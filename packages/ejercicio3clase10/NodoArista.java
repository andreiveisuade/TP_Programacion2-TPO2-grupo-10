package ejercicio3clase10;

// ============================================
// CLASE NODO ARISTA
// ============================================
class NodoArista<E> {
    private E destino;
    private int peso;
    private NodoArista<E> siguiente;

    public NodoArista(E destino, int peso) {
        this.destino = destino;
        this.peso = peso;
        this.siguiente = null;
    }

    public E getDestino() { return destino; }
    public int getPeso() { return peso; }
    public void setPeso(int peso) { this.peso = peso; }
    public NodoArista<E> getSiguiente() { return siguiente; }
    public void setSiguiente(NodoArista<E> siguiente) { this.siguiente = siguiente; }
}
