# Guía personal de sustentación

Documento de estudio. No hace parte de la entrega.

## 1. El taller en un minuto

> "La aplicación maneja conjuntos de enteros o textos. Se implementan de dos formas, en vector y en lista ligada, y el usuario escoge cuál usar. Primero se define el universal, luego se forman A y B con elementos de él, y con ellos se hacen las nueve operaciones."

Un conjunto no tiene repetidos y su orden no importa.

**Avance:** programa terminado. Falta el PDF.

### Empezar desde cero

Piensa en un grupo de amigos: U son todos los del salón, A los que juegan fútbol y B los que tocan guitarra.

- Unión: quienes hacen al menos una de las dos cosas.
- Intersección: quienes hacen ambas.
- A - B: juegan fútbol pero no tocan guitarra.
- Diferencia simétrica: hacen solo una de las dos.
- Complemento de A: los del salón que no juegan fútbol.

El programa hace eso con datos. El **vector** es una fila de casillas numeradas y de tamaño fijo. La **lista** es una búsqueda del tesoro: cada nodo dice dónde está el siguiente.

### Orden para estudiar

1. `Nodo`: solo un dato y una liga.
2. `posicion` y `pertenece` en el vector. Casi todo se apoya en ellos.
3. `agregar`, `interseccion` y `diferencia`.
4. Lo que combina métodos: simétrica, complemento, igualdad y disjuntos.
5. Los mismos métodos en la lista: cambia el recorrido.
6. `Entrada`, los menús y `Main`.

## 2. Las operaciones

U = {1,2,3,4,5,6}, A = {1,2,3}, B = {3,4}

| Operación | Idea | Resultado |
|---|---|---|
| Unión | En A, en B o en ambos | {1,2,3,4} |
| Intersección | En los dos | {3} |
| A - B | En A pero no en B | {1,2} |
| A Δ B | (A - B) unión (B - A) | {1,2,4} |
| Complemento de A | U - A | {4,5,6} |
| Pertenencia | ¿2 está en A? | Sí |
| Subconjunto | ¿Todo A está en B? | No |
| Igualdad | ¿Mismos elementos? | No |
| Disjuntos | ¿Ninguno en común? | No |

## 3. ConjuntoEnVector

Regla principal: los elementos van seguidos y el primer `null` marca el final.

```text
n = 5
[10] [20] [30] [null] [null]     cantidad = 3
```

| Método | Qué hace | Costo |
|---|---|---|
| getN | Devuelve la capacidad | O(1) |
| cantidadDeElementos | Cuenta hasta el primer null | O(n) |
| esVacio | Pregunta si la cantidad es cero | O(n) |
| getDato | Valida la posición y devuelve el dato | O(n) por la validación |
| setDato | Reemplaza sin permitir repetidos | O(n) |
| posicion | Busca y devuelve el índice o -1 | O(n) |
| pertenece | Pregunta si posicion no es -1 | O(n) |
| agregar | Ignora repetidos y guarda al final | O(n) |
| borrar | Corre los siguientes a la izquierda | O(n) |
| vaciar | Crea un vector nuevo | O(n) |
| union | Copia A y agrega lo que falte de B | O((n+m)²) |
| interseccion, diferencia | Filtran A según pertenezca o no a B | O(n·(n+m)) |
| diferenciaSimetrica | Combina diferencia y union | O((n+m)²) |
| complemento | Universal menos este conjunto | Igual que diferencia |
| subconjunto, igualdad | Buscan cada elemento de A en B | O(n·m) |
| disjuntos | Mira si la intersección es vacía | Igual que interseccion |

Aquí n y m son las capacidades de A y B. En interseccion y diferencia se suma el costo de `agregar`, que revisa el resultado en cada inserción.

Al borrar el 20:

```text
Antes:    [10] [20] [30] [null] [null]
Después:  [10] [30] [null] [null] [null]
```

## 4. Preguntas probables

**¿Por qué dos clases?** Son dos implementaciones del mismo concepto y el enunciado pide ambas.

**¿Por qué el universal es static?** Pertenece a la clase y lo comparten todos los conjuntos. Así complemento no necesita parámetros.

**¿Por qué se copia el universal?** Para que sea independiente: si cambia el conjunto original, U no se altera.

**¿Por qué Object?** Permite guardar Integer o String con el mismo código.

**¿Por qué equals y no ==?** Con objetos, == compara si son el mismo en memoria; equals compara el contenido.

**¿Por qué la unión reserva |A| + |B|?** Es el peor caso: ningún elemento en común.

