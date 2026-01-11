/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas.ABM;

import Clases.Add_bills_window.Bills;
import Conexion.Conexiones;
import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Facuymayriver
 */
public class Add_bills_window extends javax.swing.JDialog {

    static int id, saldo, band;
    int cont = 0;
    static String fecha;
    JComboBox<Bills> comboGas = new JComboBox<>();
    Connection con = Conexiones.Conexion();
    public static JDialog ventanaprincipal;

    //Metodos para editar
    public class ModeloEditablePorFila extends DefaultTableModel {

        private int editableRow = -1;

        public ModeloEditablePorFila(Object[] columnNames, int rowCount) {
            super(columnNames, rowCount);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            if (column == 0) {
                return false; // Nunca editar la columna ID
            }
            Object cod = getValueAt(row, 0);

            // Si es una fila nueva → editable
            if (cod == null || cod.toString().trim().isEmpty()) {
                return true;
            }

            // Si estamos en modo edición manual → solo esa fila
            return row == editableRow;
        }

        public void setEditableRow(int row) {
            this.editableRow = row;
            fireTableRowsUpdated(row, row);
        }

        public void bloquearEdicion() {
            this.editableRow = -1;
            fireTableDataChanged();
        }
    }
    //Metodos para editar

    //Tabla
    ModeloEditablePorFila addbills_tablemod = new ModeloEditablePorFila(new String[]{"Nº", "Gasto", "Precio"}, 0) {
        private final int editableRow = -1;
    };

    class SoloNumerosDocument extends PlainDocument {

        private final int limite;

        public SoloNumerosDocument(int limite) {
            this.limite = limite;
        }

        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str == null) {
                return;
            }

            String textoActual = getText(0, getLength());
            String textoFuturo = textoActual.substring(0, offs) + str + textoActual.substring(offs);

