package Trabajo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "juego")
@XmlType(propOrder = {"nombre", "tipo", "genero", "proceso", "edad", "descripcion"})
public class Juego {
    private String nombre;
    private String tipo;
    private String genero;
    private String proceso;
    private String edad;
    private String descripcion;

    public Juego() {
    }

    public Juego(String nombre, String tipo, String genero, String proceso, String edad, String descripcion) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.genero = genero;
        this.proceso = proceso;
        this.edad = edad;
        this.descripcion = descripcion;
    }

    @XmlElement(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlElement(name = "tipo")
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @XmlElement(name = "genero")
    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @XmlElement(name = "proceso")
    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    @XmlElement(name = "edad")
    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    @XmlElement(name = "descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
