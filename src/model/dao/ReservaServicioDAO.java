package model.dao;

import model.DatabaseConnection;
import model.ReservaServicio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaServicioDAO {

    public List<ReservaServicio> getReservaServiciosByData(String nombre, String apellido, String telefono) throws SQLException {
        String sql = "SELECT c.nombre AS nombre_cliente, c.apellido AS apellido_cliente, c.telefono AS telefono_cliente, " +
                "s.descripcion AS descripcion_servicio, rs.hora_inicio, rs.hora_fin, rs.precio_aplicado " +
                "FROM Reserva_Servicio rs " +
                "JOIN Reserva r ON rs.id_reserva = r.id_reserva " +
                "JOIN Cliente c ON r.id_cliente = c.id_cliente " +
                "JOIN Servicio s ON rs.id_servicio = s.id_servicio " +
                "WHERE c.nombre LIKE ? AND c.apellido LIKE ? AND c.telefono LIKE ? AND c.activo = 1";

        List<ReservaServicio> lista = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, telefono);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ReservaServicio r = new ReservaServicio();
                    r.setNombreCliente(rs.getString("nombre_cliente"));
                    r.setApellidoCliente(rs.getString("apellido_cliente"));
                    r.setTelefonoCliente(rs.getString("telefono_cliente"));
                    r.setDescripcionServicio(rs.getString("descripcion_servicio"));
                    r.setHoraInicio(rs.getTime("hora_inicio"));
                    r.setHoraFin(rs.getTime("hora_fin"));
                    r.setPrecioAplicado(rs.getDouble("precio_aplicado"));
                    lista.add(r);
                }
            }
        }
        return lista;
    }

    public List<ReservaServicio> getTodasLasReservas() throws SQLException {
        // return getReservaServiciosByData("%", "%", "%"); // ← original

        String sql = "SELECT rs.*, c.nombre, c.apellido, c.telefono, s.descripcion AS servicio_descripcion " +
                "FROM Reserva_Servicio rs " +
                "JOIN Reserva r ON rs.id_reserva = r.id_reserva " +
                "JOIN Cliente c ON r.id_cliente = c.id_cliente " +
                "JOIN Servicio s ON rs.id_servicio = s.id_servicio " +
                "ORDER BY r.fecha DESC, rs.hora_inicio ASC";

        List<ReservaServicio> reservaServicios = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

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
        }
        return reservaServicios;
    }

    public boolean insertReservaServicio(ReservaServicio rs, Connection conn) throws SQLException {
        String sql = "INSERT INTO Reserva_Servicio (id_reserva, id_servicio, id_empleado, hora_inicio, hora_fin, precio_aplicado) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, rs.getIdReserva());
            stmt.setInt(2, rs.getIdServicio());
            stmt.setInt(3, rs.getIdEmpleado());
            stmt.setTime(4, rs.getHoraInicio());
            stmt.setTime(5, rs.getHoraFin());
            stmt.setDouble(6, rs.getPrecioAplicado());
            return stmt.executeUpdate() > 0;
        }
    }
}
