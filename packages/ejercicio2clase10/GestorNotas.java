package ejercicio2clase10;

import java.util.List;
import java.util.ArrayList;

public class GestorNotas {
    private List<Nota> notas;

    public GestorNotas(List<Nota> notas) {
        if (notas == null) {
            throw new IllegalArgumentException("La lista de notas no puede ser nula.");
        }
        this.notas = new ArrayList<>(notas); // Copia defensiva
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