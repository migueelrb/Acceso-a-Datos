
import org.json.JSONObject;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@XmlRootElement(name = "BBDDVideojuegos")
@XmlAccessorType(XmlAccessType.FIELD)
public class BBDDVideojuegos {

    // Guardo los videojuegos en una lista
    private List<Videojuego> videojuegos;

    // Constructor de la clase
    public BBDDVideojuegos() {
        this.videojuegos = new ArrayList<>();
    }

    /**
     * Método para guardar los datos en un archivo CSV
     */
    private void guardarVideojuegosEnXML() {
        guardarEnXML(Main.RUTA_XML);
    }


    /**
     * Método para cargar los datos desde un archivo CSV, el método recibe la ruta del archivo CSV y con un bucle while
     * y el método readLine de la clase BufferedReader lee el archivo CSV y con el método split de la clase String
     * separa los datos por comas y los guarda en un array de String, después crea un objeto videojuego con los datos
     * del array y lo añade a la lista de videojuegos, después de cargar los datos desde CSV, guarda la base de datos
     * en el archivo XML
     *
     * @param rutaCSV
     */
    public void cargarDesdeCSV(String rutaCSV) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaCSV))) {
            String linea;
            boolean primeraLinea = true;
            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }
                String[] datos = linea.split(",");
                videojuegos.add(new Videojuego(
                        datos[0],
                        datos[1],
                        datos[2],
                        datos[3],
                        datos[4],
                        datos[5],
                        datos[6]
                ));
            }
            guardarEnXML(Main.RUTA_XML);
            System.out.println("Datos cargados desde el fichero CSV.");
        } catch (IOException e) {
            System.out.println("Error al cargar desde el fichero CSV: " + e.getMessage());
        }
    }


    /**
     * Este método recorre la lista de videojuegos con un bucle for y con el método equals de la clase String valido si el identificador del videojuego es igual al identificador
     * especificado y si es así devuelve true, si no devuelve false
     * @param identificador
     * @return
     */
    public boolean existeIdentificador(String identificador) {
        for (Videojuego videojuego : videojuegos) {
            if (videojuego.getIdentificador().equals(identificador)) {
                return true; // El identificador ya está en uso
            }
        }
        return false; // El identificador no está en uso
    }


    /**
     * Método para guardar los datos en un archivo XML, crea un contexto JAXB con la clase BBDDVideojuegos y crea
     * un marshaller para guardar los datos en el archivo XML
     *
     * @param rutaXML
     */
    public void guardarEnXML(String rutaXML) {
        try {
            JAXBContext context = JAXBContext.newInstance(BBDDVideojuegos.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(this, new File(rutaXML));
            System.out.println("Datos guardados en el fichero XML.");
        } catch (JAXBException e) {
            System.out.println("Error al guardar en el fichero XML: " + e.getMessage());
        }
    }

    /**
     * Método para insertar un videojuego, el método recibe un objeto videojuego y lo añade a la lista de videojuegos
     * y muestra un mensaje de que el videojuego se ha insertado correctamente
     *
     * @param videojuego
     */
    public void insertarVideojuego(Videojuego videojuego) {
        videojuegos.add(videojuego);
        System.out.println("Videojuego insertado correctamente.");

        // Guardar los datos actualizados en el archivo XML
        guardarVideojuegosEnXML();
    }

    /**
     * Método para ordenar los datos por identificador, con el método sort de la clase List ordena los datos por
     * identificador y muestra un mensaje de que el fichero se ha ordenado correctamente
     */
    public void ordenarPorIdentificador() {
        videojuegos.sort(Comparator.comparing(Videojuego::getIdentificador));
        System.out.println("Fichero ordenado por Identificador.");

        // Guardar los datos actualizados en el archivo XML
        guardarVideojuegosEnXML(); // Llama al método para guardar en XML
    }

    /**
     * Método para borrar un videojuego, con el método removeIf de la clase List recorre la lista de videojuegos
     * y con el método equals de la clase String valido si el identificador del videojuego es igual al identificador,
     *
     * @param identificador
     */
    public void borrarVideojuego(String identificador) {
        // Comprueba que el identificador exista
        if (buscarVideojuego(identificador) == null) {
            System.out.println("El videojuego con el identificador especificado no existe.");
            return;
        }

        // Utilizo el método removeIf de la clase List para borrar el videojuego con una expresión lambda
        // que comprueba si el identificador del videojuego es igual al identificador especificado
        videojuegos.removeIf(v -> v.getIdentificador().equals(identificador));
        System.out.println("Videojuego borrado correctamente.");

        // Guardar los datos actualizados en el archivo XML
        guardarVideojuegosEnXML();
    }

    /**
     * Método para modificar un videojuego, el método recibe todos los datos del videojuego y comprueba que el
     * identificador exista, si no existe no hace nada, después recorre la lista de videojuegos con un bucle for
     * y con el método equals de la clase String valido si el identificador del videojuego es igual al identificador
     * especificado y si es así modifica los datos del videojuego
     *
     * @param identificador
     * @param nuevoTitulo
     * @param nuevoGenero
     * @param nuevaDesarrolladora
     * @param nuevoPegi
     * @param nuevasPlataformas
     * @param nuevoPrecio
     */
    public void modificarVideojuego(String identificador, String nuevoTitulo, String nuevoGenero, String nuevaDesarrolladora, String nuevoPegi, String nuevasPlataformas, String nuevoPrecio) {
        // Comprueba que el identificador exista, si no existe no hace nada
        if (buscarVideojuego(identificador) == null) {
            System.out.println("El videojuego con el identificador especificado no existe.");
            return;
        }

        for (Videojuego videojuego : videojuegos) {
            if (videojuego.getIdentificador().equals(identificador)) {
                videojuego.setTitulo(nuevoTitulo);
                videojuego.setGenero(nuevoGenero);
                videojuego.setDesarrolladora(nuevaDesarrolladora);
                videojuego.setPegi(nuevoPegi);
                videojuego.setPlataformas(nuevasPlataformas);
                videojuego.setPrecio(nuevoPrecio);
                break;
            }
        }
        System.out.println("Videojuego modificado correctamente.");

        // Guardar los datos actualizados en el archivo XML
        guardarVideojuegosEnXML();
    }

    /**
     * Método para exportar un videojuego, crea un objeto videojuego con el método buscarVideojuego y si el videojuego
     * existe llama al método exportarVideojuegoXML o exportarVideojuegoJSON dependiendo del formato especificado
     *
     * @param identificador
     * @param rutaOutput
     * @param formato
     */
    public void exportarVideojuego(String identificador, String rutaOutput, String formato) {
        Videojuego videojuego = buscarVideojuego(identificador);
        if (videojuego != null) {
            if (formato.equalsIgnoreCase("xml")) {
                exportarVideojuegoXML(videojuego, rutaOutput);
            } else if (formato.equalsIgnoreCase("json")) {
                exportarVideojuegoJSON(videojuego, rutaOutput);
            } else {
                System.out.println("Formato de exportación no válido.");
            }
        } else {
            System.out.println("El videojuego con el identificador especificado no existe.");
        }
    }

    /**
     * Método privado para exportar un videojuego en formato JSON, este metodo crea un objeto JSON con la clase JSONObject
     * y lo guarda en la ruta especificada en formato JSON con el mismo nombre.
     *
     * @param videojuego
     * @param rutaOutput
     */
    private void exportarVideojuegoJSON(Videojuego videojuego, String rutaOutput) {
        JSONObject jsonObject = new JSONObject(videojuego);
        try (FileWriter fileWriter = new FileWriter(rutaOutput)) {
            fileWriter.write(jsonObject.toString(4));
        } catch (IOException e) {
            System.out.println("Error al exportar el videojuego en formato JSON: " + e.getMessage());
        }
        System.out.println("Videojuego exportado en formato JSON.");
    }

    /**
     * Método privado para exportar un videojuego en formato XML, crea un contexto JAXB con la clase Videojuego y crea
     * un marshaller para exportar el videojuego en formato XML
     *
     * @param videojuego
     * @param rutaOutput
     */
    private void exportarVideojuegoXML(Videojuego videojuego, String rutaOutput) {
        try {
            JAXBContext context = JAXBContext.newInstance(Videojuego.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(videojuego, new File(rutaOutput));
        } catch (JAXBException e) {
            System.out.println("Error al exportar el videojuego en formato XML: " + e.getMessage());
        }
        System.out.println("Videojuego exportado en formato XML.");
    }

    /**
     * Método para convertir un archivo XML a JSON, el metodo recibe la ruta del archivo XML y la ruta del archivo JSON
     * y se encarga de convertir el archivo XML a JSON y guardarlo en la ruta especificada en formato JSON con el mismo nombre.
     *
     * @param rutaJSON
     */
    public void convertirXMLaJSON(String rutaJSON, String rutaXML) {
        try {
            JAXBContext context = JAXBContext.newInstance(BBDDVideojuegos.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            JAXBElement<BBDDVideojuegos> jaxbElement = unmarshaller.unmarshal(new StreamSource(new File(rutaXML)), BBDDVideojuegos.class);
            BBDDVideojuegos bbdd = jaxbElement.getValue();

            JSONObject jsonObject = new JSONObject(bbdd);
            try (FileWriter fileWriter = new FileWriter(rutaJSON)) {
                fileWriter.write(jsonObject.toString(4));
            }
            System.out.println("Fichero XML convertido a JSON.");
        } catch (JAXBException | IOException e) {
            System.out.println("Error al convertir XML a JSON: " + e.getMessage());
        }
    }


    /**
     * Método privado para buscar un videojuego por identificador buscando en la lista de videojuegos con un bucle for
     * y con el método equals de la clase String valido si el identificador del videojuego es igual al identificador
     *
     * @param identificador
     * @return
     */
    private Videojuego buscarVideojuego(String identificador) {
        for (Videojuego videojuego : videojuegos) {
            if (videojuego.getIdentificador().equals(identificador)) {
                return videojuego;
            }
        }
        return null;
    }

    public List<Videojuego> getVideojuegos() {
        return videojuegos;
    }

    public void setVideojuegos(List<Videojuego> videojuegos) {
        this.videojuegos = videojuegos;
    }


}

