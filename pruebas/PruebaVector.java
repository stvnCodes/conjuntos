import classes.ConjuntoEnVector;

public class PruebaVector {
  private static int total = 0;

  public static void main(String[] args) {
    ConjuntoEnVector vacio = crear();
    ConjuntoEnVector a = crear(1, 2, 3);
    ConjuntoEnVector b = crear(3, 4);

    // El universal debe existir antes del complemento
    verificar("complemento sin universal", falla(() -> a.complemento()));
    ConjuntoEnVector u = crear(1, 2, 3, 4, 5, 6);
    ConjuntoEnVector.setUniversal(u);
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

    // Manejo del vector
    ConjuntoEnVector c = crear(1, 2, 3);
    c.agregar(2);
    verificar("repetido ignorado aun lleno", c.cantidadDeElementos() == 3);
    verificar("lleno", falla(() -> c.agregar(4)));
    verificar("null", falla(() -> c.agregar(null)));
    verificar("setDato repetido", falla(() -> c.setDato(0, 3)));
    c.setDato(0, 1);
    c.setDato(0, 10);
    verificar("setDato", c.getDato(0).equals(10));
    verificar("posicion invalida", falla(() -> c.getDato(-1)) && falla(() -> c.getDato(3)));
    c.borrar(99);
    c.borrar(2);
    verificar("borrar medio", c.toString().equals("{10, 3}") && c.posicion(3) == 1 && c.getN() == 3);
    c.borrar(3);
    c.borrar(10);
    verificar("borrar todo", c.esVacio() && c.toString().equals("{}"));
    c.agregar(5);
    c.vaciar();
    verificar("vaciar", c.esVacio() && c.posicion(5) == -1 && c.getN() == 3);

    System.out.println(total + " verificaciones correctas");
  }

  private static ConjuntoEnVector crear(Object... datos) {
    ConjuntoEnVector conjunto = new ConjuntoEnVector(datos.length);
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
