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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Facuymayriver
 */
public class Ud_table_window {

    public static void CUDInfo(int flag, JLabel info, int mode, JTable table, DefaultTableModel categories, DefaultTableModel zones, DefaultTableModel users, DefaultTableModel bills) {
        switch (flag) {
            case 0:
                if (mode == 0) {
                    info.setText("Actualizar Zonas");
                    table.setModel(zones);
                    return;
                } else {
                    info.setText("Eliminar Zonas");
                    table.setModel(zones);
                    return;
                }
            case 1:
                if (mode == 0) {
                    info.setText("Actualizar Categorias");
                    table.setModel(categories);
                    return;
                } else {
                    info.setText("Eliminar Categorias");
                    table.setModel(categories);
                    return;
                }
            case 2:
                if (mode == 0) {
                    info.setText("Actualizar Gastos");
                    table.setModel(bills);
                    return;
                } else {
                    info.setText("Eliminar Gastos");
                    table.setModel(bills);
                    return;
                }
            case 3:
                info.setText("Eliminar Usuario");
                table.setModel(users);
        }
    }

    public static void CUDTable(Connection conexion, int flag, JTable table, DefaultTableModel categories, DefaultTableModel zones, DefaultTableModel users, DefaultTableModel bills) throws SQLException {
        switch (flag) {
            case 0:
                PreparedStatement stm = conexion.prepareStatement("SELECT zonas.id_Zonas, zonas.Nombre from zonas where Borrado=0");
                ResultSet rs = stm.executeQuery();

                while (rs.next()) {
                    Object[] fila = new Object[2];
                    fila[0] = rs.getString("zonas.id_Zonas");
                    fila[1] = rs.getString("zonas.Nombre");
                    zones.addRow(fila);
                }
            case 1:
                PreparedStatement stm2 = conexion.prepareStatement("Select categoria.id_Categoria, categoria.Categoria from Categoria where categoria.Borrado=0");
                ResultSet rs2 = stm2.executeQuery();

                while (rs2.next()) {
                    Object[] fila = new Object[2];
                    fila[0] = rs2.getString("categoria.id_Categoria");
                    fila[1] = rs2.getString("categoria.Categoria");
                    categories.addRow(fila);
                }
            case 2:
                PreparedStatement stm3 = conexion.prepareStatement("Select tipos.idtipos, tipos.tipo from tipos where tipos.borrado=0");
                ResultSet rs3 = stm3.executeQuery();

                while (rs3.next()) {
                    Object[] fila = new Object[2];
                    fila[0] = rs3.getString("tipos.idtipos");
                    fila[1] = rs3.getString("tipos.tipo");
                    bills.addRow(fila);
                }
            case 3:
                PreparedStatement stm4 = conexion.prepareStatement("Select usuario.id_Usuario, usuario.Nombre from usuario where usuario.Borrado=0 AND id_Usuario != 1;");
                ResultSet rs4 = stm4.executeQuery();

                while (rs4.next()) {
                    Object[] fila = new Object[2];
                    fila[0] = rs4.getString("usuario.id_Usuario");
                    fila[1] = rs4.getString("usuario.Nombre");
                    users.addRow(fila);
                }

        }
    }