            // Verifica: 1. El Regex de números y 2. Que no pase el límite
            if (textoFuturo.matches("\\d*\\.?\\d*") && textoFuturo.length() <= limite) {
                super.insertString(offs, str, a);
            }
        }
    }

    class SoloLetrasDocument extends PlainDocument {

        private final int limite;

        public SoloLetrasDocument(int limite) {
            this.limite = limite;
        }

        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str == null) {
                return;
            }

            String textoActual = getText(0, getLength());
            if ((textoActual.length() + str.length()) <= limite) {
                if (str.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
                    super.insertString(offs, str, a);
                }
            }
        }
    }

    public void aplicarFiltroTexto(int tipo, int limite, int... columnas) {
        JTextField editorTxt = new JTextField();
        editorTxt.setHorizontalAlignment(JTextField.CENTER);

        // 0 = Números, 1 = Letras
        if (tipo == 0) {
            editorTxt.setDocument(new SoloNumerosDocument(limite));
        } else {
            editorTxt.setDocument(new SoloLetrasDocument(limite));
        }

        DefaultCellEditor customEditor = new DefaultCellEditor(editorTxt);

        for (int col : columnas) {
            addbills_table.getColumnModel().getColumn(col).setCellEditor(customEditor);
        }
    }

    //Tabla
    public Add_bills_window(Window window) {
        super(window, Dialog.ModalityType.APPLICATION_MODAL);
        initComponents();
        this.setLocationRelativeTo(null);
        Clases.General_configurations.table_configuration(addbills_table, addbills_tablemod);
        Clases.Add_bills_window.BillsCombo(con, comboGas);
        Clases.Add_bills_window.cargarlista(addbills_tablemod);
        TableColumn columnaCliente = addbills_table.getColumnModel().getColumn(1);
        columnaCliente.setCellEditor(new DefaultCellEditor(comboGas));

        comboGas.addActionListener((ActionEvent e) -> {
            Object item = comboGas.getSelectedItem();
            if (item instanceof Bills) {
                Bills seleccionado = (Bills) item;

                if (seleccionado.getId() == 0) {
                    return;
                }

                System.out.println("Seleccionaste: " + seleccionado.getNombre());
            } else {
            }
        });

        Clases.General_configurations.colorconfig2(jPanel1, lbl_info);
        aplicarFiltroTexto(0, 10, 2);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lbl_info = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        addbills_table = new javax.swing.JTable();
        save_button = new javax.swing.JButton();
        options_button = new javax.swing.JButton();
        mas = new javax.swing.JButton();
        menos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 102), 3, true), "Tabla", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        lbl_info.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbl_info.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_info.setText("Gastos (añade los gastos que se hicieron)");

        addbills_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(addbills_table);

        save_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/save_button.png"))); // NOI18N
        save_button.setBorderPainted(false);
        save_button.setContentAreaFilled(false);
        save_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        save_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/save_button_ro.png"))); // NOI18N
        save_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save_buttonActionPerformed(evt);
            }
        });

        options_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/options_button.png"))); // NOI18N
        options_button.setContentAreaFilled(false);
        options_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        options_button.setDefaultCapable(false);
        options_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/options_button_ro.png"))); // NOI18N
        options_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                options_buttonActionPerformed(evt);
            }
        });

        mas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/add_button.png"))); // NOI18N
        mas.setBorderPainted(false);
        mas.setContentAreaFilled(false);
        mas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mas.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/add_button_ro.png"))); // NOI18N
        mas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                masActionPerformed(evt);
            }
        });

        menos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/delete_button.png"))); // NOI18N
        menos.setContentAreaFilled(false);
        menos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menos.setDefaultCapable(false);
        menos.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/delete_button_ro.png"))); // NOI18N
        menos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_info, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(menos, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(mas, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(options_button, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(save_button, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lbl_info, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(menos, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(mas, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(options_button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(save_button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void options_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_options_buttonActionPerformed
        Cud_window ventana = new Cud_window(2, this);
        ventana.setVisible(true);
        Clases.Add_bills_window.ReloadCombos(con, comboGas);
        addbills_table.repaint();
        addbills_table.revalidate();
    }//GEN-LAST:event_options_buttonActionPerformed

    private void menosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menosActionPerformed
        if (addbills_table.isEditing()) {
            addbills_table.getCellEditor().cancelCellEditing();
        }

        int totalFilas = addbills_table.getRowCount();

        if (totalFilas > 0) {
            int filaABorrar = totalFilas - 1;

            // Obtenemos el ID de la última fila
            Object idExistente = addbills_table.getValueAt(filaABorrar, 0);

            // Si la fila tiene un número de registro, borrar de la lista global
            if (idExistente != null && !idExistente.toString().trim().isEmpty()) {
                Clases.Add_bills_window.deleteLast();
            }

            // 2. AHORA es seguro eliminar de la tabla visual
            addbills_tablemod.removeRow(filaABorrar);

        } else {
            JOptionPane.showMessageDialog(null, "La tabla está vacía.");
        }
    }//GEN-LAST:event_menosActionPerformed

    private void masActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_masActionPerformed
        cont++;
        band = 0;
        Object[] nuevaFila = {"", ""};
        addbills_tablemod.addRow(nuevaFila);

        // Obtener el índice de la nueva fila
        int nuevaFilaIndex = addbills_tablemod.getRowCount() - 1;

        // Hacerla editable
        addbills_tablemod.setEditableRow(nuevaFilaIndex);

        menos.setEnabled(true);
    }//GEN-LAST:event_masActionPerformed

    private void save_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_buttonActionPerformed
        //validacion
        for (int i = 0; i < addbills_table.getRowCount(); i++) {
            Object idObj = addbills_table.getValueAt(i, 0);
            if (idObj == null || idObj.toString().trim().isEmpty()) {
                boolean filaCompleta = true;
                for (int j = 1; j < addbills_tablemod.getColumnCount(); j++) {
                    Object valor = addbills_tablemod.getValueAt(i, j);
                    if (valor == null || valor.toString().trim().isEmpty()) {
                        filaCompleta = false;
                        break;
                    }
                }
                if (!filaCompleta) {
                    JOptionPane.showMessageDialog(null, "Hay filas nuevas con campos vacíos. Completá todos los datos antes de guardar.");
                    return; // Cancela el proceso de guardado
                }
            }

            String pre = addbills_tablemod.getValueAt(i, 2).toString();
            try {
                Double.parseDouble(pre);
            } catch (NumberFormatException e) {
                i = i + 1;
                JOptionPane.showMessageDialog(null, "¡Ha ingresado un valor NO numerico en la fila " + i + "! Porfavor, revise");
                return;
            }
        }

        if (addbills_table.isEditing()) {
            addbills_table.getCellEditor().stopCellEditing();
        }

        int sum = 1; // Valor por defecto si la lista está vacía
        int totalElementos = Clases.Add_bills_window.numlist();

        if (totalElementos > 0) {
            sum = totalElementos + 1;
        }
        for (int i = 0; i < addbills_table.getRowCount(); i++) {
            Object id = addbills_table.getValueAt(i, 0);
            if (id == null || id.toString().trim().isEmpty()) {
                Object cellValue = addbills_table.getValueAt(i, 1);
                if (cellValue instanceof Bills) {
                    Bills dat = (Bills) cellValue;
                    int idgas = dat.getId();
                    String gasnom = dat.getNombre();
                    int pre = Integer.parseInt(addbills_table.getValueAt(i, 2).toString());;
                    Clases.Add_bills_window.lista(sum, idgas, gasnom, pre);
                    sum++;
                }
            }
        }
        JOptionPane.showMessageDialog(null, "LISTA GUARDADA");
        this.dispose();
    }//GEN-LAST:event_save_buttonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Add_bills_window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Add_bills_window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Add_bills_window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Add_bills_window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Add_bills_window(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable addbills_table;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_info;
    private javax.swing.JButton mas;
    private javax.swing.JButton menos;
    private javax.swing.JButton options_button;
    private javax.swing.JButton save_button;
    // End of variables declaration//GEN-END:variables
}
