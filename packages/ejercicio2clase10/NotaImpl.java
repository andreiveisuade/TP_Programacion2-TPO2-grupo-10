package ejercicio2clase10;

/**
 * Implementación concreta de la interfaz {@link Nota}.
 * Representa una nota académica con un valor numérico y la materia asociada.
 */
public class NotaImpl implements Nota {
    private int valor;
    private String materia;

    /**
     * Constructor para crear una nueva instancia de NotaImpl.
     * @param valor El valor numérico de la nota (debe estar entre 0 y 10).
     * @param materia La materia a la que corresponde la nota (no puede ser nula o vacía).
     * @throws IllegalArgumentException Si el valor de la nota está fuera del rango permitido o la materia es nula/vacía.
     */
    public NotaImpl(int valor, String materia) {
        if (valor < 0 || valor > 10) {
            throw new IllegalArgumentException("La nota debe estar entre 0 y 10.");
        }
        if (materia == null || materia.trim().isEmpty()) {
            throw new IllegalArgumentException("La materia no puede ser nula o vacía.");
        }
        this.valor = valor;
        this.materia = materia;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getValor() {
        return valor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMateria() {
        return materia;
    }

    /**
     * Retorna la representación en cadena del valor de la nota.
     * @return El valor de la nota como String.
     */
    @Override
    public String toString() {
        return String.valueOf(valor);
    }
}