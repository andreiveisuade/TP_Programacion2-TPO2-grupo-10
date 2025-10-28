package ejercicio2clase10;

public class NotaImpl implements Nota {
    private int valor;
    private String materia;

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

    @Override
    public int getValor() {
        return valor;
    }

    @Override
    public String getMateria() {
        return materia;
    }

    @Override
    public String toString() {
        return String.valueOf(valor);
    }
}