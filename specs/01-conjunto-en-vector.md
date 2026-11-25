# 01. ConjuntoEnVector

Atributos: `n` (capacidad), `vec` (elementos) y `universal` (estático, uno para todos).

Regla interna: los elementos van seguidos desde la posición 0, sin huecos. El primer `null` marca el final.

| Método | Comportamiento |
|---|---|
| getN, cantidadDeElementos | Capacidad, y casillas ocupadas |
| getDato, setDato | Solo en posiciones ocupadas; setDato no permite repetir |
| agregar | Ignora repetidos; error si está lleno o el dato es null |
| borrar | Corre los elementos para no dejar huecos |
| posicion, pertenece | Índice o -1, y verdadero o falso |
| esVacio, vaciar, mostrar | Consulta, limpieza e impresión |
| union, interseccion, diferencia, diferenciaSimetrica, complemento | Conjunto nuevo |
| subconjunto, igualdad, disjuntos | Verdadero o falso |
| setUniversal | Guarda una copia, independiente del conjunto recibido |

## Aceptación
Pasa `pruebas/PruebaVector.java`: ejemplos de clase, vacíos, repetidos, textos, vector lleno y originales intactos.
