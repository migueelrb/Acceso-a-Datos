package Transacciones;

import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class ProgramaBanca {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banca", "root", "usuario");

            if (!existeTablaClientes(connection)) {
                crearTablaClientes(connection);
            }

            if (!existeTablaCuentas(connection)) {
                crearTablaCuentas(connection);
            }

            if (!existeTablaTransacciones(connection)) {
                crearTablaTransacciones(connection);
            }

            // Crear al menos 4 clientes con cuentas al ejecutar el programa
            crearClientesAutomaticamente(connection, 4, 2);

            // Ejecutar el menú
            ejecutarMenu(connection);

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void crearClientesAutomaticamente(Connection connection, int numClientes, int maxCuentasPorCliente) throws SQLException {
        Random random = new Random();

        for (int i = 0; i < numClientes; i++) {
            // Generar datos aleatorios para el cliente
            String nombre = "Cliente" + i;
            String apellidos = "Apellido" + i;

            // Verificar si el cliente ya existe
            int idCliente;
            if (existeCliente(connection, nombre, apellidos)) {
                idCliente = obtenerIdClienteExistente(connection, nombre, apellidos);
            } else {
                // Insertar un nuevo cliente
                idCliente = insertarNuevoCliente(connection, nombre, apellidos);
            }

            // Generar cuentas aleatorias para el cliente
            int numCuentas = random.nextInt(maxCuentasPorCliente) + 1;
            for (int j = 0; j < numCuentas; j++) {
                double saldoInicial = random.nextDouble() * 1000; // Saldo inicial aleatorio

                // Insertar una nueva cuenta para el cliente
                insertarNuevaCuenta(connection, saldoInicial, idCliente);
            }
        }
    }

    /**
     * Este método verifica si existe un cliente con el nombre y apellidos especificados.
     *
     * @param connection
     * @param nombre
     * @param apellidos
     * @return true si el cliente existe, false si no
     * @throws SQLException
     */
    public static boolean existeCliente(Connection connection, String nombre, String apellidos) throws SQLException {
        String query = "SELECT idCliente FROM clientes WHERE nombre = ? AND apellidos = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nombre);
            statement.setString(2, apellidos);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }


    /**
     * Este método verifica si existe un cliente con el nombre y apellidos especificados.
     *
     * @param connection
     * @param nombre
     * @param apellidos
     * @return
     * @throws SQLException
     */
    public static int obtenerIdClienteExistente(Connection connection, String nombre, String apellidos) throws SQLException {
        String query = "SELECT idCliente FROM clientes WHERE nombre = ? AND apellidos = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nombre);
            statement.setString(2, apellidos);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("idCliente");
                } else {
                    throw new SQLException("Error al obtener el ID del cliente existente.");
                }
            }
        }
    }


    public static int insertarNuevoCliente(Connection connection, String nombre, String apellidos) throws SQLException {
        String query = "INSERT INTO clientes (nombre, apellidos) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, nombre);
            statement.setString(2, apellidos);
            statement.executeUpdate();

            // Obtener el ID del cliente recién insertado
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Error al obtener el ID del cliente.");
                }
            }
        }
    }

    public static void insertarNuevaCuenta(Connection connection, double saldoInicial, int idCliente) throws SQLException {
        String query = "INSERT INTO cuentas (saldo, idCliente) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, saldoInicial);
            statement.setInt(2, idCliente);
            statement.executeUpdate();
        }
    }


    /**
     * Este método ejecuta el menú principal del programa y se le pide al usuario que introduzca una opción.
     * En función de la opción introducida, se ejecuta una acción u otra.
     *
     * @param connection
     * @throws SQLException
     */
    public static void ejecutarMenu(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenu();
            System.out.print("Seleccione una opción: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, introduzca un número válido.");
                scanner.next();
            }
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    // Consultar saldo
                    System.out.println("Introduce el id del cliente: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Por favor, introduzca un número válido para el id del cliente.");
                        scanner.next();
                    }
                    int idCliente = scanner.nextInt();

                    System.out.println("Introduce el id de la cuenta: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Por favor, introduzca un número válido para el id de la cuenta.");
                        scanner.next();
                    }
                    int idCuenta = scanner.nextInt();

                    consultarSaldo(connection, idCliente, idCuenta);
                    break;
                case 2:
                    // Consultar historial de la cuenta
                    System.out.println("Introduce el id de la cuenta: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Por favor, introduzca un número válido para el id de la cuenta.");
                        scanner.next();
                    }
                    int idCuentaHistorial = scanner.nextInt();
                    consultarHistorialCuenta(connection, idCuentaHistorial);
                    break;
                case 3:
                    // Realizar transacción
                    System.out.println("Introduce el id de la cuenta origen: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Por favor, introduzca un número válido para el id de la cuenta origen.");
                        scanner.next();
                    }
                    int idCuentaOrigen = scanner.nextInt();

                    System.out.println("Introduce el id de la cuenta destino: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Por favor, introduzca un número válido para el id de la cuenta destino.");
                        scanner.next();
                    }
                    int idCuentaDestino = scanner.nextInt();

                    System.out.println("Introduce el saldo a mover: ");
                    while (!scanner.hasNextDouble()) {
                        System.out.println("Por favor, introduzca un número válido para el saldo a mover.");
                        scanner.next();
                    }
                    double saldoMovido = scanner.nextDouble();

                    realizarTransaccion(connection, idCuentaOrigen, idCuentaDestino, saldoMovido);
                    break;
                case 4:
                    // Añadir cliente y cuenta
                    insertarRegistrosClientes(connection);
                    insertarRegistrosCuentas(connection);
                    break;
                case 5:
                    // Salir
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    // Si introduce una opción fuera del rango 1-5
                    System.out.println("Opción no válida.");
                    break;
            }

        } while (opcion != 5);
    }

    /**
     * Este método comprueba si existe la tabla clientes en la base de datos, los dos siguientes métodos son
     * similares a este, para comprobar si existen las tablas cuentas y transacciones.
     *
     * @param connection
     * @return
     * @throws SQLException
     */
    public static boolean existeTablaClientes(Connection connection) throws SQLException {
        String query = "SHOW TABLES LIKE 'clientes'";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public static boolean existeTablaCuentas(Connection connection) throws SQLException {
        String query = "SHOW TABLES LIKE 'cuentas'";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public static boolean existeTablaTransacciones(Connection connection) throws SQLException {
        String query = "SHOW TABLES LIKE 'transacciones'";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public static void mostrarMenu() {
        System.out.println("\nMenú:");
        System.out.println("1. Consultar saldo");
        System.out.println("2. Consultar historial de la cuenta");
        System.out.println("3. Realizar transacción");
        System.out.println("4. Añadir cliente");
        System.out.println("5. Salir");
    }


    /**
     * Este método crea la tabla clientes en la base de datos, para ello se ejecuta una query con el comando que creará la tabla, y se
     * ejecuta con el método executeUpdate. Los dos siguientes métodos son igual que este pero para crear las tablas cliente y transacciones.
     *
     * @param connection
     * @throws SQLException
     */
    public static void crearTablaCuentas(Connection connection) throws SQLException {
        String query = "CREATE TABLE cuentas (idCuenta INT AUTO_INCREMENT PRIMARY KEY, saldo FLOAT, idCliente INT, FOREIGN KEY (idCliente) REFERENCES clientes(idCliente))";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        }
    }

    public static void crearTablaClientes(Connection connection) throws SQLException {
        String query = "CREATE TABLE clientes (idCliente INT AUTO_INCREMENT PRIMARY KEY, nombre VARCHAR(45), apellidos VARCHAR(45), antiguedad DATETIME)";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        }
    }

    public static void crearTablaTransacciones(Connection connection) throws SQLException {
        String query = "CREATE TABLE transacciones (idTransaccion INT AUTO_INCREMENT PRIMARY KEY, idCuentaOrigen INT, idCuentaDestino INT, saldoMovido FLOAT, tipo VARCHAR(45), fecha DATETIME, FOREIGN KEY (idCuentaOrigen) REFERENCES cuentas(idCuenta), FOREIGN KEY (idCuentaDestino) REFERENCES cuentas(idCuenta))";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        }
    }

    /**
     * Este método inserta un registro en la tabla cuentas, para ello se pide al usuario que introduzca el saldo y el id del cliente,
     * y se ejecuta una query con el comando que insertará el registro en la tabla. El siguiente método es igual que este pero para
     * insertar un registro en la tabla clientes.
     *
     * @param connection
     * @throws SQLException
     */
    public static void insertarRegistrosCuentas(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        // Se elimina la solicitud del id del cliente
        System.out.println("Introduce el saldo de la cuenta: ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Por favor, introduzca un número válido para el saldo de la cuenta.");
            scanner.next();
        }
        double saldo = scanner.nextDouble();

        // Insertar el registro en la tabla cuentas
        String queryCuentas = "INSERT INTO cuentas (saldo, idCliente) VALUES (?, ?)";
        try (PreparedStatement statementCuentas = connection.prepareStatement(queryCuentas, Statement.RETURN_GENERATED_KEYS)) {
            statementCuentas.setDouble(1, saldo);

            // Se obtiene el ID del cliente existente
            System.out.println("Introduce el id del cliente existente: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, introduzca un número válido para el id del cliente.");
                scanner.next();
            }
            int idCliente = scanner.nextInt();

            statementCuentas.setInt(2, idCliente);
            statementCuentas.executeUpdate();

            // Obtener el ID de la cuenta recién insertada
            try (ResultSet generatedKeys = statementCuentas.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idCuenta = generatedKeys.getInt(1);
                    System.out.println("Registro de cuenta insertado correctamente. ID de la cuenta: " + idCuenta);
                } else {
                    System.out.println("Error al obtener el ID de la cuenta.");
                }
            }
        }
    }


    public static void insertarRegistrosClientes(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        int idCliente;
        boolean idClienteExistente;

        do {
            System.out.println("Introduce el id del cliente: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, introduzca un número válido para el id del cliente.");
                scanner.next();
            }
            idCliente = scanner.nextInt();

            // Verificar si el ID del cliente ya existe
            idClienteExistente = existeCliente(connection, idCliente);

            if (idClienteExistente) {
                System.out.println("Ya existe un usuario con el ID " + idCliente + ". Por favor, introduce otro ID.");
            }

        } while (idClienteExistente);

        // Resto del código para insertar el registro
        System.out.println("Introduce el nombre del cliente: ");
        String nombre = scanner.next();

        System.out.println("Introduce los apellidos del cliente: ");
        String apellidos = scanner.next();

        String query = "INSERT INTO clientes (idCliente, nombre, apellidos) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idCliente);
            statement.setString(2, nombre);
            statement.setString(3, apellidos);
            statement.executeUpdate();
            System.out.println("Registro de cliente insertado correctamente.");
        }
    }

    /**
     * Este método verifica si existe un cliente con el ID especificado.
     *
     * @param connection
     * @param idCliente
     * @return true si el cliente existe, false si no
     * @throws SQLException
     */
    public static boolean existeCliente(Connection connection, int idCliente) throws SQLException {
        String query = "SELECT idCliente FROM clientes WHERE idCliente = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idCliente);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }


    /**
     * Este método realiza una transacción entre dos cuentas, para ello se ejecutan varias queries con los comandos que insertarán
     * el registro en la tabla transacciones y actualizarán los saldos de las cuentas. El método se ejecuta dentro de un bloque try-catch,
     * y si se produce un error, se realiza un rollback.
     *
     * @param connection
     * @param idCuentaOrigen
     * @param idCuentaDestino
     * @param saldoMovido
     * @throws SQLException
     */
    public static void realizarTransaccion(Connection connection, int idCuentaOrigen, int idCuentaDestino, double saldoMovido) throws SQLException {
        // Verificar si el saldo en la cuenta de origen es suficiente
        if (!saldoSuficiente(connection, idCuentaOrigen, saldoMovido)) {
            System.out.println("No tienes suficiente dinero en la cuenta origen. Operación cancelada.");
            return;
        }

        // Determinar si la transacción es a favor o en contra
        String tipoTransaccion = (saldoMovido >= 0) ? "a favor" : "en contra";

        // Iniciar la transacción
        try {
            connection.setAutoCommit(false);

            // Insertar el registro en la tabla transacciones
            String queryTransaccion = "INSERT INTO transacciones (idCuentaOrigen, idCuentaDestino, saldoMovido, tipo) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statementTransaccion = connection.prepareStatement(queryTransaccion, Statement.RETURN_GENERATED_KEYS)) {
                statementTransaccion.setInt(1, idCuentaOrigen);
                statementTransaccion.setInt(2, idCuentaDestino);
                statementTransaccion.setDouble(3, saldoMovido);
                statementTransaccion.setString(4, tipoTransaccion);
                statementTransaccion.executeUpdate();
            }

            // Actualizar saldos de cuentas
            actualizarSaldos(connection, idCuentaOrigen, idCuentaDestino, saldoMovido);

            // Confirmar la transacción
            connection.commit();

        } catch (SQLException e) {
            // En caso de error, realizar un rollback
            connection.rollback();
            throw e;
        } finally {
            // Restaurar la configuración de autocommit
            connection.setAutoCommit(true);
        }
    }


    /**
     * Este método verifica si el saldo en la cuenta es suficiente para realizar la transacción.
     *
     * @param connection
     * @param idCuenta
     * @param saldoMovido
     * @return true si el saldo es suficiente, false si no
     * @throws SQLException
     */
    public static boolean saldoSuficiente(Connection connection, int idCuenta, double saldoMovido) throws SQLException {
        String query = "SELECT saldo FROM cuentas WHERE idCuenta = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idCuenta);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    double saldoActual = resultSet.getDouble("saldo");
                    return saldoActual >= saldoMovido;
                } else {
                    System.out.println("No se encontró la cuenta especificada");
                    return false;
                }
            }
        }
    }


    /**
     * Este método consulta el saldo de una cuenta, para ello se ejecuta una query con el comando que seleccionará el saldo de la cuenta,
     * y se muestra por pantalla.
     *
     * @param connection
     * @param idCliente
     * @param idCuenta
     * @throws SQLException
     */
    public static void consultarSaldo(Connection connection, int idCliente, int idCuenta) throws SQLException {
        String query = "SELECT saldo FROM cuentas WHERE idCliente = ? AND idCuenta = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idCliente);
            statement.setInt(2, idCuenta);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    double saldo = resultSet.getDouble("saldo");
                    System.out.println("El saldo de la cuenta es: " + saldo);
                } else {
                    System.out.println("No se encontró la cuenta especificada");
                }
            }
        }
    }

    /**
     * Este método actualiza los saldos de las cuentas de origen y destino, para ello se ejecutan dos queries con los comandos que
     * actualizarán los saldos de las cuentas.
     *
     * @param connection
     * @param idCuentaOrigen
     * @param idCuentaDestino
     * @param saldoMovido
     * @throws SQLException
     */
    private static void actualizarSaldos(Connection connection, int idCuentaOrigen, int idCuentaDestino, double saldoMovido) throws SQLException {
        // Actualizar saldo de la cuenta de origen
        String queryUpdateOrigen = "UPDATE cuentas SET saldo = saldo - ? WHERE idCuenta = ?";
        try (PreparedStatement statementUpdateOrigen = connection.prepareStatement(queryUpdateOrigen)) {
            statementUpdateOrigen.setDouble(1, saldoMovido);
            statementUpdateOrigen.setInt(2, idCuentaOrigen);
            statementUpdateOrigen.executeUpdate();
        }

        // Actualizar saldo de la cuenta de destino
        String queryUpdateDestino = "UPDATE cuentas SET saldo = saldo + ? WHERE idCuenta = ?";
        try (PreparedStatement statementUpdateDestino = connection.prepareStatement(queryUpdateDestino)) {
            statementUpdateDestino.setDouble(1, saldoMovido);
            statementUpdateDestino.setInt(2, idCuentaDestino);
            statementUpdateDestino.executeUpdate();
        }
    }

    /**
     * Este método consulta el historial de una cuenta, para ello se ejecuta una query con el comando que seleccionará los registros
     * de la tabla transacciones que tengan como idCuentaOrigen o idCuentaDestino el id de la cuenta introducido por el usuario, y se
     * muestran por pantalla.
     *
     * @param connection
     * @param idCuenta
     * @throws SQLException
     */
    public static void consultarHistorialCuenta(Connection connection, int idCuenta) throws SQLException {
        String query = "SELECT * FROM transacciones WHERE idCuentaOrigen = ? OR idCuentaDestino = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idCuenta);
            statement.setInt(2, idCuenta);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int idTransaccion = resultSet.getInt("idTransaccion");
                    int idCuentaOrigen = resultSet.getInt("idCuentaOrigen");
                    int idCuentaDestino = resultSet.getInt("idCuentaDestino");
                    double saldoMovido = resultSet.getDouble("saldoMovido");
                    String tipo = resultSet.getString("tipo");
                    System.out.println("Transacción: " + idTransaccion);
                    System.out.println("Cuenta origen: " + idCuentaOrigen);
                    System.out.println("Cuenta destino: " + idCuentaDestino);
                    System.out.println("Saldo movido: " + saldoMovido);
                    System.out.println("Tipo: " + tipo);
                    System.out.println("------------------------");
                }
            }
        }
    }
}
