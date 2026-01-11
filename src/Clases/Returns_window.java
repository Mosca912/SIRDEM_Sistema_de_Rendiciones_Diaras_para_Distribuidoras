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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Facuymayriver
 */
public class Returns_window {

    static int indice1, indice2;

    public static void ShowReturnsGeneral(Connection conexion, DefaultTableModel modelo, int id, JTable tabla, JLabel general, JLabel s1, JLabel s2, JLabel s3, JLabel s4) throws SQLException {
        indice1 = 0;
        indice2 = 0;
        int totalgasto = 0;
        LocalDate hoy = LocalDate.now();
        int mesActual = hoy.getMonthValue();
        PreparedStatement stm = conexion.prepareStatement("SELECT d.Saldo AS Saldo, cl.Nombre as Nombre, cl.Apellido as Apellido, z.Nombre as Zona, fe.Fecha as Fecha, CASE WHEN DAYOFMONTH(fe.Fecha) BETWEEN 1 AND 8 THEN 1 WHEN DAYOFMONTH(fe.Fecha) BETWEEN 9 AND 16 THEN 2 WHEN DAYOFMONTH(fe.Fecha) BETWEEN 17 AND 24 THEN 3 ELSE 4 END AS SemanaContable FROM  devolucion AS d  INNER JOIN ficha AS f ON d.id_Ficha = f.id_Ficha INNER JOIN fecha AS fe ON f.id_Fecha = fe.id_Fecha INNER JOIN clientes as cl on d.id_Cliente=cl.id_Clientes INNER JOIN zonas as z on f.id_Zona=z.id_Zonas WHERE  MONTH(fe.Fecha) = ? AND YEAR(fe.Fecha) = YEAR(CURDATE()) AND f.id_preventista = ? ORDER BY fe.Fecha;");
        stm.setInt(1, mesActual);
        stm.setInt(2, id);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            Object[] fila = new Object[5];
            String na = rs.getString("Nombre") + " " + rs.getString("Apellido");
            fila[1] = na;
            fila[2] = rs.getString("Zona");
            fila[3] = rs.getInt("Saldo");
            Timestamp fechaTimestamp = rs.getTimestamp("Fecha");
            String fechaFormateada;
            if (fechaTimestamp != null) {
                LocalDate fechaSolo = fechaTimestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                DateTimeFormatter formatoDeseado = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                fechaFormateada = fechaSolo.format(formatoDeseado);
            } else {
                fechaFormateada = "N/A";
            }
            fila[4] = fechaFormateada;
            fila[0] = rs.getInt("SemanaContable");
            modelo.addRow(fila);
        }
        general.setText("" + totalgasto);
        int tg = 0;
        int SEM1 = 0;
        int SEM2 = 0;
        int SEM3 = 0;
        int SEM4 = 0;

        for (int i = 1; i <= 4; i++) {
            PreparedStatement stm2 = conexion.prepareStatement("SELECT Subconsulta.SemanaContable, Subconsulta.SaldoTotal FROM (SELECT CASE WHEN DAYOFMONTH(fe.Fecha) BETWEEN 1 AND 8 THEN 1 WHEN DAYOFMONTH(fe.Fecha) BETWEEN 9 AND 16 THEN 2 WHEN DAYOFMONTH(fe.Fecha) BETWEEN 17 AND 24 THEN 3 ELSE 4 END AS SemanaContable, SUM(d.Saldo) AS SaldoTotal FROM devolucion AS d INNER JOIN ficha AS f ON d.id_Ficha = f.id_Ficha INNER JOIN fecha AS fe ON f.id_Fecha = fe.id_Fecha INNER JOIN clientes AS cl ON d.id_Cliente = cl.id_Clientes INNER JOIN zonas AS z ON f.id_Zona = z.id_Zonas WHERE MONTH(fe.Fecha) = ? AND YEAR(fe.Fecha) = YEAR(CURDATE()) AND f.id_preventista = ? GROUP BY SemanaContable) AS Subconsulta WHERE Subconsulta.SemanaContable = ?;");
            stm2.setInt(1, mesActual);
            stm2.setInt(2, id);
            stm2.setInt(3, i);
            ResultSet rs2 = stm2.executeQuery();
            if (rs2.next()) {
                switch (i) {
                    case 1:
                        SEM1 = rs2.getInt("Subconsulta.SaldoTotal");
                        break;
                    case 2:
                        SEM2 = rs2.getInt("Subconsulta.SaldoTotal");
                        break;
                    case 3:
                        SEM3 = rs2.getInt("Subconsulta.SaldoTotal");
                        break;
                    case 4:
                        SEM4 = rs2.getInt("Subconsulta.SaldoTotal");
                        break;
                    default:
                        break;
                }
            }
        }
        tg = SEM1 + SEM2 + SEM3 + SEM4;
        s1.setText("" + SEM1);
        s2.setText("" + SEM2);
        s3.setText("" + SEM3);
        s4.setText("" + SEM4);
        general.setText("" + tg);

        // 🚩 Después de cargar, aplicamos el renderer UNA sola vez
        DefaultTableCellRenderer renderizador = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                int Semana = Integer.parseInt(table.getValueAt(row, 0).toString());

