# Ejercicio 3 de la Clase 10: Grafo con Listas de Adyacencia

## Explicación de Conceptos Teóricos

### 📊 **1. Listas de Adyacencia (Implementación Dinámica)**

Una **lista de adyacencia** es una representación de grafos donde cada vértice mantiene una **lista enlazada** de sus vecinos (aristas salientes).

**Estructura:**
```
Lista Principal de Vértices:
┌─────┬─────┬─────┬─────┐
│  A  │  B  │  C  │  D  │
└──┬──┴──┬──┴──┬──┴──┬──┘
   │     │     │     │
   ↓     ↓     ↓     ↓
Lista de  Lista de  Lista de  Lista de
Aristas   Aristas   Aristas   Aristas
de A      de B      de C      de D
```

**Ejemplo visual:**
```
Grafo:
  A → B (peso 5)
  A → C (peso 3)
  B → D (peso 2)
  C → D (peso 4)

Representación:
A → [B,5] → [C,3] → null
B → [D,2] → null
C → [D,4] → null
D → null
```

---

### 🆚 **2. Matriz vs Listas de Adyacencia**

| Característica | Matriz | Listas |
|---------------|--------|--------|
| **Espacio** | O(n²) | **O(n + m)** |
| **Verificar arista** | O(1)* | O(k) |
| **Agregar arista** | O(1)* | **O(k)** |
| **Eliminar arista** | O(1)* | O(k) |
| **Recorrer vecinos** | O(n) | **O(k)** |
| **Mejor para** | Grafos densos | **Grafos dispersos** |

*Con matriz: O(n) si incluyes la búsqueda de índices  
k = grado del vértice (aristas salientes)  
n = cantidad de vértices  
m = cantidad de aristas

**¿Cuándo usar cada una?**

```
Grafo DENSO (muchas aristas):
m ≈ n² → Usar MATRIZ

Grafo DISPERSO (pocas aristas):
m << n² → Usar LISTAS ✓

Ejemplo:
- Red social grande: disperso (no todos son amigos de todos)
- Tablero de ajedrez: denso (cada casilla conecta con varias)
```

---

### 🎯 **3. Ejercicio 3.1: Vértices Aislados**

**Definición:** Un vértice es **aislado** si cumple AMBAS condiciones:
1. No tiene aristas **salientes** (grado de salida = 0)
2. No tiene aristas **entrantes** (grado de entrada = 0)

**Visualización:**
```
Grafo:
  A → B → C
  
  D  ← vértice aislado (no conecta con nada)
  
  E → F
  
Vértices aislados: {D}
```

**Algoritmo optimizado:**

```java
1. Crear dos conjuntos vacíos:
   - tienenSalientes (HashSet)
   - tienenEntrantes (HashSet)

2. Primera pasada - O(n):
   Para cada vértice v:
     Si v.adyacentes != null:
       tienenSalientes.add(v)

3. Segunda pasada - O(n×k):
   Para cada vértice v:
     Para cada arista a en v.adyacentes:
       tienenEntrantes.add(a.destino)

4. Tercera pasada - O(n):
   Para cada vértice v:
     Si v ∉ tienenSalientes Y v ∉ tienenEntrantes:
       aislados.add(v)

Total: O(n) + O(n×k) + O(n) = O(n×k)
```

**¿Por qué usar HashSet?**
- Inserción: O(1) promedio
- Búsqueda (contains): O(1) promedio
- Alternativa con listas: O(n) por búsqueda → ineficiente

---

### 🌉 **4. Ejercicio 3.2: Vértices Puente**

**Definición:** Un vértice `p` es **puente** entre `origen` y `destino` si:
1. Existe arista: `origen → p`
2. Existe arista: `p → destino`

Es decir, forma un **camino de longitud 2** entre origen y destino.

**Visualización:**
```
Grafo:
  A → B → D
  ↓   ↓
  C → D

Pregunta: ¿Qué vértices son puente entre A y D?

Caminos de longitud 2:
- A → B → D  ✓ (B es puente)
- A → C → D  ✓ (C es puente)

Respuesta: {B, C}
```

**Algoritmo optimizado:**

