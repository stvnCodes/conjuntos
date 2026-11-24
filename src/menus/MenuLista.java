package menus;

import classes.ConjuntoEnLista;
import utils.Entrada;

import java.io.IOException;

public class MenuLista {
  // ------------------- Atributos ------------------- //
  private Entrada entrada;
  private boolean esEntero;
  private ConjuntoEnLista universal;
  private ConjuntoEnLista a;
  private ConjuntoEnLista b;

  // ------------------- Constructores ------------------- //
  public MenuLista(Entrada entrada) {
    this.entrada = entrada;
  }

  // ------------------- Flujo principal ------------------- //
  public void ejecutar() throws IOException {
    System.out.println("\nTipo de dato: 1. Enteros   2. Texto");
    esEntero = entrada.leerOpcion("Opción: ", 1, 2) == 1;
    definirConjuntos();
    int opcion;
    do {
      mostrarMenu();
      opcion = entrada.leerOpcion("Opción: ", 0, 12);
      procesar(opcion);
    } while (opcion != 0);
  }

  private void mostrarMenu() {
    System.out.println("\n--- Conjuntos en lista ligada ---");
    System.out.println("1. Unión");
    System.out.println("2. Intersección");
    System.out.println("3. Diferencia");
    System.out.println("4. Diferencia simétrica");
    System.out.println("5. Complemento");
    System.out.println("6. Pertenencia");
    System.out.println("7. Subconjunto");
    System.out.println("8. Igualdad");
    System.out.println("9. Conjuntos disjuntos");
    System.out.println("10. Mostrar conjuntos");
    System.out.println("11. Modificar A o B");
    System.out.println("12. Redefinir universal, A y B");
    System.out.println("0. Volver");
  }

  private void procesar(int opcion) throws IOException {
    switch (opcion) {
      case 1:
        mostrar("Unión de A y B", a.union(b));
        break;
      case 2:
        mostrar("Intersección de A y B", a.interseccion(b));
        break;
      case 3:
        mostrar("A - B", a.diferencia(b));
        mostrar("B - A", b.diferencia(a));
        break;
      case 4:
        mostrar("Diferencia simétrica de A y B", a.diferenciaSimetrica(b));
        break;
      case 5:
        mostrar("Complemento de A", a.complemento());
        mostrar("Complemento de B", b.complemento());
        break;
      case 6:
        consultarPertenencia();
        break;
      case 7:
        responder("A es subconjunto de B", a.subconjunto(b));
        responder("B es subconjunto de A", b.subconjunto(a));
        break;
      case 8:
        responder("A y B son iguales", a.igualdad(b));
        break;
      case 9:
        responder("A y B son disjuntos", a.disjuntos(b));
        break;
      case 10:
        mostrarConjuntos();
        break;
      case 11:
        modificar();
        break;
      case 12:
        definirConjuntos();
        break;
    }
  }

  // ------------------- Definición de conjuntos ------------------- //
  // El universal va primero porque A y B salen de él
  private void definirConjuntos() throws IOException {
    System.out.println("\nSepare los elementos con comas. Para un conjunto vacío, presione Enter.");
    definirUniversal();
    a = leerConjunto("A");
    b = leerConjunto("B");
    mostrarConjuntos();
  }

  private void definirUniversal() throws IOException {
    Object[] datos = entrada.leerElementos("Universal: ", esEntero);
    while (datos.length == 0) {
      System.out.println("El universal no puede estar vacío.");
      datos = entrada.leerElementos("Universal: ", esEntero);
    }
    universal = new ConjuntoEnLista();
    for (Object dato : datos) {
      universal.agregar(dato);
    }
    ConjuntoEnLista.setUniversal(universal);
  }

  private ConjuntoEnLista leerConjunto(String nombre) throws IOException {
    ConjuntoEnLista conjunto = new ConjuntoEnLista();
    Object[] datos = entrada.leerElementos("Conjunto " + nombre + ": ", esEntero);
    for (Object dato : datos) {
      agregarValidado(conjunto, dato);
    }
    return conjunto;
  }

  private void agregarValidado(ConjuntoEnLista conjunto, Object dato) {
    if (!universal.pertenece(dato)) {
      System.out.println(dato + " no está en el universal, no se agrega.");
    } else if (conjunto.pertenece(dato)) {
      System.out.println(dato + " ya está en el conjunto, no se repite.");
    } else {
      conjunto.agregar(dato);
    }
  }

  // ------------------- Opciones del menú ------------------- //
  private void consultarPertenencia() throws IOException {
    ConjuntoEnLista conjunto = elegirConjunto();
    Object elemento = entrada.leerElemento("Elemento: ", esEntero);
    responder(elemento + " pertenece al conjunto", conjunto.pertenece(elemento));
  }

  private void modificar() throws IOException {
    ConjuntoEnLista conjunto = elegirConjunto();
    System.out.println("1. Agregar   2. Borrar   3. Vaciar");
    int opcion = entrada.leerOpcion("Opción: ", 1, 3);
    if (opcion == 1) {
      agregarValidado(conjunto, entrada.leerElemento("Elemento: ", esEntero));
    } else if (opcion == 2) {
      borrarValidado(conjunto, entrada.leerElemento("Elemento: ", esEntero));
    } else {
      conjunto.vaciar();
    }
    mostrarConjuntos();
  }

  private void borrarValidado(ConjuntoEnLista conjunto, Object dato) {
    if (conjunto.pertenece(dato)) {
      conjunto.borrar(dato);
    } else {
      System.out.println(dato + " no está en el conjunto.");
    }
  }

  private ConjuntoEnLista elegirConjunto() throws IOException {
    int opcion = entrada.leerOpcion("Conjunto (1. A   2. B): ", 1, 2);
    return opcion == 1 ? a : b;
  }

  // ------------------- Presentación ------------------- //
  private void mostrarConjuntos() {
    mostrar("Universal", universal);
    mostrar("A", a);
    mostrar("B", b);
  }

  private void mostrar(String nombre, ConjuntoEnLista conjunto) {
    System.out.print(nombre + " = ");
    conjunto.mostrar();
  }

  private void responder(String afirmacion, boolean resultado) {
    System.out.println(afirmacion + ": " + (resultado ? "Sí" : "No"));
  }
}
