package model.dao;

import model.DatabaseConnection;
import model.Servicio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicioDAO {

    public List<Servicio> getAllServicios() throws SQLException {
        String sql = "SELECT * FROM Servicio WHERE activo = 1";
        List<Servicio> servicios = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                servicios.add(mapServicio(rs));
            }
        }
        return servicios;
    }

    public Servicio getServicioById(int idServicio) throws SQLException {
        String sql = "SELECT * FROM Servicio WHERE id_servicio = ? AND activo = 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idServicio);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapServicio(rs);
                }
            }
        }
        return null;
    }

    public boolean updateServicio(Servicio servicio) throws SQLException {
        String sql = "UPDATE Servicio SET descripcion = ?, precio = ?, duracion_minutos = ?, activo = ? WHERE id_servicio = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, servicio.getDescripcion());
            stmt.setDouble(2, servicio.getPrecio());
            stmt.setInt(3, servicio.getDuracionMinutos());
            stmt.setBoolean(4, servicio.getActivo());
            stmt.setInt(5, servicio.getIdServicio());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteServicio(int idServicio) throws SQLException {
        String sql = "UPDATE Servicio SET activo = 0 WHERE id_servicio = ?"; // baja lógica
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idServicio);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean insertServicio(Servicio servicio) throws SQLException {
        String sql = "INSERT INTO Servicio (descripcion, precio, duracion_minutos, activo) VALUES (?, ?, ?, 1)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, servicio.getDescripcion());
            stmt.setDouble(2, servicio.getPrecio());
            stmt.setInt(3, servicio.getDuracionMinutos());

            return stmt.executeUpdate() > 0;
        }
    }

    private Servicio mapServicio(ResultSet rs) throws SQLException {
        Servicio servicio = new Servicio();
        servicio.setIdServicio(rs.getInt("id_servicio"));
        servicio.setDescripcion(rs.getString("descripcion"));
        servicio.setPrecio(rs.getDouble("precio"));
        servicio.setDuracionMinutos(rs.getInt("duracion_minutos"));
        servicio.setActivo(rs.getBoolean("activo"));
        return servicio;
    }
}