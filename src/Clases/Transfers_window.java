/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Facuymayriver
 */
public class Transfers_window {

    static List<Integer> pendientes2 = new ArrayList<>();
    static List<Integer> acreditados2 = new ArrayList<>();
    static LocalDate hoy = LocalDate.now();
    static int mesActual = hoy.getMonthValue();

    public static void ShowTransfersGeneral(Connection conexion, DefaultTableModel modelo, int id, JTable tabla, JLabel general, JLabel acreditado, JLabel pendiente) throws SQLException {
        int totalgasto = 0;
        PreparedStatement stm = conexion.prepareStatement("SELECT transferencia.idTransferencia, clientes.Nombre, clientes.Apellido, transferencia.Saldo, estado.Estado from  transferencia inner join ficha on transferencia.id_Ficha=ficha.id_Ficha inner join estado on transferencia.id_Estado=estado.idEstado inner join clientes on transferencia.id_cliente=clientes.id_Clientes inner join fecha on ficha.id_Fecha=fecha.id_Fecha where ficha.id_preventista=? and MONTH(fecha.fecha)=?;");
        stm.setInt(1, id);
        stm.setInt(2, mesActual);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            Object[] fila = new Object[4];
            fila[0] = rs.getInt("transferencia.idTransferencia");
            fila[1] = rs.getString("clientes.Nombre") + " " + rs.getString("clientes.Apellido");
            fila[2] = rs.getInt("transferencia.Saldo");
            fila[3] = rs.getString("estado.Estado");
            totalgasto = totalgasto + rs.getInt("transferencia.Saldo");
            modelo.addRow(fila);
        }
        general.setText("" + totalgasto);

        acreditados2.clear();
        pendientes2.clear();
        int saldopos = 0;
        int saldopend = 0;
        for (int i = 0; i < modelo.getRowCount(); i++) {
            int idF = (int) modelo.getValueAt(i, 0);

            PreparedStatement stm2 = conexion.prepareStatement("SELECT id_Estado, Saldo FROM transferencia WHERE idTransferencia=?");
            stm2.setInt(1, idF);
            ResultSet rs2 = stm2.executeQuery();
            int saldohist = 0;
            int saldo;
            if (rs2.next()) {
                saldohist = rs2.getInt("id_Estado");
                saldo = rs2.getInt("Saldo");
                if (saldohist == 2) {
                    pendientes2.add(idF);
                    saldopend = saldopend + saldo;
                } else {
                    acreditados2.add(idF);
                    saldopos = saldopos + saldo;
                }
            }
        }

        acreditado.setText("" + saldopos);
        pendiente.setText("" + saldopend);

        // 🚩 Después de cargar, aplicamos el renderer UNA sola vez
        DefaultTableCellRenderer renderizador = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                int idF = Integer.parseInt(table.getValueAt(row, 0).toString());

