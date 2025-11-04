package ejercicio6clase9;

import java.util.Scanner;

public class Main {
    /**
     * Método principal que inicia la aplicación del árbol AVL interactivo.
     * @param args Argumentos de la línea de comandos (no utilizados en esta aplicación).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AVLTDA<Integer> avl = new AVL<>();
        avl.initialize();

        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   PROGRAMA DE ÁRBOL AVL INTERACTIVO       ║");
        System.out.println("╚════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("Ingrese números enteros (uno por línea).");
        System.out.println("Para terminar, ingrese un punto '.'");
        System.out.println();

        int count = 0;

        while (true) {
            System.out.print("Número " + (count + 1) + ": ");
            String input = scanner.nextLine().trim();

            if (input.equals(".")) {
                break;
            }

            try {
                int number = Integer.parseInt(input);
                avl.insert(number);
                count++;
                System.out.println("  ✓ " + number + " insertado correctamente.");
            } catch (NumberFormatException e) {
                System.out.println("  ✗ Error: Ingrese un número válido o '.' para terminar.");
            }
        }

        if (count == 0) {
            System.out.println("\nNo se ingresaron números. Programa terminado.");
            scanner.close();
            return;
        }

        System.out.println("\n════════════════════════════════════════════");
        System.out.println("  Se ingresaron " + count + " elementos.");
        System.out.println("════════════════════════════════════════════");

        // Mostrar el árbol generado por niveles
        avl.printByLevels();

        // Mostrar visualización gráfica del árbol
        ((AVL<Integer>) avl).printTree();

        // Generar y mostrar el arreglo ordenado
        int[] sortedArray = avl.getOrderedArray();
        
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║      ARREGLO ORDENADO (IN-ORDER)          ║");
        System.out.println("╚════════════════════════════════════════════╝");
        System.out.print("[ ");
        for (int i = 0; i < sortedArray.length; i++) {
            System.out.print(sortedArray[i]);
            if (i < sortedArray.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println(" ]");

        // Información adicional
        System.out.println("\n════════════════════════════════════════════");
        System.out.println("  Altura del árbol: " + avl.getHeight());
        System.out.println("  Total de elementos: " + sortedArray.length);
        System.out.println("════════════════════════════════════════════\n");

        scanner.close();
    }
}
