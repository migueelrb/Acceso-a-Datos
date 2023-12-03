package Ejercicio;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        UsuariosBBDD usersBBDD = new UsuariosBBDD();

        System.out.println("Bienvenido al programa de gestión de usuarios");
        Usuarios user1 = new Usuarios(1, "usuario1", "1234", "Alejandro", "usuario1@gmail.com");
        Usuarios user2 = new Usuarios(2, "usuario2", "1234", "Juan", "usuario2@gmail.com");
        Usuarios user3 = new Usuarios(3, "usuario3", "1234", "Maria", "usuario3@gmail.com");
        Usuarios user4 = new Usuarios(4, "usuario4", "1234", "Miguel", "usuario4@gmail.com");
        Usuarios user5 = new Usuarios(5, "usuario5", "1234", "Belinda", "usuario5@gmail.com");

        try {
            if (usersBBDD.existeUnUsuario(user1) && usersBBDD.existeUnUsuario(user2) && usersBBDD.existeUnUsuario(user3) && usersBBDD.existeUnUsuario(user4) && usersBBDD.existeUnUsuario(user5)) {
                System.out.println("Creando usuarios de prueba...");
                usersBBDD.registrarUsuarios(user1.getUserName(), user1.getPassword(), user1.getName(), user1.getEmail());
                usersBBDD.registrarUsuarios(user2.getUserName(), user2.getPassword(), user2.getName(), user2.getEmail());
                usersBBDD.registrarUsuarios(user3.getUserName(), user3.getPassword(), user3.getName(), user3.getEmail());
                usersBBDD.registrarUsuarios(user4.getUserName(), user4.getPassword(), user4.getName(), user4.getEmail());
                usersBBDD.registrarUsuarios(user5.getUserName(), user5.getPassword(), user5.getName(), user5.getEmail());
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar el usuario. " + e.getMessage());
        }

        int opcion = 0;

        do {
            try {
                System.out.println(menu());
                opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        System.out.println("Introduce el nombre de usuario");
                        String userName = scanner.next();
                        System.out.println("Introduce la contraseña del usuario");
                        String password = scanner.next();
                        System.out.println("Introduce el nombre");
                        String name = scanner.next();
                        System.out.println("Introduce el email del usuario");
                        String email = scanner.next();
                        try {
                            usersBBDD.registrarUsuarios(userName, password, name, email);
                        } catch (SQLException e) {
                            System.err.println("Error al insertar el usuario. " + e.getMessage());
                        }
                        break;
                    case 2:
                        try {
                            System.out.println("Introduce el id del usuario");
                            int idUsuarioConsulta = scanner.nextInt();
                            Usuarios usuarioConsulta = usersBBDD.obtenerUsuarioPorId(idUsuarioConsulta);

                            if (usuarioConsulta != null) {
                                System.out.println(usuarioConsulta);
                            } else {
                                System.out.println("No se encontró un usuario con el ID proporcionado.");
                            }
                        } catch (InputMismatchException e) {
                            // Limpia el búfer del escáner en caso de una entrada incorrecta.
                            scanner.nextLine();
                            System.out.println("Error: El ID debe ser un número.");
                        } catch (SQLException e) {
                            System.err.println("Error al consultar el usuario. " + e.getMessage());
                        }
                        break;
                    case 3:
                        System.out.println("Usuarios registrados:");
                        try {
                            for (Usuarios user : usersBBDD.getUsers()) {
                                System.out.println(user);
                            }
                        } catch (SQLException e) {
                            System.err.println("Error al consultar los usuarios. " + e.getMessage());
                        }
                        System.out.println("Introduce el id del usuario");
                        int idUsuario = scanner.nextInt();
                        System.out.println("Introduce el nombre del usuario");
                        String userNameUpdate = scanner.next();
                        System.out.println("Introduce la contraseña del usuario");
                        String passwordUpdate = scanner.next();
                        System.out.println("Introduce el nombre del usuario");
                        String nameUpdate = scanner.next();
                        System.out.println("Introduce el email del usuario");
                        String emailUpdate = scanner.next();
                        try {
                            usersBBDD.actualizarUsuarios(idUsuario, userNameUpdate, passwordUpdate, nameUpdate, emailUpdate);
                        } catch (SQLException e) {
                            System.err.println("Error al actualizar el usuario. " + e.getMessage());
                        }
                        break;
                    case 4:
                        System.out.println("Usuarios registrados:");
                        try {
                            for (Usuarios user : usersBBDD.getUsers()) {
                                System.out.println(user);
                            }

                            System.out.println("Introduce el id del usuario");
                            int idUsuarioDelete = scanner.nextInt();

                            usersBBDD.eliminarUsuario(idUsuarioDelete);
                        } catch (SQLException e) {
                            System.err.println("Error al eliminar el usuario. " + e.getMessage());
                        } catch (InputMismatchException o) {
                            throw new InputMismatchException("El id debe ser un número");
                        }
                        break;
                    case 5:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción no válida");
                        break;
                }
            } catch (InputMismatchException e) {
                // Limpiar el búfer del escáner en caso de una entrada incorrecta.
                scanner.nextLine();
                System.out.println("Por favor, introduce solo números.");
            } catch (NoSuchElementException e) {
                // Limpiar el búfer del escáner en caso de una entrada incorrecta.
                scanner.nextLine();
                System.out.println("Entrada inválida. Introduce un número del 1 al 5.");
            } catch (Exception e) {
                System.err.println("Error inesperado: " + e.getMessage());
            }
        } while (opcion != 5);

    }

    public static String menu() {
        return "1 -> Insertar usuario\n" +
                "2 -> Mostrar usuario\n" +
                "3 -> Actualizar usuario\n" +
                "4 -> Eliminar usuario\n" +
                "5 -> Salir\n" +
                "Selecciona una opción:";
    }
}
