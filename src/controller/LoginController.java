package controller;

import model.Empleado;
import model.dao.EmpleadoDAO;
import java.sql.SQLException;

public class LoginController {
    private EmpleadoDAO empleadoDAO;

    public LoginController() {
        this.empleadoDAO = new EmpleadoDAO();
    }

    public Empleado login(String apellido, String password) {
        try {
            return empleadoDAO.login(apellido, password);
        } catch (SQLException e) {
            System.err.println("Error during login: " + e.getMessage());
            return null;
        }
    }
}
