package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Add_employee_window {
    
    public static void ShowEmployee(Connection conexion, DefaultTableModel modelo) throws SQLException {

        PreparedStatement stm = conexion.prepareStatement("Select preventista.id_preventista, preventista.nombre, preventista.apellido, preventista.dni, preventista.direccion, preventista.fechainicio from preventista where preventista.Borrado=0");
        ResultSet rs = stm.executeQuery();

        while (rs.next()) {
            Object[] fila = new Object[6];
            fila[0] = rs.getString("preventista.id_preventista");
            fila[1] = rs.getString("preventista.nombre");
            fila[2] = rs.getString("preventista.apellido");
            fila[3] = rs.getString("preventista.dni");
            fila[4] = rs.getString("preventista.direccion");
            fila[5] = rs.getString("preventista.fechainicio");
            modelo.addRow(fila);
        }
    }
    
    public static void AddEmployee(Connection conexion, String Nombre, String Apellido, int borrado, String DNI, String Direccion, String Fecha) throws SQLException {

        PreparedStatement stm = conexion.prepareStatement("INSERT INTO preventista (Nombre, Apellido, Borrado, dni, direccion, fechainicio) VALUES (?,?,?,?,?,?)");
        stm.setString(1, Nombre);
        stm.setString(2, Apellido);
        stm.setInt(3, borrado);
        stm.setString(4, DNI);
        stm.setString(5, Direccion);
        stm.setString(6, Fecha);

        try {
            stm.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR AL AGREGAR: "+e);
        }
    }
    
    public static void UpdateEmployee(Connection conexion, int Codigo, String Nombre, String Apellido, int borrado, String DNI, String Direccion, String Fecha) throws SQLException {

        PreparedStatement stm2 = conexion.prepareStatement("Select preventista.nombre, preventista.apellido, preventista.dni, preventista.direccion, preventista.fechainicio from preventista where preventista.id_Preventista=?");
        stm2.setInt(1, Codigo);
        ResultSet rs = stm2.executeQuery();

        if (rs.next()) {
            String Nom = rs.getString("preventista.nombre");
            String Ap = rs.getString("preventista.apellido");
            String D = rs.getString("preventista.dni");
            String Dir = rs.getString("preventista.direccion");
            String Fec = rs.getString("preventista.fechainicio");
            if (!Nombre.equals(Nom) || !Apellido.equals(Ap) || !DNI.equals(D) || !Direccion.equals(Dir) || !Fecha.equals(Fec)) {

                PreparedStatement stm = conexion.prepareStatement("UPDATE preventista SET Nombre = ?, Apellido = ?, Borrado = ?, dni = ?, direccion= ?, fechainicio=? WHERE id_Preventista = ?");
                stm.setString(1, Nombre);
                stm.setString(2, Apellido);
                stm.setInt(3, borrado);
                stm.setString(4, DNI);
                stm.setString(5, Direccion);
                stm.setString(6, Fecha);
                stm.setInt(7, Codigo);

                try {
                    stm.executeUpdate();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "ERROR AL ACTUALIZAR: "+e);
                }
            }
        }
    }
    
    public static void DeleteEmployee(Connection conexion, int Codigo, int borrado) throws SQLException {

        PreparedStatement stm = conexion.prepareStatement("UPDATE preventista SET Borrado= ? WHERE id_Preventista = ?");
        stm.setInt(1, borrado);
        stm.setInt(2, Codigo);

        try {
            stm.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR AL BORRAR: "+e);
        }
    }
}
