package Ejercicio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static DataBaseConnection instance;
    private String url = "jdbc:mysql://localhost:3306/sampledb?serverTimezone=UTC";
    private String usuario = "root";
    private String contraseña = "usuario";
    private Connection conexion;

    private DataBaseConnection() throws SQLException {
        conexion = DriverManager.getConnection(url, usuario, contraseña);
    }

    public static DataBaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DataBaseConnection();
        }
        return instance;
    }

    public void close() throws SQLException {

        if (conexion != null) {
            conexion.close();
        }

    }

    public Connection getConexion() {
        return conexion;
    }
}
