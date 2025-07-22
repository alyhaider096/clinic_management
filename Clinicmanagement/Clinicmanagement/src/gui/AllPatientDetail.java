/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import dao.PatientDao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;

public class AllPatientDetail extends JFrame {
    private DefaultTableModel model;
    private JTable table;
    private JTextField searchField;

    public AllPatientDetail() {
        setTitle("All Patient Details");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Header
        JLabel titleLabel = new JLabel("All Patient Records", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Table columns
        Vector<String> columnNames = new Vector<>();
        columnNames.add("ID");
        columnNames.add("Name");
        columnNames.add("Phone");
        columnNames.add("Age");
        columnNames.add("Weight");
        columnNames.add("Visit Date");
        columnNames.add("Symptoms");
        columnNames.add("Prescription");
        columnNames.add("Follow-Up Date");

        // Data
        List<Vector<String>> patientData = PatientDao.getAllPatients();
        model = new DefaultTableModel(new Vector<>(patientData), columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Table
        table = new JTable(model);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setAutoCreateRowSorter(true);

        int[] columnWidths = {40, 120, 100, 40, 60, 100, 200, 250, 100};
        for (int i = 0; i < columnWidths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Search Bar (bottom left)
        searchField = new JTextField(30);
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchField.setToolTipText("Search by name or visit date");
        searchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String keyword = searchField.getText().trim().toLowerCase();
                filterPatients(keyword);
            }
        });

        // Delete Button (bottom right)
        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.addActionListener(this::handleDelete);

        // Bottom Panel with left/right alignment
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(new JLabel("Search: "));
        leftPanel.add(searchField);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(deleteButton);

        bottomPanel.add(leftPanel, BorderLayout.WEST);
        bottomPanel.add(rightPanel, BorderLayout.EAST);

        // Main Layout
        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void handleDelete(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a patient to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = table.convertRowIndexToModel(selectedRow);
        int patientId = Integer.parseInt((String) model.getValueAt(modelRow, 0));

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete patient ID " + patientId + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean deleted = PatientDao.deletePatientById(patientId);
            if (deleted) {
                model.removeRow(modelRow);
                JOptionPane.showMessageDialog(this, "Patient deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete patient.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void filterPatients(String keyword) {
        List<Vector<String>> filteredData = PatientDao.getAllPatients();
        model.setRowCount(0); // Clear current rows

        for (Vector<String> row : filteredData) {
            String name = row.get(1).toLowerCase();
            String visitDate = row.get(5).toLowerCase();
            if (name.contains(keyword) || visitDate.contains(keyword)) {
                model.addRow(row);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AllPatientDetail::new);
    }
}
