package Trabajo2;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            // Le paso el contexto de la clase que contiene la lista de libros
            JAXBContext jaxbContext = JAXBContext.newInstance(BBDDLibros.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            // Le paso el fichero xml a través de un File
            File file = new File("books.xml");
            BBDDLibros bbddLibros = (BBDDLibros) unmarshaller.unmarshal(file);

            // Con el FileWriter escribo en el fichero libros.txt
            FileWriter writer = new FileWriter("libros.txt");
            // Voy recorriendo la lista de libros y escribiendo en el fichero
            for (Libro libro : bbddLibros.getLibros()) {
                writer.write("ID: " + libro.getId() + "\n");
                writer.write("Author: " + libro.getAuthor() + "\n");
                writer.write("Title: " + libro.getTitle() + "\n");
                writer.write("Genre: " + libro.getGenre() + "\n");
                writer.write("Price: " + libro.getPrice() + "\n");
                writer.write("Publish Date: " + libro.getPublishDate() + "\n");
                writer.write("Description: " + libro.getDescription() + "\n");
                writer.write("\n");
            }
            writer.close();

            System.out.println("Se ha creado el fichero libros.txt con los datos de los libros");
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

