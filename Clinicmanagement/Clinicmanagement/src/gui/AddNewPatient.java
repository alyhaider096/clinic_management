/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import dao.PatientDao;
import printer.PrescriptionPrinter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddNewPatient extends JFrame {

    public JTextField txtPatientId, txtName, txtPhone, txtAge, txtWeight;
    public JTextArea txtSymptoms, txtPrescription;
    public JSpinner visitDateSpinner, followUpDateSpinner;

    public AddNewPatient() {
        setTitle("Add New Patient");
        setSize(640, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(new JLabel(new ImageIcon(getClass().getResource("/resources/images/form1.png"))));
        setLayout(null);

        JLabel title = new JLabel("New Patient Entry", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(120, 10, 400, 40);
        add(title);

        JSeparator separator = new JSeparator();
        separator.setBounds(40, 60, 550, 10);
        add(separator);

        int labelX = 50, fieldX = 220, width = 350, height = 25;
        int y = 80, gap = 40;

        JLabel lblId = new JLabel("Patient ID:");
        lblId.setBounds(labelX, y, 150, height);
        add(lblId);

        txtPatientId = new JTextField("Auto Generated");
        txtPatientId.setEnabled(false);
        txtPatientId.setBounds(fieldX, y, 200, height);
        add(txtPatientId);

        y += gap;
        JLabel lblName = new JLabel("Full Name:");
        lblName.setBounds(labelX, y, 150, height);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(fieldX, y, 200, height);
        add(txtName);

        y += gap;
        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setBounds(labelX, y, 150, height);
        add(lblPhone);

        txtPhone = new JTextField();
        txtPhone.setBounds(fieldX, y, 200, height);
        add(txtPhone);

        y += gap;
        JLabel lblAge = new JLabel("Age:");
        lblAge.setBounds(labelX, y, 150, height);
        add(lblAge);

        txtAge = new JTextField();
        txtAge.setBounds(fieldX, y, 200, height);
        add(txtAge);

        y += gap;
        JLabel lblWeight = new JLabel("Weight (kg):");
        lblWeight.setBounds(labelX, y, 150, height);
        add(lblWeight);

        txtWeight = new JTextField();
        txtWeight.setBounds(fieldX, y, 200, height);
        add(txtWeight);

        y += gap;
        JLabel lblVisitDate = new JLabel("Visit Date:");
        lblVisitDate.setBounds(labelX, y, 150, height);
        add(lblVisitDate);

        SpinnerDateModel visitModel = new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH);
        visitDateSpinner = new JSpinner(visitModel);
        visitDateSpinner.setEditor(new JSpinner.DateEditor(visitDateSpinner, "yyyy-MM-dd"));
        visitDateSpinner.setBounds(fieldX, y, 200, height);
        add(visitDateSpinner);

        y += gap;
        JLabel lblSymptoms = new JLabel("Symptoms:");
        lblSymptoms.setBounds(labelX, y, 150, height);
        add(lblSymptoms);

        txtSymptoms = new JTextArea();
        txtSymptoms.setBounds(fieldX, y, 200, 50);
        add(txtSymptoms);

        y += 60;
        JLabel lblPrescription = new JLabel("Prescription:");
        lblPrescription.setBounds(labelX, y, 150, height);
        add(lblPrescription);

        txtPrescription = new JTextArea();
        txtPrescription.setBounds(fieldX, y, 200, 50);
        add(txtPrescription);

        y += 60;
        JLabel lblFollowUp = new JLabel("Follow-up Date:");
        lblFollowUp.setBounds(labelX, y, 150, height);
        add(lblFollowUp);

        SpinnerDateModel followModel = new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH);
        followUpDateSpinner = new JSpinner(followModel);
        followUpDateSpinner.setEditor(new JSpinner.DateEditor(followUpDateSpinner, "yyyy-MM-dd"));
        followUpDateSpinner.setBounds(fieldX, y, 200, height);
        add(followUpDateSpinner);

        JButton btnSave = new JButton("Save");
        JButton btnSavePrint = new JButton("Save & Print");
        JButton btnReset = new JButton("Reset");

        btnSave.setBounds(60, 600, 120, 30);
        btnSavePrint.setBounds(230, 600, 140, 30);
        btnReset.setBounds(420, 600, 120, 30);

        add(btnSave);
        add(btnSavePrint);
        add(btnReset);

        btnReset.addActionListener(e -> clearFields());
        btnSave.addActionListener((ActionEvent e) -> savePatient(false));
        btnSavePrint.addActionListener((ActionEvent e) -> savePatient(true));

        setVisible(true);
    }

    private void savePatient(boolean print) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String> patient = new HashMap<>();
        patient.put("name", txtName.getText());
        patient.put("phone", txtPhone.getText());
        patient.put("age", txtAge.getText());
        patient.put("weight", txtWeight.getText());
        patient.put("visitDate", sdf.format((Date) visitDateSpinner.getValue()));
        patient.put("symptoms", txtSymptoms.getText());
        patient.put("prescription", txtPrescription.getText());
        patient.put("followUpDate", sdf.format((Date) followUpDateSpinner.getValue()));

        boolean result = PatientDao.addPatient(patient);
        if (result) {
            JOptionPane.showMessageDialog(null, "Patient Added Successfully");
            if (print) {
                PrescriptionPrinter.print(patient);
            }
            clearFields();
        } else {
            JOptionPane.showMessageDialog(null, "Error Adding Patient");
        }
    }

    private void clearFields() {
        txtName.setText("");
        txtPhone.setText("");
        txtAge.setText("");
        txtWeight.setText("");
        txtSymptoms.setText("");
        txtPrescription.setText("");
        visitDateSpinner.setValue(new Date());
        followUpDateSpinner.setValue(new Date());
    }

    public static void main(String[] args) {
        new AddNewPatient();
    }
}
