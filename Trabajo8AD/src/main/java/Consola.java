import jakarta.persistence.*;

@Entity
public class Consola {
    @Id
    // Con la anotacion GeneratedValue indicamos que el valor de la clave primaria se genera automaticamente.
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int idConsola;

    private String nombre;

    /**
     * Con la anotacion OneToOne indicamos que una consola solo puede tener un ConsolaDetails.
     */
    @OneToOne
    private ConsolaDetails consolaDetails;

    public int getIdConsola() {
        return idConsola;
    }

    public void setIdConsola(int idConsola) {
        this.idConsola = idConsola;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ConsolaDetails getConsolaDetails() {
        return consolaDetails;
    }

    public void setConsolaDetails(ConsolaDetails consolaDetails) {
        this.consolaDetails = consolaDetails;
    }
}

