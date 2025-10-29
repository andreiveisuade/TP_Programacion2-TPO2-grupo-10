package ejercicio2clase10;

import java.util.List;

/**
 * Clase principal que demuestra el funcionamiento del cargador y gestor de notas.
 * Permite al usuario ingresar notas y luego calcula y muestra el promedio.
 */
public class Main {
    /**
     * Método principal que inicia la aplicación.
     * @param args Argumentos de la línea de comandos (no utilizados en esta aplicación).
     */
    public static void main(String[] args) {
        CargadorNotas cargadorNotas = new CargadorNotasImpl();
        List<Nota> notas = cargadorNotas.cargarNotas();

        GestorNotas gestorNotas = new GestorNotas(notas);
        gestorNotas.imprimirNotas();
        System.out.println("Promedio: " + gestorNotas.calcularPromedio());
    }
}