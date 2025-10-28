package ejercicio2clase10;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

public class CargadorNotasImpl implements CargadorNotas {
    private Scanner scanner;

    public CargadorNotasImpl() {
        this.scanner = new Scanner(System.in);
    }

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