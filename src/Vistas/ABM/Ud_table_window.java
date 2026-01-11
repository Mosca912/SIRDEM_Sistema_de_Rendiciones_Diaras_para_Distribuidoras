/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas.ABM;

import Conexion.Conexiones;
import java.awt.Dialog;
import java.awt.Window;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Facuymayriver
 */
public class Ud_table_window extends javax.swing.JDialog {

    static int flag, mode;
    Connection con = Conexiones.Conexion();

    // Crea una sola clase interna más limpia
    public class MiModeloGeneral extends DefaultTableModel {

        private int modoLocal;

        public MiModeloGeneral(Object[] columnNames, int rowCount, int modo) {
            super(columnNames, rowCount);
            this.modoLocal = modo;
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            // Si el modo es 0 (Editar), permitimos editar todo menos la columna 0 (ID)
            if (modoLocal == 0) {
                return column != 0;
            }
            // Si el modo es 1 (Eliminar), nada es editable
            return false;
        }

        // Método para actualizar el modo si es necesario
        public void setModo(int modo) {
            this.modoLocal = modo;
        }
    }
    //Metodos para editar
    MiModeloGeneral categories_table = new MiModeloGeneral(new String[]{"Nº", "Categoria"}, 0, mode);
    MiModeloGeneral zones_table = new MiModeloGeneral(new String[]{"Nº", "Zona"}, 0, mode);
    MiModeloGeneral users_table = new MiModeloGeneral(new String[]{"Nº", "Usuario"}, 0, mode);
    MiModeloGeneral bills_table = new MiModeloGeneral(new String[]{"Nº", "Gasto"}, 0, mode);

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

    class AlfanumericoDocument extends PlainDocument {

        private int limite;

        public AlfanumericoDocument(int limite) {
            this.limite = limite;
        }

        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str == null) {
                return;
            }

            String textoActual = getText(0, getLength());

