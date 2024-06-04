import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ConsolaDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idConsolaDetails;

    private String procesador;

    private String memoriaRam;

    private String almacenamiento;

    private boolean cuatro_K;

    private boolean hdr;

    public int getIdConsolaDetails() {
        return idConsolaDetails;
    }

    public void setIdConsolaDetails(int idConsolaDetails) {
        this.idConsolaDetails = idConsolaDetails;
    }

    public String getProcesador() {
        return procesador;
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    public String getMemoriaRam() {
        return memoriaRam;
    }

    public void setMemoriaRam(String memoriaRam) {
        this.memoriaRam = memoriaRam;
    }

    public String getAlmacenamiento() {
        return almacenamiento;
    }

    public void setAlmacenamiento(String almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    public boolean isCuatro_K() {
        return cuatro_K;
    }

    public void setCuatro_K(boolean cuatro_K) {
        this.cuatro_K = cuatro_K;
    }

    public boolean isHdr() {
        return hdr;
    }

    public void setHdr(boolean hdr) {
        this.hdr = hdr;
    }
}

