package ejercicio2clase10;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
            System.out.print("Nota: ");
            int valorNota = scanner.nextInt();
            if (valorNota == -1) {
                break;
            }
            scanner.nextLine(); // Consume newline

            System.out.print("Materia: ");
            String materia = scanner.nextLine();

            notas.add(new NotaImpl(valorNota, materia));
        }
        return notas;
    }
}