**¿Por qué igualdad compara cantidad y una sola inclusión?** No hay repetidos. Si tienen la misma cantidad y todo A está en B, a B no le sobra nada.

**¿Qué diferencia hay entre n y cantidadDeElementos?** n es cuánto cabe; cantidadDeElementos, cuánto hay. Es nuestra interpretación de la hoja: confirmar con los apuntes.

**¿Qué pasa si agrego un repetido?** Se ignora. Si el vector está lleno y el dato es nuevo, se lanza un error.

**¿El vacío es subconjunto de A?** Sí: no tiene ningún elemento que falte en A.

**¿Dónde hay POO?** Atributos privados (encapsulamiento), clases con una responsabilidad y operaciones que devuelven objetos nuevos.

**¿Qué método no estaba en las hojas y por qué?** disjuntos, porque lo pide el enunciado. También setUniversal, para definir el universal, y algunos métodos internos de apoyo.

## 5. ConjuntoEnLista

Cada `Nodo` tiene `dato` y `liga`. `cab` es el primero y el último tiene liga null.

```text
cab -> [10 | *] -> [20 | *] -> [30 | null]
```

Todo recorrido sigue el mismo patrón:

```java
Nodo actual = cab;
while (actual != null) {
  // usar actual.getDato()
  actual = actual.getLiga();
}
```

**Agregar:** si la lista está vacía, el nuevo nodo es cab. Si no, se enlaza después del último.

**Borrar:** se avanza con `anterior` y `actual`.

| Caso | Qué cambia |
|---|---|
| Cabeza | cab pasa al segundo |
| Medio o final | anterior se enlaza con la liga de actual |
| No está | Nada |

Costos (n y m son cantidades): esVacio, vaciar y getCab O(1); buscar, agregar, borrar y contar O(n); subconjunto, igualdad O(n·m); union O((n+m)²); interseccion, diferencia y disjuntos O(n·(n+m)). El complemento cuesta como una diferencia sobre U.

**¿Por qué no hay getN ni getDato(pos)?** La lista no tiene capacidad fija y la hoja no los pide.

**¿Por qué copiar crea nodos nuevos?** Si se compartieran, modificar el resultado dañaría el conjunto original.

**¿Por qué setLiga no es public?** Para que los enlaces solo se cambien desde el paquete classes.

**¿Qué pasa con el nodo borrado?** Nadie apunta a él y el recolector de basura de Java lo libera.

**¿Por qué anterior empieza en null?** La cabeza no tiene un nodo antes. Así se reconoce ese caso.

## 6. Vector contra lista

| | Vector | Lista |
|---|---|---|
| Tamaño | Fijo | Crece con cada nodo |
| Acceso por posición | Directo en el arreglo | Recorriendo |
| Buscar | O(n) | O(n) |
| Borrar | Corre elementos | Busca y cambia una liga |

## 7. Cómo se conecta el programa

```text
Main -> MenuVector o MenuLista -> ConjuntoEnVector o ConjuntoEnLista
                |
             Entrada (lee y valida)
```

Recorrido de una unión: el menú recibe la opción 1, llama `a.union(b)`, recibe un conjunto nuevo y lo muestra.

**¿Por qué el menú valida contra U y no agregar?** El universal también se construye con agregar; todavía no habría U contra qué comparar.

**¿Por qué A y B tienen capacidad |U|?** Están contenidos en U, así que nunca se desbordan.

**¿Por qué dos menús parecidos?** Cada uno usa el tipo de su representación y se conservan las firmas de las hojas. Una interfaz evitaría la repetición.

**¿Qué pasa con una entrada inválida?** Entrada muestra un mensaje y vuelve a preguntar.

**¿Ana y ana son iguales?** No: equals distingue mayúsculas.

**¿Por qué redefinir U pide A y B otra vez?** Podrían contener elementos que ya no están permitidos.

### Paquetes

- `classes`: conjuntos y Nodo. No leen del teclado.
- `menus`: preguntan y muestran resultados.
- `utils`: lectura y validación.

**¿Por qué separarlos?** Cada parte tiene una responsabilidad. Se puede cambiar el menú sin tocar las operaciones.

## 8. Ejercicio

U = {1,2,3,4,5}, A = {1,2}, B = {2,3}. Calcula A Δ B y el complemento de A. ¿Qué métodos se llaman?

**Respuesta:** A Δ B = {1,3}: A - B = {1}, B - A = {3} y se unen. Usa diferencia dos veces y union una vez. El complemento de A es {3,4,5} y usa diferencia sobre el universal.