                switch (Semana) {
                    case 1:
                        c.setBackground(new Color(255, 255, 204));
                        c.setForeground(Color.BLACK);
                        break;
                    case 2:
                        c.setBackground(new Color(255, 204, 204));
                        c.setForeground(Color.BLACK);
                        break;
                    case 3:
                        c.setBackground(new Color(255, 153, 255));
                        c.setForeground(Color.BLACK);
                        break;
                    case 4:
                        c.setBackground(new Color(204, 255, 255));
                        c.setForeground(Color.BLACK);
                        break;
                    default:
                        break;
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

    public static void ShowReturnsMonth(Connection conexion, DefaultTableModel modelo, int id, JTable tabla, JLabel general, int ind, JLabel s1, JLabel s2, JLabel s3, JLabel s4) throws SQLException {
        indice1 = 0;
        indice2 = 0;

        int totalgasto = 0;
        PreparedStatement stm = conexion.prepareStatement("SELECT d.Saldo AS Saldo, cl.Nombre as Nombre, cl.Apellido as Apellido, z.Nombre as Zona, fe.Fecha, CASE WHEN DAYOFMONTH(fe.Fecha) BETWEEN 1 AND 8 THEN 1 WHEN DAYOFMONTH(fe.Fecha) BETWEEN 9 AND 16 THEN 2 WHEN DAYOFMONTH(fe.Fecha) BETWEEN 17 AND 24 THEN 3 ELSE 4 END AS SemanaContable FROM  devolucion AS d  INNER JOIN ficha AS f ON d.id_Ficha = f.id_Ficha INNER JOIN fecha AS fe ON f.id_Fecha = fe.id_Fecha INNER JOIN clientes as cl on d.id_Cliente=cl.id_Clientes INNER JOIN zonas as z on f.id_Zona=z.id_Zonas WHERE  MONTH(fe.Fecha) = ? AND YEAR(fe.Fecha) = YEAR(CURDATE()) AND f.id_preventista = ? ORDER BY fe.Fecha;");
        stm.setInt(1, ind);
        stm.setInt(2, id);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            Object[] fila = new Object[5];
            String na = rs.getString("Nombre") + " " + rs.getString("Apellido");
            fila[1] = na;
            fila[2] = rs.getString("Zona");
            fila[3] = rs.getInt("Saldo");
            Timestamp fechaTimestamp = rs.getTimestamp("Fecha");
            String fechaFormateada;
            if (fechaTimestamp != null) {
                LocalDate fechaSolo = fechaTimestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                DateTimeFormatter formatoDeseado = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                fechaFormateada = fechaSolo.format(formatoDeseado);
            } else {
                fechaFormateada = "N/A";
            }
            fila[4] = fechaFormateada;
            fila[0] = rs.getInt("SemanaContable");
            modelo.addRow(fila);
        }
        general.setText("" + totalgasto);
        int tg = 0;
        int SEM1 = 0;
        int SEM2 = 0;
        int SEM3 = 0;
        int SEM4 = 0;

        for (int i = 1; i <= 4; i++) {
            PreparedStatement stm2 = conexion.prepareStatement("SELECT Subconsulta.SemanaContable, Subconsulta.SaldoTotal FROM (SELECT CASE WHEN DAYOFMONTH(fe.Fecha) BETWEEN 1 AND 8 THEN 1 WHEN DAYOFMONTH(fe.Fecha) BETWEEN 9 AND 16 THEN 2 WHEN DAYOFMONTH(fe.Fecha) BETWEEN 17 AND 24 THEN 3 ELSE 4 END AS SemanaContable, SUM(d.Saldo) AS SaldoTotal FROM devolucion AS d INNER JOIN ficha AS f ON d.id_Ficha = f.id_Ficha INNER JOIN fecha AS fe ON f.id_Fecha = fe.id_Fecha INNER JOIN clientes AS cl ON d.id_Cliente = cl.id_Clientes INNER JOIN zonas AS z ON f.id_Zona = z.id_Zonas WHERE MONTH(fe.Fecha) = ? AND YEAR(fe.Fecha) = YEAR(CURDATE()) AND f.id_preventista = ? GROUP BY SemanaContable) AS Subconsulta WHERE Subconsulta.SemanaContable = ?;");
            stm2.setInt(1, ind);
            stm2.setInt(2, id);
            stm2.setInt(3, i);
            ResultSet rs2 = stm2.executeQuery();
            if (rs2.next()) {
                switch (i) {
                    case 1:
                        SEM1 = rs2.getInt("Subconsulta.SaldoTotal");
                        break;
                    case 2:
                        SEM2 = rs2.getInt("Subconsulta.SaldoTotal");
                        break;
                    case 3:
                        SEM3 = rs2.getInt("Subconsulta.SaldoTotal");
                        break;
                    case 4:
                        SEM4 = rs2.getInt("Subconsulta.SaldoTotal");
                        break;
                    default:
                        break;
                }
            }
        }
        tg = SEM1 + SEM2 + SEM3 + SEM4;
        s1.setText("" + SEM1);
        s2.setText("" + SEM2);
        s3.setText("" + SEM3);
        s4.setText("" + SEM4);
        general.setText("" + tg);

        // 🚩 Después de cargar, aplicamos el renderer UNA sola vez
        DefaultTableCellRenderer renderizador = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                int Semana = Integer.parseInt(table.getValueAt(row, 0).toString());

                switch (Semana) {
                    case 1:
                        c.setBackground(new Color(255, 255, 204));
                        c.setForeground(Color.BLACK);
                        break;
                    case 2:
                        c.setBackground(new Color(255, 204, 204));
                        c.setForeground(Color.BLACK);
                        break;
                    case 3:
                        c.setBackground(new Color(255, 153, 255));
                        c.setForeground(Color.BLACK);
                        break;
                    case 4:
                        c.setBackground(new Color(204, 255, 255));
                        c.setForeground(Color.BLACK);
                        break;
                    default:
                        break;
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
}
