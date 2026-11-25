# Práctica de conjuntos (Algoritmos y Programación 4)

Aplicación Java 21 en IntelliJ, sin dependencias. Enunciado en `../CONJUNTOS/`. El detalle está en `specs/`.

## Reglas de trabajo
- Explicar a fondo cada paso: el código se va a sustentar.
- Avanzar por specs, en orden. Explicar y esperar visto bueno antes de pasar a la siguiente.
- Al terminar cada spec, actualizar `sustentacion/GUIA.md`. Es personal: no se entrega ni se sube a git.
- Git lo maneja Estiven: no hacer commits, ramas ni push.
- Comentarios breves, naturales y sin emojis. Código sencillo, sin enredos.
- Estructuras hechas a mano: no usar ArrayList, LinkedList, HashSet ni similares.
- Respetar los métodos de las hojas del profesor. Identificadores sin tildes y en camelCase.
- Sangría de 2 espacios y secciones como en el proyecto `../arboles-narios`.
- Respetar la separación por paquetes descrita en `specs/00-general.md`.

## Decisiones
- Las dos clases son obligatorias; el usuario elige la representación al ejecutar.
- El universal se define primero y es independiente. A y B se forman con elementos de U.
- `tipodato` es `Object` (Integer o String), comparado con `equals`.
- `n` es la capacidad del vector (interpretación nuestra: la hoja no lo aclara).
- `disjuntos` se agrega porque lo pide el enunciado y falta en las hojas.

## Pruebas
`javac -d out/pruebas src/classes/*.java pruebas/*.java && java -cp out/pruebas PruebaVector && java -cp out/pruebas PruebaLista`
