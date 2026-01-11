/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Facuymayriver
 */
public class Create_window {

    public static void CreateInfo(int flag, JLabel info) {
        switch (flag) {
            case 0:
                info.setText("Zonas");
                return;
            case 1:
                info.setText("Categorias");
                return;
            case 2:
                info.setText("Gastos");
        }
    }

    public static void AddInfo(Connection conexion, int flag, String desc, JDialog window) throws SQLException {
        switch (flag) {
            case 0:
                PreparedStatement stm = conexion.prepareStatement("INSERT INTO zonas (Nombre, Borrado) VALUES (?,0)");
                stm.setString(1, desc);
                try {
                    stm.execute();
                    JOptionPane.showMessageDialog(null, "¡Zona agregada correctamente!");
                    window.dispose();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "ERROR AL AÑADIR ZONA: " + e);
                }
                return;
            case 1:
                PreparedStatement stm2 = conexion.prepareStatement("INSERT INTO categoria (Categoria, Borrado) VALUES (?,0)");
                stm2.setString(1, desc);
                try {
                    stm2.execute();
                    JOptionPane.showMessageDialog(null, "¡Categoria agregada correctamente!");
                    window.dispose();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "ERROR AL AÑADIR CATEGORIA: " + e);
                }
                return;
            case 2:
                PreparedStatement stm3 = conexion.prepareStatement("INSERT INTO tipo (tipo, borrado) VALUES (?,0)");
                stm3.setString(1, desc);
                try {
                    stm3.execute();
                    JOptionPane.showMessageDialog(null, "¡Gasto agregada correctamente!");
                    window.dispose();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "ERROR AL AÑADIR GASTO: " + e);
                    
                }

        }
    }

}
