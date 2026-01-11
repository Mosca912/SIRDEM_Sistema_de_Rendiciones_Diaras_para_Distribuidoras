package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class New_file_window {

    public static class Cliente {

        private final int id;
        private final String nombre;

        public Cliente(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        public int getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }

        @Override
        public String toString() {
            return nombre; // lo que se muestra en el combo
        }
    }

    public static class Producto {

        private final int id;
        private final String nombre;

        public Producto(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        public int getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }

        @Override
        public String toString() {
            return nombre; // lo que se muestra en el combo
        }
    }

    public static void Combo_clients(Connection conexion, JComboBox<Cliente> combo1) {
        String sql = "SELECT id_Clientes, nombre, apellido FROM clientes WHERE borrado=0 ORDER BY id_Clientes";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            DefaultComboBoxModel<Cliente> model = new DefaultComboBoxModel<>();
            while (rs.next()) {
                int id = rs.getInt("id_Clientes");
                String nombreCompleto = rs.getString("nombre") + " " + rs.getString("apellido");
                model.addElement(new Cliente(id, nombreCompleto));
            }

            combo1.setModel(model);
            AutoCompleteDecorator.decorate(combo1);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
        }
    }

    public static void Combo_code(Connection conexion, JComboBox<String> combo1) {
        String sql = "SELECT id_Clientes FROM clientes WHERE borrado=0 ORDER BY id_Clientes";

        try {
            // Contar cuántos registros hay
            PreparedStatement ps = conexion.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = ps.executeQuery();

            rs.last(); // Mover al último
            int total = rs.getRow(); // Cantidad de filas
            rs.beforeFirst(); // Volver al principio

            String[] codigo = new String[total];

            // Llenar el array
            int i = 0;
            while (rs.next()) {
                String cod = rs.getString("id_Clientes");
                codigo[i] = cod;
                i++;
            }

            //Asignar al combo
            combo1.setModel(new DefaultComboBoxModel<>(codigo));
            AutoCompleteDecorator.decorate(combo1);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
        }
    }

    public static void Combo_products(Connection conexion, JComboBox<Producto> combo1) {
        String sql = "SELECT id_Productos, Descripcion FROM productos WHERE borrado=0 ORDER BY id_Productos";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            DefaultComboBoxModel<Producto> model = new DefaultComboBoxModel<>();
            while (rs.next()) {
                int id = rs.getInt("id_Productos");
                String nombreCompleto = rs.getString("Descripcion");
                model.addElement(new Producto(id, nombreCompleto));
            }

            combo1.setModel(model);
            AutoCompleteDecorator.decorate(combo1);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
        }
    }

    public static String ClientAut(Connection conexion, String codigo) {
        String comp = "";
        String sql = "SELECT nombre, apellido FROM clientes WHERE borrado=0 AND id_Clientes=?";
        try {
            // Contar cuántos registros hay
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String nom = rs.getString("nombre");
                String ap = rs.getString("apellido");
                comp = (nom + " " + ap);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
        }
        return comp;
    }

    public static String DescAut(Connection conexion, String nombre) {
        String id = "";
        String comp = "";
        String sql = "SELECT id_Productos FROM productos WHERE Descripcion=?";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                id = rs.getString("id_Productos");
            }

            String sql2 = "SELECT Precio_unitario FROM productos WHERE borrado=0 AND id_Productos=?";
            try {

                PreparedStatement ps2 = conexion.prepareStatement(sql2);
                ps2.setString(1, id);
                ResultSet rs2 = ps2.executeQuery();

                if (rs2.next()) {
                    comp = rs2.getString("Precio_unitario");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
        }
        return comp;
    }
}
