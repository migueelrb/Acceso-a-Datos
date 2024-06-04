import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Clase principal del programa
public class App {
    public static void main(String[] args) {
        // Creación de consolas, detalles de consolas, dirección, friki y juegos
        List<Consola> consolas = new ArrayList<>();
        Set<Juego> juegos = new HashSet<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("juegos");
        EntityManager em = emf.createEntityManager();

        ConsolaDetails cD= new ConsolaDetails();
        cD.setProcesador("AMD Ryzen 5 3600 6-Core");
        cD.setMemoriaRam("16GB");
        cD.setAlmacenamiento("1TB");
        cD.setCuatro_K(true);
        cD.setHdr(true);

        ConsolaDetails cD2= new ConsolaDetails();
        cD2.setProcesador("iNTEL i5 3600 6-Core");
        cD2.setMemoriaRam("8GB");
        cD2.setAlmacenamiento("256GB");
        cD2.setCuatro_K(false);
        cD2.setHdr(false);

        Consola c= new Consola();
        c.setNombre("PS3");
        c.setConsolaDetails(cD);

        Consola c2= new Consola();
        c2.setNombre("PS2");
        c2.setConsolaDetails(cD2);

        consolas.add(c);
        consolas.add(c2);

        Direccion d1 = new Direccion();
        d1.setCalle("Calle España");
        d1.setLocalidad("Isla Cristina");
        d1.setProvincia("Huelva");
        d1.setCp(21000);

        Direccion d2 = new Direccion();
        d2.setCalle("Calle Baja");
        d2.setLocalidad("Ayamonte");
        d2.setProvincia("Huelva");
        d2.setCp(21000);

        Friki friki1 = new Friki();
        friki1.setNombre("Mateo");
        friki1.setIdConsola(consolas);
        friki1.setDireccionCasa(d1);
        friki1.setDirecionFacturacion(d2);

        Juego j = new Juego();
        j.setNombre("God of War");

        Juego j2 = new Juego();
        j2.setNombre("Fortnite");

        Juego j3 = new Juego();
        j3.setNombre("GTA V");

        friki1.añadirJuego(j);
        friki1.añadirJuego(j2);
        friki1.añadirJuego(j3);

        // Inicio de transacciones y persistencia en la base de datos
        em.getTransaction().begin();
        em.persist(cD);
        em.persist(cD2);
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.persist(c);
        em.persist(c2);
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.persist(j);
        em.persist(j2);
        em.persist(j3);
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.persist(friki1);
        em.getTransaction().commit();

        // Cierre de EntityManager y EntityManagerFactory
        em.close();
        emf.close();
    }
}
