package model.dao;

import model.DatabaseConnection;
import model.ReservaServicio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaServicioDAO {

    public int insertReservaServicio(ReservaServicio reservaServicio, Connection conn) throws SQLException {
        String sql = "INSERT INTO Reserva_Servicio (id_reserva, id_servicio, id_empleado, hora_inicio, hora_fin, precio_aplicado) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        int reservaServicioId = -1;
        boolean externalConnection = (conn != null);

        try {
            if (!externalConnection) {
                conn = DatabaseConnection.getConnection();
            }

            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, reservaServicio.getIdReserva());
            stmt.setInt(2, reservaServicio.getIdServicio());
            stmt.setInt(3, reservaServicio.getIdEmpleado());
            stmt.setTime(4, reservaServicio.getHoraInicio());
            stmt.setTime(5, reservaServicio.getHoraFin());
            stmt.setDouble(6, reservaServicio.getPrecioAplicado());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    reservaServicioId = generatedKeys.getInt(1);
                    reservaServicio.setIdReservaServicio(reservaServicioId);
                }
            }

            return reservaServicioId;
        } finally {
            if (generatedKeys != null) generatedKeys.close();
            if (stmt != null) stmt.close();
            if (!externalConnection && conn != null) {
                conn.close();
            }
        }
    }

    public List<ReservaServicio> getReservaServiciosByData(String nombre, String apellido, String telefono) throws SQLException {
        String sql = "SELECT rs.*, r.fecha, s.descripcion AS servicio_descripcion " +
                "FROM Reserva_Servicio rs " +
                "JOIN Reserva r ON rs.id_reserva = r.id_reserva " +
                "JOIN Cliente c ON r.id_cliente = c.id_cliente " +
                "JOIN Servicio s ON rs.id_servicio = s.id_servicio " +
                "WHERE c.nombre LIKE ? AND c.apellido LIKE ? AND c.telefono LIKE ? " +
                "ORDER BY r.fecha DESC, rs.hora_inicio ASC";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ReservaServicio> reservaServicios = new ArrayList<>();

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + nombre + "%");
            stmt.setString(2, "%" + apellido + "%");
            stmt.setString(3, "%" + telefono + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ReservaServicio reservaServicio = new ReservaServicio();
                reservaServicio.setIdReservaServicio(rs.getInt("id_reserva_servicio"));
                reservaServicio.setIdReserva(rs.getInt("id_reserva"));
                reservaServicio.setIdServicio(rs.getInt("id_servicio"));
                reservaServicio.setIdEmpleado(rs.getInt("id_empleado"));
                reservaServicio.setHoraInicio(rs.getTime("hora_inicio"));
                reservaServicio.setHoraFin(rs.getTime("hora_fin"));
                reservaServicio.setPrecioAplicado(rs.getDouble("precio_aplicado"));
                reservaServicio.setDescripcionServicio(rs.getString("servicio_descripcion"));
                reservaServicios.add(reservaServicio);
            }

            return reservaServicios;
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }
}