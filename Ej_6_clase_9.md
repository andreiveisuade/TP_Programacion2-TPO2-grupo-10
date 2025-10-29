# Ejercicio 6 de la Clase 9: AVL Interactivo

Este documento explora la implementación de un Árbol AVL (Adelson-Velskii y Landis) interactivo. Se detalla su funcionamiento, las operaciones clave como la inserción con auto-balanceo mediante rotaciones, y el análisis de su eficiencia.

## Explicación de Conceptos Teóricos

### 🌳 **1. ¿Qué es un Árbol AVL?**

Un **AVL** (Adelson-Velskii y Landis) es un **Árbol Binario de Búsqueda auto-balanceable**. Esto significa que:

- Mantiene todas las propiedades de un ABB
- **Se rebalancea automáticamente** después de cada inserción/eliminación
- Garantiza que la altura del árbol sea siempre **O(log n)**

**¿Por qué es importante?**

```
ABB Normal (degenerado):    ABB Balanceado (AVL):
    1                            4
     \                          / \
      2                        2   6
       \                      / \ / \
        3                    1  3 5  7
         \
          4                Altura: 3
           \               Búsqueda: O(log n)
            5
             \
              6
               \
                7

Altura: 7
Búsqueda: O(n) ← ¡Como una lista!
```

---

### ⚖️ **2. Factor de Balance (FB)**

Es el **concepto central** de los árboles AVL:

```java
FB(nodo) = Altura(Subárbol Izquierdo) - Altura(Subárbol Derecho)
```

**Regla de oro del AVL:**
```
Un árbol está balanceado si TODOS sus nodos tienen:
FB ∈ {-1, 0, 1}
```

**Interpretación:**
- **FB = 0**: Subárboles de igual altura (perfectamente balanceado)
- **FB = 1**: Subárbol izquierdo es 1 nivel más alto
- **FB = -1**: Subárbol derecho es 1 nivel más alto
- **FB = 2 o FB = -2**: ¡DESBALANCEADO! → Necesita rotación

**Ejemplo visual:**
```
      10 (FB=1)
     /  \
    5    15 (FB=0)
   / \     \
  3   7     20
(FB=0)(FB=0) (FB=0)

Este árbol está balanceado ✓
```

---

### 🔄 **3. Las 4 Rotaciones del AVL**

Cuando un nodo queda con **FB = 2 o FB = -2**, debemos rotarlo. Hay 4 casos:

#### **CASO 1: Rotación Simple a la Derecha (Izquierda-Izquierda)**

**Cuándo:** Inserción en el subárbol izquierdo del hijo izquierdo

```
Antes:              Después:
    30 (FB=2)           20
   /                   /  \
  20 (FB=1)           10   30
 /
10

Código: rotateRight(30)
```

#### **CASO 2: Rotación Simple a la Izquierda (Derecha-Derecha)**

**Cuándo:** Inserción en el subárbol derecho del hijo derecho

```
Antes:              Después:
  10 (FB=-2)            20
    \                  /  \
    20 (FB=-1)        10   30
      \
      30

Código: rotateLeft(10)
```

#### **CASO 3: Rotación Doble Izquierda-Derecha**

**Cuándo:** Inserción en el subárbol derecho del hijo izquierdo

```
Antes:              Paso 1:            Paso 2:
    30                 30                 20
   /                  /                  /  \
  10                 20                 10   30
    \               /
    20             10

Código: rotateLeft(10), luego rotateRight(30)
```

#### **CASO 4: Rotación Doble Derecha-Izquierda**

**Cuándo:** Inserción en el subárbol izquierdo del hijo derecho

```
Antes:              Paso 1:            Paso 2:
  10                  10                  20
    \                   \                /  \
    30                  20              10   30
   /                      \
  20                      30

Código: rotateRight(30), luego rotateLeft(10)
```

---

### 🎯 **4. Algoritmo de Inserción AVL**

