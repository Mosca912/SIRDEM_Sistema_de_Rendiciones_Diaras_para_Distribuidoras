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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Facuymayriver
 */
public class Clients_window {

    public static class Zone {

        private final int id;
        private final String nombre;
        private final int cont;

        public Zone(int id, String nombre, int cont) {
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

    public static void ShowClients(Connection conexion, DefaultTableModel modelo) throws SQLException {

        PreparedStatement stm = conexion.prepareStatement("SELECT clientes.id_Clientes, clientes.Nombre, clientes.Apellido, clientes.Domicilio, clientes.Telefono, zonas.id_zonas, zonas.Nombre FROM clientes inner join zonas on clientes.zona=zonas.id_zonas where clientes.borrado=0");
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
            Object[] fila = new Object[6];
            fila[0] = rs.getString("clientes.id_Clientes");
            fila[1] = rs.getString("clientes.Nombre");
            fila[2] = rs.getString("clientes.Apellido");
            fila[3] = rs.getString("clientes.Domicilio");
            fila[4] = rs.getString("clientes.Telefono");
            int idZ = rs.getInt("zonas.id_zonas");
            String nombreZ = rs.getString("zonas.Nombre");

            // Guardamos el objeto completo en la posición 5
            fila[5] = new Clases.Clients_window.Zone(idZ, nombreZ, 0);
            modelo.addRow(fila);
        }
    }

    public static void ShowZone(Connection conexion, JComboBox<Zone> combo1) {

        String sql = "SELECT id_Zonas, Nombre FROM Zonas WHERE borrado=0 ORDER BY id_Zonas;";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            DefaultComboBoxModel<Zone> model = new DefaultComboBoxModel<>();

            model.addElement(new Zone(0, "Opciones", 0));
            int cont = 1;
            while (rs.next()) {
                int id = rs.getInt("id_Zonas");
                String nombreCompleto = rs.getString("Nombre");
                model.addElement(new Zone(id, nombreCompleto, cont));
                cont++;
            }

            combo1.setModel(model);
            AutoCompleteDecorator.decorate(combo1);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR AL MOSTRAR ZONA: " + ex.getMessage());
        }
    }

    public static int VeriZone(Connection conexion) {
        int veri = 0;
        String sql = "SELECT id_Zonas, Nombre FROM Zonas where borrado=0 ORDER BY id_Zonas;";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "Por favor! Cargue zonas primero para añadir un cliente");
                veri = 1;
                return veri;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR AL MOSTRAR ZONA: " + ex.getMessage());
        }
        return veri;
    }

    public static void DeleteClients(Connection conexion, int Codigo, int borrado) throws SQLException {

        PreparedStatement stm = conexion.prepareStatement("UPDATE Clientes SET Borrado= ? WHERE id_Clientes = ?");
        stm.setInt(1, borrado);
        stm.setInt(2, Codigo);

        try {
            stm.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR AL ELIMINAR: " + e);
        }
    }

    public static void AddClients(Connection conexion, String Nombre, String Apellido, String Domicilio, String Telefono, int Zona, int borrado) throws SQLException {
        PreparedStatement stm = conexion.prepareStatement("INSERT INTO clientes (Nombre, Apellido, Domicilio, Telefono, Zona, Borrado) VALUES (?,?,?,?,?,?)");
        stm.setString(1, Nombre);
        stm.setString(2, Apellido);
        stm.setString(3, Domicilio);
        stm.setString(4, Telefono);
        stm.setInt(5, Zona);
        stm.setInt(6, borrado);

        try {
            stm.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR AL AÑADIR: " + e);
        }
    }

    public static void UpdateClients(Connection conexion, int Codigo, String Nombre, String Apellido, String Domicilio, String Telefono, int Zona, int borrado) throws SQLException {

        PreparedStatement stm2 = conexion.prepareStatement("SELECT clientes.Nombre, clientes.Apellido, clientes.Domicilio, clientes.Telefono, clientes.Zona FROM clientes where clientes.id_Clientes=?");
        stm2.setInt(1, Codigo);
        ResultSet rs = stm2.executeQuery();

        if (rs.next()) {
            String nom = rs.getString("clientes.Nombre");
            String ap = rs.getString("clientes.Apellido");
            String dom = rs.getString("clientes.Domicilio");
            String tel = rs.getString("clientes.Telefono");
            int zon = rs.getInt("clientes.Zona");
            if (!Nombre.equals(nom) || !Apellido.equals(ap) || !Domicilio.equals(dom) || !Telefono.equals(tel) || Zona != zon) {

                PreparedStatement stm = conexion.prepareStatement("UPDATE Clientes SET Nombre = ?, Apellido = ?, Domicilio = ?, Telefono = ?, Zona= ? WHERE id_Clientes = ?");
                stm.setString(1, Nombre);
                stm.setString(2, Apellido);
                stm.setString(3, Domicilio);
                stm.setString(4, Telefono);
                stm.setInt(5, Zona);
                stm.setInt(6, Codigo);

                try {
                    stm.executeUpdate();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "ERROR AL ACTUALIZAR " + e);
                }
            }
        }
    }
}
