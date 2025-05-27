package view;

import controller.ControladorUsuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class VentanaCrearUsuario extends JFrame {

    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JTextField txtCargo;
    private JPasswordField txtPass;
    private JTextField txtIdTipoEmpleado;
    private JTextField txtFechaContratacion;
    private JCheckBox chkActivo;
    private JButton btnCrear;

    private ControladorUsuario controladorUsuario;

    public VentanaCrearUsuario() {
        super("Crear Usuario");

        controladorUsuario = new ControladorUsuario();

        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Nombre:"), gbc);
        txtNombre = new JTextField(20);
        gbc.gridx = 1;
        add(txtNombre, gbc);

        // Apellido
        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Apellido:"), gbc);
        txtApellido = new JTextField(20);
        gbc.gridx = 1;
        add(txtApellido, gbc);

        // Teléfono
        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Teléfono:"), gbc);
        txtTelefono = new JTextField(20);
        gbc.gridx = 1;
        add(txtTelefono, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Email:"), gbc);
        txtEmail = new JTextField(20);
        gbc.gridx = 1;
        add(txtEmail, gbc);

        // Cargo
        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Cargo:"), gbc);
        txtCargo = new JTextField(20);
        gbc.gridx = 1;
        add(txtCargo, gbc);

        // Contraseña
        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Contraseña:"), gbc);
        txtPass = new JPasswordField(20);
        gbc.gridx = 1;
        add(txtPass, gbc);

        // id_tipo_empleado
        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("ID Tipo Empleado:"), gbc);
        txtIdTipoEmpleado = new JTextField(20);
        gbc.gridx = 1;
        add(txtIdTipoEmpleado, gbc);

        // fecha_contratacion (yyyy-MM-dd)
        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Fecha Contratación (yyyy-MM-dd):"), gbc);
        txtFechaContratacion = new JTextField(20);
        gbc.gridx = 1;
        add(txtFechaContratacion, gbc);

        // Activo
        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Activo:"), gbc);
        chkActivo = new JCheckBox();
        chkActivo.setSelected(true);
        gbc.gridx = 1;
        add(chkActivo, gbc);

        // Botón crear
        btnCrear = new JButton("Crear Usuario");
        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        add(btnCrear, gbc);

        btnCrear.addActionListener(this::accionCrearUsuario);
    }

    private void accionCrearUsuario(ActionEvent e) {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();
        String cargo = txtCargo.getText().trim();
        String pass = new String(txtPass.getPassword()).trim();

        Integer idTipoEmpleado = null;
        if (!txtIdTipoEmpleado.getText().trim().isEmpty()) {
            try {
                idTipoEmpleado = Integer.parseInt(txtIdTipoEmpleado.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID Tipo Empleado debe ser un número entero", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        LocalDate fechaContratacion = null;
        if (!txtFechaContratacion.getText().trim().isEmpty()) {
            try {
                fechaContratacion = LocalDate.parse(txtFechaContratacion.getText().trim());
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Fecha Contratación formato inválido (debe ser yyyy-MM-dd)", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        Boolean activo = chkActivo.isSelected();

        if (nombre.isEmpty() || apellido.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar los campos Nombre, Apellido y Contraseña", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean creado = controladorUsuario.crearUsuario(nombre, apellido, telefono.isEmpty() ? null : telefono,
                email.isEmpty() ? null : email, cargo.isEmpty() ? null : cargo, pass, idTipoEmpleado, fechaContratacion, activo);

        if (creado) {
            JOptionPane.showMessageDialog(this, "Usuario creado correctamente.");
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al crear usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtCargo.setText("");
        txtPass.setText("");
        txtIdTipoEmpleado.setText("");
        txtFechaContratacion.setText("");
        chkActivo.setSelected(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaCrearUsuario ventana = new VentanaCrearUsuario();
            ventana.setVisible(true);
        });
    }
}
