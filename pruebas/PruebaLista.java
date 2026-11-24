import classes.ConjuntoEnLista;

public class PruebaLista {
  private static int total = 0;

  public static void main(String[] args) {
    ConjuntoEnLista vacio = crear();
    ConjuntoEnLista a = crear(1, 2, 3);
    ConjuntoEnLista b = crear(3, 4);

    // El universal debe existir antes del complemento
    verificar("complemento sin universal", falla(() -> a.complemento()));
    ConjuntoEnLista u = crear(1, 2, 3, 4, 5, 6);
    ConjuntoEnLista.setUniversal(u);
    verificar("complemento", a.complemento().igualdad(crear(4, 5, 6)));
    verificar("complemento de U", u.complemento().esVacio());
    verificar("complemento de vacio", vacio.complemento().igualdad(u));
    u.borrar(6);
    verificar("U independiente", a.complemento().igualdad(crear(4, 5, 6)));

    // Operaciones
    verificar("union", a.union(b).igualdad(crear(4, 3, 2, 1)));
    verificar("union con vacio", a.union(vacio).igualdad(a));
    verificar("interseccion", a.interseccion(b).igualdad(crear(3)));
    verificar("diferencia", a.diferencia(b).igualdad(crear(1, 2)));
    verificar("diferencia inversa", b.diferencia(a).igualdad(crear(4)));
    verificar("simetrica", a.diferenciaSimetrica(b).igualdad(crear(1, 2, 4)));
    verificar("simetrica consigo", a.diferenciaSimetrica(a).esVacio());
    verificar("originales intactos", a.toString().equals("{1, 2, 3}") && b.toString().equals("{3, 4}"));

    // Consultas
    verificar("pertenece", a.pertenece(2) && !a.pertenece(9));
    verificar("numero no es texto", !a.pertenece("2"));
    verificar("subconjunto", crear(1, 3).subconjunto(a) && !a.subconjunto(b));
    verificar("vacio subconjunto", vacio.subconjunto(a) && !a.subconjunto(vacio));
    verificar("igualdad sin orden", a.igualdad(crear(3, 1, 2)));
    verificar("no iguales", !a.igualdad(crear(1, 2)) && !a.igualdad(crear(1, 2, 4)));
    verificar("disjuntos", a.disjuntos(crear(7, 8)) && !a.disjuntos(b) && vacio.disjuntos(a));
    verificar("textos", crear("ana", "luis").pertenece(new String("ana")));

    // Manejo de la lista
    ConjuntoEnLista c = crear(1, 2, 3, 4);
    c.agregar(2);
    verificar("repetido ignorado", c.toString().equals("{1, 2, 3, 4}"));
    verificar("null", falla(() -> c.agregar(null)));
    verificar("posicion", c.posicion(1) == 0 && c.posicion(4) == 3 && c.posicion(9) == -1);
    c.borrar(99);
    verificar("borrar inexistente", c.cantidadDeElementos() == 4);
    c.borrar(1);
    verificar("borrar cabeza", c.toString().equals("{2, 3, 4}") && c.getCab().getDato().equals(2));
    c.borrar(3);
    verificar("borrar medio", c.toString().equals("{2, 4}"));
    c.borrar(4);
    verificar("borrar final", c.toString().equals("{2}") && c.getCab().getLiga() == null);
    c.borrar(2);
    verificar("borrar unico", c.esVacio() && c.getCab() == null && c.toString().equals("{}"));
    c.borrar(2);
    verificar("borrar en vacio", c.esVacio());
    c.agregar(7);
    verificar("agregar tras vaciar", c.toString().equals("{7}"));
    c.vaciar();
    verificar("vaciar", c.esVacio());

    // La copia de la union no comparte nodos con A
    ConjuntoEnLista d = a.union(vacio);
    d.borrar(2);
    d.agregar(50);
    verificar("nodos independientes", a.toString().equals("{1, 2, 3}"));

    System.out.println(total + " verificaciones correctas");
  }

  private static ConjuntoEnLista crear(Object... datos) {
    ConjuntoEnLista conjunto = new ConjuntoEnLista();
    for (Object dato : datos) {
      conjunto.agregar(dato);
    }
    return conjunto;
  }

  private static void verificar(String nombre, boolean correcto) {
    if (!correcto) {
      throw new AssertionError("Fallo: " + nombre);
    }
    total++;
  }

  // Solo cuentan los errores que la clase lanza a proposito
  private static boolean falla(Runnable accion) {
    try {
      accion.run();
      return false;
    } catch (IllegalStateException | IllegalArgumentException error) {
      return true;
    }
  }
}
