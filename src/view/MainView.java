package view;

import controller.ReservaController;
import model.Empleado;
import model.ReservaServicio;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainView extends JFrame {
    private Empleado empleadoActual;
    private JButton btnNuevaReserva;
    private JButton btnBuscarCliente;
    private JButton btnGestionServicios;
    private JButton btnSalir;
    private JPanel panelReservas;
    private ReservaController reservaController;

    public MainView(Empleado empleado) {
        this.empleadoActual = empleado;
        this.reservaController = new ReservaController();
        initComponents();
        cargarReservas();
    }

     public void initComponents() {
        setTitle("Peluquería - Sistema de Gestión");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel lblBienvenido = new JLabel("Bienvenido, " + empleadoActual.getNombre() + " " + empleadoActual.getApellido());
        lblBienvenido.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(lblBienvenido, BorderLayout.WEST);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        btnNuevaReserva = new JButton("Nueva Reserva");
        btnBuscarCliente = new JButton("Buscar Cliente");
        btnGestionServicios = new JButton("Gestión de Servicios");
        btnSalir = new JButton("Salir");

        buttonsPanel.add(btnNuevaReserva);
        buttonsPanel.add(btnBuscarCliente);

        // Only show service management for admin (tipo_empleado = 1)
        if (empleadoActual.getIdTipoEmpleado() == 1) {
            buttonsPanel.add(btnGestionServicios);
        }

        buttonsPanel.add(btnSalir);

        headerPanel.add(buttonsPanel, BorderLayout.SOUTH);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Reservations panel
        JPanel reservationsContainer = new JPanel(new BorderLayout());
        JLabel lblReservas = new JLabel("Reservas Recientes");
        lblReservas.setFont(new Font("Arial", Font.BOLD, 16));
        reservationsContainer.add(lblReservas, BorderLayout.NORTH);

        panelReservas = new JPanel();
        panelReservas.setLayout(new BoxLayout(panelReservas, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(panelReservas);
        reservationsContainer.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(reservationsContainer, BorderLayout.CENTER);

        add(mainPanel);

        // Add action listeners
        btnNuevaReserva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NuevaReservaView nuevaReservaView = new NuevaReservaView(empleadoActual);
                nuevaReservaView.setVisible(true);
                dispose();
            }
        });

        btnBuscarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuscarClienteView buscarClienteView = new BuscarClienteView(empleadoActual);
                buscarClienteView.setVisible(true);
                dispose();
            }
        });

        if (empleadoActual.getIdTipoEmpleado() == 1) {
            btnGestionServicios.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GestionServiciosView gestionServiciosView = new GestionServiciosView(empleadoActual);
                    gestionServiciosView.setVisible(true);
                    dispose();
                }
            });
        }

        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginView loginView = new LoginView();
                loginView.setVisible(true);
                dispose();
            }
        });
    }

     public void cargarReservas() {
        List<ReservaServicio> reservas = reservaController.obtenerTodasReservas();
        panelReservas.removeAll();

        if (reservas.isEmpty()) {
            panelReservas.add(new JLabel("No hay reservas registradas"));
        } else {
            for (ReservaServicio reserva : reservas) {
                JPanel panelReserva = new JPanel(new BorderLayout());
                panelReserva.setBorder(BorderFactory.createEtchedBorder());

                JLabel lblCliente = new JLabel(reserva.getNombreCliente() + " " + reserva.getApellidoCliente());
                lblCliente.setFont(new Font("Arial", Font.BOLD, 14));

                JLabel lblServicio = new JLabel(reserva.getDescripcionServicio());
                JLabel lblHora = new JLabel("Hora: " + reserva.getHoraInicio().toString());
                JLabel lblTelefono = new JLabel("Tel: " + reserva.getTelefonoCliente());

                JPanel infoPanel = new JPanel(new GridLayout(3, 1));
                infoPanel.add(lblServicio);
                infoPanel.add(lblHora);
                infoPanel.add(lblTelefono);

                panelReserva.add(lblCliente, BorderLayout.NORTH);
                panelReserva.add(infoPanel, BorderLayout.CENTER);

                panelReservas.add(panelReserva);
                panelReservas.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        panelReservas.revalidate();
        panelReservas.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crear empleado de prueba
            Empleado empleadoDemo = new Empleado();
            empleadoDemo.setNombre("Juan");
            empleadoDemo.setApellido("Ortega");
            empleadoDemo.setIdTipoEmpleado(1); // 1 = administrador, 2 = empleado normal
            empleadoDemo.setPassword("1234");

            // Mostrar vista principal
            MainView mainView = new MainView(empleadoDemo);
            mainView.setVisible(true);
        });
    }

//    public static void main(String[] args) {
//        Empleado empleado = new Empleado();
//        empleado.setIdEmpleado(empleado.getIdEmpleado());
//        empleado.setIdEmpleado(JFrame.DISPOSE_ON_CLOSE);
//        empleado.setActivo(true);
//        empleado.getClass();
//    }
}