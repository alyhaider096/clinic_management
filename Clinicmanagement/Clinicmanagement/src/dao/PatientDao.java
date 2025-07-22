/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class PatientDao {

    /**
     * Adds a new patient to the database.
     */
    public static boolean addPatient(Map<String, String> patient) {
        String query = "INSERT INTO patients (name, phone, age, weight, visit_date, symptoms, prescription, follow_up_date) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConnectionProvider.getCon();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, patient.get("name"));
            ps.setString(2, patient.get("phone"));
            ps.setString(3, patient.get("age"));
            ps.setString(4, patient.get("weight"));
            ps.setString(5, patient.get("visitDate"));
            ps.setString(6, patient.get("symptoms"));
            ps.setString(7, patient.get("prescription"));
            ps.setString(8, patient.get("followUpDate"));

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.err.println("❌ Error adding patient:");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns the ID of the most recently added patient.
     */
    public static int getLastPatientId() {
        String query = "SELECT id FROM patients ORDER BY id DESC LIMIT 1";

        try (Connection con = ConnectionProvider.getCon();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (Exception e) {
            System.err.println("❌ Error getting last patient ID:");
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * Retrieves all patients from the database, ordered by visit_date (latest first).
     */
    public static List<Vector<String>> getAllPatients() {
        List<Vector<String>> data = new Vector<>();

        String query = "SELECT id, name, phone, age, weight, visit_date, symptoms, prescription, follow_up_date " +
                       "FROM patients ORDER BY visit_date DESC";

        try (Connection con = ConnectionProvider.getCon();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Vector<String> row = new Vector<>();
                row.add(String.valueOf(rs.getInt("id")));
                row.add(rs.getString("name"));
                row.add(rs.getString("phone"));
                row.add(rs.getString("age"));
                row.add(rs.getString("weight"));
                row.add(rs.getString("visit_date"));
                row.add(rs.getString("symptoms"));
                row.add(rs.getString("prescription"));
                row.add(rs.getString("follow_up_date"));
                data.add(row);
            }

        } catch (Exception e) {
            System.err.println("❌ Error fetching patient list:");
            e.printStackTrace();
        }

        return data;
    }

    /**
     * Deletes a patient from the database by ID.
     */
    public static boolean deletePatientById(int id) {
        String query = "DELETE FROM patients WHERE id = ?";

        try (Connection con = ConnectionProvider.getCon();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.err.println("❌ Error deleting patient with ID " + id + ":");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all patients with a follow-up date set (non-empty), ordered by follow_up_date ASC.
     */
    public static List<Vector<String>> getFollowUps() {
        List<Vector<String>> data = new Vector<>();

        String query = "SELECT id, name, phone, visit_date, follow_up_date, symptoms, prescription " +
                       "FROM patients WHERE follow_up_date IS NOT NULL AND follow_up_date <> '' " +
                       "ORDER BY follow_up_date ASC";

        try (Connection con = ConnectionProvider.getCon();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Vector<String> row = new Vector<>();
                row.add(String.valueOf(rs.getInt("id")));
                row.add(rs.getString("name"));
                row.add(rs.getString("phone"));
                row.add(rs.getString("visit_date"));
                row.add(rs.getString("follow_up_date"));
                row.add(rs.getString("symptoms"));
                row.add(rs.getString("prescription"));
                data.add(row);
            }

        } catch (Exception e) {
            System.err.println("❌ Error fetching follow-up patients:");
            e.printStackTrace();
        }

        return data;
    }
}
