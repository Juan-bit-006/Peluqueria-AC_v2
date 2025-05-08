package view;

import controller.ClienteController;
import model.Cliente;
import model.Empleado;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NuevoClienteView extends JFrame {
    private Empleado empleadoActual;
    private ClienteController clienteController;

    // Components
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JTextArea txtNotas;
    private JButton btnGuardar;
    private JButton btnCancelar;

    public NuevoClienteView(Empleado empleado) {
        this.empleadoActual = empleado;
        this.clienteController = new ClienteController();
        initComponents();
    }

    private void initComponents() {
        // Implementation details would go here
        // This is just a stub
    }

    // Methods would go here
}

