package ejercicio2clase10;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Implementación de {@link CargadorNotas} que permite al usuario ingresar notas a través de la consola.
 * Valida la entrada del usuario para asegurar que las notas estén en un rango válido y que la materia no esté vacía.
 */
public class CargadorNotasImpl implements CargadorNotas {
    private Scanner scanner;

    /**
     * Constructor que inicializa un nuevo Scanner para leer la entrada del usuario.
     */
    public CargadorNotasImpl() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * {@inheritDoc}
     * Permite al usuario ingresar notas y materias por consola hasta que se introduce -1 como nota.
     * Realiza validaciones básicas sobre el valor de la nota y la materia.
     * @return Una lista de objetos {@link Nota} ingresados por el usuario.
     */
    @Override
    public List<Nota> cargarNotas() {
        List<Nota> notas = new ArrayList<>();
        System.out.println("Ingrese las notas (ingrese -1 para finalizar):");

        while (true) {
            try {
                System.out.print("Nota: ");
                int valorNota = scanner.nextInt();
                
                if (valorNota == -1) break;
                if (valorNota < 0 || valorNota > 10) {
                    System.out.println("  ✗ Nota inválida. Debe estar entre 0 y 10.");
                    continue;
                }
                
                scanner.nextLine(); // Consume newline
                System.out.print("Materia: ");
                String materia = scanner.nextLine().trim();
                
                if (materia.isEmpty()) {
                    System.out.println("  ✗ La materia no puede estar vacía.");
                    continue;
                }
                
                notas.add(new NotaImpl(valorNota, materia));
                System.out.println("  ✓ Nota agregada.");
                
            } catch (InputMismatchException e) {
                System.out.println("  ✗ Ingrese un número válido.");
                scanner.nextLine(); // Limpiar buffer
            }
        }
        return notas;
    }
}