El proceso tiene **4 pasos clave:**

```java
1. INSERTAR como en un ABB normal (recursivamente)
   └─> Buscar la posición correcta según el orden

2. ACTUALIZAR la altura del nodo ancestro
   └─> altura = 1 + max(altura(izq), altura(der))

3. CALCULAR el Factor de Balance
   └─> FB = altura(izq) - altura(der)

4. SI está DESBALANCEADO (FB > 1 o FB < -1):
   └─> Aplicar la rotación correspondiente
```

**Ejemplo paso a paso:**

```
Insertar: 10, 20, 30

Paso 1: Insertar 10
    10

Paso 2: Insertar 20
    10
      \
      20

Paso 3: Insertar 30
    10 (FB=-2) ← ¡DESBALANCEADO!
      \
      20 (FB=-1)
        \
        30

    Caso: Derecha-Derecha → Rotación Simple Izquierda

Resultado:
      20
     /  \
    10   30
```

---

### 📊 **5. Recorrido por Niveles (BFS - Breadth-First Search)**

Es un recorrido que visita el árbol **nivel por nivel**, de izquierda a derecha.

**Algoritmo con Cola:**
```java
1. Encolar la raíz
2. Mientras la cola no esté vacía:
   a. Desencolar un nodo
   b. Procesar el nodo (imprimirlo)
   c. Encolar sus hijos (izquierdo y derecho)
```

**Ejemplo:**
```
Árbol:
      20
     /  \
    10   30
   /    /  \
  5    25   35

Resultado: Nivel 0: 20
           Nivel 1: 10 30
           Nivel 2: 5 25 35
```

**¿Por qué usar una Cola?**
- La cola garantiza el orden FIFO (First-In, First-Out)
- Esto asegura que procesemos todos los nodos de un nivel antes de pasar al siguiente

---

### 📈 **6. Altura de un Nodo**

La **altura** es la distancia más larga desde ese nodo hasta una hoja:

```
Árbol:
        20 (altura=3)
       /  \
      10   30 (altura=2)
     /    /  \
    5    25   35 (altura=1)

Hojas tienen altura = 1
Nodos null tienen altura = 0
```

**Fórmula recursiva:**
```java
altura(nodo) = 1 + max(altura(izquierdo), altura(derecho))
```

---

### 🔍 **7. Recorrido In-Order (Para Arreglo Ordenado)**

El recorrido **In-Order** (Izquierda → Raíz → Derecha) en un ABB/AVL **siempre devuelve los elementos ordenados**.

```
Árbol:
      20
     /  \
    10   30
   /    /  \
  5    25   35

In-Order: 5, 10, 20, 25, 30, 35 ← ¡Ordenados!
```

**¿Por qué funciona?**
- Visitamos primero todo lo menor (subárbol izquierdo)
- Luego el nodo actual
- Finalmente todo lo mayor (subárbol derecho)

---

### 💡 8. Análisis de Eficiencia (Big O Notation)

La notación Big O se utiliza para describir el rendimiento de un algoritmo y cómo escala a medida que aumenta el tamaño de la entrada (en este caso, el número de nodos `n` en el árbol).

#### Complejidad de las Operaciones del AVL

La principal ventaja de un árbol AVL sobre un ABB simple es su garantía de rendimiento.

| Operación | ABB (Peor Caso) | AVL (Peor y Mejor Caso) |
|:---|:---|:---|
| Búsqueda | O(n) | **O(log n)** |
| Inserción | O(n) | **O(log n)** |
| Eliminación | O(n) | **O(log n)** |

**Justificación de O(log n):**
- Un árbol AVL se mantiene **balanceado**, lo que significa que su altura `h` es siempre proporcional al logaritmo del número de nodos (`h ≈ log₂(n)`).
- Las operaciones de búsqueda, inserción y eliminación implican recorrer el árbol desde la raíz hasta un nodo, lo que equivale a la altura del árbol.
- Las **rotaciones** que mantienen el balance se realizan en tiempo constante, **O(1)**, por lo que no alteran la complejidad logarítmica general de la inserción o eliminación.

