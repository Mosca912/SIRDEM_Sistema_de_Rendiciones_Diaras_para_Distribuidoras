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
import javax.swing.JTextField;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Facuymayriver
 */
public class Add_modify_user {

    static DefaultComboBoxModel<Rank> model;
    static DefaultComboBoxModel<User> model2;

    public static class Rank {

        private final int id;
        private final String nombre;

        public Rank(int id, String nombre) {
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

    public static class User {

        private final int id;
        private final String nombre;

        public User(int id, String nombre) {
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

    public static int Verification_User(Connection conexion) throws SQLException {
        int verification = 0;
        String sql2 = "SELECT id_Usuario from usuario where Borrado=0";
        PreparedStatement ps = conexion.prepareStatement(sql2);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            verification = 1;
            return verification;
        }
        return verification;
    }

    public static void ShowCombos(Connection conexion, JComboBox<Rank> combo1, JComboBox<User> combo2) {
        String sql = "SELECT id_rangos, rango FROM rangos";
        String sql2 = "SELECT id_Usuario, Nombre from usuario where Borrado=0";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            model = new DefaultComboBoxModel<>();
            model.addElement(new Rank(0, "Opciones"));
            while (rs.next()) {
                int id = rs.getInt("id_rangos");
                String nombreCompleto = rs.getString("rango");
                model.addElement(new Rank(id, nombreCompleto));
            }
            combo1.setModel(model);
            AutoCompleteDecorator.decorate(combo1);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
        }

        try {
            PreparedStatement ps = conexion.prepareStatement(sql2);
            ResultSet rs = ps.executeQuery();
            model2 = new DefaultComboBoxModel<>();
            model2.addElement(new User(0, "Opciones"));
            while (rs.next()) {
                int id = rs.getInt("id_Usuario");
                String nombreCompleto = rs.getString("Nombre");
                model2.addElement(new User(id, nombreCompleto));
            }
            combo2.setModel(model2);
            AutoCompleteDecorator.decorate(combo1);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
        }
    }

    public static void ShowCombosNewUser(Connection conexion, JComboBox<Rank> combo1) {
        String sql = "SELECT id_rangos, rango FROM rangos";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            model = new DefaultComboBoxModel<>();
            model.addElement(new Rank(0, "Opciones"));
            while (rs.next()) {
                int id = rs.getInt("id_rangos");
                String nombreCompleto = rs.getString("rango");
                model.addElement(new Rank(id, nombreCompleto));
            }
            combo1.setModel(model);
            AutoCompleteDecorator.decorate(combo1);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
        }

    }

    public static int AddNewUser(Connection conexion, String nombre, String contrasena, String contrasenarepeat, String email, int id, int band, int id2, int estado, String dni) throws SQLException {
        int valid = 0;
        String contrasenaHasheada = BCrypt.hashpw(contrasena, BCrypt.gensalt());
        if (band == 0) {
            String sql2 = "INSERT into usuario (nombre, Borrado, contrasena, correo, id_rangos, dni) VALUES (?,0,?,?,?, ?) ";
            try {
                PreparedStatement ps2 = conexion.prepareStatement(sql2);
                ps2.setString(1, nombre);
                ps2.setString(2, contrasenaHasheada);
                ps2.setString(3, email);
                ps2.setInt(4, id);
                ps2.setString(5, dni);
                ps2.execute();
                JOptionPane.showMessageDialog(null, "Usuario registrado con exito!");
                valid = 1;
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR " + e);
            }
        } else if (band == 1) {
            if (estado == 0) {
                String sql = "UPDATE usuario SET nombre=?, correo=?, id_rangos = ?, dni = ? WHERE id_Usuario=?";
                try {
                    PreparedStatement ps = conexion.prepareStatement(sql);
                    ps.setString(1, nombre);
                    ps.setString(2, email);
                    ps.setInt(3, id);
                    ps.setInt(4, id2);
                    ps.setString(5, dni);
                    ps.execute();
                    JOptionPane.showMessageDialog(null, "Actualización de usuario exitosa");
                    valid = 1;
                } catch (SQLException e) {
                    int errorCode = e.getErrorCode();
                    if (errorCode == 1062) {
                        // Mensaje Personalizado para DNI/Correo Repetido
                        JOptionPane.showMessageDialog(null,
                                "⚠️ Error: El correo electrónico que intentás ingresar ya existen en el sistema. Deben ser únicos.",
                                "Error de Registro Duplicado", // Título de la ventana
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Si es cualquier otro error de SQL, mostramos el mensaje genérico
                        JOptionPane.showMessageDialog(null, "ERROR SQL no esperado: " + e.getMessage());
                    }
                }
            } else if (estado == 1) {
                String sql = "UPDATE usuario SET nombre=?, contrasena=?, correo=?, id_rangos = ?, dni = ? WHERE id_Usuario=?";
                try {
                    PreparedStatement ps = conexion.prepareStatement(sql);
                    ps.setString(1, nombre);
                    ps.setString(2, contrasenaHasheada);
                    ps.setString(3, email);
                    ps.setInt(4, id);
                    ps.setString(5, dni);
                    ps.setInt(6, id2);
                    ps.execute();
                    JOptionPane.showMessageDialog(null, "Actualización de usuario exitosa");
                    valid = 1;
                } catch (SQLException e) {
                    int errorCode = e.getErrorCode();
                    if (errorCode == 1062) {
                        // Mensaje Personalizado para DNI/Correo Repetido
                        JOptionPane.showMessageDialog(null,
                                "⚠️ Error: El correo electrónico que intentás ingresar ya existen en el sistema. Deben ser únicos.",
                                "Error de Registro Duplicado", // Título de la ventana
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Si es cualquier otro error de SQL, mostramos el mensaje genérico
                        JOptionPane.showMessageDialog(null, "ERROR SQL no esperado: " + e.getMessage());
                    }
                }
            }
        }
        return valid;
    }

    public static void BringUser(Connection conexion, int id, JTextField nombre, JTextField contrasena, JTextField correo, JComboBox<Rank> combo, JTextField dni) throws SQLException {
        String sql2 = "SELECT Nombre, correo, id_rangos, dni FROM usuario WHERE id_Usuario = ?;";
        try {
            PreparedStatement ps2 = conexion.prepareStatement(sql2);
            ps2.setInt(1, id);
            ResultSet rs = ps2.executeQuery();
            if (rs.next()) {
                String nom = rs.getString("nombre");
                String cor = rs.getString("correo");
                String doc = rs.getString("dni");
                int idrango = rs.getInt("id_rangos");
                nombre.setText(nom);
                correo.setText(cor);
                dni.setText(doc);
                for (int i = 0; i < combo.getItemCount(); i++) {
                    Rank item = combo.getItemAt(i);
                    if (item.getId() == idrango) {
                        combo.setSelectedItem(item);
                        return; // Salimos del bucle una vez encontrado
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR " + e);
        }
    }
}
