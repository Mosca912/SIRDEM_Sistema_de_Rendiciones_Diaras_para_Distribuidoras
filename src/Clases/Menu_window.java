/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Facuymayriver
 */
public class Menu_window {

    static int valid;
    static int rango;
    static int iduser;
    static String correo;

    public static int Login(Connection conexion, String dni, String contrasena) throws SQLException {
        valid = 0;
        String contrasenadb;
        String sql = "SELECT contrasena, correo, id_rangos, id_Usuario FROM usuario WHERE dni = ? and Borrado = 0;";
        try {
            PreparedStatement ps2 = conexion.prepareStatement(sql);
            ps2.setString(1, dni);
            ResultSet rs = ps2.executeQuery();
            if (rs.next()) {
                contrasenadb = rs.getString("contrasena");
                rango = rs.getInt("id_rangos");
                iduser = rs.getInt("id_Usuario");
                correo = rs.getString("correo");
                if (BCrypt.checkpw(contrasena, contrasenadb)) {
                    valid = 1;
                } else {
                    valid = 0;
                    rango = 0;
                    iduser = 0;
                    correo = "";
                    JOptionPane.showMessageDialog(null, "Contraseña Incorrecta");
                }
            } else {
                JOptionPane.showMessageDialog(null, "DNI Incorrecto/Usuario inexistente");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR " + e);
        }
        return valid;
    }

    public static int rank() {
        return rango;
    }

    public static void Logout() {
        valid = 0;
        rango = 0;
        iduser = 0;
        correo = "";
    }
}
