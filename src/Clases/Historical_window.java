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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Facuymayriver
 */
public class Historical_window {

    public static void ShowHistorial(Connection conexion, DefaultTableModel modelo, int id, JTable tabla) throws SQLException {

        PreparedStatement stm = conexion.prepareStatement("SELECT historial.idhistorial, historial.saldo, historial.fecha FROM historial WHERE historial.id_fiado=?");
        stm.setInt(1, id);
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
            Object[] fila = new Object[3];
            fila[0] = rs.getInt("historial.idhistorial");
            fila[1] = rs.getInt("historial.saldo");
            fila[2] = rs.getString("historial.fecha");
            modelo.addRow(fila);
        }

    }

    public static void AddHistorical(Connection conexion, int saldo, String Fecha, int id) throws SQLException {
        PreparedStatement stm = conexion.prepareStatement("INSERT INTO historial (saldo, fecha, id_fiado) VALUES (?,?,?);");
        stm.setInt(1, saldo);
        stm.setString(2, Fecha);
        stm.setInt(3, id);
        try {
            stm.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e);
        }
    }

    public static int Verification(Connection conexion, int saldo, int id) throws SQLException {
        int x = 0;
        PreparedStatement stm = conexion.prepareStatement("SELECT SUM(saldo) FROM historial WHERE id_fiado=?");
        stm.setInt(1, id);
        ResultSet rs = stm.executeQuery();
        try {
            if (rs.next()) {
                int saldohist = rs.getInt("SUM(saldo)");
                if (saldo == saldohist) {
                    x = 1;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e);
        }
        return x;
    }

}
