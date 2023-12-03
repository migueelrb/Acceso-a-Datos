package Trabajo;

import org.json.JSONObject;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcionElegida;

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Generar XML");
            System.out.println("2. Generar JSON");
            System.out.println("3. Salir");
            System.out.print("Elije una opcion: ");

            if (scanner.hasNextInt()) {
                opcionElegida = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (opcionElegida) {
                    case 1:
                        generarXML();
                        break;
                    case 2:
                        generarJSON();
                        break;
                    case 3:
                        System.out.println("Saliendo del programa...");
                        return;
                    default:
                        System.out.println("Has elegido una opcion erronea");
                }
            } else {
                System.out.println("Has introducido un caracter diferente a un numero");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }


    public static void generarXML() {
        Juego juego = new Juego("FIFA 14", "pc", "DEPORTES", "fifa14", "3+", "Simulador de fútbol multijugador.");

        // Marshalling a XML
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Juego.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(juego, new File("juego.xml"));
            System.out.println("XML generado correctamente");
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static void generarJSON() {
        Juego juego = new Juego("FIFA 14", "pc", "DEPORTES", "fifa14", "3+", "Simulador de fútbol multijugador.");

        // Generate JSON
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nombre", juego.getNombre());
        jsonObject.put("tipo", juego.getTipo());
        jsonObject.put("genero", juego.getGenero());
        jsonObject.put("proceso", juego.getProceso());
        jsonObject.put("edad", juego.getEdad());
        jsonObject.put("descripcion", juego.getDescripcion());

        try (FileWriter file = new FileWriter("juego.json")) {
            file.write(jsonObject.toString());
            System.out.println("JSON generado correctamente");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
