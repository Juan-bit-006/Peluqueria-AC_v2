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
        setTitle("Registrar Nuevo Cliente");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);

        panel.add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        panel.add(txtApellido);

        panel.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panel.add(txtTelefono);

        panel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panel.add(txtEmail);

        panel.add(new JLabel("Notas:"));
        txtNotas = new JTextArea(3, 20);
        JScrollPane scrollNotas = new JScrollPane(txtNotas);
        panel.add(scrollNotas);

        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");

        panel.add(btnGuardar);
        panel.add(btnCancelar);

        add(panel);

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCliente();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuscarClienteView buscarClienteView = new BuscarClienteView(empleadoActual);
                buscarClienteView.setVisible(true);
                dispose();
            }
        });
    }

    private void guardarCliente() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();
        String notas = txtNotas.getText().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre, apellido y teléfono son obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setTelefono(telefono);
        cliente.setEmail(email);
        cliente.setNotas(notas);

        boolean registrado = clienteController.registrarCliente(cliente);
        if (registrado) {
            JOptionPane.showMessageDialog(this, "Cliente registrado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            BuscarClienteView buscarClienteView = new BuscarClienteView(empleadoActual);
            buscarClienteView.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar cliente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
