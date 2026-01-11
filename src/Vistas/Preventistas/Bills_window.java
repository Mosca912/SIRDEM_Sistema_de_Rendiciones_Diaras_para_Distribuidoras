package Vistas.Preventistas;

import Conexion.Conexiones;
import Vistas.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Bills_window extends javax.swing.JFrame {

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
    ModeloEditablePorFila bills_tablemod = new ModeloEditablePorFila(new String[]{"Gasto", "Precio"}, 0) {
        private final int editableRow = -1;
    };
    //Tabla

    static int empId, indice = 0;
    Connection con = Conexiones.Conexion();

    public Bills_window(int empId) throws SQLException {
        initComponents();
        Bills_window.empId = empId;
        Clases.General_configurations.Icon(this);
        this.setLocationRelativeTo(null);
        Clases.General_configurations.MaxMinWindow(this);
        Clases.General_configurations.table_configurationBills(bills_table, bills_tablemod);
        Clases.Bills_window.ShowBillsGeneral(con, bills_tablemod, empId, bills_table, lbl_general);
        if (bills_table.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "¡No se encontró ningún Gasto!");
        }

        Clases.General_configurations.colorconfig4(section_menu, lbl_title, option_panel, general_panel, lbl_general, month_cb);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JPanel();
        section_menu = new javax.swing.JPanel();
        lbl_title = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        bills_table = new javax.swing.JTable();
        option_panel = new javax.swing.JPanel();
        back_button = new javax.swing.JButton();
        month_cb = new javax.swing.JComboBox<>();
        general_panel = new javax.swing.JPanel();
        lbl_general = new javax.swing.JLabel();
        toc = new javax.swing.JLabel();
        start_button = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        new_file_button = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        products_button = new javax.swing.JButton();
        clients_button = new javax.swing.JButton();
        employee_button = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        configuration_button = new javax.swing.JButton();
        help_button = new javax.swing.JButton();
        logout_button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("start_window");
        setUndecorated(true);
        setResizable(false);

        background.setBackground(new java.awt.Color(0, 0, 102));
        background.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        section_menu.setBackground(new java.awt.Color(255, 255, 255));
        section_menu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lbl_title.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lbl_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_title.setText("GASTOS");

        bills_table.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(bills_table);

        option_panel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true), "Opciones:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N
        option_panel.setOpaque(false);

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

        month_cb.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        month_cb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Opciones", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        month_cb.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Selección por mes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        month_cb.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        month_cb.setOpaque(false);
        month_cb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                month_cbActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout option_panelLayout = new javax.swing.GroupLayout(option_panel);
        option_panel.setLayout(option_panelLayout);
        option_panelLayout.setHorizontalGroup(
            option_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(option_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(back_button, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(month_cb, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        option_panelLayout.setVerticalGroup(
            option_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, option_panelLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(option_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(month_cb)
                    .addComponent(back_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        general_panel.setBackground(new java.awt.Color(255, 255, 255));
        general_panel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true), "Total General:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N
        general_panel.setOpaque(false);

        lbl_general.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_general.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_general.setText("0");

        javax.swing.GroupLayout general_panelLayout = new javax.swing.GroupLayout(general_panel);
        general_panel.setLayout(general_panelLayout);
        general_panelLayout.setHorizontalGroup(
            general_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_general, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
        );
        general_panelLayout.setVerticalGroup(
            general_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_general, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout section_menuLayout = new javax.swing.GroupLayout(section_menu);
        section_menu.setLayout(section_menuLayout);
        section_menuLayout.setHorizontalGroup(
            section_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(section_menuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(section_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4)
                    .addComponent(lbl_title, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 956, Short.MAX_VALUE)
                    .addComponent(option_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(section_menuLayout.createSequentialGroup()
                        .addComponent(general_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        section_menuLayout.setVerticalGroup(
            section_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(section_menuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_title, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(option_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(general_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        toc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        toc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/img_login_icon.png"))); // NOI18N

        start_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/start_button.png"))); // NOI18N
        start_button.setBorderPainted(false);
        start_button.setContentAreaFilled(false);
        start_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        start_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/start_button_ro.png"))); // NOI18N
        start_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                start_buttonActionPerformed(evt);
            }
        });

        new_file_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/new_file_button.png"))); // NOI18N
        new_file_button.setBorderPainted(false);
        new_file_button.setContentAreaFilled(false);
        new_file_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        new_file_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/new_file_button_ro.png"))); // NOI18N
        new_file_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new_file_buttonActionPerformed(evt);
            }
        });

        products_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/products_button.png"))); // NOI18N
        products_button.setBorderPainted(false);
        products_button.setContentAreaFilled(false);
        products_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        products_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/products_button_ro.png"))); // NOI18N
        products_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                products_buttonActionPerformed(evt);
            }
        });

        clients_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/clients_button.png"))); // NOI18N
        clients_button.setBorderPainted(false);
        clients_button.setContentAreaFilled(false);
        clients_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clients_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/clients_button_ro.png"))); // NOI18N
        clients_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clients_buttonActionPerformed(evt);
            }
        });

        employee_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/employee_button_selected.png"))); // NOI18N
        employee_button.setBorderPainted(false);
        employee_button.setContentAreaFilled(false);
        employee_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        employee_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employee_buttonActionPerformed(evt);
            }
        });

        configuration_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/configuration_button.png"))); // NOI18N
        configuration_button.setBorderPainted(false);
        configuration_button.setContentAreaFilled(false);
        configuration_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        configuration_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/configuration_button_ro.png"))); // NOI18N
        configuration_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configuration_buttonActionPerformed(evt);
            }
        });

        help_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/help_button.png"))); // NOI18N
        help_button.setBorderPainted(false);
        help_button.setContentAreaFilled(false);
        help_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        help_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/help_button_ro.png"))); // NOI18N
        help_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                help_buttonActionPerformed(evt);
            }
        });

        logout_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/logout_button.png"))); // NOI18N
        logout_button.setBorderPainted(false);
        logout_button.setContentAreaFilled(false);
        logout_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logout_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/logout_button_ro.png"))); // NOI18N
        logout_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logout_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(start_button, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jSeparator1)
                        .addComponent(new_file_button, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jSeparator2))
                    .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(logout_button, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(products_button, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(clients_button, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(employee_button, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(configuration_button, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(help_button, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(toc, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(section_menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(section_menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addComponent(toc, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(start_button, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(new_file_button, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(products_button, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(clients_button, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(employee_button, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(configuration_button, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(help_button, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(logout_button, javax.swing.GroupLayout.PREFERRED_SIZE, 56, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void configuration_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configuration_buttonActionPerformed
        Configuration_window ventana = new Configuration_window();
        ventana.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_configuration_buttonActionPerformed

    private void logout_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logout_buttonActionPerformed
        int opcion = JOptionPane.showConfirmDialog(null, "¿Deseas Cerrar sesión?", "Confirmar acción",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (opcion != JOptionPane.OK_OPTION) {
            return;
        }
        
        
        Clases.Menu_window.Logout();
        Clases.Employee_window.logout();

        Menu_window ventana = new Menu_window();
        ventana.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_logout_buttonActionPerformed

    private void new_file_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_new_file_buttonActionPerformed
        int verificacionfile = Clases.General_configurations.VerificationFile(con);
        if (verificacionfile == 0) {
            New_file_window ventana = new New_file_window();
            ventana.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_new_file_buttonActionPerformed

    private void products_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_products_buttonActionPerformed
        Products_window ventana;
        try {
            ventana = new Products_window();
            ventana.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Bills_window.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_products_buttonActionPerformed

    private void clients_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clients_buttonActionPerformed
        Clients_window ventana = new Clients_window();
        ventana.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_clients_buttonActionPerformed

    private void employee_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employee_buttonActionPerformed

    }//GEN-LAST:event_employee_buttonActionPerformed

    private void start_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_start_buttonActionPerformed
        Start_window ventana;
        try {
            ventana = new Start_window();
            ventana.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Bills_window.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_start_buttonActionPerformed

    private void back_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_back_buttonActionPerformed
        Employee_window ventana = new Employee_window();
        ventana.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_back_buttonActionPerformed

    private void month_cbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_month_cbActionPerformed
        indice = month_cb.getSelectedIndex();
        if (indice != 0) {
            bills_tablemod.setRowCount(0);
            try {
                Clases.Bills_window.ShowBillsMonth(con, bills_tablemod, empId, bills_table, lbl_general, indice);
                if (bills_table.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "¡No se encontró ningún Gasto este mes!");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "ERRROR: " + ex);
            }
        } else {
            bills_tablemod.setRowCount(0);
            try {
                Clases.Bills_window.ShowBillsGeneral(con, bills_tablemod, empId, bills_table, lbl_general);
                if (bills_table.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "¡No se encontró ningún Gasto!");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "ERRROR: " + ex);
            }
        }
    }//GEN-LAST:event_month_cbActionPerformed

    private void help_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_help_buttonActionPerformed
        Clases.PDF.abrirPDF("/Recursos/ManualPreventista.pdf");
    }//GEN-LAST:event_help_buttonActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Bills_window(0).setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Bills_window.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton back_button;
    private javax.swing.JPanel background;
    private javax.swing.JTable bills_table;
    private javax.swing.JButton clients_button;
    private javax.swing.JButton configuration_button;
    private javax.swing.JButton employee_button;
    private javax.swing.JPanel general_panel;
    private javax.swing.JButton help_button;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lbl_general;
    private javax.swing.JLabel lbl_title;
    private javax.swing.JButton logout_button;
    private javax.swing.JComboBox<String> month_cb;
    private javax.swing.JButton new_file_button;
    private javax.swing.JPanel option_panel;
    private javax.swing.JButton products_button;
    private javax.swing.JPanel section_menu;
    private javax.swing.JButton start_button;
    private javax.swing.JLabel toc;
    // End of variables declaration//GEN-END:variables
}
