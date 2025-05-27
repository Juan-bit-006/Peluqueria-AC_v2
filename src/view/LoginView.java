package view;

import controller.LoginController;
import model.Empleado;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField txtApellido;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private LoginController controller;


    public LoginView() {
        controller = new LoginController();
        initComponents();
    }

    private void initComponents() {
        setTitle("Peluquería - Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        panel.add(txtApellido);

        panel.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        btnLogin = new JButton("Iniciar Sesión");
        panel.add(new JLabel(""));
        panel.add(btnLogin);

        add(panel);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
    }

    private void login() {
        String apellido = txtApellido.getText();
        String password = new String(txtPassword.getPassword());

        if (apellido.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Empleado empleado = controller.login(apellido, password);

        if (empleado != null) {
            MainView mainView = new MainView(empleado);
            mainView.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView login = new LoginView();
            login.setVisible(true);
        });
    }
}
