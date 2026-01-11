/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Vistas.ABM.Historical_window;
import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
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
public class Trusted_window {

    static int indice1, indice2;
    static List<Integer> pendientes = new ArrayList<>();
    static List<Integer> acreditados = new ArrayList<>();
    static LocalDate hoy = LocalDate.now();
    static int mesActual = hoy.getMonthValue();

    public static void ShowTrustedGeneral(Connection conexion, DefaultTableModel modelo, int id, JTable tabla, JLabel general, JLabel Pendiente, JLabel Acreditado) throws SQLException {
        indice1 = 0;
        indice2 = 0;

        PreparedStatement stm = conexion.prepareStatement("SELECT fiado.idFiado, fiado.Saldo, Fecha.fecha, clientes.Nombre, clientes.Apellido from fiado inner join clientes on fiado.id_Cliente=clientes.id_Clientes inner join ficha on fiado.id_Ficha=ficha.id_Ficha inner join fecha on ficha.id_Fecha=fecha.id_Fecha where ficha.id_Preventista=? and fiado.Saldo>0 and MONTH(Fecha.fecha)=?;");
        stm.setInt(1, id);
        stm.setInt(2, mesActual);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            Object[] fila = new Object[4];
            fila[0] = rs.getInt("fiado.idFiado");
            fila[1] = rs.getString("fiado.Saldo");
            fila[2] = rs.getString("clientes.Nombre") + " " + rs.getString("clientes.Apellido");
            fila[3] = rs.getString("Fecha.fecha");
            modelo.addRow(fila);
        }

        // 🚩 Ahora calculo los totales recorriendo el modelo
        int tg = 0;
        int tp = 0;
        int ta = 0;

        for (int i = 0; i < tabla.getRowCount(); i++) {
            int idF = (int) modelo.getValueAt(i, 0);

            Object fal = modelo.getValueAt(i, 1);
            int faltante = Integer.parseInt(fal.toString()); // ✅ parseamos

            PreparedStatement stm2 = conexion.prepareStatement("SELECT SUM(saldo) as total FROM historial WHERE id_fiado=?");
            stm2.setInt(1, idF);
            ResultSet rs2 = stm2.executeQuery();
            int saldohist = 0;
            if (rs2.next()) {
                saldohist = rs2.getInt("total");
            }

            tg += faltante;
            if (faltante > saldohist) {
                tp += faltante;
            } else {
                ta += faltante;
            }
        }

        // ✅ Ahora recién actualizo labels
        general.setText("" + tg);
        Pendiente.setText("" + tp);
        Acreditado.setText("" + ta);

        acreditados.clear();
        pendientes.clear();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            int idF = (int) modelo.getValueAt(i, 0);
            int faltante = Integer.parseInt(modelo.getValueAt(i, 1).toString());

            PreparedStatement stm2 = conexion.prepareStatement("SELECT SUM(saldo) as total FROM historial WHERE id_fiado=?");
            stm2.setInt(1, idF);
            ResultSet rs2 = stm2.executeQuery();
            int saldohist = 0;
            if (rs2.next()) {
                saldohist = rs2.getInt("total");
            }

