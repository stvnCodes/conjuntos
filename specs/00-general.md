# 00. General

| Spec | Contenido | Estado |
|---|---|---|
| 01 | ConjuntoEnVector | Implementada |
| 02 | Nodo y ConjuntoEnLista | Implementada |
| 03 | Menús, entrada y universal | Implementada |
| 04 | Guía de sustentación y PDF | Falta completar portada y revisar conclusiones |

## Reglas comunes
- Sin repetidos; el orden no importa.
- Las operaciones entre conjuntos devuelven uno nuevo y no modifican los originales.
- `a.subconjunto(b)` significa que todo A está en B. El vacío es subconjunto de todos.
- `diferencia` es A - B. El complemento es U - A.
- Sin universal definido, `complemento` lanza un error controlado.
- Agregar un repetido o borrar algo inexistente no hace nada.

## Arquitectura

| Ubicación | Responsabilidad |
|---|---|
| `src/Main.java` | Arrancar y elegir la representación |
| `src/classes` | Nodo y conjuntos: datos y operaciones |
| `src/menus` | Interacción con el usuario |
| `src/utils` | Lectura y validación de entradas |
| `pruebas` | Verificaciones de los conjuntos |

`classes` no depende de menús ni de lectura. Los menús usan `classes` y `utils`.
