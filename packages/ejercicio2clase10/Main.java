package ejercicio2clase10;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CargadorNotas cargadorNotas = new CargadorNotasImpl();
        List<Nota> notas = cargadorNotas.cargarNotas();

        GestorNotas gestorNotas = new GestorNotas(notas);
        gestorNotas.imprimirNotas();
        System.out.println("Promedio: " + gestorNotas.calcularPromedio());
    }
}