                if (pendientes2.contains(idF)) {
                    c.setBackground(Color.YELLOW);
                    c.setForeground(Color.BLACK);
                } else if (acreditados2.contains(idF)) {
                    c.setBackground(Color.GREEN);
                    c.setForeground(Color.BLACK);
                } else {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                }

                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(table.getSelectionForeground());
                }
                return c;
            }
        };

        renderizador.setHorizontalAlignment(SwingConstants.CENTER);
        tabla.setDefaultRenderer(Object.class, renderizador);
        // Aplicamos el render a todas las columnas
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(renderizador);
        }
    }

    public static void ShowTransfersPending(Connection conexion, DefaultTableModel modelo, int id, JTable tabla, JLabel general, JLabel pendiente, JLabel acreditado) throws SQLException {
        modelo.setRowCount(0);
        int totalgasto = 0;
        for (Integer valor : pendientes2) {
            PreparedStatement stm = conexion.prepareStatement(
                    "SELECT transferencia.idTransferencia, clientes.Nombre, clientes.Apellido, transferencia.Saldo, estado.Estado from  transferencia inner join ficha on transferencia.id_Ficha=ficha.id_Ficha inner join estado on transferencia.id_Estado=estado.idEstado inner join clientes on transferencia.id_cliente=clientes.id_Clientes inner join fecha on ficha.id_Fecha=fecha.id_Fecha where ficha.id_preventista=? and MONTH(fecha.fecha)=? and transferencia.idTransferencia=?;"
            );
            stm.setInt(1, id);
            stm.setInt(2, mesActual);
            stm.setInt(3, valor);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Object[] fila = new Object[4];
                fila[0] = rs.getInt("transferencia.idTransferencia");
                fila[1] = rs.getString("clientes.Nombre") + " " + rs.getString("clientes.Apellido");
                fila[2] = rs.getInt("transferencia.Saldo");
                fila[3] = rs.getString("estado.Estado");
                totalgasto = totalgasto + rs.getInt("transferencia.Saldo");
                modelo.addRow(fila);
            }
        }
        general.setText("" + totalgasto);

        int saldopend = 0;
        for (Integer valor2 : pendientes2) {

            PreparedStatement stm2 = conexion.prepareStatement("SELECT Saldo FROM transferencia WHERE idTransferencia=?");
            stm2.setInt(1, valor2);
            ResultSet rs2 = stm2.executeQuery();
            int saldo;
            if (rs2.next()) {
                saldo = rs2.getInt("Saldo");
                saldopend = saldopend + saldo;
            }
        }

        acreditado.setText("" + 0);
        pendiente.setText("" + saldopend);

        // 🚩 Después de cargar, aplicamos el renderer UNA sola vez
        DefaultTableCellRenderer renderizador = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                int idF = Integer.parseInt(table.getValueAt(row, 0).toString());

                if (pendientes2.contains(idF)) {
                    c.setBackground(Color.YELLOW);
                    c.setForeground(Color.BLACK);
                }

                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(table.getSelectionForeground());
                }
                return c;
            }
        };

        renderizador.setHorizontalAlignment(SwingConstants.CENTER);
        tabla.setDefaultRenderer(Object.class, renderizador);
        // Aplicamos el render a todas las columnas
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(renderizador);
        }
    }

    public static void ShowTransfersMonth(Connection conexion, DefaultTableModel modelo, int id, JTable tabla, JLabel general, int indice, JLabel acreditado, JLabel pendiente) throws SQLException {
        int totalgasto = 0;
        PreparedStatement stm = conexion.prepareStatement("SELECT transferencia.idTransferencia, clientes.Nombre, clientes.Apellido, transferencia.Saldo, estado.Estado from  transferencia inner join ficha on transferencia.id_Ficha=ficha.id_Ficha inner join estado on transferencia.id_Estado=estado.idEstado inner join clientes on transferencia.id_cliente=clientes.id_Clientes inner join fecha on ficha.id_Fecha=fecha.id_Fecha where ficha.id_preventista=? and MONTH(fecha.fecha)=?;");
        stm.setInt(1, id);
        stm.setInt(2, indice);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            Object[] fila = new Object[4];
            fila[0] = rs.getInt("transferencia.idTransferencia");
            fila[1] = rs.getString("clientes.Nombre") + " " + rs.getString("clientes.Apellido");
            fila[2] = rs.getInt("transferencia.Saldo");
            fila[3] = rs.getString("estado.Estado");
            totalgasto = totalgasto + rs.getInt("transferencia.Saldo");
            modelo.addRow(fila);
        }
        general.setText("" + totalgasto);

        acreditados2.clear();
        pendientes2.clear();
        int saldopos = 0;
        int saldopend = 0;
        for (int i = 0; i < modelo.getRowCount(); i++) {
            int idF = (int) modelo.getValueAt(i, 0);

            PreparedStatement stm2 = conexion.prepareStatement("SELECT id_Estado, Saldo FROM transferencia WHERE idTransferencia=?");
            stm2.setInt(1, idF);
            ResultSet rs2 = stm2.executeQuery();
            int saldohist = 0;
            int saldo;
            if (rs2.next()) {
                saldohist = rs2.getInt("id_Estado");
                saldo = rs2.getInt("Saldo");
                if (saldohist == 2) {
                    pendientes2.add(idF);
                    saldopend = saldopend + saldo;
                } else {
                    acreditados2.add(idF);
                    saldopos = saldopos + saldo;
                }
            }
        }

        acreditado.setText("" + saldopos);
        pendiente.setText("" + saldopend);
        // 🚩 Después de cargar, aplicamos el renderer UNA sola vez
        DefaultTableCellRenderer renderizador = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                int idF = Integer.parseInt(table.getValueAt(row, 0).toString());

                if (pendientes2.contains(idF)) {
                    c.setBackground(Color.YELLOW);
                    c.setForeground(Color.BLACK);
                } else if (acreditados2.contains(idF)) {
                    c.setBackground(Color.GREEN);
                    c.setForeground(Color.BLACK);
                } else {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                }

                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(table.getSelectionForeground());
                }
                return c;
            }
        };

        renderizador.setHorizontalAlignment(SwingConstants.CENTER);
        tabla.setDefaultRenderer(Object.class, renderizador);
        // Aplicamos el render a todas las columnas
        for (int i = 0; i < modelo.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(renderizador);
        }
    }

    public static void ActEst(Connection conexion, int id, int estado) throws SQLException {
        PreparedStatement stm = conexion.prepareStatement("UPDATE transferencia SET id_Estado= ? WHERE idTransferencia = ?");
        stm.setInt(1, estado);
        stm.setInt(2, id);
        try {
            stm.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR " + e);
        }
    }
}
