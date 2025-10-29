package ejercicio2clase10;

import java.util.List;
import java.util.ArrayList;

/**
 * Clase encargada de gestionar una colección de notas, permitiendo calcular el promedio
 * e imprimir las notas registradas.
 */
public class GestorNotas {
    private List<Nota> notas;

    /**
     * Constructor para crear una nueva instancia de GestorNotas.
     * Realiza una copia defensiva de la lista de notas proporcionada.
     * @param notas La lista de objetos {@link Nota} a gestionar. No puede ser nula.
     * @throws IllegalArgumentException Si la lista de notas proporcionada es nula.
     */
    public GestorNotas(List<Nota> notas) {
        if (notas == null) {
            throw new IllegalArgumentException("La lista de notas no puede ser nula.");
        }
        this.notas = new ArrayList<>(notas); // Copia defensiva
    }

    /**
     * Calcula el promedio de todas las notas gestionadas.
     * @return El promedio de las notas. Retorna 0.0 si no hay notas.
     */
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

    /**
     * Imprime en la consola todas las notas gestionadas, mostrando la materia y el valor de cada nota.
     */
    public void imprimirNotas() {
        System.out.println("Notas ingresadas:");
        for (Nota nota : notas) {
            System.out.println("Materia: " + nota.getMateria() + ", Nota: " + nota.getValor());
        }
    }
}