            if (faltante > saldohist) {
                pendientes.add(idF);
            } else {
                acreditados.add(idF);
            }
        }
        // 🚩 Después de cargar, aplicamos el renderer UNA sola vez
        DefaultTableCellRenderer renderizador = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                int idF = Integer.parseInt(table.getValueAt(row, 0).toString());

                if (pendientes.contains(idF)) {
                    c.setBackground(Color.RED);
                    c.setForeground(Color.WHITE);
                } else if (acreditados.contains(idF)) {
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
    
    public static void Verification(Connection conexion, int saldo, int id, String fecha, JFrame venta) throws SQLException {
        int band = 0;
        PreparedStatement stm = conexion.prepareStatement("SELECT SUM(saldo) FROM historial WHERE id_fiado=?");
        stm.setInt(1, id);
        ResultSet rs = stm.executeQuery();
        try {
            if (rs.next()) {
                int saldohist = rs.getInt("SUM(saldo)");
                if (saldo <= saldohist) {
                    band = 1;
                    Historical_window ventana = new Historical_window(id, saldo, fecha, band, venta);
                    ventana.setVisible(true);
                } else {
                    Historical_window ventana = new Historical_window(id, saldo, fecha, band, venta);
                    ventana.setVisible(true);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR12");
        }
    }

    public static void ShowTrustedMonth(Connection conexion, DefaultTableModel modelo, int id, JTable tabla, JLabel general, JLabel Acreditado, JLabel Pendiente, int indice) throws SQLException {
        indice1 = 0;
        indice2 = 0;
        
        PreparedStatement stm = conexion.prepareStatement("SELECT fiado.idFiado, fiado.Saldo, Fecha.fecha, clientes.Nombre, clientes.Apellido from fiado inner join clientes on fiado.id_Cliente=clientes.id_Clientes inner join ficha on fiado.id_Ficha=ficha.id_Ficha inner join fecha on ficha.id_Fecha=fecha.id_Fecha where ficha.id_Preventista=? and fiado.Saldo>0 and MONTH(Fecha.fecha)=?;");
        stm.setInt(1, id);
        stm.setInt(2, indice);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            Object[] fila = new Object[4];
            fila[0] = rs.getInt("fiado.idFiado");
            fila[1] = rs.getString("fiado.Saldo");
            fila[2] = rs.getString("clientes.Nombre") + " " + rs.getString("clientes.Apellido");
            fila[3] = rs.getString("Fecha.fecha");
            modelo.addRow(fila);
        }

        int tg = 0;
        int tp = 0;
        int ta = 0;

        for (int i = 0; i < tabla.getRowCount(); i++) {
            int idF = (int) modelo.getValueAt(i, 0);

            Object fal = modelo.getValueAt(i, 1);
            int faltante = Integer.parseInt(fal.toString()); // ✅ parseamos

            PreparedStatement stm2 = conexion.prepareStatement("SELECT SUM(saldo) as total FROM historial WHERE id_fiado=?");
            stm2.setInt(1, idF);
            ResultSet rs2 = stm2.executeQuery();
            int saldohist = 0;
            if (rs2.next()) {
                saldohist = rs2.getInt("total");
            }

            tg += faltante;
            if (faltante > saldohist) {
                tp += faltante;
            } else {
                ta += faltante;
            }
        }

        // ✅ Ahora recién actualizo labels
        general.setText("" + tg);
        Pendiente.setText("" + tp);
        Acreditado.setText("" + ta);

        acreditados.clear();
        pendientes.clear();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            int idF = (int) modelo.getValueAt(i, 0);
            int faltante = Integer.parseInt(modelo.getValueAt(i, 1).toString());

            PreparedStatement stm2 = conexion.prepareStatement("SELECT SUM(saldo) as total FROM historial WHERE id_fiado=?");
            stm2.setInt(1, idF);
            ResultSet rs2 = stm2.executeQuery();
            int saldohist = 0;
            if (rs2.next()) {
                saldohist = rs2.getInt("total");
            }

            if (faltante > saldohist) {
                pendientes.add(idF);
            } else {
                acreditados.add(idF);
            }
        }
        // 🚩 Después de cargar, aplicamos el renderer UNA sola vez
        DefaultTableCellRenderer renderizador = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                int idF = Integer.parseInt(table.getValueAt(row, 0).toString());

                if (pendientes.contains(idF)) {
                    c.setBackground(Color.RED);
                    c.setForeground(Color.WHITE);
                } else if (acreditados.contains(idF)) {
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

    public static void ShowTrustedPending(Connection conexion, DefaultTableModel modelo, int id, JTable tabla, JLabel general, JLabel Pendiente, JLabel Acreditado) throws SQLException {
        modelo.setRowCount(0);
        for (Integer valor : pendientes) {
            PreparedStatement stm = conexion.prepareStatement(
                    "SELECT fiado.idFiado, fiado.Saldo, Fecha.fecha, clientes.Nombre, clientes.Apellido "
                    + "FROM fiado "
                    + "INNER JOIN clientes ON fiado.id_Cliente=clientes.id_Clientes "
                    + "INNER JOIN ficha ON fiado.id_Ficha=ficha.id_Ficha "
                    + "INNER JOIN fecha ON ficha.id_Fecha=fecha.id_Fecha "
                    + "WHERE ficha.id_Preventista=? AND fiado.idFiado=? and MONTH(Fecha.fecha)=?;"
            );
            stm.setInt(1, id);
            stm.setInt(2, valor);
            stm.setInt(3, mesActual);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Object[] fila = new Object[4];
                fila[0] = rs.getInt("fiado.idFiado");
                fila[1] = rs.getString("fiado.Saldo");
                fila[2] = rs.getString("clientes.Nombre") + " " + rs.getString("clientes.Apellido");
                fila[3] = rs.getString("Fecha.fecha");
                modelo.addRow(fila);
            }
        }

        // 🚩 Calculo los totales UNA sola vez
        int tg = 0, tp = 0, ta = 0;
        for (int e = 0; e < tabla.getRowCount(); e++) {
            int idF = (int) modelo.getValueAt(e, 0);
            int faltante = Integer.parseInt(modelo.getValueAt(e, 1).toString());

            PreparedStatement stm2 = conexion.prepareStatement("SELECT SUM(saldo) as total FROM historial WHERE id_fiado=?");
            stm2.setInt(1, idF);
            ResultSet rs2 = stm2.executeQuery();
            int saldohist = 0;
            if (rs2.next()) {
                saldohist = rs2.getInt("total");
            }

            tg += faltante;
            if (faltante > saldohist) {
                tp += faltante;
            } else {
                ta += faltante;
            }
        }

        // ✅ Actualizo labels
        general.setText("" + tg);
        Pendiente.setText("" + tp);
        Acreditado.setText("" + ta);
    }
}
