package Trabajo1;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProgramaCoche {

    public static void main(String[] args) {
        // Menú de opciones para que el usuario elija
        int opcion;
        do {
            System.out.println("1. Cargar información");
            System.out.println("2. Insertar un coche");
            System.out.println("3. Ordenar por matrícula");
            System.out.println("4. Borrar un registro");
            System.out.println("5. Modificar un registro");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    cargarInformacion();
                    break;
                case 2:
                    insertarCoche();
                    break;
                case 3:
                    ordenarPorMatricula();
                    break;
                case 4:
                    borrarRegistro();
                    break;
                case 5:
                    modificarRegistro();
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida");
                    break;
            }
        } while (opcion != 0);
    }

    /**
     * Este método carga la información de un fichero CSV en un fichero binario, para ello
     * creo un objeto File para el fichero CSV y un BufferedReader para leer el fichero CSV,
     * después con un while leo línea a línea el fichero CSV, separo los campos de la línea
     * por comas y los guardo en un array, después creo un objeto Coche con los datos y llamo
     * al método guardarCocheEnFichero para guardar el coche en el fichero binario
     */
    public static void cargarInformacion() {
        try {
            // Objeto File que guarda la ruta del fichero CSV
            File ficheroCSV = new File("BBDD Coches.csv");
            BufferedReader br = new BufferedReader(new FileReader(ficheroCSV));
            String linea;

            br.readLine(); // Hago que el while olvide la primera linea del fichero CSV
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(",");
                String matricula = campos[0];
                String marca = campos[1];
                String modelo = campos[2];

                Coche coche = new Coche(matricula, marca, modelo);
                guardarCocheEnFichero(coche);
            }

            // Cierro el BufferedReader
            br.close();
            // Muestro un mensaje de que la información se ha cargado correctamente
            System.out.println("Información cargada correctamente");
            // Si ocurre un error al cargar la información, muestro un mensaje de error
        } catch (IOException e) {
            System.out.println("Error al cargar la información");
            e.printStackTrace();
        }
    }

    /**
     * Este método inserta un coche en el fichero binario, para ello le pido al usuario la
     * posición de inserción, si la posición es válida le pido los datos del coche y creo
     * un objeto Coche con los datos, después cargo los coches del fichero en una lista de
     * tipo Coche, inserto el coche en la lista en la posición indicada y guardo la lista
     * en el fichero binario
     */
    public static void insertarCoche() {
        String matricula, marca, modelo;
        System.out.print("Introduce la posición de inserción: ");
        int posicion = leerEntero();

        List<Coche> coches = leerCochesDelFichero();

        if (posicion >= 0 && posicion <= coches.size()) {
            System.out.print("Introduce la matrícula: ");
            matricula = leerCadena();
            System.out.print("Introduce la marca: ");
            marca = leerCadena();
            System.out.print("Introduce el modelo: ");
            modelo = leerCadena();

            Coche coche = new Coche(matricula, marca, modelo);

            coches.add(posicion, coche);

            guardarCochesEnFichero(coches);

            System.out.println("Coche insertado correctamente");
        } else {
            System.out.println("Posición inválida");
        }
    }


    /**
     * Este método ordena los coches del fichero por matrícula, su implementación es muy sencilla,
     * creo una lista de tipo coche y le cargo los coches del fichero, despues con el .sort
     * de la clase Collections ordeno la lista y por último guardo la lista ordenada en el fichero
     */
    public static void ordenarPorMatricula() {
        List<Coche> coches = leerCochesDelFichero();
        Collections.sort(coches);

        guardarCochesEnFichero(coches);

        System.out.println("Fichero ordenado por matrícula");
    }

    /**
     * Este método borra un registro del fichero, para ello le pregunto al usuario si quiere
     * borrar el registro por matrícula o por posición, si elige matrícula le pido la matrícula
     * del coche a borrar y llamo al método borrarRegistroPorMatricula, si elige posición le pido
     * la posición del coche a borrar y llamo al método borrarRegistroPorPosicion
     */
    public static void borrarRegistro() {
        System.out.println("¿Cómo desea borrar el registro?");
        System.out.println("1. Por matrícula");
        System.out.println("2. Por posición");
        System.out.print("Selecciona una opción: ");
        int opcion = leerEntero();

        if (opcion == 1) {
            System.out.print("Introduce la matrícula del coche a borrar: ");
            String matricula = leerCadena();
            borrarRegistroPorMatricula(matricula);
        } else if (opcion == 2) {
            System.out.print("Introduce la posición del coche a borrar: ");
            int posicion = leerEntero();
            borrarRegistroPorPosicion(posicion);
        } else {
            System.out.println("Opción inválida");
        }
    }

    /**
     * Este método borra un registro del fichero por matrícula, para ello le paso la matrícula
     * del coche a borrar y llamo al método buscarCochePorMatricula, si el método me devuelve
     * un -1 es que no se encontró el coche, si me devuelve un número mayor o igual a 0 es que
     * se encontró el coche y lo borro de la lista de coches y guardo la lista en el fichero
     *
     * @param matricula
     */
    public static void borrarRegistroPorMatricula(String matricula) {
        List<Coche> coches = leerCochesDelFichero();
        int indice = buscarCochePorMatricula(coches, matricula);

        if (indice != -1) {
            coches.remove(indice);
            guardarCochesEnFichero(coches);
            System.out.println("Coche borrado correctamente");
        } else {
            System.out.println("No se encontró ningún coche con esa matrícula");
        }
    }

    /**
     * Este método borra un registro del fichero por posición, para ello le paso la posición
     * del coche a borrar y si la posición es válida lo borro de la lista de coches y guardo
     * la lista en el fichero
     *
     * @param posicion
     */
    public static void borrarRegistroPorPosicion(int posicion) {
        List<Coche> coches = leerCochesDelFichero();
        if (posicion >= 0 && posicion < coches.size()) {
            coches.remove(posicion);
            guardarCochesEnFichero(coches);
            System.out.println("Coche borrado correctamente");
        } else {
            System.out.println("Posición inválida");
        }
    }


    /**
     * Este método modifica un registro del fichero, para ello le pregunto al usuario si quiere
     * modificar el registro por matrícula o por posición, si elige matrícula le pido la matrícula
     * del coche a modificar y llamo al método modificarRegistroPorMatricula, si elige posición le pido
     * la posición del coche a modificar y llamo al método modificarRegistroPorPosicion
     */
    public static void modificarRegistro() {
        System.out.print("Introduce la posición del coche a modificar: ");
        int posicion = leerEntero();

        List<Coche> coches = leerCochesDelFichero();
        if (posicion >= 0 && posicion < coches.size()) {
            Coche coche = coches.get(posicion);
            System.out.print("Introduce la nueva marca: ");
            String marca = leerCadena();
            System.out.print("Introduce el nuevo modelo: ");
            String modelo = leerCadena();
            coche.setMarca(marca);
            coche.setModelo(modelo);

            guardarCochesEnFichero(coches);
            System.out.println("Coche modificado correctamente");
        } else {
            System.out.println("Posición inválida");
        }
    }

    /**
     * Este método guarda un coche en el fichero, para ello le paso el coche y con el método
     * seek del RandomAccessFile me posiciono al final del fichero y escribo los datos del coche
     * en el fichero
     *
     * @param coche
     */
    public static void guardarCocheEnFichero(Coche coche) {
        try {
            // Creo un RandomAccessFile para el fichero binario y el rw es para que se pueda leer y escribir
            RandomAccessFile raf = new RandomAccessFile("fichero.txt", "rw");
            // Con el seek me posiciono al final del fichero
            raf.seek(raf.length());

            // Escribo los datos del coche en el fichero
            raf.writeBytes(rellena(coche.getMatricula(), 7));

            raf.writeBytes(rellena(coche.getMarca(), 32));

            raf.writeBytes(rellena(coche.getModelo(), 32));

            raf.close();
        } catch (IOException e) {
            System.out.println("Error al guardar el coche en el fichero");
            e.printStackTrace();
        }
    }

    /**
     * Este método lee los coches del fichero, para ello creo una lista de tipo Coche y con el
     * método getFilePointer del RandomAccessFile me posiciono al principio del fichero y con
     * el método length del RandomAccessFile obtengo la longitud del fichero, con un while
     * recorro el fichero y voy leyendo los datos de cada coche y los voy guardando en la lista
     *
     * @return
     */
    public static List<Coche> leerCochesDelFichero() {
        List<Coche> coches = new ArrayList<>();

        try {
            RandomAccessFile raf = new RandomAccessFile("fichero.txt", "r");

            while (raf.getFilePointer() < raf.length()) {
                // Leer matrícula
                byte[] matriculaBytes = new byte[7];
                raf.read(matriculaBytes);
                String matricula = new String(matriculaBytes).trim();

                // Leer marca
                byte[] marcaBytes = new byte[32];
                raf.read(marcaBytes);
                String marca = new String(marcaBytes).trim();

                // Leer modelo
                byte[] modeloBytes = new byte[32];
                raf.read(modeloBytes);
                String modelo = new String(modeloBytes).trim();

                Coche coche = new Coche(matricula, marca, modelo);
                coches.add(coche);
            }

            raf.close();
        } catch (IOException e) {
            System.out.println("Error al leer los coches del fichero");
            e.printStackTrace();
        }

        return coches;
    }

    /**
     * Este método guarda los coches en el fichero, para ello le paso la lista de coches y con
     * el método setLength del RandomAccessFile borro el contenido del fichero, después con un
     * for recorro la lista de coches y voy escribiendo los datos de cada coche en el fichero
     *
     * @param coches
     */
    public static void guardarCochesEnFichero(List<Coche> coches) {
        try {
            RandomAccessFile raf = new RandomAccessFile("fichero.txt", "rw");
            raf.setLength(0); // Borro el contenido actual del fichero

            for (Coche coche : coches) {

                raf.writeBytes(rellena(coche.getMatricula(), 7));

                raf.writeBytes(rellena(coche.getMarca(), 32));

                raf.writeBytes(rellena(coche.getModelo(), 32));
            }

            raf.close();
        } catch (IOException e) {
            System.out.println("Error al guardar los coches en el fichero");
            e.printStackTrace();
        }
    }

    /**
     * Este método busca un coche por matrícula, para ello le paso la lista de coches y la matrícula
     * del coche a buscar, con un for recorro la lista de coches y si la matrícula del coche actual
     * es igual a la matrícula que le pasé al método devuelvo la posición del coche, si no se encontró
     * el coche devuelvo un -1
     *
     * @param coches
     * @param matricula
     * @return
     */
    public static int buscarCochePorMatricula(List<Coche> coches, String matricula) {
        for (int i = 0; i < coches.size(); i++) {
            Coche coche = coches.get(i);
            if (coche.getMatricula().equals(matricula)) {
                return i;
            }
        }
        return -1; // No se encontró el coche
    }

    /**
     * Este método rellena una cadena con espacios hasta que tenga la longitud indicada
     *
     * @param string
     * @param length
     * @return
     */
    public static String rellena(String string, int length) {
        StringBuilder paddedString = new StringBuilder(string);
        for (int i = string.length(); i < length; i++) {
            paddedString.append(" ");
        }
        return paddedString.toString();
    }


    public static int leerEntero() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            return Integer.parseInt(br.readLine());
        } catch (NumberFormatException | IOException e) {
            System.out.println("Introduce un número válido");
            return leerEntero();
        }
    }

    public static String leerCadena() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            return br.readLine();
        } catch (IOException e) {
            System.out.println("Error al leer la cadena");
            return "";
        }
    }
}

/**
 * Clase Coche, la podria hacer en otra clase pero he decidido ponerla en la misma clase
 * esta clase tiene su constructor, getters y setters y el método compareTo para poder
 * ordenar los coches por matrícula
 */
class Coche implements Comparable<Coche> {

    private String matricula;
    private String marca;
    private String modelo;

    public Coche(String matricula, String marca, String modelo) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    @Override
    public int compareTo(Coche otroCoche) {
        return this.matricula.compareTo(otroCoche.matricula);
    }
}