package entitis;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
public class Juego {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Basic
    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;


    //@ManyToOne
    //@JoinColumn(name = "desarrollador_id")
    // private Desarrollador desarrollador;


    @Basic
    @Column(name = "fecha_lanzamiento", nullable = false)
    private Date fechaLanzamiento;

    @Basic
    @Column(name = "plataforma", nullable = false, length = 100)
    private String plataforma;

    // Getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(Date fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }


    // Equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Juego juego = (Juego) o;
        return id == juego.id && Objects.equals(titulo, juego.titulo) && Objects.equals(fechaLanzamiento, juego.fechaLanzamiento) && Objects.equals(plataforma, juego.plataforma);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, fechaLanzamiento, plataforma);
    }
}
