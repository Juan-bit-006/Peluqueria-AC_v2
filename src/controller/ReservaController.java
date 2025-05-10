package controller;
import model.DatabaseConnection;
import model.Reserva;
import model.ReservaServicio;
import model.Servicio;
import model.dao.ReservaDAO;
import model.dao.ReservaServicioDAO;
import model.dao.ServicioDAO;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class ReservaController {
    private ReservaDAO reservaDAO;
    private ReservaServicioDAO reservaServicioDAO;
    private ServicioDAO servicioDAO;

    public ReservaController() {
        this.reservaDAO = new ReservaDAO();
        this.reservaServicioDAO = new ReservaServicioDAO();
        this.servicioDAO = new ServicioDAO();
    }

    public boolean crearReserva(Reserva reserva) {
        try {
            int id = reservaDAO.insertReserva(reserva);
            return id > 0;
        } catch (SQLException e) {
            System.err.println("Error creando reserva: " + e.getMessage());
            return false;
        }
    }

    public List<ReservaServicio> obtenerTodasReservas() {
        try {
            return reservaServicioDAO.getTodasLasReservas();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<ReservaServicio> buscarReservasPorCliente(String nombre, String apellido, String telefono) {
        try {
            return reservaServicioDAO.getReservaServiciosByData(nombre, apellido, telefono);
        } catch (SQLException e) {
            System.err.println("Error buscando reservas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean agregarServicioAReserva(Reserva reserva, int idServicio, int idEmpleado, Time horaInicio) {
        try {
            Servicio servicio = servicioDAO.getServicioById(idServicio);
            if (servicio == null) {
                System.err.println("Servicio no encontrado");
                return false;
            }

            long endTimeMillis = horaInicio.getTime() + (servicio.getDuracionMinutos() * 60 * 1000);
            Time horaFin = new Time(endTimeMillis);

            ReservaServicio reservaServicio = new ReservaServicio(
                    reserva.getIdReserva(),
                    idServicio,
                    idEmpleado,
                    horaInicio,
                    horaFin,
                    servicio.getPrecio()
            );

            reserva.addServicio(reservaServicio);

            // INSERTA directamente en la base de datos
            boolean insertado = reservaServicioDAO.insertReservaServicio(reservaServicio, DatabaseConnection.getConnection());

            if (!insertado) {
                System.err.println("Error: El servicio no fue guardado en la base de datos.");
            }

            return insertado;

        } catch (SQLException e) {
            System.err.println("Error agregando servicio a reserva: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}