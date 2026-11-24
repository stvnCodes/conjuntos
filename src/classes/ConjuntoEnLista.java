package classes;

public class ConjuntoEnLista {
  // ------------------- Atributos ------------------- //
  // Es static porque todos los conjuntos comparten el mismo universal
  private static ConjuntoEnLista universal;

  private Nodo cab;

  // ------------------- Constructores ------------------- //
  public ConjuntoEnLista() {
    this.cab = null;
  }

  // ------------------- Getters & Setters ------------------- //
  public Nodo getCab() {
    return cab;
  }

  // Se guarda una copia para que cambios externos no alteren el universal
  public static void setUniversal(ConjuntoEnLista nuevo) {
    universal = nuevo.copiar();
  }

  // ------------------- Consultas básicas ------------------- //
  public int cantidadDeElementos() {
    int cantidad = 0;
    Nodo actual = cab;
    while (actual != null) {
      cantidad++;
      actual = actual.getLiga();
    }
    return cantidad;
  }

  public boolean esVacio() {
    return cab == null;
  }

  // Devuelve la posición desde cero, o -1 si no está
  public int posicion(Object elemento) {
    int pos = 0;
    Nodo actual = cab;
    while (actual != null) {
      if (actual.getDato().equals(elemento)) {
        return pos;
      }
      pos++;
      actual = actual.getLiga();
    }
    return -1;
  }

  public boolean pertenece(Object elemento) {
    return posicion(elemento) != -1;
  }

  // ------------------- Modificaciones ------------------- //
  public void agregar(Object elemento) {
    if (elemento == null) {
      throw new IllegalArgumentException("El elemento no puede ser nulo.");
    }
    if (pertenece(elemento)) {
      return;
    }
    Nodo nuevo = new Nodo(elemento);
    if (cab == null) {
      cab = nuevo;
    } else {
      ultimo().setLiga(nuevo);
    }
  }

  // El nodo anterior se enlaza con el siguiente del que se borra
  public void borrar(Object elemento) {
    Nodo anterior = null;
    Nodo actual = cab;
    while (actual != null && !actual.getDato().equals(elemento)) {
      anterior = actual;
      actual = actual.getLiga();
    }
    if (actual == null) {
      return;
    }
    if (anterior == null) {
      cab = actual.getLiga();
    } else {
      anterior.setLiga(actual.getLiga());
    }
  }

  public void vaciar() {
    cab = null;
  }

  // ------------------- Operaciones entre conjuntos ------------------- //
  public ConjuntoEnLista union(ConjuntoEnLista b) {
    ConjuntoEnLista resultado = copiar();
    Nodo actual = b.cab;
    while (actual != null) {
      resultado.agregar(actual.getDato());
      actual = actual.getLiga();
    }
    return resultado;
  }

  public ConjuntoEnLista interseccion(ConjuntoEnLista b) {
    ConjuntoEnLista resultado = new ConjuntoEnLista();
    Nodo actual = cab;
    while (actual != null) {
      if (b.pertenece(actual.getDato())) {
        resultado.agregar(actual.getDato());
      }
      actual = actual.getLiga();
    }
    return resultado;
  }

  public ConjuntoEnLista diferencia(ConjuntoEnLista b) {
    ConjuntoEnLista resultado = new ConjuntoEnLista();
    Nodo actual = cab;
    while (actual != null) {
      if (!b.pertenece(actual.getDato())) {
        resultado.agregar(actual.getDato());
      }
      actual = actual.getLiga();
    }
    return resultado;
  }

  // Une lo que es exclusivo de cada conjunto
  public ConjuntoEnLista diferenciaSimetrica(ConjuntoEnLista b) {
    return diferencia(b).union(b.diferencia(this));
  }

  // Lo que le falta a este conjunto para completar el universal
  public ConjuntoEnLista complemento() {
    if (universal == null) {
      throw new IllegalStateException("Primero debe definir el conjunto universal.");
    }
    return universal.diferencia(this);
  }

  // ------------------- Comparaciones ------------------- //
  // Verifica que todos los elementos de este conjunto estén en b
  public boolean subconjunto(ConjuntoEnLista b) {
    Nodo actual = cab;
    while (actual != null) {
      if (!b.pertenece(actual.getDato())) {
        return false;
      }
      actual = actual.getLiga();
    }
    return true;
  }

  public boolean igualdad(ConjuntoEnLista b) {
    return cantidadDeElementos() == b.cantidadDeElementos() && subconjunto(b);
  }

  public boolean disjuntos(ConjuntoEnLista b) {
    return interseccion(b).esVacio();
  }

  // ------------------- Presentación ------------------- //
  public void mostrar() {
    System.out.println(this);
  }

  @Override
  public String toString() {
    StringBuilder texto = new StringBuilder("{");
    Nodo actual = cab;
    while (actual != null) {
      texto.append(actual.getDato());
      if (actual.getLiga() != null) {
        texto.append(", ");
      }
      actual = actual.getLiga();
    }
    return texto.append("}").toString();
  }

  // ------------------- Apoyo interno ------------------- //
  // Crea nodos nuevos para no compartirlos con el original
  private ConjuntoEnLista copiar() {
    ConjuntoEnLista copia = new ConjuntoEnLista();
    Nodo actual = cab;
    while (actual != null) {
      copia.agregar(actual.getDato());
      actual = actual.getLiga();
    }
    return copia;
  }

  // Solo se llama cuando la lista tiene al menos un nodo
  private Nodo ultimo() {
    Nodo actual = cab;
    while (actual.getLiga() != null) {
      actual = actual.getLiga();
    }
    return actual;
  }
}
