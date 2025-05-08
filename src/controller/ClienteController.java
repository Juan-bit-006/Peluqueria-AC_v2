package controller;

import model.Cliente;
import model.dao.ClienteDAO;
import java.sql.SQLException;

public class ClienteController {
    private ClienteDAO clienteDAO;

    public ClienteController() {
        this.clienteDAO = new ClienteDAO();
    }

    public Cliente buscarClientePorTelefono(String telefono) {
        try {
            return clienteDAO.getClienteByTelefono(telefono);
        } catch (SQLException e) {
            System.err.println("Error buscando cliente: " + e.getMessage());
            return null;
        }
    }

    public boolean registrarCliente(Cliente cliente) {
        try {
            int id = clienteDAO.insertCliente(cliente);
            return id > 0;
        } catch (SQLException e) {
            System.err.println("Error registrando cliente: " + e.getMessage());
            return false;
        }
    }
}