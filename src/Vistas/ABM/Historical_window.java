/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas.ABM;

import Conexion.Conexiones;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.DefaultCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.MaskFormatter;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Facuymayriver
 */
public class Historical_window extends javax.swing.JDialog {

    static int id, saldo, band;
    static String fecha;
    Connection con = Conexiones.Conexion();

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
    ModeloEditablePorFila historical_tablemod = new ModeloEditablePorFila(new String[]{"Nº", "Cantidad", "Fecha"}, 0) {
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
            historical_table.getColumnModel().getColumn(col).setCellEditor(customEditor);
        }
    }
    
    //Tabla

    public Historical_window(int id, int saldo, String fecha, int band, JFrame ventanaPrincipal) {
        super(ventanaPrincipal, true);
        Historical_window.id = id;
        Historical_window.saldo = saldo;
        Historical_window.fecha = fecha;
        Historical_window.band = band;
        initComponents();
        this.setLocationRelativeTo(null);
        Clases.General_configurations.table_configuration(historical_table, historical_tablemod);
        Clases.General_configurations.colorconfig5(backpanel, date_lbl, trusted_lbl, Status_lbl);

        //Mostrar tabla
        try {
            Clases.Historical_window.ShowHistorial(con, historical_tablemod, id, historical_table);
            if (historical_table.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "No se encontró ningún Historial.");
            }
        } catch (SQLException ex) {

        }
        //Mostrar tabla
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter("##/##/####");
        } catch (ParseException ex) {

        }
        formatter.setPlaceholderCharacter('0');
        JFormattedTextField horaField = new JFormattedTextField(formatter);

        DefaultCellEditor editor = new DefaultCellEditor(horaField);
        historical_table.getColumnModel().getColumn(2).setCellEditor(editor);

        String saldolbl = String.valueOf(saldo);
        trusted_lbl.setText("Saldo: " + saldolbl);
        date_lbl.setText("Fecha: " + fecha);
        if (band == 1) {
            add_button.setEnabled(false);
            save_button.setEnabled(false);
            Status_lbl.setText("Estado: Abonado");
        }
        delete_button.setEnabled(false);
        aplicarFiltroTexto(0, 10, 1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backpanel = new javax.swing.JPanel();
        back_button = new javax.swing.JButton();
        save_button = new javax.swing.JButton();
        add_button = new javax.swing.JButton();
        delete_button = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        historical_table = new javax.swing.JTable();
        date_lbl = new javax.swing.JLabel();
        trusted_lbl = new javax.swing.JLabel();
        Status_lbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        backpanel.setBackground(new java.awt.Color(255, 255, 255));
        backpanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true), "Historial", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        back_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/back_button.png"))); // NOI18N
        back_button.setBorderPainted(false);
        back_button.setContentAreaFilled(false);
        back_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        back_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/back_button_ro.png"))); // NOI18N
        back_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                back_buttonActionPerformed(evt);
            }
        });

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

        add_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/add_button.png"))); // NOI18N
        add_button.setToolTipText("Añadir una nueva fila en la tabla para una carga de preventista");
        add_button.setBorderPainted(false);
        add_button.setContentAreaFilled(false);
        add_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/add_button_ro.png"))); // NOI18N
        add_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_buttonActionPerformed(evt);
            }
        });

        delete_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/delete_button.png"))); // NOI18N
        delete_button.setToolTipText("Seleccione al preventista para eliminarlo!");
        delete_button.setBorderPainted(false);
        delete_button.setContentAreaFilled(false);
        delete_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        delete_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/delete_button_ro.png"))); // NOI18N
        delete_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_buttonActionPerformed(evt);
            }
        });

        historical_table.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(historical_table);

        date_lbl.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        date_lbl.setText("Fecha:");

        trusted_lbl.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        trusted_lbl.setText("Fiado:");

        Status_lbl.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        Status_lbl.setText("Estado: Pendiente");

        javax.swing.GroupLayout backpanelLayout = new javax.swing.GroupLayout(backpanel);
        backpanel.setLayout(backpanelLayout);
        backpanelLayout.setHorizontalGroup(
            backpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(backpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(trusted_lbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(date_lbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
                    .addGroup(backpanelLayout.createSequentialGroup()
                        .addComponent(back_button, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(save_button, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backpanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(delete_button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(add_button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Status_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        backpanelLayout.setVerticalGroup(
            backpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(date_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(trusted_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Status_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(add_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(delete_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(back_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(save_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void back_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_back_buttonActionPerformed
        this.dispose();
    }//GEN-LAST:event_back_buttonActionPerformed

    private void save_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_buttonActionPerformed
        //validacion
        for (int i = 0; i < historical_table.getRowCount(); i++) {
            Object idObj = historical_table.getValueAt(i, 0);
            // Si es una fila nueva (sin código), la validamos
            if (idObj == null || idObj.toString().trim().isEmpty()) {
                boolean filaCompleta = true;
                // Recorremos todas las columnas
                for (int j = 1; j < historical_tablemod.getColumnCount(); j++) {
                    Object valor = historical_tablemod.getValueAt(i, j);
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

            // Obtener los datos actualizados desde la tabla
            String fecver = historical_table.getValueAt(i, 1).toString();
            try {
                Double.parseDouble(fecver);
            } catch (NumberFormatException e) {
                i = i + 1;
                JOptionPane.showMessageDialog(null, "¡Ha ingresado un valor NO numerico en la fila " + i + "! Porfavor, revise");
                return;
            }
        }
        //validacion

        //validacion
        if (historical_table.isEditing()) {
            historical_table.getCellEditor().stopCellEditing();
        }

        //Insert
        for (int i = 0; i < historical_table.getRowCount(); i++) {
            Object idObj = historical_table.getValueAt(i, 0);
            if (idObj == null || idObj.toString().trim().isEmpty()) {
                String sal = historical_table.getValueAt(i, 1).toString();
                int saldo2 = Integer.parseInt(sal);
                String FechaPag = historical_table.getValueAt(i, 2).toString();
                if (saldo < saldo2) {
                    int opcion = JOptionPane.showConfirmDialog(
                            null,
                            "Ha ingreasdo un valor superior al saldo, ¿Desea Agregar?",
                            "Confirmar acción",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );
                    if (opcion == JOptionPane.OK_OPTION) {
                        try {
                            // Formato que viene de la JTable (dd/MM/yyyy)
                            DateTimeFormatter entrada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            LocalDate hoy = LocalDate.now();
                            // Parsear la fecha a LocalDate
                            LocalDate fecha3 = LocalDate.parse(FechaPag, entrada);

                            if (fecha3.isAfter(hoy)) {
                                JOptionPane.showMessageDialog(null, "Error: La fecha no puede ser futura. Hoy es: " + hoy.format(entrada));
                                return; // Cortamos la ejecución aquí para que no guarde en la BD
                            }

                            // Formato que necesita la base de datos (yyyy/MM/dd)
                            DateTimeFormatter salida = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                            String fechaFormateada = fecha3.format(salida);

                            Clases.Historical_window.AddHistorical(con, saldo2, fechaFormateada, id);;

                            JOptionPane.showMessageDialog(null, "Registro agregado correctamente");
                            //Mostrar tabla
                            try {
                                historical_tablemod.setRowCount(0);
                                Clases.Historical_window.ShowHistorial(con, historical_tablemod, id, historical_table);
                            } catch (SQLException ex) {

                            }
                            //Mostrar tabla
                        } catch (HeadlessException | SQLException e) {
                            JOptionPane.showMessageDialog(null, "Formato de fecha inválido: " + FechaPag);
                        };
                    } else if (opcion == JOptionPane.CANCEL_OPTION || opcion == JOptionPane.CLOSED_OPTION) {
                        // El usuario eligió "Cancelar" o cerró la ventana
                        System.out.println("Cancelado");
                    }
                } else {
                    try {
                        // Formato que viene de la JTable (dd/MM/yyyy)
                        DateTimeFormatter entrada = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                        // Parsear la fecha a LocalDate
                        LocalDate fecha3 = LocalDate.parse(FechaPag, entrada);
                        LocalDate hoy = LocalDate.now();

                        if (fecha3.isAfter(hoy)) {
                            JOptionPane.showMessageDialog(null, "Error: La fecha no puede ser futura. Hoy es: " + hoy.format(entrada));
                            return; // Cortamos la ejecución aquí para que no guarde en la BD
                        }

                        // Formato que necesita la base de datos (yyyy/MM/dd)
                        DateTimeFormatter salida = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                        String fechaFormateada = fecha3.format(salida);

                        Clases.Historical_window.AddHistorical(con, saldo2, fechaFormateada, id);

                        JOptionPane.showMessageDialog(null, "Registro agregado correctamente");
                        //Mostrar tabla
                        try {
                            int veri = Clases.Historical_window.Verification(con, saldo, id);
                            if (veri == 1) {
                                this.dispose();
                            } else {
                                try {
                                    historical_tablemod.setRowCount(0);
                                    Clases.Historical_window.ShowHistorial(con, historical_tablemod, id, historical_table);
                                } catch (SQLException ex) {
                                    JOptionPane.showMessageDialog(null, "Error al cargar la tabla: " + ex);
                                }
                                add_button.setEnabled(true);
                                delete_button.setEnabled(false);
                            }
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Error al verificar: " + ex);
                        }
                        //Mostrar tabla
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Formato de fecha inválido: " + FechaPag);
                    };
                }
            }
        }
    }//GEN-LAST:event_save_buttonActionPerformed

    private void add_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_buttonActionPerformed
        int sum = 0;
        if (historical_table.getRowCount() == 0) {
            Object[] nuevaFila = {"", ""};
            historical_tablemod.addRow(nuevaFila);
            // Obtener el índice de la nueva fila
            int nuevaFilaIndex = historical_tablemod.getRowCount() - 1;

            // Hacerla editable
            historical_tablemod.setEditableRow(nuevaFilaIndex);

            // Seleccionar automáticamente
            historical_table.setRowSelectionInterval(nuevaFilaIndex, nuevaFilaIndex);
            historical_table.editCellAt(nuevaFilaIndex, 1); // enfocar en la primera celda editable
            historical_table.requestFocus();
        } else {
            for (int i = 0; i < historical_table.getRowCount(); i++) {
                Object valor = historical_table.getValueAt(i, 1);

                if (valor != null && !valor.toString().trim().isEmpty()) {
                    try {
                        int saldo2 = Integer.parseInt(valor.toString());
                        sum += saldo2;
                    } catch (NumberFormatException e) {
                        System.out.println("Valor no numérico en fila " + i + ": " + valor);
                    }
                }
            }

            int total = saldo - sum;
            Object[] nuevaFila = {"", ""};
            historical_tablemod.addRow(nuevaFila);
            // Obtener el índice de la nueva fila
            int nuevaFilaIndex = historical_tablemod.getRowCount() - 1;

            // Hacerla editable
            historical_tablemod.setEditableRow(nuevaFilaIndex);

            // Seleccionar automáticamente
            historical_table.setRowSelectionInterval(nuevaFilaIndex, nuevaFilaIndex);
            historical_table.editCellAt(nuevaFilaIndex, 2); // enfocar en la primera celda editable
            historical_table.requestFocus();
            int filaSeleccionada = historical_table.getSelectedRow();
            // 🚩 Actualizamos en la fila seleccionada, columna 1ç
            try {
                historical_table.setValueAt(total, filaSeleccionada, 1);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "El precio no es numérico");
            }
        }
        add_button.setEnabled(false);
        delete_button.setEnabled(true);
    }//GEN-LAST:event_add_buttonActionPerformed

    private void delete_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_buttonActionPerformed
        int totalFilas = historical_tablemod.getRowCount();
        if (totalFilas > 0) {
            historical_tablemod.removeRow(totalFilas - 1); // elimina la última
            add_button.setEnabled(true);
            delete_button.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(null, "La tabla está vacía.");
        }
    }//GEN-LAST:event_delete_buttonActionPerformed

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
            java.util.logging.Logger.getLogger(Historical_window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Historical_window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Historical_window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Historical_window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Historical_window(id, saldo, fecha, band, null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Status_lbl;
    private javax.swing.JButton add_button;
    private javax.swing.JButton back_button;
    private javax.swing.JPanel backpanel;
    private javax.swing.JLabel date_lbl;
    private javax.swing.JButton delete_button;
    private javax.swing.JTable historical_table;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton save_button;
    private javax.swing.JLabel trusted_lbl;
    // End of variables declaration//GEN-END:variables
}
