import jakarta.persistence.*;
import org.hibernate.grammars.hql.HqlParser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Friki {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idFriki;

    /**
     * Con la anotacion ManyToMany indicamos que un friki puede tener varios juegos y un juego puede tener varios frikis.
     * Con la anotacion JoinTable indicamos el nombre de la tabla que se va a crear en la BBDD para relacionar los frikis con los juegos.
     * Con la anotacion JoinColumns indicamos el nombre de la columna que se va a crear en la BBDD para relacionar los frikis con los juegos.
     * Con la anotacion InverseJoinColumns indicamos el nombre de la columna que se va a crear en la BBDD para relacionar los frikis con los juegos.
     */
    @ManyToMany(targetEntity = Juego.class)
    @JoinTable(
            name = "Fiki_has_Juego",
            joinColumns = @JoinColumn(name = "friki_idFriki"),
            inverseJoinColumns = @JoinColumn(name = "juego_idJuego"))
    Set<Juego> juegos = new HashSet<>();


    private String nombre;

    /**
     * Con la anotacion OneToMany indicamos que un friki puede tener varias consolas y una consola puede tener varios frikis.
     */
    @OneToMany(targetEntity = Consola.class)
    private List<Consola> idConsola;

    /**
     * Con la anotacion Embedded indicamos que la clase Direccion es una clase embebida, una clase embebida es una clase que se
     * utiliza como atributo de otra clase.
     */
    @Embedded
    private Direccion direccionCasa;

    /**
     * Con la anotacion Embedded indicamos que la clase Direccion es una clase embebida, una clase embebida es una clase que se
     * utiliza como atributo de otra clase.
     * Con la anotacion AttributeOverrides indicamos que vamos a sobreescribir los atributos de la clase Direccion.
     * Con la anotacion AttributeOverride indicamos que atributo de la clase Direccion vamos a sobreescribir.
     * Con la anotacion Column indicamos el nombre de la columna que se va a crear en la BBDD para relacionar los frikis con los juegos.
     */
    @Embedded
    @AttributeOverrides(  {
            @AttributeOverride(name="calle", column = @Column(name="calle_Facturacion")),
            @AttributeOverride(name="localidad", column = @Column(name="localidad_Facturacion")),
            @AttributeOverride(name="provincia", column = @Column(name="provincia_Facturacion")),
            @AttributeOverride(name="cp", column = @Column(name="cp_Facturacion"))
    })
    private Direccion direcionFacturacion;

    public int getIdFriki() {
        return idFriki;
    }

    public void setIdFriki(int idFriki) {
        this.idFriki = idFriki;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Consola> getIdConsola() {
        return idConsola;
    }

    public void setIdConsola(List<Consola> idConsola) {
        this.idConsola = idConsola;
    }

    public Direccion getDireccionCasa() {
        return direccionCasa;
    }

    public void setDireccionCasa(Direccion direccionCasa) {
        this.direccionCasa = direccionCasa;
    }

    public Direccion getDirecionFacturacion() {
        return direcionFacturacion;
    }

    public void setDirecionFacturacion(Direccion direcionFacturacion) {
        this.direcionFacturacion = direcionFacturacion;
    }

    public void añadirJuego (Juego juego){
        this.juegos.add(juego);
    }

    public Set<Juego> getJuegos() {
        return juegos;
    }
}