            // 1. Validar límite de longitud
            if ((textoActual.length() + str.length()) <= limite) {
                if (str.matches("[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s.,:;()\\-]+")) {
                    super.insertString(offs, str, a);
                }
            }
        }
    }

    public void aplicarFiltroTabla(int tipo, int limite, int... columnas) {
        JTextField editorTxt = new JTextField();
        editorTxt.setHorizontalAlignment(JTextField.CENTER);

        switch (tipo) {
            case 0:
                editorTxt.setDocument(new SoloNumerosDocument(limite));
                break;
            case 1:
                editorTxt.setDocument(new SoloLetrasDocument(limite));
                break;
            case 2:
                editorTxt.setDocument(new SoloLetrasDocument(limite));
                break;
        }

        DefaultCellEditor customEditor = new DefaultCellEditor(editorTxt);
        for (int col : columnas) {
            ud_table.getColumnModel().getColumn(col).setCellEditor(customEditor);
        }
    }

    public Ud_table_window(int flag, Window window, int mode) throws SQLException {
        super(window, Dialog.ModalityType.APPLICATION_MODAL);
        initComponents();
        this.setLocationRelativeTo(null);
        Ud_table_window.flag = flag;
        Ud_table_window.mode = mode;
        if (mode == 1) {
            Icon normal = new ImageIcon(getClass().getResource("/Recursos/delete_button.png"));
            Icon encima = new ImageIcon(getClass().getResource("/Recursos/delete_button_ro.png"));
            save_button.setIcon(normal);
            save_button.setRolloverIcon(encima);
        }

        categories_table.setModo(mode);
        zones_table.setModo(mode);
        users_table.setModo(mode);
        bills_table.setModo(mode);

        Clases.Ud_table_window.CUDInfo(flag, lbl_info, mode, ud_table, categories_table, zones_table, users_table, bills_table);
        Clases.Ud_table_window.CUDTable(con, flag, ud_table, categories_table, zones_table, users_table, bills_table);
        Clases.General_configurations.table_configurationUD(ud_table);
        if (ud_table.getRowCount() == 0) {
            switch (flag) {
                case 0:
                    JOptionPane.showMessageDialog(null, "¡No se encontró ninguna Zona!");
                    return;
                case 1:
                    JOptionPane.showMessageDialog(null, "¡No se encontró ninguna Categoria!");
                    return;
                case 2:
                    JOptionPane.showMessageDialog(null, "¡No se encontró ningún Gasto!");
                    return;
                case 3:
                    JOptionPane.showMessageDialog(null, "¡No se encontró ningún Usuario!");
            }
        }
        if (mode == 0) {
            switch (flag) {
                case 0:
                    aplicarFiltroTabla(1, 30, 1);
                    break;
                case 1:
                    aplicarFiltroTabla(1, 30, 1);
                    break;
                case 2:
                    aplicarFiltroTabla(2, 50, 1);
                    break;
            }
        }

        Clases.General_configurations.colorconfig2(jPanel1, lbl_info);
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
        back_button = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ud_table = new javax.swing.JTable();
        save_button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(530, 620));
        setMinimumSize(new java.awt.Dimension(530, 612));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(530, 620));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 102), 3, true), "Tabla", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        lbl_info.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbl_info.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_info.setText("Zonas");

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

        ud_table.setModel(new javax.swing.table.DefaultTableModel(
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
        ud_table.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane1.setViewportView(ud_table);

        save_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/modify_button.png"))); // NOI18N
        save_button.setBorderPainted(false);
        save_button.setContentAreaFilled(false);
        save_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        save_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/modify_button_ro.png"))); // NOI18N
        save_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save_buttonActionPerformed(evt);
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
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(back_button, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(save_button, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lbl_info, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(back_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(save_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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

    private void back_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_back_buttonActionPerformed
        this.dispose();
    }//GEN-LAST:event_back_buttonActionPerformed

    private void save_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_buttonActionPerformed
        if (mode == 1) {
            int filaSeleccionada;
            switch (flag) {
                case 0: //Zonas
                    filaSeleccionada = ud_table.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        int opcion = JOptionPane.showConfirmDialog(
                                null,
                                "¿Deseás Eliminar?",
                                "Confirmar acción",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE
                        );

                        if (opcion == JOptionPane.OK_OPTION) {
                            int fila = ud_table.getSelectedRow();
                            int cod = Integer.parseInt(ud_table.getValueAt(fila, 0).toString());
                            try {
                                int veri = Clases.Ud_table_window.Validation(con, cod, flag);
                                int borrado = 1;
                                if (veri == 1 || veri == 0) {
                                    try {
                                        Clases.Ud_table_window.DeleteZone(con, cod, borrado);
                                        JOptionPane.showMessageDialog(null, "¡Zona eliminada!");
                                        zones_table.setRowCount(0);
                                        Clases.Ud_table_window.CUDTable(con, flag, ud_table, categories_table, zones_table, users_table, bills_table);
                                    } catch (SQLException ex) {
                                        JOptionPane.showMessageDialog(null, "ERROR" + ex);
                                    }
                                }
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, "ERROR" + ex);
                            }
                        } else if (opcion == JOptionPane.CANCEL_OPTION || opcion == JOptionPane.CLOSED_OPTION) {
                            System.out.println("Cancelado");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Seleccione una fila primero.");
                    }
                    return;
                case 1: //Categorias
                    filaSeleccionada = ud_table.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        int opcion = JOptionPane.showConfirmDialog(
                                null,
                                "¿Deseás Eliminar?",
                                "Confirmar acción",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE
                        );

                        if (opcion == JOptionPane.OK_OPTION) {
                            int fila = ud_table.getSelectedRow();
                            int cod = Integer.parseInt(ud_table.getValueAt(fila, 0).toString());
                            int veri;
                            try {
                                veri = Clases.Ud_table_window.Validation(con, cod, flag);
                                int borrado = 1;
                                if (veri == 1 || veri == 0) {
                                    try {
                                        Clases.Ud_table_window.DeleteCategory(con, cod, borrado);
                                        JOptionPane.showMessageDialog(null, "¡Categoria eliminada!");
                                        categories_table.setRowCount(0);
                                        Clases.Ud_table_window.CUDTable(con, flag, ud_table, categories_table, zones_table, users_table, bills_table);
                                    } catch (SQLException ex) {
                                        JOptionPane.showMessageDialog(null, "ERROR" + ex);
                                    }
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(Ud_table_window.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else if (opcion == JOptionPane.CANCEL_OPTION || opcion == JOptionPane.CLOSED_OPTION) {
                            System.out.println("Cancelado");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Seleccione una fila primero.");
                    }
                    return;
                case 2: //Gasto
                    filaSeleccionada = ud_table.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        int opcion = JOptionPane.showConfirmDialog(
                                null,
                                "¿Deseás Eliminar?",
                                "Confirmar acción",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE
                        );

                        if (opcion == JOptionPane.OK_OPTION) {
                            int fila = ud_table.getSelectedRow();
                            int cod = Integer.parseInt(ud_table.getValueAt(fila, 0).toString());
                            try {
                                Clases.Ud_table_window.DeleteBills(con, cod, 1);
                                JOptionPane.showMessageDialog(null, "¡Gasto eliminado!");
                                bills_table.setRowCount(0);
                                Clases.Ud_table_window.CUDTable(con, flag, ud_table, categories_table, zones_table, users_table, bills_table);
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, "ERROR" + ex);
                            }
                        } else if (opcion == JOptionPane.CANCEL_OPTION || opcion == JOptionPane.CLOSED_OPTION) {
                            System.out.println("Cancelado");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Seleccione una fila primero.");
                    }
                    return;
                case 3:
                    filaSeleccionada = ud_table.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        int opcion = JOptionPane.showConfirmDialog(
                                null,
                                "¿Deseás Eliminar?",
                                "Confirmar acción",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE
                        );

                        if (opcion == JOptionPane.OK_OPTION) {
                            int fila = ud_table.getSelectedRow();
                            int cod = Integer.parseInt(ud_table.getValueAt(fila, 0).toString());
                            try {
                                Clases.Ud_table_window.DeleteUser(con, cod, 1);
                                JOptionPane.showMessageDialog(null, "¡Usuario eliminado!");
                                users_table.setRowCount(0);
                                Clases.Ud_table_window.CUDTable(con, flag, ud_table, categories_table, zones_table, users_table, bills_table);
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, "ERROR" + ex);
                            }
                        } else if (opcion == JOptionPane.CANCEL_OPTION || opcion == JOptionPane.CLOSED_OPTION) {
                            System.out.println("Cancelado");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Seleccione una fila primero.");
                    }
            }
        } else if (mode == 0) {
            switch (flag) {
                case 0:
                    for (int i = 0; i < ud_table.getRowCount(); i++) {
                        boolean filaCompleta = true;
                        for (int j = 1; j < ud_table.getColumnCount(); j++) {
                            Object valor = ud_table.getValueAt(i, j);
                            if (valor == null || valor.toString().trim().isEmpty()) {
                                filaCompleta = false;
                                break;
                            }
                        }
                        if (!filaCompleta) {
                            JOptionPane.showMessageDialog(null, "Hay filas nuevas con campos vacíos. Completá todos los datos antes de guardar.");
                            return;
                        }
                    }

                    for (int i = 0; i < ud_table.getRowCount(); i++) {
                        int cod = Integer.parseInt(ud_table.getValueAt(i, 0).toString());
                        String Zon = ud_table.getValueAt(i, 1).toString();
                        try {
                            Clases.Ud_table_window.UpdateZone(con, cod, Zon);
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "ERROR AL ACTUALIZAR ZONAS (1):" + ex);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "¡Zona/s actualizada/s correctamente!");
                    try {
                        zones_table.setRowCount(0);
                        Clases.Ud_table_window.CUDTable(con, flag, ud_table, categories_table, zones_table, users_table, bills_table);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "ERROR AL ACTUALIZAR LA TABLA ZONAS: " + ex);
                    }
                    return;
                case 1:
                    for (int i = 0; i < ud_table.getRowCount(); i++) {
                        boolean filaCompleta = true;
                        for (int j = 1; j < ud_table.getColumnCount(); j++) {
                            Object valor = ud_table.getValueAt(i, j);
                            if (valor == null || valor.toString().trim().isEmpty()) {
                                filaCompleta = false;
                                break;
                            }
                        }
                        if (!filaCompleta) {
                            JOptionPane.showMessageDialog(null, "Hay filas nuevas con campos vacíos. Completá todos los datos antes de guardar.");
                            return;
                        }
                    }

                    for (int i = 0; i < ud_table.getRowCount(); i++) {
                        int cod = Integer.parseInt(ud_table.getValueAt(i, 0).toString());
                        String Cat = ud_table.getValueAt(i, 1).toString();
                        try {
                            Clases.Ud_table_window.UpdateCategories(con, cod, Cat);
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "ERROR AL ACTUALIZAR CATEGORIAS (1):" + ex);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "¡Categoria/s actualizada/s correctamente!");
                    try {
                        categories_table.setRowCount(0);
                        Clases.Ud_table_window.CUDTable(con, flag, ud_table, categories_table, zones_table, users_table, bills_table);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "ERROR AL ACTUALIZAR LA TABLA CATEGORIAS: " + ex);
                    }
                    return;
                case 2:
                    for (int i = 0; i < ud_table.getRowCount(); i++) {
                        boolean filaCompleta = true;
                        for (int j = 1; j < ud_table.getColumnCount(); j++) {
                            Object valor = ud_table.getValueAt(i, j);
                            if (valor == null || valor.toString().trim().isEmpty()) {
                                filaCompleta = false;
                                break;
                            }
                        }
                        if (!filaCompleta) {
                            JOptionPane.showMessageDialog(null, "Hay filas nuevas con campos vacíos. Completá todos los datos antes de guardar.");
                            return;
                        }
                    }

                    for (int i = 0; i < ud_table.getRowCount(); i++) {
                        int cod = Integer.parseInt(ud_table.getValueAt(i, 0).toString());
                        String Bill = ud_table.getValueAt(i, 1).toString();
                        try {
                            Clases.Ud_table_window.UpdateBills(con, cod, Bill);
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "ERROR AL ACTUALIZAR GASTOS (1):" + ex);
                        }
                    }
                    JOptionPane.showMessageDialog(null, "¡Gasto/s actualizada/s correctamente!");
                    try {
                        bills_table.setRowCount(0);
                        Clases.Ud_table_window.CUDTable(con, flag, ud_table, categories_table, zones_table, users_table, bills_table);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "ERROR AL ACTUALIZAR LA TABLA GASTOS: " + ex);
                    }
            }
        }
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
            java.util.logging.Logger.getLogger(Ud_table_window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ud_table_window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ud_table_window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ud_table_window.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Ud_table_window(0, null, 0).setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Ud_table_window.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton back_button;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_info;
    private javax.swing.JButton save_button;
    private javax.swing.JTable ud_table;
    // End of variables declaration//GEN-END:variables
}
