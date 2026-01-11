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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Facuymayriver
 */
public class Products_window {

    public static class Categories {

        private final int id;
        private final String nombre;
        private final int cont;

        public Categories(int id, String nombre, int cont) {
            this.id = id;
            this.nombre = nombre;
            this.cont = cont;
        }

        public int getId() {
            return id;
        }

        public int getCont() {
            return cont;
        }

        public String getNombre() {
            return nombre;
        }

        @Override
        public String toString() {
            return nombre;
        }
    }

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void ShowProducts(Connection conexion, DefaultTableModel modelo, JTable tabla) throws SQLException {
        PreparedStatement stm = conexion.prepareStatement("Select productos.id_productos, productos.Descripcion, productos.Precio_unitario, productos.Unidad, categoria.Categoria, categoria.id_Categoria, productos.Vencimiento from productos inner join categoria on categoria.id_categoria=productos.id_categoria where productos.Borrado=0");
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
            Object[] fila = new Object[6];
            fila[0] = rs.getString("productos.id_productos");
            fila[1] = rs.getString("productos.Descripcion");
            fila[2] = rs.getString("productos.Precio_unitario");
            fila[3] = rs.getString("productos.Unidad");
            int idC = rs.getInt("categoria.id_Categoria");
            String nombreC = rs.getString("categoria.Categoria");
            fila[4] = new Clases.Products_window.Categories(idC, nombreC, 0);
            try {
                Date fechaVencimiento = rs.getDate("productos.Vencimiento");

                if (fechaVencimiento != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    fila[5] = sdf.format(fechaVencimiento);
                } else {
                    fila[5] = "Sin fecha";
                }
            } catch (SQLException e) {
                fila[5] = "Error formato";
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
                Color amarilloPastel = new Color(255, 255, 204);
                Color verdePastel = new Color(204, 255, 204);

                try {
                    Object fechaObj = table.getValueAt(row, 5);

                    if (fechaObj != null && !fechaObj.toString().equals("Sin fecha") && !fechaObj.toString().equals("00-00-0000") && !fechaObj.toString().equals("Error formato")) {

                        LocalDate fechaVenc = LocalDate.parse(fechaObj.toString(), dtf);
                        LocalDate hoy = LocalDate.now();

                        // Calculamos la diferencia en días
                        long diasRestantes = ChronoUnit.DAYS.between(hoy, fechaVenc);

                        if (diasRestantes <= 0) {
                            c.setBackground(rojoIntenso);
                            c.setForeground(Color.WHITE);
                            c.setFont(c.getFont().deriveFont(Font.BOLD));
                        } else if (diasRestantes <= 10) {
                            c.setBackground(rojoPastel);
                            c.setFont(c.getFont().deriveFont(Font.BOLD));
                        } else if (diasRestantes <= 30) {
                            c.setBackground(amarilloPastel);
                            c.setFont(c.getFont().deriveFont(Font.BOLD));
                        } else {
                            c.setBackground(verdePastel);
                            c.setFont(c.getFont().deriveFont(Font.BOLD));
                        }
                    } else {
                        // Si no tiene fecha, dejar el color por defecto (blanco o el de selección)
                        c.setBackground(Color.WHITE);
                    }
                } catch (Exception e) {
                    c.setBackground(Color.WHITE);
                }

                // Si la fila está seleccionada, mantenemos el color de selección estándar para que el usuario sepa qué marcó
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

    public static void SearchProducts(Connection conexion, DefaultTableModel modelo, JTable tabla, String texto) throws SQLException {
        String sql = "SELECT p.id_productos, p.Descripcion, p.Precio_unitario, p.Unidad, c.Categoria, c.id_Categoria, p.Vencimiento FROM productos p INNER JOIN categoria c ON c.id_categoria = p.id_categoria WHERE p.Borrado = 0 AND (p.Descripcion LIKE ? OR p.id_productos LIKE ?)";

        try {
            PreparedStatement stm = conexion.prepareStatement(sql);
            stm.setString(1, "%" + texto + "%");
            stm.setString(2, "%" + texto + "%");

            ResultSet rs = stm.executeQuery();
            modelo.setRowCount(0);

            while (rs.next()) {
                Object[] fila = new Object[6];
                fila[0] = rs.getString("id_productos");
                fila[1] = rs.getString("Descripcion");
                fila[2] = rs.getString("Precio_unitario");
                fila[3] = rs.getString("Unidad");

                int idC = rs.getInt("id_Categoria");
                String nombreC = rs.getString("Categoria");
                fila[4] = new Clases.Products_window.Categories(idC, nombreC, 0);

                java.sql.Date fechaV = rs.getDate("Vencimiento");
                if (fechaV != null) {
                    fila[5] = new java.text.SimpleDateFormat("dd-MM-yyyy").format(fechaV);
                } else {
                    fila[5] = "00-00-0000";
                }
                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            System.err.println("Error en búsqueda: " + e.getMessage());
        }

        DefaultTableCellRenderer renderizador = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                c.setBackground(table.getBackground());
                c.setForeground(table.getForeground());

                Color rojoIntenso = new Color(180, 0, 0); // Rojo fuerte
                Color rojoPastel = new Color(255, 204, 203);
                Color amarilloPastel = new Color(255, 255, 204);
                Color verdePastel = new Color(204, 255, 204);

                try {
                    Object fechaObj = table.getValueAt(row, 5);

                    if (fechaObj != null && !fechaObj.toString().equals("Sin fecha") && !fechaObj.toString().equals("00-00-0000") && !fechaObj.toString().equals("Error formato")) {

                        LocalDate fechaVenc = LocalDate.parse(fechaObj.toString(), dtf);
                        LocalDate hoy = LocalDate.now();

                        // Calculamos la diferencia en días
                        long diasRestantes = ChronoUnit.DAYS.between(hoy, fechaVenc);

                        if (diasRestantes <= 0) {
                            c.setBackground(rojoIntenso);
                            c.setForeground(Color.WHITE);
                            c.setFont(c.getFont().deriveFont(Font.BOLD));
                        } else if (diasRestantes <= 10) {
                            c.setBackground(rojoPastel);
                            c.setFont(c.getFont().deriveFont(Font.BOLD));
                        } else if (diasRestantes <= 30) {
                            c.setBackground(amarilloPastel);
                            c.setFont(c.getFont().deriveFont(Font.BOLD));
                        } else {
                            c.setBackground(verdePastel);
                            c.setFont(c.getFont().deriveFont(Font.BOLD));
                        }
                    } else {
                        // Si no tiene fecha, dejar el color por defecto (blanco o el de selección)
                        c.setBackground(Color.WHITE);
                    }
                } catch (Exception e) {
                    c.setBackground(Color.WHITE);
                }

                // Si la fila está seleccionada, mantenemos el color de selección estándar para que el usuario sepa qué marcó
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

    public static boolean esFechaValida(String fecha) {
        try {
            // Definimos el formato que viene de la tabla
            java.time.format.DateTimeFormatter formatter
                    = java.time.format.DateTimeFormatter.ofPattern("dd-MM-uuuu")
                            .withResolverStyle(java.time.format.ResolverStyle.STRICT);

            java.time.LocalDate.parse(fecha, formatter);
            return true;
        } catch (java.time.format.DateTimeParseException e) {
            return false;
        }
    }

    public static void ShowCategorie(Connection conexion, JComboBox<Products_window.Categories> combo1) {

        String sql = "SELECT id_Categoria, Categoria FROM categoria where borrado=0 ORDER BY id_Categoria";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            DefaultComboBoxModel<Products_window.Categories> model = new DefaultComboBoxModel<>();

            model.addElement(new Products_window.Categories(0, "Opciones", 0));
            int cont = 1;
            while (rs.next()) {
                int id = rs.getInt("id_Categoria");
                String nombreCompleto = rs.getString("Categoria");
                model.addElement(new Products_window.Categories(id, nombreCompleto, cont));
                cont++;
            }

            combo1.setModel(model);
            AutoCompleteDecorator.decorate(combo1);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR AL MOSTRAR CATEGORIA: " + ex.getMessage());
        }
    }

    public static int VerificationCategorie(Connection conexion) {
        int verification = 0;
        String sql = "SELECT id_Categoria, Categoria FROM categoria where borrado=0 ORDER BY id_Categoria";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "Por favor! Cargue categorias primero para añadir un cliente");
                verification = 1;
                return verification;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR AL MOSTRAR CATEGORIA: " + ex.getMessage());
        }
        return verification;
    }

    public static void DeleteProducts(Connection conexion, int Codigo, int borrado) throws SQLException {

        PreparedStatement stm = conexion.prepareStatement("UPDATE productos SET Borrado= ? WHERE id_Productos = ?");
        stm.setInt(1, borrado);
        stm.setInt(2, Codigo);

        try {
            stm.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR AL ACTUALIZAR PRODUCTOS: " + e);
        }
    }

    public static void AddProd(Connection conexion, String Descripcion, String PrecioU, String Unidad, int Cat, int borrado, String fecha) throws SQLException {
        PreparedStatement stm = conexion.prepareStatement("INSERT INTO productos (Descripcion, Precio_unitario, Unidad, id_Categoria, Borrado, Vencimiento) VALUES (?,?,?,?,?,?)");
        stm.setString(1, Descripcion);
        stm.setString(2, PrecioU);
        stm.setString(3, Unidad);
        stm.setInt(4, Cat);
        stm.setInt(5, borrado);
        stm.setString(6, fecha);
        try {
            stm.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR AL CARGAR PRODUCTO: " + e);
        }
    }

    public static void UpdateProducts(Connection conexion, int Codigo, String Descripcion, String PrecioU, String Unidad, int Cat, int borrado, String fecha) throws SQLException {
        PreparedStatement stm = conexion.prepareStatement("UPDATE productos SET Descripcion = ?, Precio_unitario = ?, Unidad = ?, id_Categoria = ?, Borrado= ?, Vencimiento = ?WHERE id_Productos = ?");
        stm.setString(1, Descripcion);
        stm.setString(2, PrecioU);
        stm.setString(3, Unidad);
        stm.setInt(4, Cat);
        stm.setInt(5, borrado);
        stm.setString(6, fecha);
        stm.setInt(7, Codigo);
        try {
            stm.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR AL ACTUALIZAR PRODUCTOS: " + e);
        }
    }
}
