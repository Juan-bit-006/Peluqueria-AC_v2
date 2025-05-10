package view;

import controller.ClienteController;
import controller.EmpleadoController;
import controller.ReservaController;
import controller.ServicioController;
import model.Cliente;
import model.Empleado;
import model.Reserva;
import model.Servicio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class NuevaReservaView extends JFrame {
    private Empleado empleadoActual;
    private Cliente clienteSeleccionado;

    // Controllers
    private ClienteController clienteController;
    private ServicioController servicioController;
    private EmpleadoController empleadoController;
    private ReservaController reservaController;

    // Components
    private JComboBox<Cliente> cbClientes;
    private JTextField txtFecha;
    private JTextField txtHora;
    private JComboBox<Servicio> cbServicios;
    private JComboBox<Empleado> cbEmpleados;
    private JButton btnGuardar;
    private JButton btnCancelar;

    public NuevaReservaView(Empleado empleado) {
        this.empleadoActual = empleado;
        this.clienteController = new ClienteController();
        this.servicioController = new ServicioController();
        this.empleadoController = new EmpleadoController();
        this.reservaController = new ReservaController();
        initComponents();
    }
    public NuevaReservaView(Empleado empleado, Cliente cliente) {
        this(empleado); // llama al constructor existente
        this.clienteSeleccionado = cliente; // guarda el cliente seleccionado (aunque ya no se usa directamente)
        cbClientes.setSelectedItem(cliente); // selecciona el cliente en el combo
    }

    private void initComponents() {
        setTitle("Nueva Reserva");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblCliente = new JLabel("Cliente:");
        cbClientes = new JComboBox<>();
        for (Cliente c : clienteController.obtenerClientesActivos()) cbClientes.addItem(c);

        JLabel lblFecha = new JLabel("Fecha (YYYY-MM-DD):");
        txtFecha = new JTextField();

        JLabel lblHora = new JLabel("Hora Inicio (HH:MM):");
        txtHora = new JTextField();

        JLabel lblServicio = new JLabel("Servicio:");
        cbServicios = new JComboBox<>();
        for (Servicio s : servicioController.obtenerTodosServicios()) cbServicios.addItem(s);

        JLabel lblEmpleado = new JLabel("Empleado:");
        cbEmpleados = new JComboBox<>();
        for (Empleado e : empleadoController.obtenerTodosEmpleados()) cbEmpleados.addItem(e);

        btnGuardar = new JButton("Crear Reserva");
        btnCancelar = new JButton("Cancelar");

        panel.add(lblCliente); panel.add(cbClientes);
        panel.add(lblFecha); panel.add(txtFecha);
        panel.add(lblHora); panel.add(txtHora);
        panel.add(lblServicio); panel.add(cbServicios);
        panel.add(lblEmpleado); panel.add(cbEmpleados);
        panel.add(btnGuardar); panel.add(btnCancelar);

        add(panel);

        btnGuardar.addActionListener(e -> {
            try {
                Cliente cliente = (Cliente) cbClientes.getSelectedItem();
                if (cliente == null) {
                    JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente");
                    return;
                }

                if (cbServicios.getSelectedItem() == null || cbEmpleados.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(this, "Debe seleccionar un servicio y un empleado");
                    return;
                }

                Date fecha = Date.valueOf(txtFecha.getText().trim());
                Time hora = Time.valueOf(txtHora.getText().trim() + ":00");
                Servicio servicio = (Servicio) cbServicios.getSelectedItem();
                Empleado empleado = (Empleado) cbEmpleados.getSelectedItem();

                Reserva reserva = new Reserva(cliente.getIdCliente(), fecha);
                boolean creada = reservaController.crearReserva(reserva);
                if (!creada) {
                    JOptionPane.showMessageDialog(this, "Error al crear la reserva", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean servicioAgregado = reservaController.agregarServicioAReserva(reserva, servicio.getIdServicio(), empleado.getIdEmpleado(), hora);
                if (!servicioAgregado) {
                    JOptionPane.showMessageDialog(this, "Error al agregar el servicio a la reserva.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(this, "Reserva creada exitosamente");
                new MainView(empleadoActual).setVisible(true);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        btnCancelar.addActionListener(e -> {
            new MainView(empleadoActual).setVisible(true);
            dispose();
        });
    }
}