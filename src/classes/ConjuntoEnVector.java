package classes;

public class ConjuntoEnVector {
  // ------------------- Atributos ------------------- //
  // Es static porque todos los conjuntos comparten el mismo universal
  private static ConjuntoEnVector universal;

  private int n;
  // Los elementos van seguidos; el primer null marca el final
  private Object[] vec;

  // ------------------- Constructores ------------------- //
  public ConjuntoEnVector(int n) {
    this.n = n;
    this.vec = new Object[n];
  }

  // ------------------- Getters & Setters ------------------- //
  public int getN() {
    return n;
  }

  public Object getDato(int pos) {
    validarPosicion(pos);
    return vec[pos];
  }

  // Reemplaza un elemento sin permitir repetidos
  public void setDato(int pos, Object dato) {
    validarPosicion(pos);
    validarDato(dato);
    int encontrado = posicion(dato);
    if (encontrado != -1 && encontrado != pos) {
      throw new IllegalArgumentException("El elemento ya está en el conjunto.");
    }
    vec[pos] = dato;
  }

  // Se guarda una copia para que cambios externos no alteren el universal
  public static void setUniversal(ConjuntoEnVector nuevo) {
    universal = nuevo.copiar(nuevo.n);
  }

  // ------------------- Consultas básicas ------------------- //
  public int cantidadDeElementos() {
    int cantidad = 0;
    while (cantidad < n && vec[cantidad] != null) {
      cantidad++;
    }
    return cantidad;
  }

  public boolean esVacio() {
    return cantidadDeElementos() == 0;
  }

  // Devuelve la posición del elemento, o -1 si no está
  public int posicion(Object elemento) {
    int i = 0;
    while (i < n && vec[i] != null) {
      if (vec[i].equals(elemento)) {
        return i;
      }
      i++;
    }
    return -1;
  }

  public boolean pertenece(Object elemento) {
    return posicion(elemento) != -1;
  }

  // ------------------- Modificaciones ------------------- //
  public void agregar(Object elemento) {
    validarDato(elemento);
    if (pertenece(elemento)) {
      return;
    }
    int cantidad = cantidadDeElementos();
    if (cantidad == n) {
      throw new IllegalStateException("El conjunto está lleno.");
    }
    vec[cantidad] = elemento;
  }

  // Corre los elementos siguientes para no dejar huecos
  public void borrar(Object elemento) {
    int pos = posicion(elemento);
    if (pos == -1) {
      return;
    }
    for (int i = pos; i < n - 1; i++) {
      vec[i] = vec[i + 1];
    }
    vec[n - 1] = null;
  }

  public void vaciar() {
    vec = new Object[n];
  }

  // ------------------- Operaciones entre conjuntos ------------------- //
  public ConjuntoEnVector union(ConjuntoEnVector b) {
    int cantidadB = b.cantidadDeElementos();
    ConjuntoEnVector resultado = copiar(cantidadDeElementos() + cantidadB);
    for (int i = 0; i < cantidadB; i++) {
      resultado.agregar(b.vec[i]);
    }
    return resultado;
  }

  public ConjuntoEnVector interseccion(ConjuntoEnVector b) {
    int cantidad = cantidadDeElementos();
    ConjuntoEnVector resultado = new ConjuntoEnVector(cantidad);
    for (int i = 0; i < cantidad; i++) {
      if (b.pertenece(vec[i])) {
        resultado.agregar(vec[i]);
      }
    }
    return resultado;
  }

  public ConjuntoEnVector diferencia(ConjuntoEnVector b) {
    int cantidad = cantidadDeElementos();
    ConjuntoEnVector resultado = new ConjuntoEnVector(cantidad);
    for (int i = 0; i < cantidad; i++) {
      if (!b.pertenece(vec[i])) {
        resultado.agregar(vec[i]);
      }
    }
    return resultado;
  }

  // Une lo que es exclusivo de cada conjunto
  public ConjuntoEnVector diferenciaSimetrica(ConjuntoEnVector b) {
    return diferencia(b).union(b.diferencia(this));
  }

  // Lo que le falta a este conjunto para completar el universal
  public ConjuntoEnVector complemento() {
    if (universal == null) {
      throw new IllegalStateException("Primero debe definir el conjunto universal.");
    }
    return universal.diferencia(this);
  }

  // ------------------- Comparaciones ------------------- //
  // Verifica que todos los elementos de este conjunto estén en b
  public boolean subconjunto(ConjuntoEnVector b) {
    int cantidad = cantidadDeElementos();
    for (int i = 0; i < cantidad; i++) {
      if (!b.pertenece(vec[i])) {
        return false;
      }
    }
    return true;
  }

  public boolean igualdad(ConjuntoEnVector b) {
    return cantidadDeElementos() == b.cantidadDeElementos() && subconjunto(b);
  }

  public boolean disjuntos(ConjuntoEnVector b) {
    return interseccion(b).esVacio();
  }

  // ------------------- Presentación ------------------- //
  public void mostrar() {
    System.out.println(this);
  }

  @Override
  public String toString() {
    StringBuilder texto = new StringBuilder("{");
    int cantidad = cantidadDeElementos();
    for (int i = 0; i < cantidad; i++) {
      if (i > 0) {
        texto.append(", ");
      }
      texto.append(vec[i]);
    }
    return texto.append("}").toString();
  }

  // ------------------- Apoyo interno ------------------- //
  // La capacidad debe alcanzar para los elementos actuales
  private ConjuntoEnVector copiar(int capacidad) {
    ConjuntoEnVector copia = new ConjuntoEnVector(capacidad);
    int cantidad = cantidadDeElementos();
    for (int i = 0; i < cantidad; i++) {
      copia.vec[i] = vec[i];
    }
    return copia;
  }

  private void validarPosicion(int pos) {
    if (pos < 0 || pos >= cantidadDeElementos()) {
      throw new IllegalArgumentException("No hay un elemento en la posición " + pos + ".");
    }
  }

  private void validarDato(Object dato) {
    if (dato == null) {
      throw new IllegalArgumentException("El elemento no puede ser nulo.");
    }
  }
}