```java
1. Buscar nodoOrigen - O(n)
2. Si no existe → lanzar excepción

3. Verificar que destino existe - O(n)

4. puentes = HashSet vacío

5. Para cada arista a en nodoOrigen.adyacentes: - O(k₁)
     candidato = a.destino
     
     Si existe arista (candidato → destino): - O(n + k₂)
       puentes.add(candidato)

Total: O(n) + O(k₁ × (n + k₂))
```

**Complejidad en la práctica:**

```
Mejor caso (grafo disperso):
- k₁ = 2, k₂ = 2
- O(4) ≈ O(1)

Caso promedio:
- k₁ = k₂ = √n
- O(n) + O(√n × √n) = O(n)

Peor caso (grafo completo):
- k₁ = k₂ = n
- O(n) + O(n × n) = O(n²)
```

---

### ⚡ **5. Análisis Detallado de Complejidades**

**Operaciones básicas (con `HashMap` para vértices):**

| Operación | Matriz (peor caso) | Listas (promedio) | Explicación (Listas) |
|-----------|--------------------|-------------------|----------------------|
| `inicializar` | O(1) | O(1) | Inicialización de `HashMap` |
| `agregarVertice` | O(1) amortizado | **O(1)** | `containsKey` y `put` en `HashMap` |
| `eliminarVertice` | O(n²) | **O(n + m)** | Recorrer todas las aristas para eliminar entrantes (O(n*k)), luego `remove` de `HashMap` (O(1)) |
| `agregarArista` | O(1) | **O(k)** | `buscarVertice` (O(1)), recorrer lista de adyacencia (O(k)) |
| `eliminarArista` | O(1) | **O(k)** | `buscarVertice` (O(1)), recorrer lista de adyacencia (O(k)) |
| `existeArista` | O(1) | **O(k)** | `buscarVertice` (O(1)), recorrer lista de adyacencia (O(k)) |
| `pesoArista` | O(1) | **O(k)** | `buscarVertice` (O(1)), recorrer lista de adyacencia (O(k)) |
| `vertices` | O(n) | **O(n)** | `keySet()` y creación de `ArrayList` |

**Operaciones del ejercicio:**

| Operación | Complejidad (promedio) | Explicación |
|-----------|------------------------|-------------|
| `verticesAislados` | **O(n×k)** | 3 pasadas por vértices y sus adyacencias |
| `verticesPuente` | **O(k₁×k₂)** | Por cada vecino del origen (k₁), verificar si tiene arista al destino (k₂) |
| | **O(n²) peor caso** | Grafo completo |

*n = cantidad de vértices, m = cantidad de aristas, k = grado del vértice (aristas salientes), k₁ = grado de origen, k₂ = grado de candidato

---

### 💾 **6. Comparación de Espacio**

**Matriz de Adyacencia:**
```
Espacio = n² × sizeof(int)

Ejemplo: 1000 vértices, 5000 aristas
- Matriz: 1,000,000 × 4 bytes = 4 MB
- Solo 5000 celdas usadas → 99.5% desperdicio
```

**Listas de Adyacencia:**
```
Espacio = n × sizeof(NodoVertice) 
        + m × sizeof(NodoArista)

Ejemplo: 1000 vértices, 5000 aristas
- Vértices: 1000 × 24 bytes = 24 KB
- Aristas: 5000 × 32 bytes = 160 KB
- Total: ~184 KB ← ¡21× más eficiente!
```

---

### 🔍 **7. Técnicas de Optimización Aplicadas**

**1. Uso de HashSet en vez de List:**
```java
// ❌ INEFICIENTE - O(n) por búsqueda
List<E> tienenSalientes = new ArrayList<>();
if (tienenSalientes.contains(vertice)) { ... }

// ✅ EFICIENTE - O(1) por búsqueda
Set<E> tienenSalientes = new HashSet<>();
if (tienenSalientes.contains(vertice)) { ... }
```

**2. Minimizar recorridos:**
```java
// En verticesAislados: 3 pasadas O(n) cada una
// Total: O(3n) = O(n)
// 
// Alternativa menos eficiente: Para cada vértice,
// recorrer TODO el grafo buscando referencias
// Total: O(n²) ← Evitado
```

**3. Early return en búsquedas:**
```java
// En verticesPuente: apenas encontramos una arista
// al destino, agregamos y continuamos
if (existeArista(candidato, destino)) {
    puentes.add(candidato); // No seguir buscando
}
```

