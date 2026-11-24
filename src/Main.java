import menus.MenuLista;
import menus.MenuVector;
import utils.Entrada;

import java.io.EOFException;
import java.io.IOException;

public class Main {
  // ------------------- Punto de entrada ------------------- //
  public static void main(String[] args) {
    System.out.println("Práctica de conjuntos");
    try {
      elegirRepresentacion(new Entrada());
    } catch (EOFException error) {
      System.out.println("\nFin de la entrada.");
    } catch (IOException error) {
      System.out.println("\nNo se pudo leer la entrada: " + error.getMessage());
    }
    System.out.println("Aplicación finalizada.");
  }

  private static void elegirRepresentacion(Entrada entrada) throws IOException {
    int opcion;
    do {
      System.out.println("\nRepresentación: 1. Vector   2. Lista   0. Salir");
      opcion = entrada.leerOpcion("Opción: ", 0, 2);
      if (opcion == 1) {
        new MenuVector(entrada).ejecutar();
      } else if (opcion == 2) {
        new MenuLista(entrada).ejecutar();
      }
    } while (opcion != 0);
  }
}
