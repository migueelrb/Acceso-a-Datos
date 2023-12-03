package Trabajo2;

import javax.xml.bind.annotation.XmlRootElement;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "Catalog")
public class BBDDLibros {
    private List<Libro> libros;

    @XmlElement(name = "Book")
    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }
}

