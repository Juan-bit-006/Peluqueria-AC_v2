package controller;

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
            return reservaDAO.getAllReservaServicios();
        } catch (SQLException e) {
            System.err.println("Error obteniendo reservas: " + e.getMessage());
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
                return false;
            }

            // Calculate end time based on service duration
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
            return true;
        } catch (SQLException e) {
            System.err.println("Error agregando servicio a reserva: " + e.getMessage());
            return false;
        }
    }
}