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
import java.time.Year;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Facuymayriver
 */
public class Sales_window {

    public static void updateAll(
            JTextField txtTotal, JTextField txtPdv, JTextField txtDays,
            JLabel tg, JLabel resultado, JLabel res_dias,
            JLabel totd, JLabel pdvd, JLabel dg1,
            JLabel diar, JLabel pdvr,
            JLabel totp, JLabel diferencia, JLabel alcance
    ) {
        try {
            double valTotal = parse(txtTotal.getText());
            double valPdv = parse(txtPdv.getText());
            double valDays = parse(txtDays.getText());

            double valTg = parse(tg.getText());
            double valResultado = parse(resultado.getText());
            double res_diasD = parse(res_dias.getText());

            double restaTotal = valTotal - valTg;
            if (restaTotal <= 0) {
                totd.setText("0");
                totd.setForeground(Color.GREEN);
            } else {
                totd.setText(format(restaTotal));
                totd.setForeground(Color.RED);
            }

            double restaPdv = valPdv - valResultado;
            if (restaPdv <= 0) {
                pdvd.setText("0");
                pdvd.setForeground(Color.GREEN);
            } else {
                pdvd.setText(format(restaPdv));
                pdvd.setForeground(Color.RED);
            }

            double restaDias = valDays - res_diasD;
            if (restaDias <= 0) {
                dg1.setText("0");
                dg1.setForeground(Color.GREEN);
                restaDias = 0;
            } else {
                dg1.setText(format(restaDias));
                dg1.setForeground(Color.RED);
            }

            if (restaDias > 0) {
                diar.setText(format(restaTotal / restaDias));
                pdvr.setText(format(restaPdv / restaDias));
            } else {
                diar.setText("0,00");
                pdvr.setText("0,00");
            }

            double proyeccion = 0;
            if (res_diasD > 0) {
                proyeccion = (valTg / res_diasD) * valDays;
            }
            totp.setText(format(proyeccion));

            double dif = proyeccion - valTotal;
            diferencia.setText(format(dif));
            diferencia.setForeground(dif >= 0 ? Color.GREEN : Color.RED);

            double alc = (valTotal != 0) ? (proyeccion / valTotal) * 100 : 0;
            alcance.setText(format(alc) + "%");

        } catch (Exception e) {
            System.err.println("Error en cálculos: " + e.getMessage());
        }
    }

// Helpers para limpieza y brevedad
    private static double parse(String s) {
        if (s == null || s.trim().isEmpty()) {
            return 0;
        }
        return Double.parseDouble(s.replace(",", "."));
    }

    private static String format(double d) {
        return String.format("%.2f", d);
    }

    static double totalgeneralpreventista = 0;

