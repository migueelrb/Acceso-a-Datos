import javax.xml.bind.annotation.*;

@XmlRootElement(name = "Videojuego")
@XmlAccessorType(XmlAccessType.FIELD)
public class Videojuego {
    @XmlAttribute(name = "Identificador")
    private String identificador;
    @XmlElement(name = "Titulo")
    private String titulo;
    @XmlElement(name = "Genero")
    private String genero;
    @XmlElement(name = "Desarrolladora")
    private String desarrolladora;
    @XmlElement(name = "PEGI")
    private String pegi;
    @XmlElement(name = "Plataformas")
    private String plataformas;
    @XmlElement(name = "Precio")
    private String precio;

    public Videojuego() {
    }

    public Videojuego(String identificador, String titulo, String genero, String desarrolladora, String pegi, String plataformas, String precio) {
        this.identificador = identificador;
        this.titulo = titulo;
        this.genero = genero;
        this.desarrolladora = desarrolladora;
        this.pegi = pegi;
        this.plataformas = plataformas;
        this.precio = precio;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDesarrolladora() {
        return desarrolladora;
    }

    public void setDesarrolladora(String desarrolladora) {
        this.desarrolladora = desarrolladora;
    }

    public String getPegi() {
        return pegi;
    }

    public void setPegi(String pegi) {
        this.pegi = pegi;
    }

    public String getPlataformas() {
        return plataformas;
    }

    public void setPlataformas(String plataformas) {
        this.plataformas = plataformas;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}


