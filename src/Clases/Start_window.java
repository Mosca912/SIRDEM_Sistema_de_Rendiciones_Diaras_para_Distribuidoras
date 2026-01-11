/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Facuymayriver
 */
public class Start_window {

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void ShowProductsNext(Connection conexion, DefaultTableModel modelo, JTable tabla) throws SQLException {
        PreparedStatement stm = conexion.prepareStatement("SELECT p.Descripcion, p.Unidad, c.Categoria, p.Vencimiento FROM productos p INNER JOIN categoria c ON c.id_categoria = p.id_categoria WHERE p.Borrado = 0 AND DATEDIFF(p.Vencimiento, CURDATE()) BETWEEN 11 AND 30 ORDER BY p.Vencimiento ASC;");
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
            Object[] fila = new Object[4];
            fila[0] = rs.getString("p.Descripcion");
            fila[1] = rs.getString("p.Unidad");
            fila[2] = rs.getString("c.Categoria");
            try {
                Date fechaVencimiento = rs.getDate("p.Vencimiento");

                if (fechaVencimiento != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    fila[3] = sdf.format(fechaVencimiento);
                } else {
                    fila[3] = "Sin fecha";
                }
            } catch (SQLException e) {
                fila[3] = "Error formato";
            }

            modelo.addRow(fila);
        }

        DefaultTableCellRenderer renderizador = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                c.setBackground(table.getBackground());
                c.setForeground(table.getForeground());

                Color amarilloPastel = new Color(255, 255, 204);

                try {
                    Object fechaObj = table.getValueAt(row, 3);

                    if (fechaObj != null && !fechaObj.toString().equals("Sin fecha") && !fechaObj.toString().equals("00-00-0000") && !fechaObj.toString().equals("Error formato")) {
                        c.setBackground(amarilloPastel);
                        c.setFont(c.getFont().deriveFont(Font.BOLD));
                    } else {
                        c.setBackground(Color.WHITE);
                    }
                } catch (Exception e) {
                    c.setBackground(Color.WHITE);
                }

                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                }
                return c;
            }
        };
        renderizador.setHorizontalAlignment(SwingConstants.CENTER);
        tabla.setDefaultRenderer(Object.class, renderizador);
        for (int i = 0; i < tabla.getColumnModel().getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(renderizador);
        }
    }

    public static void ShowProductsMaturity(Connection conexion, DefaultTableModel modelo, JTable tabla) throws SQLException {
        PreparedStatement stm = conexion.prepareStatement("SELECT p.Descripcion, p.Unidad, c.Categoria, p.Vencimiento FROM productos p INNER JOIN categoria c ON c.id_categoria = p.id_categoria WHERE p.Borrado = 0 AND DATEDIFF(p.Vencimiento, CURDATE()) BETWEEN 0 AND 10 ORDER BY p.Vencimiento ASC;");
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
            Object[] fila = new Object[4];
            fila[0] = rs.getString("p.Descripcion");
            fila[1] = rs.getString("p.Unidad");
            fila[2] = rs.getString("c.Categoria");
            try {
                Date fechaVencimiento = rs.getDate("p.Vencimiento");

                if (fechaVencimiento != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    fila[3] = sdf.format(fechaVencimiento);
                } else {
                    fila[3] = "Sin fecha";
                }
            } catch (SQLException e) {
                fila[3] = "Error formato";
            }

            modelo.addRow(fila);
        }

        DefaultTableCellRenderer renderizador = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                c.setBackground(table.getBackground());
                c.setForeground(table.getForeground());

                Color rojoIntenso = new Color(180, 0, 0); // Rojo fuerte
                Color rojoPastel = new Color(255, 204, 203);

                try {
                    Object fechaObj = table.getValueAt(row, 3);

                    if (fechaObj != null && !fechaObj.toString().equals("Sin fecha") && !fechaObj.toString().equals("00-00-0000") && !fechaObj.toString().equals("Error formato")) {
                        LocalDate fechaVenc = LocalDate.parse(fechaObj.toString(), dtf);
                        LocalDate hoy = LocalDate.now();

                        long diasRestantes = ChronoUnit.DAYS.between(hoy, fechaVenc);

                        if (diasRestantes <= 0) {
                            c.setBackground(rojoIntenso);
                            c.setForeground(Color.WHITE);
                            c.setFont(c.getFont().deriveFont(Font.BOLD));
                        } else if (diasRestantes <= 10) {
                            c.setBackground(rojoPastel);
                            c.setFont(c.getFont().deriveFont(Font.BOLD));
                        }
                    } else {
                        c.setBackground(Color.WHITE);
                    }
                } catch (Exception e) {
                    c.setBackground(Color.WHITE);
                }

                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                }
                return c;
            }
        };
        renderizador.setHorizontalAlignment(SwingConstants.CENTER);
        tabla.setDefaultRenderer(Object.class, renderizador);
        for (int i = 0; i < tabla.getColumnModel().getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(renderizador);
        }
    }
}
