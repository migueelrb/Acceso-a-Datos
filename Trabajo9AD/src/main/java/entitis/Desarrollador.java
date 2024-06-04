package entitis;

import jakarta.persistence.*;


import java.util.List;
import java.util.Objects;

@Entity
public class Desarrollador {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Basic
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    // @OneToMany(mappedBy = "desarrollador", fetch = FetchType.LAZY)
    //  private List<Juego> juegos;


    @Basic
    @Column(name = "pais", nullable = false, length = 100)
    private String pais;

    // Getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }


    // Equals y hashCode


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Desarrollador that = (Desarrollador) o;
        return id == that.id && Objects.equals(nombre, that.nombre) && Objects.equals(pais, that.pais);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, pais);
    }
}

