package view;

import controller.ServicioController;
import model.Empleado;
import model.Servicio;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GestionServiciosView extends JFrame {
    private Empleado empleadoActual;
    private ServicioController servicioController;
    private JTable tablaServicios;
    private DefaultListModel<Servicio> modeloLista;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnVolver;
    private JTextField txtNombre;
    private JTextField txtDescripcion;
    private JTextField txtPrecio;
    private JTextField txtDuracion;
    private JPanel formPanel;
    private JPanel mainPanel;

    public GestionServiciosView(Empleado empleado) {
        this.empleadoActual = empleado;
        this.servicioController = new ServicioController();
        initComponents();
        cargarServicios();
    }


    private void initComponents() {
        setTitle("Gestión de Servicios");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel de formulario
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Servicio"));

        formPanel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        formPanel.add(txtNombre);

        formPanel.add(new JLabel("Descripción:"));
        txtDescripcion = new JTextField();
        formPanel.add(txtDescripcion);

        formPanel.add(new JLabel("Precio ($):"));
        txtPrecio = new JTextField();
        formPanel.add(txtPrecio);

        formPanel.add(new JLabel("Duración (minutos):"));
        txtDuracion = new JTextField();
        formPanel.add(txtDuracion);

        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");

        botonesPanel.add(btnAgregar);
        botonesPanel.add(btnEditar);
        botonesPanel.add(btnEliminar);

        formPanel.add(new JLabel(""));
        formPanel.add(botonesPanel);

        mainPanel.add(formPanel, BorderLayout.NORTH);

        // Panel de tabla
        String[] columnas = {"ID", "Nombre", "Descripción", "Precio", "Duración (min)"};
        Object[][] datos = {};
        tablaServicios = new JTable();
        JScrollPane scrollPane = new JScrollPane(tablaServicios);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Botón volver
        btnVolver = new JButton("Volver al Menú Principal");
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(btnVolver);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Agregar listeners
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarServicio();
            }
        });

        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarServicio();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarServicio();
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

        tablaServicios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaServicios.getSelectedRow();
                if (fila >= 0) {
                    cargarServicioSeleccionado(fila);
                }
            }
        });
    }

    private void cargarServicios() {
        List<Servicio> servicios = servicioController.obtenerTodosServicios();

        // Columnas de la tabla
        String[] columnas = {"ID", "Nombre", "Descripción", "Precio", "Duración (min)"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);

        // Cargar datos en el modelo
        for (Servicio servicio : servicios) {
            Object[] fila = {
                    servicio.getIdServicio(),
                    servicio.getNombre(), // Asegurate de que setNombre y getNombre están bien en tu clase Servicio
                    servicio.getDescripcion(),
                    servicio.getPrecio(),
                    servicio.getDuracionMinutos()
            };
            modelo.addRow(fila);
        }

        // Actualizar modelo de la tabla ya existente (NO crear una nueva JTable)
        tablaServicios.setModel(modelo);
    }


    private void cargarServicioSeleccionado(int fila) {
        txtNombre.setText(tablaServicios.getValueAt(fila, 1).toString());
        txtDescripcion.setText(tablaServicios.getValueAt(fila, 2).toString());
        txtPrecio.setText(tablaServicios.getValueAt(fila, 3).toString());
        txtDuracion.setText(tablaServicios.getValueAt(fila, 4).toString());
    }

    private void agregarServicio() {
        if (validarCampos()) {
            String nombre = txtNombre.getText();
            String descripcion = txtDescripcion.getText();
            double precio = Double.parseDouble(txtPrecio.getText());
            int duracion = Integer.parseInt(txtDuracion.getText());

            Servicio servicio = new Servicio();
            servicio.setNombre(nombre);
            servicio.setDescripcion(descripcion);
            servicio.setPrecio(precio);
            servicio.setDuracionMinutos(duracion);

            boolean resultado = servicioController.crearServicio(servicio);

            if (resultado) {
                JOptionPane.showMessageDialog(this, "Servicio agregado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarServicios();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar el servicio", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarServicio() {
        int fila = tablaServicios.getSelectedRow();
        if (fila >= 0) {
            if (validarCampos()) {
                int id = (int) tablaServicios.getValueAt(fila, 0);
                String nombre = txtNombre.getText();
                String descripcion = txtDescripcion.getText();
                double precio = Double.parseDouble(txtPrecio.getText());
                int duracion = Integer.parseInt(txtDuracion.getText());

                Servicio servicio = new Servicio();
                servicio.setIdServicio(id);
                servicio.setNombre(nombre);
                servicio.setDescripcion(descripcion);
                servicio.setPrecio(precio);
                servicio.setDuracionMinutos(duracion);

                boolean resultado = servicioController.actualizarServicio(servicio);

                if (resultado) {
                    JOptionPane.showMessageDialog(this, "Servicio actualizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarServicios();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar el servicio", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio de la tabla", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarServicio() {
        int fila = tablaServicios.getSelectedRow();
        if (fila >= 0) {
            int id = (int) tablaServicios.getValueAt(fila, 0);

            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de eliminar este servicio?\nEsta acción no se puede deshacer.",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean resultado = servicioController.eliminarServicio(id);

                if (resultado) {
                    JOptionPane.showMessageDialog(this, "Servicio eliminado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarServicios();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el servicio.\nPuede que existan reservas asociadas a este servicio.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio de la tabla", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre del servicio es obligatorio", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (txtDescripcion.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La descripción del servicio es obligatoria", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            double precio = Double.parseDouble(txtPrecio.getText());
            if (precio <= 0) {
                JOptionPane.showMessageDialog(this, "El precio debe ser mayor que cero", "Error de validación", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un número válido", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            int duracion = Integer.parseInt(txtDuracion.getText());
            if (duracion <= 0) {
                JOptionPane.showMessageDialog(this, "La duración debe ser mayor que cero", "Error de validación", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La duración debe ser un número entero válido", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");
        txtDuracion.setText("");
        tablaServicios.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Empleado empleadoDemo = new Empleado();
            empleadoDemo.setNombre("Juan");
            empleadoDemo.setApellido("Pérez");
            empleadoDemo.setIdTipoEmpleado(1);
            MainView mainView = new MainView(empleadoDemo);
            mainView.setVisible(true);
        });
    }

}