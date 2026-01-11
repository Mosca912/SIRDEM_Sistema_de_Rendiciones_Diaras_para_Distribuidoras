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
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Facuymayriver
 */
public class Add_bills_window {

    static DefaultComboBoxModel<Bills> model;

    public static class Bills {

        private final int id;
        private final String nombre;

        public Bills(int id, String nombre) {
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

    public static void BillsCombo(Connection conexion, JComboBox<Bills> combo1) {
        String sql = "SELECT idtipos, tipo FROM tipos;";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            DefaultComboBoxModel<Bills> gastos = new DefaultComboBoxModel();
            gastos.addElement(new Bills(0, "Opciones"));
            while (rs.next()) {
                String tipo = rs.getString("tipo");
                int id = rs.getInt("idtipos");
                gastos.addElement(new Bills(id, tipo));
            }

            combo1.setModel(gastos);
            AutoCompleteDecorator.decorate(combo1);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
        }
    }

    static ArrayList<Object[]> listaValoresGlobal = new ArrayList<>();
    static int totalprecio = 0;

    public static void lista(int numero, int id, String valor, int precio) {

        totalprecio = totalprecio + precio;
        Object[] registro = {numero, id, valor, precio};

        // 2. Agregar el array (la fila) a la lista
        listaValoresGlobal.add(registro);
    }
    
    
    public static void deleteLast() {
    if (!listaValoresGlobal.isEmpty()) {
        int ultimoIdx = listaValoresGlobal.size() - 1;
        
        // 1. Restar el precio del total antes de borrar
        Object[] registro = listaValoresGlobal.get(ultimoIdx);
        int precioABorrar = (int) registro[3]; 
        totalprecio -= precioABorrar;

        // 2. Eliminar de la lista
        listaValoresGlobal.remove(ultimoIdx);
    }
}

    public static void mostrarLista() {
        System.out.println("--- Contenido de la Lista ---");
        System.out.println("No. | Valor | Precio");
        System.out.println("---------------------------");

        // 1. Iterar sobre cada 'registro' (que es un Object[]) en la lista global
        for (Object[] registro : listaValoresGlobal) {

            // 2. Acceder a cada elemento del arreglo 'registro'
            int numero = (int) registro[0];
            String valor = (String) registro[2];
            int precio = (int) registro[3];

            // 3. Imprimir el registro en un formato legible
            System.out.printf("%3d | "+ valor+"| %6d%n", numero, precio);

            // Nota: "%3d", "%5d", "%6d" es formato para alinear los números.
        }

        // Opcional: Mostrar el total acumulado
        System.out.println("---------------------------");
        System.out.println("TOTAL ACUMULADO: " + totalprecio);
        System.out.println("---------------------------");
    }

    public static void limpiarlista() {
        listaValoresGlobal.clear();
        totalprecio = 0;
    }
    static int ultimoIndice;

    public static int numlist() {
        // Si la lista está vacía, devuelve 0 o -1 dependiendo de tu lógica
        if (listaValoresGlobal.isEmpty()) {
            return 0;
        }
        // Retorna el tamaño actual de la lista
        return listaValoresGlobal.size();
    }

    public static int totalprecio() {
        return totalprecio;
    }

    public static void cargarlista(DefaultTableModel modelo) {
        modelo.setRowCount(0);

        if (listaValoresGlobal == null) {
            System.out.println("Error: La lista no ha sido inicializada.");
            return;
        }

        for (Object[] fila : listaValoresGlobal) {
            // Creamos un array de 3 elementos con la data filtrada
            // fila[0] = numero, fila[1] = id (LO SALTAMOS), fila[2] = valor, fila[3] = precio
            Object[] filaParaTabla = {fila[0], fila[2], fila[3]};

            modelo.addRow(filaParaTabla);
        }
    }

    public static void ReloadCombos(Connection conexion, JComboBox<Bills> combo1) {
        DefaultComboBoxModel<Bills> modelActual = (DefaultComboBoxModel<Bills>) combo1.getModel();

        // 2. Limpiar el modelo existente
        modelActual.removeAllElements();
        modelActual.addElement(new Bills(0, "Opciones"));

        String sql = "SELECT idtipos, tipo FROM tipos;";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String tipo = rs.getString("tipo");
                int id = rs.getInt("idtipos");
                // 3. Agregar directamente al modelo actual
                modelActual.addElement(new Bills(id, tipo));
            }
            // No es necesario setModel ni re-decorar si usas el mismo modelo
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
        }
    }
}
