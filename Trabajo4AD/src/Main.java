import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final String RUTA_CSV = "videojuegos.csv";
    public static final String RUTA_XML = "videojuegos.xml";

    public static void main(String[] args) {
        BBDDVideojuegos bbdd = new BBDDVideojuegos();
        bbdd.cargarDesdeCSV(RUTA_CSV);

        boolean realizarOtraAccion = true;
        Scanner scanner = new Scanner(System.in);

        while (realizarOtraAccion) {
            printMenu();
            int opcion = -1;

            // Valido que la opción introducida sea un número del 0 al 6 y que no sea un String
            do {
                try {
                    opcion = scanner.nextInt();
                    if (opcion < 0 || opcion > 6) {
                        System.out.println("Por favor, introduce un número del 0 al 6.");
                        printMenu();
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Por favor, introduce solo numeros");
                    scanner.next(); // Limpia el búfer de entrada
                    printMenu();
                }
            } while (opcion < 0 || opcion > 6);

            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Identificador (debe tener 5 caracteres): ");
                    String identificador = scanner.nextLine();

                    // Valido que el identificador tenga 5 caracteres y que no exista ya en la BBDD
                    if (identificador.length() != 5) {
                        System.out.println("El identificador debe tener exactamente 5 caracteres.");
                    } else if (bbdd.existeIdentificador(identificador)) {
                        System.out.println("Ese identificador ya está en uso. Por favor, elige uno nuevo.");
                    } else {
                        System.out.print("Título: ");
                        String titulo = scanner.nextLine();
                        System.out.print("Género: ");
                        String genero = scanner.nextLine();
                        System.out.print("Desarrolladora: ");
                        String desarrolladora = scanner.nextLine();
                        System.out.print("PEGI: ");
                        String pegi = scanner.nextLine();
                        System.out.print("Plataformas: ");
                        String plataformas = scanner.nextLine();
                        System.out.print("Precio: ");
                        String precio = scanner.nextLine();

                        bbdd.insertarVideojuego(new Videojuego(identificador, titulo, genero, desarrolladora, pegi, plataformas, precio));
                    }
                    break;

                case 2:
                    bbdd.ordenarPorIdentificador();
                    break;
                case 3:
                    System.out.print("Identificador: ");
                    String identificadorBorrado = scanner.nextLine();
                    bbdd.borrarVideojuego(identificadorBorrado);
                    break;
                case 4:
                    System.out.print("Identificador: ");
                    String identificadorModificado = scanner.nextLine();
                    System.out.print("Nuevo título: ");
                    String nuevoTitulo = scanner.nextLine();
                    System.out.print("Nuevo género: ");
                    String nuevoGenero = scanner.nextLine();
                    System.out.print("Nueva desarrolladora: ");
                    String nuevaDesarrolladora = scanner.nextLine();
                    System.out.print("Nuevo PEGI: ");
                    String nuevoPegi = scanner.nextLine();
                    System.out.print("Nuevas plataformas: ");
                    String nuevasPlataformas = scanner.nextLine();
                    System.out.print("Nuevo precio: ");
                    String nuevoPrecio = scanner.nextLine();

                    bbdd.modificarVideojuego(identificadorModificado, nuevoTitulo, nuevoGenero, nuevaDesarrolladora, nuevoPegi, nuevasPlataformas, nuevoPrecio);
                    break;
                case 5:
                    System.out.print("Identificador del videojuego a exportar: ");
                    String identificadorExportar = scanner.nextLine();
                    System.out.print("Formato de exportación (XML / JSON): ");
                    String formatoExportacion = scanner.nextLine();
                    System.out.print("Ruta de salida: ");
                    String rutaSalida = scanner.nextLine();

                    bbdd.exportarVideojuego(identificadorExportar, rutaSalida, formatoExportacion);
                    break;
                case 6:
                    System.out.print("Ruta del fichero de entrada XML (incluye la extension): ");
                    String rutaXML = scanner.nextLine();

                    System.out.print("Ruta del fichero de salida JSON (incluye la extensión): ");
                    String rutaJSON = scanner.nextLine();

                    bbdd.convertirXMLaJSON(rutaJSON, rutaXML);
                    break;

                case 0:
                    realizarOtraAccion = false;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n--- Menú ---");
        System.out.println("1. Insertar videojuego");
        System.out.println("2. Ordenar por identificador");
        System.out.println("3. Borrar videojuego");
        System.out.println("4. Modificar videojuego");
        System.out.println("5. Exportar videojuego");
        System.out.println("6. Convertir BBDD XML a JSON");
        System.out.println("0. Salir");
        System.out.print("Elige una opción: ");
    }
}




