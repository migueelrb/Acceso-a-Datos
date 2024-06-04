package entitis;

import jakarta.persistence.*;

@Entity
@Table(name = "Juego_Desarrollador")
public class JuegoDesarrollador {
    @Id
    @ManyToOne
    @JoinColumn(name = "juego_id")
    private Juego juego;

    @Id
    @ManyToOne
    @JoinColumn(name = "desarrollador_id")
    private Desarrollador desarrollador;

    // getters y setters
    public Juego getJuego() {
        return juego;
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
    }

    public Desarrollador getDesarrollador() {
        return desarrollador;
    }

    public void setDesarrollador(Desarrollador desarrollador) {
        this.desarrollador = desarrollador;
    }
}

