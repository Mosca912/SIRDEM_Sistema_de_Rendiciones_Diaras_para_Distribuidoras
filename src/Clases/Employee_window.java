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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Facuymayriver
 */
public class Employee_window {

    static int empId = 0;
    static String empName = "Opciones";
    static int cont = 0;

    public static class Employee {

        private final int id;
        private final String nombre;
        private final int cont;

        public Employee(int id, String nombre, int cont) {
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
    
    public static void logout() {
        empId=0;
        empName="Opciones";
        cont=0;
    }
    

    public static void saveemployee(int empId, String empName, int cont) {
        Employee_window.empId = empId;
        Employee_window.empName = empName;
        Employee_window.cont = cont;
    }

    public static int getIdEmployee() {
        return empId;
    }
    
    public static int getContEmployee() {
        return cont;
    }
    
    public static String getEmpName() {
        return empName;
    }

    //Inicio
    public static void set_Textlbl(JLabel lbl_addemployee, JLabel lbl_trusted, JLabel lbl_sales, JLabel lbl_bills, JLabel lbl_returns, JLabel lbl_differences, JLabel lbl_transfers) {
        String textoLargo = "<html> \u2190 Sección para el ingreso, modificación y eliminación de los preventistas.</html>";
        lbl_addemployee.setText(textoLargo);

        textoLargo = "<html> \u2190 Sección en donde podra consultar y cancelar los fiados de los clientes.</html>";
        lbl_trusted.setText(textoLargo);

        textoLargo = "<html> \u2190 Sección para consultar las ventas obtenidas por un preventista.</html>";
        lbl_sales.setText(textoLargo);

        textoLargo = "<html> \u2190 Sección para consultar los gastos realizados por un preventista.</html>";
        lbl_bills.setText(textoLargo);

        textoLargo = "<html> \u2190 Sección para consultar las devoluciones realizadas por los clientes.</html>";
        lbl_returns.setText(textoLargo);

        textoLargo = "<html> \u2190 Sección para consultar las diferencias obtenidas por un preventista.</html>";
        lbl_differences.setText(textoLargo);

        textoLargo = "<html> \u2190 Sección en donde podra consultar y cancelar las transferencias realizadas por los clientes.</html>";
        lbl_transfers.setText(textoLargo);
    }

    public static void Employee_cb(Connection conexion, JComboBox<Employee> combo1) {

        String sql = "SELECT id_Preventista, Nombre, Apellido FROM preventista WHERE borrado=0 ORDER BY id_Preventista";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            DefaultComboBoxModel<Employee> model = new DefaultComboBoxModel<>();

            model.addElement(new Employee(0, "Opciones", 0));
            int cont=1;
            while (rs.next()) {
                int id = rs.getInt("id_Preventista");
                String nombreCompleto = rs.getString("Nombre") + " " + rs.getString("Apellido");
                model.addElement(new Employee(id, nombreCompleto, cont));
                cont++;
            }

            combo1.setModel(model);
            AutoCompleteDecorator.decorate(combo1);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
        }
    }
}
