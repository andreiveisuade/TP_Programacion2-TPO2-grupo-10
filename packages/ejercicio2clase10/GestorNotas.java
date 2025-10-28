package ejercicio2clase10;

import java.util.List;

public class GestorNotas {
    private List<Nota> notas;

    public GestorNotas(List<Nota> notas) {
        this.notas = notas;
    }

    public double calcularPromedio() {
        if (notas.isEmpty()) {
            return 0.0;
        }
        int suma = 0;
        for (Nota nota : notas) {
            suma += nota.getValor();
        }
        return (double) suma / notas.size();
    }

    public void imprimirNotas() {
        System.out.println("Notas ingresadas:");
        for (Nota nota : notas) {
            System.out.println("Materia: " + nota.getMateria() + ", Nota: " + nota.getValor());
        }
    }
}