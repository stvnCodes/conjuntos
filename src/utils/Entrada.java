package utils;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Entrada {
  // ------------------- Atributos ------------------- //
  private BufferedReader lector;

  // ------------------- Constructores ------------------- //
  public Entrada() {
    lector = new BufferedReader(new InputStreamReader(System.in));
  }

  // ------------------- Lectura y validación ------------------- //
  public int leerOpcion(String mensaje, int minimo, int maximo) throws IOException {
    while (true) {
      int opcion = leerEntero(mensaje);
      if (opcion >= minimo && opcion <= maximo) {
        return opcion;
      }
      System.out.println("Elija una opción entre " + minimo + " y " + maximo + ".");
    }
  }

  // Devuelve un Integer o un String según el tipo elegido
  public Object leerElemento(String mensaje, boolean esEntero) throws IOException {
    if (esEntero) {
      return leerEntero(mensaje);
    }
    return leerTexto(mensaje);
  }

  // Los elementos se separan con comas; una línea vacía representa el conjunto vacío
  public Object[] leerElementos(String mensaje, boolean esEntero) throws IOException {
    while (true) {
      String linea = leerLinea(mensaje);
      if (linea.isEmpty()) {
        return new Object[0];
      }
      try {
        return convertir(linea.split(",", -1), esEntero);
      } catch (IllegalArgumentException error) {
        System.out.println("Datos inválidos. Revise el tipo de dato y que no haya comas seguidas o sobrantes.");
      }
    }
  }

  // ------------------- Apoyo interno ------------------- //
  private int leerEntero(String mensaje) throws IOException {
    while (true) {
      String texto = leerTexto(mensaje);
      try {
        return Integer.parseInt(texto);
      } catch (NumberFormatException error) {
        System.out.println("Ingrese un número entero válido.");
      }
    }
  }

  private String leerTexto(String mensaje) throws IOException {
    while (true) {
      String texto = leerLinea(mensaje);
      if (!texto.isEmpty()) {
        return texto;
      }
      System.out.println("El valor no puede estar vacío.");
    }
  }

  private String leerLinea(String mensaje) throws IOException {
    System.out.print(mensaje);
    String linea = lector.readLine();
    if (linea == null) {
      throw new EOFException();
    }
    return linea.trim();
  }

  private Object[] convertir(String[] partes, boolean esEntero) {
    Object[] elementos = new Object[partes.length];
    for (int i = 0; i < partes.length; i++) {
      String parte = partes[i].trim();
      if (parte.isEmpty()) {
        throw new IllegalArgumentException();
      }
      if (esEntero) {
        elementos[i] = Integer.parseInt(parte);
      } else {
        elementos[i] = parte;
      }
    }
    return elementos;
  }
}
