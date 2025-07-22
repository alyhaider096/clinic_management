/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

public class LoginDao {
    public static boolean validate(String username, String password) {
        // Hardcoded credentials for single admin
        return username.equals("admin") && password.equals("clinic123");
    }
}
