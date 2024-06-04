import jakarta.persistence.Embeddable;

// Con la anotacion Embeddable indicamos que esta clase es una clase embebida, una clase embebida es una clase que se
// utiliza como atributo de otra clase.
@Embeddable

public class Direccion {

    private String calle;

    private String localidad;

    private String provincia;

    private int cp;

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }
}
