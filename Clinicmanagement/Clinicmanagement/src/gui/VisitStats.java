/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import dao.VisitStatsDao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;
import java.util.Vector;

public class VisitStats extends JFrame {

    public VisitStats() {
        setTitle("Monthly Visit Statistics");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel heading = new JLabel("Patients Visited (Last 12 Months)", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Table Columns
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Month");
        columnNames.add("Number of Patients");

        // Table Data
        Vector<Vector<String>> tableData = new Vector<>();
        Map<String, Integer> stats = VisitStatsDao.getMonthlyVisitStats();

        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            Vector<String> row = new Vector<>();
            row.add(entry.getKey());
            row.add(String.valueOf(entry.getValue()));
            tableData.add(row);
        }

        JTable table = new JTable(new DefaultTableModel(tableData, columnNames));
        table.setRowHeight(25);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        JScrollPane scrollPane = new JScrollPane(table);

        setLayout(new BorderLayout());
        add(heading, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VisitStats::new);
    }
}

