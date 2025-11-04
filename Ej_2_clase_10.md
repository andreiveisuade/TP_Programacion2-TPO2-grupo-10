# Ejercicio 2 de la Clase 10: Grafo con Matriz de Adyacencia

Este documento detalla la implementación de un TDA Grafo utilizando una **matriz de adyacencia** (implementación estática). Sobre esta estructura, se desarrollan dos métodos específicos: uno para calcular el mayor costo de las aristas salientes de un vértice y otro para encontrar todos los predecesores de un vértice.

## Explicación de Conceptos Teóricos

### 📊 **1. Matriz de Adyacencia**

Una **matriz de adyacencia** es una representación de grafos donde las relaciones entre vértices se almacenan en una matriz cuadrada `n x n`, donde `n` es el número de vértices.

- **`matriz[i][j] = 0`**: No hay arista del vértice `i` al vértice `j`.
- **`matriz[i][j] = peso`**: Hay una arista del vértice `i` al vértice `j` con un `peso` determinado.

**Ejemplo visual:**
```
Grafo:           Matriz (A=0, B=1, C=2):
  A → B (5)
  A → C (3)      A B C
  B → C (2)    A [0,5,3]
               B [0,0,2]
               C [0,0,0]
```

**Ventajas y Desventajas:**
- **Ventaja:** Verificar si existe una arista entre dos vértices es una operación muy rápida de **O(1)**.
- **Desventaja:** El espacio requerido es **O(n²)**, lo que la hace ineficiente para grafos con pocas aristas (grafos dispersos).

### 🗺️ **2. Mapeo de Vértices a Índices**

Como los vértices pueden ser cualquier objeto (Strings, etc.), y la matriz funciona con índices numéricos (0, 1, 2...), es necesario un mecanismo de traducción. En esta implementación, se utilizan dos estructuras para lograrlo:

1.  **`Map<E, Integer> verticeIndice`**: Un `HashMap` que permite obtener el índice de la matriz para un vértice dado en **O(1)** en promedio.
2.  **`List<E> indiceVertice`**: Un `ArrayList` que permite obtener el vértice a partir de un índice de la matriz en **O(1)**.

---

## 📈 Análisis de Eficiencia (Big O Notation)

Asumimos `n` como el número de vértices en el grafo.

### Complejidad de Operaciones Básicas

- **`agregarVertice(v)`**: **O(1)**. Añadir el vértice al mapa y a la lista es una operación de tiempo constante amortizado.
- **`eliminarVertice(v)`**: **O(n²)**. Aunque la implementación provista intenta optimizar moviendo el último elemento, la necesidad de reajustar la matriz y los índices sigue siendo costosa. Una implementación más simple requeriría reconstruir los mapeos, lo que es O(n).
- **`agregarArista(o, d, p)`**: **O(1)**. Se obtienen los índices de origen y destino en O(1) y se asigna el peso en la matriz, también en O(1).
- **`existeArista(o, d)`**: **O(1)**. La principal ventaja de la matriz de adyacencia.

### Complejidad de Métodos del Ejercicio

#### 1. **`mayorCostoAristasSalientes(vertice)`**
- **Algoritmo:**
  1. Obtener el índice `i` del `vértice` (O(1) gracias al `HashMap`).
  2. Recorrer toda la fila `i` de la matriz, que contiene todas las aristas salientes.
  3. Mantener un registro del peso máximo encontrado.
- **Complejidad:** El recorrido de la fila tiene `n` elementos. Por lo tanto, la complejidad es **O(n)**.

#### 2. **`predecesores(vertice)`**
- **Algoritmo:**
  1. Obtener el índice `j` del `vértice` (O(1)).
  2. Recorrer toda la columna `j` de la matriz. Una entrada `matriz[i][j]` con peso significa que el vértice `i` es un predecesor.
  3. Por cada arista encontrada en la columna, se añade el vértice correspondiente al índice `i` al conjunto de predecesores.
- **Complejidad:** El recorrido de la columna tiene `n` elementos. Por lo tanto, la complejidad es **O(n)**.

### Conclusión sobre la Eficiencia

La implementación con matriz de adyacencia es ideal para **grafos densos**, donde el número de aristas es cercano a n². En estos casos, la velocidad de **O(1)** para `existeArista` y `agregarArista` es una gran ventaja.

Para los métodos específicos del ejercicio, la complejidad **O(n)** es la esperada, ya que es necesario inspeccionar todas las posibles conexiones (una fila o una columna completa) para encontrar la información requerida.