    public static void UpdateZone(Connection conexion, int Codigo, String Zonas) throws SQLException {

        PreparedStatement stm2 = conexion.prepareStatement("Select Zonas.Nombre from Zonas where Zonas.id_Zonas=?");
        stm2.setInt(1, Codigo);
        ResultSet rs = stm2.executeQuery();

        if (rs.next()) {
            String Zon = rs.getString("Zonas.Nombre");
            if (!Zonas.equals(Zon)) {
                PreparedStatement stm = conexion.prepareStatement("UPDATE Zonas SET Nombre = ? WHERE id_Zonas = ?");
                stm.setString(1, Zonas);
                stm.setInt(2, Codigo);
                try {
                    stm.executeUpdate();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "ERROR AL ACTUALIZAR ZONA (2): " + e);
                }
            }
        }
    }

    public static void UpdateCategories(Connection conexion, int Codigo, String Categoria) throws SQLException {
        PreparedStatement stm2 = conexion.prepareStatement("Select categoria.Categoria from categoria where categoria.id_Categoria=?");
        stm2.setInt(1, Codigo);
        ResultSet rs = stm2.executeQuery();

        if (rs.next()) {
            String Cat = rs.getString("categoria.Categoria");
            if (!Categoria.equals(Cat)) {
                PreparedStatement stm = conexion.prepareStatement("UPDATE categoria SET Categoria = ? WHERE id_Categoria = ?");
                stm.setString(1, Categoria);
                stm.setInt(2, Codigo);
                try {
                    stm.executeUpdate();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "ERROR AL ACTUALIZAR CATEGORIAS (2): " + e);
                }
            }
        }
    }

    public static void UpdateBills(Connection conexion, int Codigo, String Bills) throws SQLException {
        PreparedStatement stm2 = conexion.prepareStatement("Select tipos.tipo from tipos where tipos.idtipos=?");
        stm2.setInt(1, Codigo);
        ResultSet rs = stm2.executeQuery();

        if (rs.next()) {
            String Cat = rs.getString("tipos.tipo");
            if (!Bills.equals(Cat)) {
                PreparedStatement stm = conexion.prepareStatement("UPDATE tipos SET tipo = ? WHERE idtipos = ?");
                stm.setString(1, Bills);
                stm.setInt(2, Codigo);
                try {
                    stm.executeUpdate();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "ERROR AL ACTUALIZAR CATEGORIAS (2): " + e);
                }
            }
        }
    }

    public static int Validation(Connection conexion, int Codigo, int flag) throws SQLException {
        int resultado = 0;
        switch (flag) {
            case 0:
                PreparedStatement stm = conexion.prepareStatement("Select Clientes.Zona from Clientes where Clientes.Zona=?");
                stm.setInt(1, Codigo);
                ResultSet rs = stm.executeQuery();
                if (rs.next()) {
                    int opcion = JOptionPane.showConfirmDialog(
                            null,
                            "¿Deseás Eliminar todos los clientes con esta zona?",
                            "Confirmar acción",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );
                    if (opcion == JOptionPane.OK_OPTION) {
                        PreparedStatement stm2 = conexion.prepareStatement("UPDATE Clientes SET Borrado=1 WHERE Zona=?");
                        stm2.setInt(1, Codigo);
                        try {
                            stm2.executeUpdate();
                            JOptionPane.showMessageDialog(null, "¡Clientes eliminados correctamente!");
                            resultado = 1;
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(null, "ERROR AL ELIMINAR REGISTROS: " + e);
                        }
                    } else if (opcion == JOptionPane.CANCEL_OPTION || opcion == JOptionPane.CLOSED_OPTION) {
                        System.out.println("Cancelado");
                        resultado = 2;
                    }
                } else {
                    resultado = 0;
                }
                break;
            case 1:
                PreparedStatement stm2 = conexion.prepareStatement("Select Productos.id_Productos from Productos where id_Categoria=?");
                stm2.setInt(1, Codigo);
                ResultSet rs2 = stm2.executeQuery();
                if (rs2.next()) {
                    int opcion = JOptionPane.showConfirmDialog(
                            null,
                            "¿Deseás Eliminar todos los productos de esta categoria?",
                            "Confirmar acción",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );
                    if (opcion == JOptionPane.OK_OPTION) {
                        PreparedStatement stm3 = conexion.prepareStatement("UPDATE Productos SET Borrado=1 WHERE id_Categoria=?");
                        stm3.setInt(1, Codigo);
                        try {
                            stm3.executeUpdate();
                            JOptionPane.showMessageDialog(null, "¡Productos eliminados correctamente!");
                            resultado = 1;
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(null, "ERROR AL ELIMINAR REGISTROS: " + e);
                        }
                    } else if (opcion == JOptionPane.CANCEL_OPTION || opcion == JOptionPane.CLOSED_OPTION) {
                        System.out.println("Cancelado");
                        resultado = 2;
                    }
                } else {
                    resultado = 0;
                }
                break;
        }
        return resultado;
    }

    public static void DeleteCategory(Connection conexion, int Codigo, int borrado) throws SQLException {

        PreparedStatement stm = conexion.prepareStatement("UPDATE Categoria SET Borrado= ? WHERE id_Categoria = ?");
        stm.setInt(1, borrado);
        stm.setInt(2, Codigo);

        try {
            stm.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR AL ELIMINAR CATEGORIA: " + e);
        }
    }

    public static void DeleteZone(Connection conexion, int Codigo, int borrado) throws SQLException {

        PreparedStatement stm = conexion.prepareStatement("UPDATE Zonas SET Borrado= ? WHERE id_Zonas = ?");
        stm.setInt(1, borrado);
        stm.setInt(2, Codigo);

        try {
            stm.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR AL ELIMINAR LA ZONA: " + e);
        }
    }

    public static void DeleteUser(Connection conexion, int Codigo, int borrado) throws SQLException {

        PreparedStatement stm = conexion.prepareStatement("UPDATE usuario SET Borrado=? WHERE id_Usuario=?");
        stm.setInt(1, borrado);
        stm.setInt(2, Codigo);

        try {
            stm.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR AL ELIMINAR AL USUARIO: " + e);
        }
    }

    public static void DeleteBills(Connection conexion, int Codigo, int borrado) throws SQLException {

        PreparedStatement stm = conexion.prepareStatement("UPDATE tipos SET Borrado=? WHERE idtipos=?");
        stm.setInt(1, borrado);
        stm.setInt(2, Codigo);

        try {
            stm.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR AL ELIMINAR EL GASTO: " + e);
        }
    }
}
