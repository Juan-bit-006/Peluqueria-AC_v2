package controller;

import model.Usuario;
import model.dao.UsuarioDAO;
import java.sql.SQLException;

public class ControladorUsuario {

    private UsuarioDAO usuarioDAO;

    public ControladorUsuario() {
        usuarioDAO = new UsuarioDAO();
    }

    public void crearUsuario(String nombre, String apellido, String telefono, String email, String cargo, String pass, int idTipoEmpleado) {
        try {
            Usuario usuario = new Usuario(nombre, apellido, telefono, email, cargo, pass, idTipoEmpleado);
            usuarioDAO.crearUsuario(usuario);
            System.out.println("Usuario creado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al crear usuario: " + e.getMessage());
            // Aquí podrías lanzar un mensaje al GUI o hacer log de error
        }
    }
}
