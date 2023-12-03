package Ejercicio;

import java.util.Objects;

public class Usuarios {

    private int idUsuario;
    private String userName;
    private String password;
    private String name;
    private String email;

    public Usuarios(int idUsuario, String userName, String password, String name, String email) {
        this.idUsuario = idUsuario;
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "id: " + idUsuario + ", User Name: " + userName + ", Nombre: " + name + ", Email: " + email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuarios users = (Usuarios) o;
        return userName.equals(users.userName) || email.equals(users.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, email);
    }
}
