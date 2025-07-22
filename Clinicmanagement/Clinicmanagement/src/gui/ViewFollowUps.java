/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import dao.PatientDao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class ViewFollowUps extends JFrame {
    private JTable table;

    public ViewFollowUps() {
        setTitle("Upcoming Follow-Ups");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel heading = new JLabel("Patients with Follow-Up Visits", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 22));
        heading.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Column Names
        Vector<String> columnNames = new Vector<>();
        columnNames.add("ID");
        columnNames.add("Name");
        columnNames.add("Phone");
        columnNames.add("Visit Date");
        columnNames.add("Follow-Up Date");
        columnNames.add("Symptoms");
        columnNames.add("Prescription");

        // Data from DAO
        List<Vector<String>> followUps = PatientDao.getFollowUps();
        DefaultTableModel model = new DefaultTableModel(new Vector<>(followUps), columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.setAutoCreateRowSorter(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Set column widths
        int[] widths = {40, 120, 100, 100, 100, 200, 250};
        for (int i = 0; i < widths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }

        JScrollPane scrollPane = new JScrollPane(table);

        setLayout(new BorderLayout());
        add(heading, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ViewFollowUps::new);
    }
}
