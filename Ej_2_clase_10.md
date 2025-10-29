# Ejercicio 2 de la Clase 10: Gestión de Notas

Este documento presenta un análisis detallado de la eficiencia Big O de la implementación para la gestión de notas, que incluye la carga, cálculo de promedio e impresión de calificaciones.

## Análisis de Eficiencia Big O

El análisis de eficiencia Big O para el algoritmo implementado es el siguiente:

### 1. `Nota.java` (Interfaz `Nota`)
*   No tiene operaciones computacionales. Su complejidad es **O(1)**.

### 2. `NotaImpl.java` (Clase `NotaImpl`)
*   El constructor y los métodos `getValor()` y `getMateria()` realizan operaciones de asignación o retorno directo. Su complejidad es **O(1)**.

### 3. `CargadorNotas.java` (Interfaz `CargadorNotas`)
*   No tiene operaciones computacionales. Su complejidad es **O(1)**.

### 4. `CargadorNotasImpl.java` (Clase `CargadorNotasImpl`)
*   El constructor es **O(1)**.
*   El método `cargarNotas()`:
    *   Inicializa un `ArrayList` (**O(1)**).
    *   Contiene un bucle `while` que se ejecuta `n` veces, donde `n` es el número de notas ingresadas por el usuario.
    *   Dentro del bucle, las operaciones de `Scanner` y `notas.add()` son, en promedio, **O(1)**.
    *   Por lo tanto, la complejidad de `cargarNotas()` es **O(n)**.

### 5. `GestorNotas.java` (Clase `GestorNotas`)
*   El constructor es **O(1)**.
*   El método `calcularPromedio()`:
    *   Realiza una iteración lineal sobre la lista de `n` notas.
    *   Las operaciones dentro del bucle son **O(1)**.
    *   Por lo tanto, la complejidad de `calcularPromedio()` es **O(n)**.
*   El método `imprimirNotas()`:
    *   Realiza una iteración lineal sobre la lista de `n` notas.
    *   Las operaciones dentro del bucle son **O(1)**.
    *   Por lo tanto, la complejidad de `imprimirNotas()` es **O(n)**.

### 6. `Main.java` (Clase principal)
*   Las llamadas a `cargadorNotas.cargarNotas()`, `gestorNotas.imprimirNotas()` y `gestorNotas.calcularPromedio()` son las operaciones dominantes. Cada una tiene una complejidad de **O(n)**.
*   La complejidad total del método `main` es **O(n)**.

### Conclusión sobre la eficiencia:

El algoritmo es eficiente. La complejidad temporal dominante es **O(n)**, donde `n` es el número de notas. Esto significa que el tiempo de ejecución crece linealmente con el número de notas. Para las tareas de cargar, calcular el promedio e imprimir una lista de `n` elementos, una complejidad **O(n)** es óptima, ya que se requiere al menos una pasada por cada elemento para procesarlo.

### Componentes evaluados:

*   **Estructuras de datos:** El uso de `ArrayList` es apropiado y eficiente para esta tarea, ofreciendo adiciones y accesos en tiempo promedio constante.
*   **Operaciones de E/S:** Las operaciones de entrada (`Scanner`) y salida (`System.out.println`) contribuyen a la complejidad lineal general, ya que se realizan por cada nota.
*   **Algoritmos:** Los algoritmos implementados para la carga, cálculo del promedio y visualización son recorridos lineales simples, que son la forma más eficiente de realizar estas operaciones en una colección de datos.

### 🔑 Conceptos Clave para Dominar

1.  **Complejidad Lineal (O(n)):** Entender por qué las operaciones de procesamiento de listas son inherentemente lineales.
2.  **Eficiencia de `ArrayList`:** Reconocer que `add()` y `get()` son O(1) en promedio.
3.  **Optimización de E/S:** Aunque las operaciones de E/S son lentas en comparación con la CPU, su impacto en la complejidad Big O es lineal cuando se procesa cada elemento una vez.
4.  **Diseño Modular:** La separación de responsabilidades en interfaces y clases (`Nota`, `CargadorNotas`, `GestorNotas`) facilita el análisis y mantenimiento del código.