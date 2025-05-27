package model.dao;

import model.DatabaseConnection;
import model.Usuario;

import java.sql.*;

public class UsuarioDAO {

    // Inserta un tipo empleado y retorna su id
    public int crearTipoEmpleado(String descripcion, int nivelExperiencia) throws SQLException {
        String sql = "INSERT INTO Tipo_Empleado (descripcion, nivel_experiencia) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, descripcion);
            ps.setInt(2, nivelExperiencia);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // id_tipo_empleado generado
                }
            }
        }
        return -1; // error
    }

    // Crea usuario (empleado) con id_tipo_empleado
    public boolean crearUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO Empleado (nombre, apellido, telefono, email, cargo, pass, id_tipo_empleado, fecha_contratacion, activo) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, CURDATE(), 1)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getTelefono());
            ps.setString(4, usuario.getEmail());
            ps.setString(5, usuario.getCargo());
            ps.setString(6, usuario.getPass());
            ps.setInt(7, usuario.getIdTipoEmpleado()); // FK a Tipo_Empleado
            int filas = ps.executeUpdate();
            return filas > 0;
        }
    }
}
