package model.dao;

import model.DatabaseConnection;
import model.Empleado;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    public Empleado login(String apellido, String password) throws SQLException {
        String sql = "SELECT * FROM Empleado WHERE apellido = ? AND pass = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Empleado empleado = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, apellido);
            stmt.setString(2, password);
            rs = stmt.executeQuery();

            if (rs.next()) {
                empleado = new Empleado();
                empleado.setIdEmpleado(rs.getInt("id_empleado"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setApellido(rs.getString("apellido"));
                empleado.setTelefono(rs.getString("telefono"));
                empleado.setEmail(rs.getString("email"));
                empleado.setCargo(rs.getString("cargo"));
                empleado.setIdTipoEmpleado(rs.getInt("id_tipo_empleado"));
                empleado.setFechaContratacion(rs.getDate("fecha_contratacion"));
                empleado.setActivo(rs.getBoolean("activo"));
            }

            return empleado;
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
    }

    public List<Empleado> getAllEmpleados() throws SQLException {
        String sql = "SELECT * FROM Empleado WHERE activo = 1";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Empleado> empleados = new ArrayList<>();

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setIdEmpleado(rs.getInt("id_empleado"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setApellido(rs.getString("apellido"));
                empleado.setTelefono(rs.getString("telefono"));
                empleado.setEmail(rs.getString("email"));
                empleado.setCargo(rs.getString("cargo"));
                empleado.setIdTipoEmpleado(rs.getInt("id_tipo_empleado"));
                empleado.setFechaContratacion(rs.getDate("fecha_contratacion"));
                empleado.setActivo(rs.getBoolean("activo"));
                empleados.add(empleado);
            }

            return empleados;
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
    }
}