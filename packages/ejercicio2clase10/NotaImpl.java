package ejercicio2clase10;

public class NotaImpl implements Nota {
    private int valor;
    private String materia;

    public NotaImpl(int valor, String materia) {
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
}