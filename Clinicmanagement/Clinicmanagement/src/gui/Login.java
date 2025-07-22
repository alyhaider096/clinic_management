/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import dao.LoginDao;
import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public Login() {
        setTitle("Clinic Management - Login");
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Set background image
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/resources/images/clinic.png")));
        background.setBounds(0, 0, 1366, 768);
        setContentPane(background);
        background.setLayout(null);

        // Username Label
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(500, 300, 100, 30);
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        userLabel.setForeground(Color.BLACK);
        background.add(userLabel);

        // Username Field
        usernameField = new JTextField();
        usernameField.setBounds(600, 300, 200, 30);
        background.add(usernameField);

        // Password Label
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(500, 350, 100, 30);
        passLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passLabel.setForeground(Color.BLACK);
        background.add(passLabel);

        // Password Field
        passwordField = new JPasswordField();
        passwordField.setBounds(600, 350, 200, 30);
        background.add(passwordField);

        // Login Button
        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(600, 400, 200, 30);
        background.add(loginBtn);

        // Action on Login
        loginBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (LoginDao.validate(username, password)) {
                JOptionPane.showMessageDialog(null, "Login Successful!");
                new Home().setVisible(true);  // Change to Clinic Home
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Username or Password");
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}
