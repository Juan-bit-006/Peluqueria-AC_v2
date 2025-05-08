package view;

import controller.ClienteController;
import controller.ReservaController;
import model.Cliente;
import model.Empleado;
import model.ReservaServicio;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BuscarClienteView extends JFrame {
    private Empleado empleadoActual;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtTelefono;
    private JButton btnBuscar;
    private JButton btnNuevoCliente;
    private JButton btnVolver;
    private JPanel panelResultados;
    private ClienteController clienteController;
    private ReservaController reservaController;

    public BuscarClienteView(Empleado empleado) {
        this.empleadoActual = empleado;
        this.clienteController = new ClienteController();
        this.reservaController = new ReservaController();
        initComponents();
    }

    private void initComponents() {
        setTitle("Buscar Cliente");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Search panel
        JPanel searchPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Búsqueda de Cliente"));

        searchPanel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        searchPanel.add(txtNombre);

        searchPanel.add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        searchPanel.add(txtApellido);

        searchPanel.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        searchPanel.add(txtTelefono);

        btnBuscar = new JButton("Buscar");
        btnNuevoCliente = new JButton("Nuevo Cliente");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(btnBuscar);
        buttonPanel.add(btnNuevoCliente);

        searchPanel.add(new JLabel(""));
        searchPanel.add(buttonPanel);

        mainPanel.add(searchPanel, BorderLayout.NORTH);

        // Results panel
        JPanel resultsContainer = new JPanel(new BorderLayout());
        JLabel lblResultados = new JLabel("Resultados de la búsqueda");
        lblResultados.setFont(new Font("Arial", Font.BOLD, 14));
        resultsContainer.add(lblResultados, BorderLayout.NORTH);

        panelResultados = new JPanel();
        panelResultados.setLayout(new BoxLayout(panelResultados, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(panelResultados);
        resultsContainer.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(resultsContainer, BorderLayout.CENTER);

        // Back button
        btnVolver = new JButton("Volver al Menú Principal");
        mainPanel.add(btnVolver, BorderLayout.SOUTH);

        add(mainPanel);

        // Add action listeners
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarClientes();
            }
        });

        btnNuevoCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NuevoClienteView nuevoClienteView = new NuevoClienteView(empleadoActual);
                nuevoClienteView.setVisible(true);
                dispose();
            }
        });

        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainView mainView = new MainView(empleadoActual);
                mainView.setVisible(true);
                dispose();
            }
        });
    }

    private void buscarClientes() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String telefono = txtTelefono.getText().trim();

        // If telefono is provided, try to find exact match first
        if (!telefono.isEmpty()) {
            Cliente cliente = clienteController.buscarClientePorTelefono(telefono);
            if (cliente != null) {
                mostrarClienteEncontrado(cliente);
                return;
            }
        }

        // Otherwise, search reservations with partial match
        List<ReservaServicio> reservas = reservaController.buscarReservasPorCliente(nombre, apellido, telefono);
        mostrarResultadosBusqueda(reservas);
    }

    private void mostrarClienteEncontrado(Cliente cliente) {
        panelResultados.removeAll();

        JPanel panelCliente = new JPanel(new BorderLayout());
        panelCliente.setBorder(BorderFactory.createEtchedBorder());

        JLabel lblNombre = new JLabel(cliente.getNombre() + " " + cliente.getApellido());
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.add(new JLabel("Teléfono: " + cliente.getTelefono()));
        if (cliente.getEmail() != null) {
            infoPanel.add(new JLabel("Email: " + cliente.getEmail()));
        }
        if (cliente.getFechaRegistro() != null) {
            infoPanel.add(new JLabel("Fecha Registro: " + cliente.getFechaRegistro()));
        }

        JButton btnNuevaReserva = new JButton("Nueva Reserva");
        btnNuevaReserva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NuevaReservaView nuevaReservaView = new NuevaReservaView(empleadoActual, cliente);
                nuevaReservaView.setVisible(true);
                dispose();
            }
        });

        panelCliente.add(lblNombre, BorderLayout.NORTH);
        panelCliente.add(infoPanel, BorderLayout.CENTER);
        panelCliente.add(btnNuevaReserva, BorderLayout.SOUTH);

        panelResultados.add(panelCliente);

        // Also show reservations
        List<ReservaServicio> reservas = reservaController.buscarReservasPorCliente(
                cliente.getNombre(), cliente.getApellido(), cliente.getTelefono());

        if (!reservas.isEmpty()) {
            JLabel lblHistorial = new JLabel("Historial de Reservas");
            lblHistorial.setFont(new Font("Arial", Font.BOLD, 14));
            lblHistorial.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
            panelResultados.add(lblHistorial);

            for (ReservaServicio reserva : reservas) {
                JPanel panelReserva = new JPanel(new BorderLayout());
                panelReserva.setBorder(BorderFactory.createEtchedBorder());

                JLabel lblServicio = new JLabel(reserva.getDescripcionServicio());
                lblServicio.setFont(new Font("Arial", Font.BOLD, 12));

                JPanel detallePanel = new JPanel(new GridLayout(2, 1));
                detallePanel.add(new JLabel("Hora: " + reserva.getHoraInicio()));
                detallePanel.add(new JLabel("Precio: $" + reserva.getPrecioAplicado()));

                panelReserva.add(lblServicio, BorderLayout.NORTH);
                panelReserva.add(detallePanel, BorderLayout.CENTER);

                panelResultados.add(panelReserva);
                panelResultados.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        }

        panelResultados.revalidate();
        panelResultados.repaint();
    }

    private void mostrarResultadosBusqueda(List<ReservaServicio> reservas) {
        panelResultados.removeAll();

        if (reservas.isEmpty()) {
            panelResultados.add(new JLabel("No se encontraron resultados"));
        } else {
            // Group reservations by client
            // This is a simplified approach; in a real app, you might want to use a more sophisticated grouping method
            String currentClient = "";

            for (ReservaServicio reserva : reservas) {
                String clientName = reserva.getNombreCliente() + " " + reserva.getApellidoCliente();

                if (!currentClient.equals(clientName)) {
                    currentClient = clientName;

                    JLabel lblCliente = new JLabel(clientName);
                    lblCliente.setFont(new Font("Arial", Font.BOLD, 14));
                    lblCliente.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
                    panelResultados.add(lblCliente);

                    JLabel lblTelefono = new JLabel("Teléfono: " + reserva.getTelefonoCliente());
                    panelResultados.add(lblTelefono);

                    JButton btnNuevaReserva = new JButton("Nueva Reserva");
                    final String telefono = reserva.getTelefonoCliente();
                    btnNuevaReserva.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Cliente cliente = clienteController.buscarClientePorTelefono(telefono);
                            if (cliente != null) {
                                NuevaReservaView nuevaReservaView = new NuevaReservaView(empleadoActual, cliente);
                                nuevaReservaView.setVisible(true);
                                dispose();
                            }
                        }
                    });
                    panelResultados.add(btnNuevaReserva);
                    panelResultados.add(Box.createRigidArea(new Dimension(0, 10)));
                }

                JPanel panelReserva = new JPanel(new BorderLayout());
                panelReserva.setBorder(BorderFactory.createEtchedBorder());

                JLabel lblServicio = new JLabel(reserva.getDescripcionServicio());

                JPanel detallePanel = new JPanel(new GridLayout(2, 1));
                detallePanel.add(new JLabel("Hora: " + reserva.getHoraInicio()));
                detallePanel.add(new JLabel("Precio: $" + reserva.getPrecioAplicado()));

                panelReserva.add(lblServicio, BorderLayout.NORTH);
                panelReserva.add(detallePanel, BorderLayout.CENTER);

                panelResultados.add(panelReserva);
                panelResultados.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        }

        panelResultados.revalidate();
        panelResultados.repaint();
    }
}