/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Facuymayriver
 */
public class Salary_window {

    public static void ShowSpentMonth(Connection conexion, int id, JLabel general, int ind) throws SQLException {
        int totalgasto = 0;
        PreparedStatement stm = conexion.prepareStatement("SELECT lg.precio as Precio FROM listagastos as lg inner join gastos as g on lg.idgastos=g.id_Gastos inner join rendicion as r on g.id_Gastos=r.id_gasto inner join ficha as f on r.id_ficha=f.id_Ficha inner join fecha as fe on f.id_Fecha=fe.id_Fecha WHERE MONTH(fe.Fecha)=? AND YEAR(fe.Fecha) = YEAR(CURDATE())and f.id_preventista=?;");
        stm.setInt(1, ind);
        stm.setInt(2, id);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            totalgasto = totalgasto + rs.getInt("Precio");;
        }
        general.setText("" + totalgasto);
    }

    public static void ShowTrustedMonth(Connection conexion, int id, JLabel general, int ind) throws SQLException {
        int totalgasto = 0;
        PreparedStatement stm = conexion.prepareStatement("SELECT SUM(Subconsulta.SaldoActualPendiente) AS SaldoPendienteTotalMes FROM (SELECT f.idFiado, f.Saldo AS SaldoInicial, COALESCE(SUM(h.saldo), 0) AS PagosAcumulados, (f.Saldo - COALESCE(SUM(h.saldo), 0)) AS SaldoActualPendiente FROM fiado AS f INNER JOIN ficha AS fi ON f.id_Ficha = fi.id_Ficha INNER JOIN fecha AS fec ON fi.id_Fecha = fec.id_Fecha LEFT JOIN historial AS h ON f.idFiado = h.id_fiado WHERE fi.id_preventista = ? AND MONTH(fec.Fecha) = ? AND YEAR(fec.Fecha) = YEAR(CURDATE()) GROUP BY f.idFiado, f.Saldo) AS Subconsulta;");
        stm.setInt(1, id);
        stm.setInt(2, ind);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            totalgasto = rs.getInt("SaldoPendienteTotalMes");;
        }
        general.setText("" + totalgasto);
    }

    public static void UpdateCalc(JTextField txtCash, JLabel lblSalary, JLabel lblComm, JLabel lblBills, JLabel lblTrusted) {
        try {
            // 1. Validar entrada principal (Basico)
            String cashText = txtCash.getText().trim().replace(",", ".");
            if (cashText.isEmpty()) {
                lblSalary.setText("0.00");
                return;
            }

            // 2. Parsear todos los valores a double para máxima precisión
            double basico = Double.parseDouble(cashText);
            double comision = Double.parseDouble(lblComm.getText().replace(",", "."));
            double gastos = Double.parseDouble(lblBills.getText().replace(",", "."));
            double fiados = Double.parseDouble(lblTrusted.getText().replace(",", "."));

            // 3. La fórmula: Sueldo = Básico + Comisión - Gastos - Fiados
            double resultadoFinal = basico + comision - gastos - fiados;

            // 4. Mostrar con 2 decimales para evitar errores visuales
            lblSalary.setText(String.format("%.2f", resultadoFinal));
            if (resultadoFinal < 0) {
                lblSalary.setForeground(Color.RED);
            } else {
                lblSalary.setForeground(Color.GREEN);
            }

        } catch (NumberFormatException e) {
            lblSalary.setText("Error");
            System.err.println("Error de formato en UpdateCalc: " + e.getMessage());
        }
    }
}
