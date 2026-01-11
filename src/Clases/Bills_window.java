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
import java.time.LocalDate;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Facuymayriver
 */
public class Bills_window {
    
    static int indice1, indice2;
    
    public static void ShowBillsGeneral(Connection conexion, DefaultTableModel modelo, int id, JTable tabla, JLabel general) throws SQLException {
        indice1 = 0;
        indice2 = 0;
        int totalgasto = 0;
        LocalDate hoy = LocalDate.now();
        int mesActual = hoy.getMonthValue();
        PreparedStatement stm = conexion.prepareStatement("SELECT t.tipo AS Tipo_Gasto, COALESCE(SUM(lg.precio), 0) AS Total_Gastado FROM tipos AS t LEFT JOIN listagastos AS lg ON t.idtipos = lg.idtipos LEFT JOIN gastos AS g ON lg.idgastos = g.id_Gastos LEFT JOIN rendicion AS r ON g.id_Gastos = r.id_gasto LEFT JOIN ficha AS f ON r.id_ficha = f.id_Ficha LEFT JOIN fecha AS fe ON f.id_Fecha = fe.id_Fecha WHERE (f.id_preventista = ? OR f.id_preventista IS NULL) AND ((MONTH(fe.Fecha) = ? AND YEAR(fe.Fecha) = YEAR(CURDATE())) OR fe.Fecha IS NULL) GROUP BY t.tipo, t.idtipos HAVING Total_Gastado > 0 ORDER BY Total_Gastado DESC;");
        stm.setInt(1, id);
        stm.setInt(2, mesActual);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            Object[] fila = new Object[2];
            fila[0] = rs.getString("Tipo_Gasto");
            fila[1] = rs.getInt("Total_Gastado");
            totalgasto = totalgasto + rs.getInt("Total_Gastado");
            modelo.addRow(fila);
        }
        general.setText("" + totalgasto);
    }
    
    public static void ShowBillsMonth(Connection conexion, DefaultTableModel modelo, int id, JTable tabla, JLabel general, int ind) throws SQLException {
        indice1 = 0;
        indice2 = 0;
        int totalgasto = 0;
        PreparedStatement stm = conexion.prepareStatement("SELECT t.tipo AS Tipo_Gasto, COALESCE(SUM(lg.precio), 0) AS Total_Gastado FROM tipos AS t LEFT JOIN listagastos AS lg ON t.idtipos = lg.idtipos LEFT JOIN gastos AS g ON lg.idgastos = g.id_Gastos LEFT JOIN rendicion AS r ON g.id_Gastos = r.id_gasto LEFT JOIN ficha AS f ON r.id_ficha = f.id_Ficha LEFT JOIN fecha AS fe ON f.id_Fecha = fe.id_Fecha WHERE (f.id_preventista = ? OR f.id_preventista IS NULL) AND ((MONTH(fe.Fecha) = ? AND YEAR(fe.Fecha) = YEAR(CURDATE())) OR fe.Fecha IS NULL) GROUP BY t.tipo, t.idtipos HAVING Total_Gastado > 0 ORDER BY Total_Gastado DESC;");
        stm.setInt(1, id);
        stm.setInt(2, ind);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            Object[] fila = new Object[2];
            fila[0] = rs.getString("Tipo_Gasto");
            fila[1] = rs.getInt("Total_Gastado");
            totalgasto = totalgasto + rs.getInt("Total_Gastado");
            modelo.addRow(fila);
        }
        general.setText("" + totalgasto);
    }

}
