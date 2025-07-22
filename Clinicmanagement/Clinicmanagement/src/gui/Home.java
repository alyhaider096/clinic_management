/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class Home extends JFrame {

    public Home() {
        setTitle("Clinic Management - Home");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Background Image
        URL bgUrl = getClass().getResource("/resources/images/clinic.png");
        if (bgUrl != null) {
            setContentPane(new JLabel(new ImageIcon(bgUrl)));
        } else {
            System.out.println("❌ Background image not found!");
        }

        // Add Patient
        JButton addBtn = createIconButton("/resources/images/Donor_1.png", 50, 40, "ADD PATIENT");
        addBtn.addActionListener(e -> new AddNewPatient().setVisible(true));
        add(addBtn);
        add(createLabel("ADD PATIENT", 55, 130));

        // All Patients
        JButton allBtn = createIconButton("/resources/images/Details.png", 180, 40, "ALL PATIENTS");
        allBtn.addActionListener(e -> new AllPatientDetail().setVisible(true));
        add(allBtn);
        add(createLabel("ALL PATIENTS", 185, 130));

        // Follow-Ups
        JButton followBtn = createIconButton("/resources/images/search1.png", 310, 40, "FOLLOW-UPS");
        followBtn.addActionListener(e -> new ViewFollowUps().setVisible(true));
        add(followBtn);
        add(createLabel("FOLLOW-UPS", 315, 130));

        // Stats
        JButton statsBtn = createIconButton("/resources/images/Dec.png", 440, 40, "STATS");
        statsBtn.addActionListener(e -> new VisitStats().setVisible(true));
        add(statsBtn);
        add(createLabel("STATS", 455, 130));

        // Exit
        JButton exitBtn = createIconButton("/resources/images/Close.png", 570, 40, "EXIT");
        exitBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Exit the application?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) System.exit(0);
        });
        add(exitBtn);
        add(createLabel("EXIT", 590, 130));

        setVisible(true);
    }

    private JButton createIconButton(String path, int x, int y, String tooltip) {
        URL iconURL = getClass().getResource(path);
        ImageIcon icon = iconURL != null ? new ImageIcon(iconURL) : null;
        JButton button = new JButton(icon);
        button.setBounds(x, y, 80, 80);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setToolTipText(tooltip);

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                button.setBorderPainted(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBorderPainted(false);
            }
        });

        return button;
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 150, 25);
        label.setFont(new Font("Segoe UI Semibold", Font.BOLD, 18));
        label.setForeground(Color.BLACK);
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Home::new);
    }
}
