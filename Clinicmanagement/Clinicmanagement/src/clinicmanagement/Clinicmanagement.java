/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package clinicmanagement;

import gui.Login;
import javax.swing.SwingUtilities;

public class Clinicmanagement {
    public static void main(String[] args) {
        // Launch the login screen
        SwingUtilities.invokeLater(() -> new Login());
    }
}
