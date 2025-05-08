// ServicioController
package controller;

import model.Servicio;
import model.dao.ServicioDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicioController {
    private ServicioDAO servicioDAO;

    public ServicioController() {
        this.servicioDAO = new ServicioDAO();
    }
    public boolean actualizarServicio(Servicio servicio) {
        try {
            return servicioDAO.updateServicio(servicio);
        } catch (SQLException e) {
            System.err.println("Error al actualizar servicio: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarServicio(int idServicio) {
        try {
            return servicioDAO.deleteServicio(idServicio);
        } catch (SQLException e) {
            System.err.println("Error al eliminar servicio: " + e.getMessage());
            return false;
        }
    }

    public List<Servicio> obtenerTodosServicios() {
        try {
            return servicioDAO.getAllServicios();
        } catch (SQLException e) {
            System.err.println("Error obteniendo servicios: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Servicio obtenerServicioPorId(int idServicio) {
        try {
            return servicioDAO.getServicioById(idServicio);
        } catch (SQLException e) {
            System.err.println("Error obteniendo servicio: " + e.getMessage());
            return null;
        }
    }

    public boolean crearServicio(Servicio servicio) {


        return false;
    }
}
