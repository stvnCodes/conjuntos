package classes;

public class Nodo {
  // ------------------- Atributos ------------------- //
  private Object dato;
  private Nodo liga;

  // ------------------- Constructores ------------------- //
  public Nodo(Object dato) {
    this.dato = dato;
    this.liga = null;
  }

  // ------------------- Getters & Setters ------------------- //
  public Object getDato() {
    return dato;
  }

  public Nodo getLiga() {
    return liga;
  }

  // Solo las clases de este paquete pueden cambiar los enlaces
  void setLiga(Nodo liga) {
    this.liga = liga;
  }
}
