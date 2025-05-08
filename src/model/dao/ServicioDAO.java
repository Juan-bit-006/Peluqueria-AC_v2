package model.dao;

import model.DatabaseConnection;
import model.Servicio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicioDAO {

    public List<Servicio> getAllServicios() throws SQLException {
        String sql = "SELECT * FROM Servicio WHERE activo = 1";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Servicio> servicios = new ArrayList<>();

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Servicio servicio = new Servicio();
                servicio.setIdServicio(rs.getInt("id_servicio"));
                servicio.setDescripcion(rs.getString("descripcion"));
                servicio.setPrecio(rs.getDouble("precio"));
                servicio.setDuracionMinutos(rs.getInt("duracion_minutos"));
                servicio.setActivo(rs.getBoolean("activo"));
                servicios.add(servicio);
            }

            return servicios;
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
    }

    public Servicio getServicioById(int idServicio) throws SQLException {
        String sql = "SELECT * FROM Servicio WHERE id_servicio = ? AND activo = 1";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Servicio servicio = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idServicio);
            rs = stmt.executeQuery();





            if (rs.next()) {
                servicio = new Servicio();
                servicio.setIdServicio(rs.getInt("id_servicio"));
                servicio.setDescripcion(rs.getString("descripcion"));
                servicio.setPrecio(rs.getDouble("precio"));
                servicio.setDuracionMinutos(rs.getInt("duracion_minutos"));
                servicio.setActivo(rs.getBoolean("activo"));
            }

            return servicio;
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
    }


    public boolean updateServicio(Servicio servicio) throws SQLException {
        String sql = "UPDATE servicios SET nombre = ?, descripcion = ?, precio = ?, duracion_minutos = ? WHERE id_servicio = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, servicio.getNombre());
            stmt.setString(2, servicio.getDescripcion());
            stmt.setDouble(3, servicio.getPrecio());
            stmt.setInt(4, servicio.getDuracionMinutos());
            stmt.setInt(5, servicio.getIdServicio());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteServicio(int idServicio) throws   SQLException  {
        String sql = "DELETE FROM servicios WHERE id_servicio = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idServicio);
            return stmt.executeUpdate() > 0;
        }
    }
}