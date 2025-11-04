# Ejercicio 7 de la Clase 8: Diccionario Múltiple con ABB

Este documento detalla la implementación de un Diccionario Múltiple utilizando una estructura de datos compuesta que anida un Árbol Binario de Búsqueda (ABB) para las claves y otro ABB para los valores. Se analiza la eficiencia de sus operaciones y los conceptos teóricos subyacentes.

## Explicación de Conceptos Teóricos

### 📚 **1. Tipo de Dato Abstracto (TDA)**

Un **TDA** es una especificación matemática de una estructura de datos que define:
- **QUÉ** operaciones se pueden realizar (la interfaz).
- **NO CÓMO** se implementan internamente (la implementación concreta).

**En este ejercicio:**
- `DictionaryTDA<K,V>`: Define las operaciones del diccionario (`put`, `get`, `remove`, etc.).
- `ABBTDA<E>`: Define las operaciones del Árbol Binario de Búsqueda.

### 🌳 **2. Árbol Binario de Búsqueda (ABB)**

Un **ABB** es un árbol binario donde cada nodo cumple la **propiedad de ordenamiento**:
- Nodos en el subárbol izquierdo son menores que el nodo actual.
- Nodos en el subárbol derecho son mayores que el nodo actual.

**Complejidad:**
- **Caso Promedio:** Búsqueda, inserción y eliminación en **O(log n)**.
- **Peor Caso:** Si el árbol se degenera en una lista (por inserción de elementos ordenados), la complejidad se convierte en **O(n)**.

### 📖 **3. Diccionario Múltiple**

Un **diccionario múltiple** permite que una misma clave tenga **múltiples valores** asociados, a diferencia de un diccionario simple que solo permite un valor por clave.
```
Diccionario Simple:    "frutas" → 5
Diccionario Múltiple:  "frutas" → {5, 2, 8, 1, 9}
```

### 🔗 **4. Estructura de Datos Compuesta (Anidada)**

Esta implementación anida dos ABB para gestionar el diccionario:

```
Dictionary (Árbol ABB)
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

1.  **Estructura Externa (Claves):** Un **ABB** almacena las `Entry` (pares clave-valor).
    -   **Propósito:** Gestionar las claves del diccionario.
    -   **Riesgo:** Si las claves se insertan en orden, el árbol puede degenerar, afectando el rendimiento.

2.  **Estructura Interna (Valores):** Un **ABB** individual gestiona los valores asociados a cada clave.
    -   **Propósito:** Almacenar y ordenar los múltiples valores de una clave.

---

## 📈 Análisis de Eficiencia (Big O Notation)

La eficiencia de un algoritmo se mide por cómo escala su tiempo de ejecución a medida que crece el tamaño de la entrada. La notación Big O describe el límite superior de esta complejidad.

### Complejidad de las Operaciones del Diccionario

Asumimos:
- **k**: número total de claves en el diccionario.
- **v**: número de valores asociados a una clave específica.

#### 1. **`put(clave, valor)` - Inserción**
- **Paso 1: Buscar la clave.** Se busca la `Entry` en el ABB externo.
  - Complejidad: **O(log k)** promedio / **O(k)** peor caso.
- **Paso 2: Insertar el valor.** Si la clave existe, se inserta el `valor` en el ABB interno.
  - Complejidad: **O(log v)** promedio / **O(v)** peor caso.

**Complejidad Total de `put`:**
- **Promedio:** O(log k + log v)
- **Peor Caso:** O(k + v)

#### 2. **`get(clave)` - Búsqueda de Valores**
- **Paso 1: Buscar la clave.** Se busca la `Entry` en el ABB externo.
  - Complejidad: **O(log k)** promedio / **O(k)** peor caso.
- **Paso 2: Devolver el ABB.** Se retorna la referencia al ABB de valores.
  - Complejidad: **O(1)**.

**Complejidad Total de `get`:**
- **Promedio:** O(log k)
- **Peor Caso:** O(k)

#### 3. **`remove(clave)` - Eliminar Clave y todos sus Valores**
- **Paso 1: Eliminar la `Entry` del ABB externo.**
  - Complejidad: **O(log k)** promedio / **O(k)** peor caso.

**Complejidad Total de `remove(clave)`:**
- **Promedio:** O(log k)
- **Peor Caso:** O(k)

#### 4. **`remove(clave, valor)` - Eliminar un Valor Específico**
- **Paso 1: Buscar la clave.** Se busca la `Entry` en el ABB externo.
  - Complejidad: **O(log k)** promedio / **O(k)** peor caso.
- **Paso 2: Eliminar el valor.** Se elimina el `valor` del ABB interno.
  - Complejidad: **O(log v)** promedio / **O(v)** peor caso.

**Complejidad Total de `remove(clave, valor)`:**
- **Promedio:** O(log k + log v)
- **Peor Caso:** O(k + v)

### Conclusión sobre la Eficiencia

La elección de un **ABB** para gestionar las claves cumple con la consigna y ofrece un buen rendimiento en el **caso promedio**. Sin embargo, es crucial entender que **no garantiza la eficiencia en el peor caso**. Si las claves del diccionario se insertan en un orden secuencial (por ejemplo, "A", "B", "C", ...), el ABB externo se degenerará en una lista enlazada, y la complejidad de las operaciones sobre las claves se degradará a **O(k)**.

Para una implementación robusta que garantice un rendimiento logarítmico en todos los escenarios, la estructura externa debería ser un árbol auto-balanceable como un **Árbol AVL**.