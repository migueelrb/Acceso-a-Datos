import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Juego {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idJuego;


    private String nombre;

    public int getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(int idJuego) {
        this.idJuego = idJuego;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
