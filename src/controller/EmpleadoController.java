package controller;

import model.Empleado;
import model.dao.EmpleadoDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoController {
    private EmpleadoDAO empleadoDAO;

    public EmpleadoController() {
        this.empleadoDAO = new EmpleadoDAO();
    }

    public List<Empleado> obtenerTodosEmpleados() {
        try {
            return empleadoDAO.getAllEmpleados();
        } catch (SQLException e) {
            System.err.println("Error obteniendo empleados: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}

