package controller;

import model.Cliente;
import model.dao.ClienteDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteController {

    public Cliente buscarClientePorTelefono(String telefono) {
        ClienteDAO clienteDAO = new ClienteDAO();
        try {
            return clienteDAO.getClienteByTelefono(telefono);
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente por teléfono: " + e.getMessage());
            return null;
        }
    }

    public boolean registrarCliente(Cliente cliente) {
        ClienteDAO clienteDAO = new ClienteDAO();
        try {
            return clienteDAO.insertCliente(cliente) > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar cliente: " + e.getMessage());
            return false;
        }
    }

    public List<Cliente> obtenerClientesActivos() {
        ClienteDAO clienteDAO = new ClienteDAO();
        try {
            return clienteDAO.getClientesActivos();
        } catch (SQLException e) {
            System.err.println("Error obteniendo clientes activos: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
