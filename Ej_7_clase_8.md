# Ejercicio 7 de la Clase 8: Diccionario Múltiple con ABB (Versión Eficiente)

Este documento detalla la implementación de un Diccionario Múltiple eficiente, utilizando una estructura de datos compuesta que anida un Árbol AVL para las claves y Árboles Binarios de Búsqueda (ABB) para los valores. Se analiza la eficiencia de sus operaciones y los conceptos teóricos subyacentes.

## Explicación de Conceptos Teóricos

### 📚 **1. Tipo de Dato Abstracto (TDA)**

Un **TDA** es una especificación matemática de una estructura de datos que define:
- **QUÉ** operaciones se pueden realizar (la interfaz).
- **NO CÓMO** se implementan internamente (la implementación concreta).

**En este ejercicio:**
- `DictionaryTDA<K,V>`: Define las operaciones del diccionario (`put`, `get`, `remove`, etc.).
- `ABBTDA<E>`: Define las operaciones del Árbol Binario de Búsqueda.
- `AVLTDA<E>`: Define las operaciones del Árbol AVL, una versión auto-balanceable del ABB.

### 🌳 **2. Árboles de Búsqueda: ABB y AVL**

#### Árbol Binario de Búsqueda (ABB)
Un **ABB** es un árbol binario donde cada nodo cumple la **propiedad de ordenamiento**:
- Nodos en el subárbol izquierdo son menores que el nodo actual.
- Nodos en el subárbol derecho son mayores que el nodo actual.
**Complejidad:** Búsqueda, inserción y eliminación en **O(log n)** en promedio, pero **O(n)** en el peor caso (si el árbol se degenera en una lista).

#### Árbol AVL
Un **Árbol AVL** es un ABB auto-balanceable. Mantiene su altura balanceada mediante **rotaciones** después de cada inserción o eliminación.
- **Garantía:** La diferencia de altura entre los subárboles de cualquier nodo es como máximo 1.
- **Ventaja:** Asegura que las operaciones de búsqueda, inserción y eliminación siempre tengan una complejidad de **O(log n)**, incluso en el peor caso.

### 📖 **3. Diccionario Múltiple**

Un **diccionario múltiple** permite que una misma clave tenga **múltiples valores** asociados, a diferencia de un diccionario simple que solo permite un valor por clave.
```
Diccionario Simple:    "frutas" → 5
Diccionario Múltiple:  "frutas" → {5, 2, 8, 1, 9}
```

### 🔗 **4. Estructura de Datos Compuesta (Optimizado)**

Esta implementación mejorada anida dos estructuras de árbol para máxima eficiencia:

```
Dictionary (Árbol AVL)
│
├── Entry("frutas", ABB)
│   └── ABB: 1 → 2 → 5 → 8 → 9
│
├── Entry("letras", ABB)
│   └── ABB: 25 → 30
│
└── Entry("numeros", ABB)
    └── ABB: 10 → 15 → 20
```

1.  **Estructura Externa (Claves):** Un **Árbol AVL** almacena las `Entry` (pares clave-valor).
    -   **Propósito:** Gestionar las claves del diccionario.
    -   **Beneficio:** Permite encontrar, insertar o eliminar cualquier clave en tiempo logarítmico **O(log k)**, donde `k` es el número de claves.

2.  **Estructura Interna (Valores):** Un **ABB** individual gestiona los valores asociados a cada clave.
    -   **Propósito:** Almacenar y ordenar los múltiples valores de una clave.
    -   **Beneficio:** Permite buscar, insertar o eliminar un valor específico en tiempo logarítmico **O(log v)**, donde `v` es el número de valores para esa clave.

---

## 📈 Análisis de Eficiencia (Big O Notation)

La eficiencia de un algoritmo se mide por cómo escala su tiempo de ejecución o uso de memoria a medida que crece el tamaño de la entrada. La notación Big O describe el límite superior de esta complejidad.

### Complejidad de las Operaciones del Diccionario

Asumimos:
- **k**: número total de claves en el diccionario.
- **v**: número de valores asociados a una clave específica.

#### 1. **`put(clave, valor)` - Inserción**
- **Paso 1: Buscar la clave.** Se busca la `Entry` correspondiente a la `clave` en el árbol AVL externo.
  - Complejidad: **O(log k)**.
- **Paso 2: Insertar el valor.** Si la clave existe, se inserta el `valor` en el ABB interno.
  - Complejidad: **O(log v)**.
- **Paso 3 (si la clave no existe):** Crear un nuevo ABB e insertar la nueva `Entry` en el AVL.
  - Complejidad: **O(log k)**.

**Complejidad Total de `put`:** **O(log k + log v)**. Es una operación altamente eficiente.

#### 2. **`get(clave)` - Búsqueda de Valores**
- **Paso 1: Buscar la clave.** Se busca la `Entry` en el AVL externo.
  - Complejidad: **O(log k)**.
- **Paso 2: Devolver el ABB.** Se retorna la referencia al ABB de valores.
  - Complejidad: **O(1)**.

**Complejidad Total de `get`:** **O(log k)**. Muy eficiente.

#### 3. **`remove(clave)` - Eliminar Clave y todos sus Valores**
- **Paso 1: Eliminar la `Entry` del AVL.** La operación de eliminación en el AVL (incluyendo el rebalanceo) es logarítmica.
  - Complejidad: **O(log k)**.

**Complejidad Total de `remove(clave)`:** **O(log k)**.

#### 4. **`remove(clave, valor)` - Eliminar un Valor Específico**
- **Paso 1: Buscar la clave.** Se busca la `Entry` en el AVL.
  - Complejidad: **O(log k)**.
- **Paso 2: Eliminar el valor.** Se elimina el `valor` del ABB interno.
  - Complejidad: **O(log v)**.

**Complejidad Total de `remove(clave, valor)`:** **O(log k + log v)**.

### Conclusión sobre la Eficiencia

La elección de un **Árbol AVL** para gestionar las claves es la decisión de diseño clave que hace que esta implementación sea **altamente eficiente y escalable**. A diferencia de una lista enlazada (que tendría una complejidad de O(k) para la búsqueda de claves), el AVL garantiza un rendimiento logarítmico.

Esta estructura compuesta aprovecha lo mejor de ambos mundos:
- **Búsqueda rápida de claves** gracias al AVL.
- **Almacenamiento ordenado y búsqueda rápida de valores** gracias a los ABBs internos.

Algorítmicamente, esta solución es robusta y adecuada para manejar grandes volúmenes de datos con un rendimiento predecible.
