/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// File: dao/VisitStatDao.java
package dao;

import db.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class VisitStatsDao {

    public static Map<String, Integer> getMonthlyVisitStats() {
        Map<String, Integer> stats = new LinkedHashMap<>();

        String query = "SELECT DATE_FORMAT(visit_date, '%Y-%m') AS month, COUNT(*) AS count " +
                       "FROM patients " +
                       "GROUP BY month " +
                       "ORDER BY month DESC " +
                       "LIMIT 12";

        try (Connection con = ConnectionProvider.getCon();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                stats.put(rs.getString("month"), rs.getInt("count"));
            }

        } catch (Exception e) {
            System.err.println("❌ Error fetching visit statistics:");
            e.printStackTrace();
        }

        return stats;
    }
}
