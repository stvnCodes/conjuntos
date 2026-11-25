# Práctica de conjuntos

Aplicación de consola en Java 21 que maneja conjuntos de enteros o textos, en vector y en lista ligada.

## Ejecutar en IntelliJ
1. Abrir la carpeta del proyecto y seleccionar un JDK 21.
2. Ejecutar `src/Main.java`.

## Ejecutar en terminal (macOS o Linux)
```bash
javac -d out $(find src -name '*.java')
java -cp out Main
```

Los elementos se escriben separados por comas. Primero se define el universal y luego A y B.

## Organización
- `src/classes`: Nodo y conjuntos.
- `src/menus`: interacción con el usuario.
- `src/utils`: lectura y validación.
- `documentacion`: documento de la práctica.
