package Ejercicio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class UsuariosBBDD {

    public static Connection getConnection() throws SQLException {

        DataBaseConnection connection;
        connection = DataBaseConnection.getInstance();
        return connection.getConexion();
    }

    public List<Usuarios> getUsers() throws SQLException {
        List<Usuarios> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM usuarios")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(new Usuarios(resultSet.getInt("idUsuario"), resultSet.getString("usuario"), resultSet.getString("password"), resultSet.getString("nombre"), resultSet.getString("email")
                ));
            }
        }
        return users;
    }

    /**
     * Este método comprueba si el usuario existe en la base de datos. para ello recorre la lista de usuarios y
     * comprueba si el nombre y el email ya existen.
     *
     * @param users;
     * @return
     * @throws SQLException;
     */
    public boolean existeUsuario(Usuarios users) throws SQLException {
        boolean existName = false;
        boolean existEmail = false;
        for (Usuarios user : getUsers()) {
            if (user.getUserName().equals(users.getUserName())) {
                existName = true;
            }
        }
        for (Usuarios user : getUsers()) {
            if (user.getEmail().equals(users.getEmail())) {
                existEmail = true;
            }
        }
        if (existName) {
            throw new SQLException("El nombre ya existe");
        }
        if (existEmail) {
            throw new SQLException("El email ya existe");
        }
        return false;
    }

    /**
     * Este método comprueba si el usuario existe en la base de datos. Para ello recorre la lista de usuarios y si el
     * id del usuario es igual al id del usuario que se le pasa por parámetro devuelve false. Si no es igual devuelve true.
     * @param id
     * @return
     * @throws SQLException
     */
    public boolean existe(int id) throws SQLException {
        boolean existe = true;

        for (Usuarios user : getUsers()) {
            if(user.getIdUsuario() != id){
                existe = false;
            }else{
                existe = true;
                break;
            }
        }

        if (!existe) {
            throw new SQLException("El usuario no existe");
        }

        return existe;
    }

    /**
     * Este método registra el usuario en la base de datos. Para ello comprueba si el usuario existe en la base de datos.
     * Si existe, lanza una excepción. Si no existe, registra el usuario.
     * @param userName
     * @param password
     * @param name
     * @param email
     * @throws SQLException
     */
    public void registrarUsuarios(String userName, String password, String name, String email) throws SQLException {
        if (userName.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty()) {
            throw new SQLException("Los campos no pueden estar vacíos");
        }
        if (existeUsuario(new Usuarios(0, userName, password, name, email))) {
            throw new SQLException("El usuario ya existe");
        } else {
            try (PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO usuarios VALUES (?,?, ?, ?, ?)")) {
                preparedStatement.setInt(1, 0);
                preparedStatement.setString(2, userName);
                preparedStatement.setString(3, password);
                preparedStatement.setString(4, name);
                preparedStatement.setString(5, email);
                preparedStatement.executeUpdate();
            }
        }
        System.out.println("Usuario registrado en la base de datos");
    }

    /**
     * Este método elimina el usuario de la base de datos. Para ello comprueba si el usuario existe en la base de datos.
     * Si existe, elimina el usuario.
     * @param id
     * @throws SQLException
     * @throws InputMismatchException
     */
    public void eliminarUsuario(int id) throws SQLException, InputMismatchException {
        if(existe(id)) {
            try (PreparedStatement preparedStatement = getConnection().prepareStatement("DELETE FROM usuarios WHERE idUsuario = ?")) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }
        }
    }

    /**
     * Este método actualiza los datos del usuario. Para ello comprueba si el usuario existe en la base de datos.
     * Si existe, comprueba que los campos no estén vacíos. Si no están vacíos, actualiza los datos del usuario.
     * @param idUser
     * @param userName
     * @param password
     * @param name
     * @param email
     * @throws SQLException
     */
    public void actualizarUsuarios(int idUser, String userName, String password, String name, String email) throws SQLException {


        if(existe(idUser)) {
            if (userName.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty()) {
                throw new SQLException("Los campos no pueden estar vacíos");
            } else {
                try (PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE usuarios SET usuario = ?, password = ?, nombre = ?, email = ? WHERE idUsuario = ?")) {
                    preparedStatement.setString(1, userName);
                    preparedStatement.setString(2, password);
                    preparedStatement.setString(3, name);
                    preparedStatement.setString(4, email);
                    preparedStatement.setInt(5, idUser);
                    preparedStatement.executeUpdate();
                }
            }
        }
        System.out.println("Usuario actualizado en la base de datos");
    }

    /**
     * Este método comprueba si el usuario existe en la base de datos. para ello recorre la lista de usuarios y si el
     * id del usuario es igual al id del usuario que se le pasa por parámetro devuelve false. Si no es igual devuelve true.
     * @param user
     * @return
     * @throws SQLException
     */
    public boolean existeUnUsuario(Usuarios user) throws SQLException {
        boolean existe = true;
        for (Usuarios usuario : getUsers()) {
            if (usuario.getIdUsuario() == user.getIdUsuario()) {
                existe = false;
            }
        }
        return existe;
    }

    public Usuarios obtenerUsuarioPorId(int id) throws SQLException {
        for (Usuarios user : getUsers()) {
            if (user.getIdUsuario() == id) {
                return user;
            }
        }
        return null;
    }

}