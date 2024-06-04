package Main;

import entitis.Desarrollador;
import entitis.Juego;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        // Inserción de registros
        insertarRegistros(em);

        // Menú de consultas
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\nMenú de Consultas:");
            System.out.println("1. SELECT todos los juegos");
            System.out.println("2. SELECT todos los desarrolladores");
            System.out.println("3. SELECT los juegos desarrollados por un desarrollador específico");
            System.out.println("4. SELECT los nombres de los desarrolladores de un juego determinado");
            System.out.println("5. UPDATE el título de un juego");
            System.out.println("6. DELETE un juego");
            System.out.println("7. SELECT los juegos lanzados antes de una fecha determinada");
            System.out.println("8. SELECT los juegos lanzados después de una fecha determinada");
            System.out.println("9. SELECT los juegos lanzados en un rango de fechas determinado");
            System.out.println("10. SELECT los juegos lanzados en una plataforma determinada");
            System.out.println("0. Salir");

            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    selectTodosLosJuegos(em);
                    break;
                case 2:
                    selectTodosLosDesarrolladores(em);
                    break;
                case 3:
                    selectJuegosDesarrolladosPorDesarrollador(em);
                    break;
                case 4:
                    selectNombresDesarrolladoresDeJuego(em);
                    break;
                case 5:
                    updateTituloJuego(em);
                    break;
                case 6:
                    deleteJuego(em);
                    break;
                case 7:
                    selectJuegosLanzadosAntesDeFecha(em);
                    break;
                case 8:
                    selectJuegosLanzadosDespuesDeFecha(em);
                    break;
                case 9:
                    selectJuegosLanzadosEnRangoDeFechas(em);
                    break;
                case 10:
                    selectJuegosLanzadosEnPlataforma(em);
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }
        } while (opcion != 0);

        em.close();
        emf.close();
    }

    private static void insertarRegistros(EntityManager em) {
        em.getTransaction().begin();

        // Desarrolladores
        Desarrollador desarrollador1 = new Desarrollador();
        desarrollador1.setNombre("Naughty Dog");
        desarrollador1.setPais("EEUU");
        em.persist(desarrollador1);

        Desarrollador desarrollador2 = new Desarrollador();
        desarrollador2.setNombre("Santa Monica Studio");
        desarrollador2.setPais("EEUU");
        em.persist(desarrollador2);

        Desarrollador desarrollador3 = new Desarrollador();
        desarrollador3.setNombre("Rockstar Games");
        desarrollador3.setPais("EEUU");
        em.persist(desarrollador3);

        Desarrollador desarrollador4 = new Desarrollador();
        desarrollador4.setNombre("Rocksteady Studios");
        desarrollador4.setPais("Reino Unido");
        em.persist(desarrollador4);

        Desarrollador desarrollador5 = new Desarrollador();
        desarrollador5.setNombre("Ubisoft");
        desarrollador5.setPais("Francia");
        em.persist(desarrollador5);

        // Juegos
        Juego juego1 = new Juego();
        juego1.setTitulo("The Last of Us");
        juego1.setFechaLanzamiento(Date.valueOf("2013-06-14"));
        juego1.setPlataforma("PlayStation 3");
        em.persist(juego1);

        Juego juego2 = new Juego();
        juego2.setTitulo("The Last of Us Part II");
        juego2.setFechaLanzamiento(Date.valueOf("2020-06-19"));
        juego2.setPlataforma("PlayStation 4");
        em.persist(juego2);

        Juego juego3 = new Juego();
        juego3.setTitulo("God of War");
        juego3.setFechaLanzamiento(Date.valueOf("2018-04-20"));
        juego3.setPlataforma("PlayStation 4");
        em.persist(juego3);

        Juego juego4 = new Juego();
        juego4.setTitulo("Red Dead Redemption 2");
        juego4.setFechaLanzamiento(Date.valueOf("2018-10-26"));
        juego4.setPlataforma("PlayStation 4");
        em.persist(juego4);

        Juego juego5 = new Juego();
        juego5.setTitulo("Assassin's Creed Valhalla");
        juego5.setFechaLanzamiento(Date.valueOf("2020-11-10"));
        juego5.setPlataforma("PlayStation 5");
        em.persist(juego5);

        em.getTransaction().commit();
    }


    private static void selectTodosLosJuegos(EntityManager em) {
        TypedQuery<Juego> query = em.createQuery("SELECT j FROM Juego j", Juego.class);
        List<Juego> juegos = query.getResultList();

        System.out.println("\nTodos los juegos:");
        for (Juego juego : juegos) {
            System.out.println(juego.getTitulo());
        }

    }

    private static void selectTodosLosDesarrolladores(EntityManager em) {
        TypedQuery<Desarrollador> query = em.createQuery("SELECT d FROM Desarrollador d", Desarrollador.class);
        List<Desarrollador> desarrolladores = query.getResultList();

        System.out.println("\nTodos los desarrolladores:");
        for (Desarrollador desarrollador : desarrolladores) {
            System.out.println(desarrollador.getNombre());
        }


    }


    private static void selectJuegosDesarrolladosPorDesarrollador(EntityManager em) {
        /**
         Scanner scanner = new Scanner(System.in);
         System.out.print("Ingrese el ID del desarrollador: ");
         int desarrolladorId = scanner.nextInt();

         TypedQuery<Juego> query = em.createQuery(
         "SELECT j FROM Juego j WHERE j.desarrollador.id = :desarrolladorId", Juego.class)
         .setParameter("desarrolladorId", desarrolladorId);

         List<Juego> juegos = query.getResultList();

         System.out.println("\nJuegos desarrollados por el desarrollador con ID " + desarrolladorId + ":");
         for (Juego juego : juegos) {
         System.out.println(juego.getTitulo());
         }
         */
    }


    private static void selectNombresDesarrolladoresDeJuego(EntityManager em) {
        /**
         Scanner scanner = new Scanner(System.in);
         System.out.print("Ingrese el ID del juego: ");
         int juegoId = scanner.nextInt();

         TypedQuery<Desarrollador> query = em.createQuery(
         "SELECT d FROM Desarrollador d JOIN d.juegos j WHERE j.id = :juegoId", Desarrollador.class)
         .setParameter("juegoId", juegoId);

         List<Desarrollador> desarrolladores = query.getResultList();

         System.out.println("\nDesarrolladores del juego con ID " + juegoId + ":");
         for (Desarrollador desarrollador : desarrolladores) {
         System.out.println(desarrollador.getNombre());
         }
         */
    }


    private static void updateTituloJuego(EntityManager em) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el ID del juego que desea actualizar: ");
        int juegoId = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        System.out.print("Ingrese el nuevo título del juego: ");
        String nuevoTitulo = scanner.nextLine();

        em.getTransaction().begin();

        Juego juego = em.find(Juego.class, juegoId);
        if (juego != null) {
            juego.setTitulo(nuevoTitulo);
            System.out.println("Título del juego actualizado correctamente.");
        } else {
            System.out.println("Juego con ID " + juegoId + " no encontrado.");
        }

        em.getTransaction().commit();
    }

    private static void deleteJuego(EntityManager em) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el ID del juego que desea eliminar: ");
        int juegoId = scanner.nextInt();

        em.getTransaction().begin();

        Juego juego = em.find(Juego.class, juegoId);
        if (juego != null) {
            em.remove(juego);
            System.out.println("Juego eliminado correctamente.");
        } else {
            System.out.println("Juego con ID " + juegoId + " no encontrado.");
        }

        em.getTransaction().commit();
    }

    private static void selectJuegosLanzadosAntesDeFecha(EntityManager em) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la fecha en formato yyyy-mm-dd: ");
        String fechaStr = scanner.nextLine();
        Date fecha = Date.valueOf(fechaStr);

        TypedQuery<Juego> query = em.createQuery(
                        "SELECT j FROM Juego j WHERE j.fechaLanzamiento < :fecha", Juego.class)
                .setParameter("fecha", fecha);

        List<Juego> juegos = query.getResultList();

        System.out.println("\nJuegos lanzados antes de la fecha " + fechaStr + ":");
        for (Juego juego : juegos) {
            System.out.println(juego.getTitulo());
        }
    }

    private static void selectJuegosLanzadosDespuesDeFecha(EntityManager em) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la fecha en formato yyyy-mm-dd: ");
        String fechaStr = scanner.nextLine();
        Date fecha = Date.valueOf(fechaStr);

        TypedQuery<Juego> query = em.createQuery(
                        "SELECT j FROM Juego j WHERE j.fechaLanzamiento > :fecha", Juego.class)
                .setParameter("fecha", fecha);

        List<Juego> juegos = query.getResultList();

        System.out.println("\nJuegos lanzados después de la fecha " + fechaStr + ":");
        for (Juego juego : juegos) {
            System.out.println(juego.getTitulo());
        }
    }

    private static void selectJuegosLanzadosEnRangoDeFechas(EntityManager em) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la fecha de inicio en formato yyyy-mm-dd: ");
        String fechaInicioStr = scanner.nextLine();
        Date fechaInicio = Date.valueOf(fechaInicioStr);

        System.out.print("Ingrese la fecha de fin en formato yyyy-mm-dd: ");
        String fechaFinStr = scanner.nextLine();
        Date fechaFin = Date.valueOf(fechaFinStr);

        TypedQuery<Juego> query = em.createQuery(
                        "SELECT j FROM Juego j WHERE j.fechaLanzamiento BETWEEN :fechaInicio AND :fechaFin", Juego.class)
                .setParameter("fechaInicio", fechaInicio)
                .setParameter("fechaFin", fechaFin);

        List<Juego> juegos = query.getResultList();

        System.out.println("\nJuegos lanzados en el rango de fechas " + fechaInicioStr + " a " + fechaFinStr + ":");
        for (Juego juego : juegos) {
            System.out.println(juego.getTitulo());
        }
    }

    private static void selectJuegosLanzadosEnPlataforma(EntityManager em) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el nombre de la plataforma: ");
        String plataforma = scanner.nextLine();

        TypedQuery<Juego> query = em.createQuery(
                        "SELECT j FROM Juego j WHERE j.plataforma = :plataforma", Juego.class)
                .setParameter("plataforma", plataforma);

        List<Juego> juegos = query.getResultList();

        System.out.println("\nJuegos lanzados en la plataforma " + plataforma + ":");
        for (Juego juego : juegos) {
            System.out.println(juego.getTitulo());
        }
    }
}


