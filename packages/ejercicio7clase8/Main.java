package ejercicio7clase8;

// ============================================ 
// PROGRAMA PRINCIPAL DE PRUEBA
// ============================================ 
/**
 * Clase principal que contiene el método main para probar la implementación del Diccionario Múltiple con ABB.
 */
public class Main {
    /**
     * Método principal donde se ejecuta la lógica de prueba del diccionario.
     * @param args Argumentos de la línea de comandos (no utilizados en este programa).
     */
    public static void main(String[] args) {
        // Crear el diccionario
        Dictionary<String, Integer> dictionary = new Dictionary<>();
        dictionary.initialize();

        System.out.println("=== PRUEBA DEL DICCIONARIO MÚLTIPLE CON ABB (EFICIENTE) ===\n");

        // Agregar valores
        System.out.println("1. Agregando valores...");
        dictionary.put("frutas", 5);
        dictionary.put("frutas", 2);
        dictionary.put("frutas", 8);
        dictionary.put("frutas", 1);
        dictionary.put("frutas", 9);
        
        dictionary.put("numeros", 15);
        dictionary.put("numeros", 10);
        dictionary.put("numeros", 20);
        
        dictionary.put("letras", 25);
        dictionary.put("letras", 30);

        // Mostrar el diccionario
        System.out.println("\n2. Estado del diccionario:");
        dictionary.printDictionary();

        // Consultar valores de una clave
        System.out.println("\n3. Consultando valores de 'frutas':");
        ABBTDA<Integer> frutasABB = dictionary.get("frutas");
        if (frutasABB != null) {
            System.out.println("   In-order: " + frutasABB.inOrder());
        }

        // Verificar si contiene un valor
        System.out.println("\n4. Verificando si 'frutas' contiene el valor 8:");
        System.out.println("   Resultado: " + frutasABB.contains(8));
        
        System.out.println("   Verificando si 'frutas' contiene el valor 100:");
        System.out.println("   Resultado: " + frutasABB.contains(100));

        // Eliminar un valor específico
        System.out.println("\n5. Eliminando el valor 2 de 'frutas'...");
        dictionary.remove("frutas", 2);
        System.out.println("   Valores actuales: " + dictionary.get("frutas").inOrder());

        // Eliminar una clave completa
        System.out.println("\n6. Eliminando la clave 'letras' completa...");
        dictionary.remove("letras");
        
        System.out.println("\n7. Estado final del diccionario:");
        dictionary.printDictionary();
    }
}