---

### 🎨 **8. Casos de Prueba del Código**

El programa crea este grafo:

```
    A → B → D → E → F
    ↓
    C → D

G (aislado)
H (aislado)
```

**Resultados esperados:**

```
1. Vértices aislados: {G, H}
   - No tienen aristas entrantes ni salientes

2. Puentes entre A y D:
   - B: porque A→B→D
   - C: porque A→C→D

3. Puentes entre A y E:
   - D: porque A→B→D→E (B no es puente directo)
   - D: porque A→C→D→E (C no es puente directo)
   
4. Puentes entre A y F:
   - E: porque necesita A→...→E→F
```

---

### 🚀 **9. Ventajas de las Listas de Adyacencia**

**✅ Ventajas:**
1. **Eficiencia espacial:** O(n + m) vs O(n²)
2. **Iteración eficiente:** Recorrer vecinos es O(k), no O(n)
3. **Escalabilidad:** Ideal para grafos grandes y dispersos
4. **Flexibilidad:** Fácil agregar información adicional a aristas

**❌ Desventajas:**
1. **Verificar arista lenta:** O(k) vs O(1)
2. **Buscar vértices:** O(n) sin estructura auxiliar
3. **Más compleja de implementar:** Doble lista enlazada

---

### 💡 **10. Mejoras Adicionales Posibles**

La implementación actual ya incorpora una optimización clave: el uso de un `HashMap` (`verticesMap`) para almacenar los vértices. Esto permite que la búsqueda de un vértice por su valor (`buscarVertice`) tenga una complejidad promedio de **O(1)**, en lugar de **O(n)** que tendría si se usara una lista simple. Esta mejora ya está reflejada en el análisis de complejidad detallado.

**Impacto de esta optimización:**
- Las operaciones que requieren buscar un vértice (como `agregarArista`, `eliminarArista`, `existeArista`, `pesoArista`, y las operaciones de `verticesPuente`) se benefician enormemente, reduciendo su dependencia de `n` a solo `k` (el grado del vértice) en promedio.

---

### 🎯 **11. Casos de Uso en el Mundo Real**

**Grafos dispersos (Listas de Adyacencia):**
- 🌐 Redes sociales (Facebook, LinkedIn)
- 🗺️ Mapas y rutas (Google Maps)
- 🔗 Web (enlaces entre páginas)
- 💻 Dependencias de paquetes (npm, pip)

**Ejemplo:**
```
Facebook: 2.9 mil millones de usuarios
Promedio: 338 amigos por usuario

Con matriz: 2,900,000,000² = 8.4×10¹⁸ bytes ← Imposible
Con listas: 2.9B × 338 = 980 mil millones de nodos ← Factible
```

---

### 🔑 **Conceptos Clave para Dominar**

1. **Listas son O(n + m) en espacio** - escalables
2. **Iteración de vecinos es O(k)** - muy eficiente
3. **HashSet para búsquedas O(1)** - optimización clave
4. **Aristas entrantes requieren recorrer todo** - limitación
5. **Tres pasadas son mejor que n²** - análisis de algoritmos
6. **k₁ × k₂ en práctica << n²** - complejidad amortizada
7. **Grafos dispersos = listas** - regla general

---

### 📊 **Resumen Comparativo Final**

```
┌────────────────────┬──────────┬──────────┐
│    Operación       │  Matriz  │  Listas  │
├────────────────────┼──────────┼──────────┤
│ Espacio            │  O(n²)   │ O(n + m) │ ← Listas ganan
│ Verificar arista   │  O(1)    │  O(k)    │ ← Matriz gana (pero O(k) es bueno para dispersos)
│ Recorrer vecinos   │  O(n)    │  O(k)    │ ← Listas ganan
│ Agregar vértice    │  O(1)    │  O(1)    │ ← Empate (promedio)
│ Agregar arista     │  O(1)    │  O(k)    │ ← Matriz gana (pero O(k) es bueno para dispersos)
│ Grafos dispersos   │  Malo    │  Ideal   │ ← Listas ganan
│ Grafos densos      │  Ideal   │  Malo    │ ← Matriz gana
└────────────────────┴──────────┴──────────┘

*n = cantidad de vértices, m = cantidad de aristas, k = grado del vértice (aristas salientes)
```
