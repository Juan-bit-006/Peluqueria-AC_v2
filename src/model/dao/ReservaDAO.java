// ReservaDAO
package model.dao;

import model.DatabaseConnection;
import model.Reserva;
import model.ReservaServicio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    public int insertReserva(Reserva reserva) throws SQLException {
        String sql = "INSERT INTO Reserva (id_cliente, fecha, id_estado, comentarios) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        int reservaId = -1;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, reserva.getIdCliente());
            stmt.setDate(2, reserva.getFecha());
            stmt.setInt(3, reserva.getIdEstado());
            stmt.setString(4, reserva.getComentarios());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    reservaId = generatedKeys.getInt(1);
                    reserva.setIdReserva(reservaId);
                }

                // Insert services for the reservation
                ReservaServicioDAO reservaServicioDAO = new ReservaServicioDAO();
                for (ReservaServicio servicio : reserva.getServicios()) {
                    servicio.setIdReserva(reservaId);
                    reservaServicioDAO.insertReservaServicio(servicio, conn);
                }
            }

            conn.commit();
            return reservaId;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new SQLException("Error during rollback: " + ex.getMessage());
                }
            }
            throw e;
        } finally {
            if (generatedKeys != null) generatedKeys.close();
            if (stmt != null) stmt.close();
            if (conn != null) {
                conn.setAutoCommit(true);
            }
        }
    }

    public List<ReservaServicio> getAllReservaServicios() throws SQLException {
        String sql = "SELECT rs.*, c.nombre, c.apellido, c.telefono, s.descripcion AS servicio_descripcion " +
                "FROM Reserva_Servicio rs " +
                "JOIN Reserva r ON rs.id_reserva = r.id_reserva " +
                "JOIN Cliente c ON r.id_cliente = c.id_cliente " +
                "JOIN Servicio s ON rs.id_servicio = s.id_servicio " +
                "ORDER BY r.fecha DESC, rs.hora_inicio ASC";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<ReservaServicio> reservaServicios = new ArrayList<>();

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                ReservaServicio reservaServicio = new ReservaServicio();
                reservaServicio.setIdReservaServicio(rs.getInt("id_reserva_servicio"));
                reservaServicio.setIdReserva(rs.getInt("id_reserva"));
                reservaServicio.setIdServicio(rs.getInt("id_servicio"));
                reservaServicio.setIdEmpleado(rs.getInt("id_empleado"));
                reservaServicio.setHoraInicio(rs.getTime("hora_inicio"));
                reservaServicio.setHoraFin(rs.getTime("hora_fin"));
                reservaServicio.setPrecioAplicado(rs.getDouble("precio_aplicado"));
                reservaServicio.setNombreCliente(rs.getString("nombre"));
                reservaServicio.setApellidoCliente(rs.getString("apellido"));
                reservaServicio.setTelefonoCliente(rs.getString("telefono"));
                reservaServicio.setDescripcionServicio(rs.getString("servicio_descripcion"));
                reservaServicios.add(reservaServicio);
            }

            return reservaServicios;
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
    }
}