    public static void ShowTableSales(Connection conexion, int mes, DefaultTableModel modelo, int id, int indice) {
        totalgeneralpreventista = 0;
        try {

            PreparedStatement stm2 = conexion.prepareStatement("SELECT Subconsulta.SemanaContable,Subconsulta.TotalCombinado,Subconsulta.PDV,Subconsulta.Dias FROM (SELECT CASE WHEN DAYOFMONTH(fe.Fecha) BETWEEN 1 AND 8 THEN 1 WHEN DAYOFMONTH(fe.Fecha) BETWEEN 9 AND 16 THEN 2 WHEN DAYOFMONTH(fe.Fecha) BETWEEN 17 AND 24 THEN 3 ELSE 4 END AS SemanaContable, SUM(r.Efectivo + r.Gastos - r.Cobranzas + t.totalfiado+t.totaltransferencia) AS TotalCombinado, SUM(f.PuntosVentas) AS PDV, COUNT(fe.Fecha) AS Dias FROM  ficha AS f INNER JOIN  fecha AS fe ON f.id_Fecha = fe.id_Fecha INNER JOIN  rendicion AS r ON f.id_Ficha = r.id_ficha INNER JOIN  total AS t ON f.id_Ficha = t.idficha  WHERE  MONTH(fe.Fecha) = ?  AND YEAR(fe.Fecha) = YEAR(CURDATE())  AND f.id_preventista = ? GROUP BY  SemanaContable ) AS Subconsulta ORDER BY Subconsulta.SemanaContable;");
            stm2.setInt(1, indice);
            stm2.setInt(2, id);
            ResultSet rs2 = stm2.executeQuery();
            while (rs2.next()) {
                Object[] fila = new Object[4];
                fila[0] = rs2.getString("Subconsulta.SemanaContable");
                fila[1] = rs2.getString("Subconsulta.TotalCombinado");
                String tc = rs2.getString("Subconsulta.TotalCombinado");
                double sumatc = Double.parseDouble(tc);
                totalgeneralpreventista = totalgeneralpreventista + sumatc;
                fila[2] = rs2.getString("Subconsulta.PDV");
                fila[3] = rs2.getString("Subconsulta.Dias");
                modelo.addRow(fila);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR AL CARGAR TABLA: " + ex.getMessage());
        }
    }

    public static void ShowLabels(Connection conexion, int id, JLabel vista, JLabel pdv, JLabel dias, int indice) {
        int acpv = 0;
        int acd = 0;

        try {
            PreparedStatement stm2 = conexion.prepareStatement("SELECT SUM(Subconsulta.TotalGeneral) AS TotalGeneralMes, SUM(Subconsulta.PDV) AS TotalPDVMes, SUM(Subconsulta.Dias) AS TotalDiasMes FROM (SELECT CASE WHEN DAYOFMONTH(fe.Fecha) BETWEEN 1 AND 8 THEN 1 WHEN DAYOFMONTH(fe.Fecha) BETWEEN 9 AND 16 THEN 2 WHEN DAYOFMONTH(fe.Fecha) BETWEEN 17 AND 24 THEN 3 ELSE 4 END AS SemanaContable, SUM(t.totalgeneral) AS TotalGeneral, SUM(f.PuntosVentas) AS PDV, COUNT(fe.Fecha) AS Dias FROM total AS t INNER JOIN ficha AS f ON t.idficha = f.id_Ficha INNER JOIN fecha AS fe ON f.id_Fecha = fe.id_Fecha INNER JOIN rendicion AS r ON f.id_Ficha = r.id_ficha WHERE MONTH(fe.Fecha) = ? AND YEAR(fe.Fecha) = YEAR(CURDATE()) AND f.id_preventista = ? GROUP BY SemanaContable) AS Subconsulta;");
            stm2.setInt(1, indice);
            stm2.setInt(2, id);
            ResultSet rs2 = stm2.executeQuery();
            if (rs2.next()) {
                acpv = rs2.getInt("TotalPDVMes");
                acd = rs2.getInt("TotalDiasMes");
            } else {
                JOptionPane.showMessageDialog(null, "No hay información para presentar");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR AL CARGAR LOS LABELS: " + ex.getMessage());
        }

        String tg = String.valueOf(totalgeneralpreventista);
        String pv = String.valueOf(acpv);
        String d = String.valueOf(acd);
        vista.setText(tg);
        pdv.setText(pv);
        dias.setText(d);
    }

    public static void LoadTarget(Connection conexion, int id, JTextField dias, JTextField PDV, JTextField Objetivo, int indice, JButton cargar, JButton Editar, JButton salario) {
        int anioActual = Year.now().getValue();
        try {
            PreparedStatement stm2 = conexion.prepareStatement("Select dias, pdv, total from objetivos where idpreventista=? and mes=? and anio=?;");
            stm2.setInt(1, id);
            stm2.setInt(2, indice);
            stm2.setInt(3, anioActual);
            ResultSet rs2 = stm2.executeQuery();
            if (rs2.next()) {
                int d = rs2.getInt("dias");
                int p = rs2.getInt("pdv");
                int t = rs2.getInt("total");
                String day = String.valueOf(d);
                String pdvp = String.valueOf(p);
                String totobj = String.valueOf(t);
                dias.setText(day);
                PDV.setText(pdvp);
                Objetivo.setText(totobj);
                dias.setEnabled(false);
                PDV.setEnabled(false);
                Objetivo.setEnabled(false);
                cargar.setEnabled(false);
                Editar.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "No se registro el objetivo! Por favor, ingrese uno");
                cargar.setEnabled(true);
                Editar.setEnabled(false);
                dias.setEnabled(true);
                PDV.setEnabled(true);
                Objetivo.setEnabled(true);
                salario.setEnabled(false);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR AL CARGAR LOS OBJETIVOS: " + ex.getMessage());
        }
    }

    public static void InsertUpdTarget(Connection conexion, int id, String dias, String PDV, String Objetivo, int indice, JButton cargar, JButton Editar, int verificacion) throws SQLException {
        int day = Integer.parseInt(dias);
        int pdv = Integer.parseInt(PDV);
        int totobj = Integer.parseInt(Objetivo);
        int anioActual = Year.now().getValue();
        if (verificacion == 0) {
            String sql = "INSERT INTO objetivos (dias, pdv, total, mes, anio, idpreventista) VALUES (?,?,?,?,?,?)";
            try {
                PreparedStatement ps2 = conexion.prepareStatement(sql);
                ps2.setInt(1, day);
                ps2.setInt(2, pdv);
                ps2.setInt(3, totobj);
                ps2.setInt(4, indice);
                ps2.setInt(5, anioActual);
                ps2.setInt(6, id);
                try {
                    ps2.execute();
                    JOptionPane.showMessageDialog(null, "Objetivo cargado!");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "ERROR12");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
            }
        } else if (verificacion == 1) {
            PreparedStatement stm = conexion.prepareStatement("UPDATE objetivos SET dias=?, pdv=?, total=? WHERE mes=? AND anio=? AND idpreventista = ? ");
            stm.setInt(1, day);
            stm.setInt(2, pdv);
            stm.setInt(3, totobj);
            stm.setInt(4, indice);
            stm.setInt(5, anioActual);
            stm.setInt(6, id);
            try {
                stm.execute();
                JOptionPane.showMessageDialog(null, "Objetivo actualizado!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR12");
            }
        }
    }
}