#### Análisis de las Partes del Programa

Analicemos la eficiencia de cada etapa del programa principal, asumiendo que se insertan `n` elementos en el árbol.

1.  **Construcción del Árbol (Bucle de Inserción)**
    - El programa lee `n` números y los inserta uno por uno en el AVL.
    - Cada operación `avl.insert()` tiene una complejidad de **O(log i)**, donde `i` es el número de nodos en el árbol en ese momento.
    - La complejidad total para insertar `n` elementos es la suma de `log(1) + log(2) + ... + log(n)`, lo que se aproxima a **O(n log n)**.

2.  **`printByLevels()` - Recorrido por Niveles**
    - Este método visita cada nodo y cada arista del árbol una sola vez, utilizando una cola para gestionar el orden.
    - Es una implementación del algoritmo **Breadth-First Search (BFS)**.
    - Complejidad: **O(n)**, ya que debe procesar los `n` nodos del árbol.

3.  **`getOrderedArray()` - Generación de Arreglo Ordenado**
    - **Paso 1: Recorrido In-Order.** El método `collectInOrder` realiza un recorrido in-order para visitar cada nodo y añadir su valor a un `ArrayList`.
      - Complejidad: **O(n)**, porque visita cada nodo una vez.
    - **Paso 2: Copia al arreglo.** Se crea un `int[]` y se copian los `n` elementos desde el `ArrayList`.
      - Complejidad: **O(n)**.

    **Complejidad Total de `getOrderedArray`:** **O(n)**.

### Conclusión sobre la Eficiencia

El algoritmo es **altamente eficiente** para la tarea solicitada. La elección de un árbol AVL es la correcta, ya que previene el peor escenario de un ABB degenerado y garantiza un rendimiento logarítmico para las inserciones.

- La fase más costosa es la **construcción del árbol (O(n log n))**.
- Las fases de procesamiento y visualización (`printByLevels`, `getOrderedArray`) son lineales **(O(n))**, lo cual es óptimo, ya que es necesario visitar cada elemento al menos una vez para mostrarlo o copiarlo.

En resumen, la solución es robusta, escalable y no presenta cuellos de botella algorítmicos significativos.

---

### 🎨 **9. Visualización del Código**

**Entrada del usuario:**
```
Ingrese: 50, 25, 75, 10, 30, 60, 80
```

**Proceso interno:**
```
Insertar 50:           50

Insertar 25:           50
                      /
                     25

Insertar 75:           50
                      /  \
                     25   75

Insertar 10:           50
                      /  \
                     25   75
                    /
                   10

... y así sucesivamente, rebalanceando cuando sea necesario
```

**Salidas del programa:**
1. **Por niveles:** Muestra el árbol piso por piso con sus factores de balance
2. **Visualización gráfica:** Estructura tipo árbol en ASCII
3. **Arreglo ordenado:** Resultado del recorrido in-order

---

### 🔑 **Conceptos Clave para Dominar**

1. **Factor de Balance:** Es el corazón del AVL
2. **Las 4 rotaciones:** Debes reconocer cuándo aplicar cada una
3. **Actualización de alturas:** Crucial después de cada inserción
4. **Recursividad:** Los AVL se implementan naturalmente de forma recursiva
5. **BFS con Cola:** Técnica fundamental para recorridos por nivel
6. **In-Order = Ordenado:** Propiedad fundamental de los ABB/AVL

---

Esta implementación incluye:
- ✅ Inserción con auto-balanceo
- ✅ Las 4 rotaciones implementadas
- ✅ Visualización por niveles
- ✅ Visualización gráfica del árbol
- ✅ Generación de arreglo ordenado
- ✅ Entrada interactiva del usuario
- ✅ Información detallada (altura, factor de balance)
