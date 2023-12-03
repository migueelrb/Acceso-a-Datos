import java.sql.*;
import java.util.Scanner;

public class BibliotecaVideojuegos {

    public static void main(String[] args) {
        // Establecer la conexión con la base de datos
        String url = "jdbc:mysql://localhost/biblioteca";
        String username = "root";
        String password = "usuario";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Conexión exitosa");

            Scanner scanner = new Scanner(System.in);
            int opcion;

            do {
                System.out.println("Menú de opciones:");
                System.out.println("1. Creación de tabla Videojuegos");
                System.out.println("2. Creación de tabla Observaciones");
                System.out.println("3. Creación de tabla Usuarios");
                System.out.println("4. Inserción de registros en Videojuegos");
                System.out.println("5. Inserción de registros en Observaciones");
                System.out.println("6. Inserción de registros en Usuarios");
                System.out.println("7. Consultar información de observaciones de un usuario");
                System.out.println("8. Salir");
                System.out.print("Elija una opción: ");
                opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        crearTablaVideojuegos(connection);
                        break;
                    case 2:
                        crearTablaObservaciones(connection);
                        break;
                    case 3:
                        crearTablaUsuarios(connection);
                        break;
                    case 4:
                        insertarRegistrosEnVideojuegos(connection);
                        break;
                    case 5:
                        insertarRegistrosEnObservaciones(connection);
                        break;
                    case 6:
                        insertarRegistrosEnUsuarios(connection);
                        break;
                    case 7:
                        consultarObservacionesDeUsuario(connection);
                        break;
                    case 8:
                        System.out.println("Saliendo del programa.");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } while (opcion != 8);
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    /**
     * Este método crea la tabla Videojuegos en la base de datos, para ello realiza una consulta SQL que crea la tabla con los campos idVideojuego, titulo, plataforma, genero y duracion.
     *
     * @param connection
     * @throws SQLException
     */
    public static void crearTablaVideojuegos(Connection connection) throws SQLException {
        String createVideojuegosTable = "CREATE TABLE Videojuegos (idVideojuego INT AUTO_INCREMENT PRIMARY KEY, nombre VARCHAR(45), plataforma VARCHAR(45), genero VARCHAR(45), PEGI VARCHAR(45), precio FLOAT)";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createVideojuegosTable);
            System.out.println("Tabla Videojuegos creada exitosamente");
        } catch (SQLException e) {
            System.out.println("Error al crear la tabla Videojuegos: " + e.getMessage());
        }
    }

    /**
     * Este método crea la tabla Observaciones en la base de datos, para ello realiza una consulta SQL que crea la tabla con los campos idObservacion, idUsuario, idVideojuego, duracion, puntuacion y vecesJugado.
     *
     * @param connection
     * @throws SQLException
     */
    public static void crearTablaObservaciones(Connection connection) throws SQLException {
        String createObservacionesTable = "CREATE TABLE Observaciones (idObservacion INT AUTO_INCREMENT PRIMARY KEY, idUsuario INT, idVideojuego INT, duracion float, puntuacion float, vecesJugado INT, FOREIGN KEY (idUsuario) REFERENCES Usuarios(idUsuario), FOREIGN KEY (idVideojuego) REFERENCES Videojuegos(idVideojuego))";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createObservacionesTable);
            System.out.println("Tabla Observaciones creada exitosamente");
        } catch (SQLException e) {
            System.out.println("Error al crear la tabla Observaciones: " + e.getMessage());
        }
    }

    /**
     * Este método crea la tabla Usuarios en la base de datos, para ello realiza una consulta SQL que crea la tabla con los campos idUsuario, nombre y apellidos.
     *
     * @param connection
     * @throws SQLException
     */
    public static void crearTablaUsuarios(Connection connection) throws SQLException {
        String createUsuariosTable = "CREATE TABLE Usuarios (idUsuario INT AUTO_INCREMENT PRIMARY KEY, nombre VARCHAR(45), apellidos VARCHAR(45))";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createUsuariosTable);
            System.out.println("Tabla Usuarios creada exitosamente");
        } catch (SQLException e) {
            System.out.println("Error al crear la tabla Usuarios: " + e.getMessage());
        }
    }

    /**
     * Este método inserta registros en la tabla Videojuegos, para ello realiza una consulta SQL que inserta 5 registros en la tabla.
     * Los otros dos metodos de abajo hacen lo mismo pero en las tablas Observaciones y Usuarios.
     *
     * @param connection
     * @throws SQLException
     */
    public static void insertarRegistrosEnVideojuegos(Connection connection) throws SQLException {
        String insertVideojuegos = "INSERT INTO Videojuegos (nombre, plataforma, genero, PEGI, precio) VALUES " +
                "('Videojuego 1', 'Plataforma 1', 'Género 1', 'PEGI 1', 19.99), " +
                "('Videojuego 2', 'Plataforma 2', 'Género 2', 'PEGI 2', 29.99), " +
                "('Videojuego 3', 'Plataforma 1', 'Género 1', 'PEGI 1', 39.99), " +
                "('Videojuego 4', 'Plataforma 2', 'Género 2', 'PEGI 2', 49.99), " +
                "('Videojuego 5', 'Plataforma 1', 'Género 1', 'PEGI 1', 59.99)";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(insertVideojuegos);
            System.out.println("Registros insertados en la tabla Videojuegos");
        } catch (SQLException e) {
            System.out.println("Error al insertar registros en la tabla Videojuegos: " + e.getMessage());
        }
    }

    public static void insertarRegistrosEnObservaciones(Connection connection) throws SQLException {
        String insertObservaciones = "INSERT INTO Observaciones (idUsuario, idVideojuego, duracion, puntuacion, vecesJugado) VALUES " +
                "(1, 1, 8.5, 9.0, 1), " +
                "(1, 2, 12.0, 7.5, 2), " +
                "(2, 3, 18.5, 10.0, 3), " +
                "(2, 4, 22.0, 6.5, 1), " +
                "(2, 5, 28.5, 8.0, 2)";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(insertObservaciones);
            System.out.println("Registros insertados en la tabla Observaciones");
        } catch (SQLException e) {
            System.out.println("Error al insertar registros en la tabla Observaciones: " + e.getMessage());
        }
    }

    public static void insertarRegistrosEnUsuarios(Connection connection) throws SQLException {
        String insertUsuarios = "INSERT INTO Usuarios (nombre) VALUES ('Usuario 1'), ('Usuario 2')";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(insertUsuarios);
            System.out.println("Registros insertados en la tabla Usuarios");
        } catch (SQLException e) {
            System.out.println("Error al insertar registros en la tabla Usuarios: " + e.getMessage());
        }
    }

    /**
     * Este metodo consulta las observaciones de un usuario, para ello realiza una consulta SQL que muestra la información de las observaciones del usuario que se le pide al usuario, si no hay observaciones para ese usuario o no existe el usuario, se muestra un mensaje de error.
     *
     * @param connection
     * @throws SQLException
     */
    public static void consultarObservacionesDeUsuario(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduzca el ID del usuario para consultar sus observaciones: ");

        if (scanner.hasNextInt()) {
            int idUsuario = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea después de leer el número

            String selectObservaciones = "SELECT Usuarios.nombre, Videojuegos.nombre, Observaciones.duracion, Observaciones.puntuacion, Observaciones.vecesJugado FROM Observaciones INNER JOIN Usuarios ON Observaciones.idUsuario = Usuarios.idUsuario INNER JOIN Videojuegos ON Observaciones.idVideojuego = Videojuegos.idVideojuego WHERE Usuarios.idUsuario = ?";
            try (PreparedStatement statement = connection.prepareStatement(selectObservaciones)) {
                statement.setInt(1, idUsuario);
                ResultSet resultSet = statement.executeQuery();

                if (!resultSet.isBeforeFirst()) {
                    System.out.println("No hay usuario con ese ID o no hay observaciones para mostrar.");
                } else {
                    System.out.println("Información de las observaciones del usuario " + idUsuario + ":");
                    while (resultSet.next()) {
                        String nombreUsuario = resultSet.getString("nombre");
                        String tituloVideojuego = resultSet.getString("nombre");
                        float duracion = resultSet.getFloat("duracion");
                        float puntuacion = resultSet.getFloat("puntuacion");
                        int vecesCompletado = resultSet.getInt("vecesJugado");
                        System.out.println("Nombre del usuario: " + nombreUsuario);
                        System.out.println("Título del videojuego: " + tituloVideojuego);
                        System.out.println("Duración: " + duracion + " horas");
                        System.out.println("Puntuación: " + puntuacion);
                        System.out.println("Veces completado: " + vecesCompletado);
                        System.out.println("------------------------");
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error al consultar las observaciones: " + e.getMessage());
            }
        } else {
            System.out.println("Por favor, ingrese un ID de usuario válido (número entero).");
            scanner.nextLine(); // Consumir la entrada incorrecta
        }
    }

}


