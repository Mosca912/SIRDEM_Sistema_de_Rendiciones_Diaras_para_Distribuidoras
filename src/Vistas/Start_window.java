package Vistas;

import Conexion.Conexiones;
import Vistas.ABM.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

public class Start_window extends javax.swing.JFrame {

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
    ModeloEditablePorFila maturity_tablemod = new ModeloEditablePorFila(new String[]{"Descripcion", "Unidad", "Categoria", "Vencimiento"}, 0) {
        private final int editableRow = -1;
    };

    ModeloEditablePorFila nextmaturity_tablemod = new ModeloEditablePorFila(new String[]{"Descripcion", "Unidad", "Categoria", "Vencimiento"}, 0) {
        private final int editableRow = -1;
    };

    //Tabla
    public Start_window() throws SQLException {
        initComponents();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Creamos un Timer que se ejecute cada 1000 milisegundos (1 segundo)
        Timer timer = new Timer(1000, e -> {
            // Obtenemos la hora actual
            String horaActual = LocalDateTime.now().format(dtf);
            // Suponiendo que tu JLabel se llama lblHora
            time_label.setText(horaActual);
        });

        // Iniciamos el Timer
        timer.start();
        Clases.General_configurations.Icon(this);
        this.setLocationRelativeTo(null);
        Clases.General_configurations.MaxMinWindow(this);
        Clases.General_configurations.colorconfig5(lbl_title, lbl_maturitynext, lbl_maturity, time_label, section_menu, jPanel1, information_panel, jPanel2, time_label);
        nextmaturity_table.setModel(nextmaturity_tablemod);
        maturity_table.setModel(maturity_tablemod);
        Clases.Start_window.ShowProductsNext(con, nextmaturity_tablemod, nextmaturity_table);
        Clases.Start_window.ShowProductsMaturity(con, maturity_tablemod, maturity_table);
        Clases.General_configurations.table_configurationBills(maturity_table, maturity_tablemod);
        Clases.General_configurations.table_configurationBills(nextmaturity_table, nextmaturity_tablemod);
        
        int rankvalid = Clases.Menu_window.rank();
        if (rankvalid == 2) {
            add_user_button.setEnabled(false);
            modify_user_button.setEnabled(false);
            delete_user_button.setEnabled(false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JPanel();
        section_menu = new javax.swing.JPanel();
        lbl_title = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        information_panel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        maturity_table = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        nextmaturity_table = new javax.swing.JTable();
        lbl_maturitynext = new javax.swing.JLabel();
        lbl_maturity = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        time_label = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        add_user_button = new javax.swing.JButton();
        delete_user_button = new javax.swing.JButton();
        modify_user_button = new javax.swing.JButton();
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
        setMaximumSize(new java.awt.Dimension(1200, 700));
        setMinimumSize(new java.awt.Dimension(1200, 700));
        setUndecorated(true);
        setResizable(false);

        background.setBackground(new java.awt.Color(0, 0, 102));
        background.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        section_menu.setBackground(new java.awt.Color(255, 255, 255));
        section_menu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lbl_title.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lbl_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_title.setText("Inicio");

        information_panel.setBackground(new java.awt.Color(255, 255, 255));
        information_panel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "Información", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 24))); // NOI18N

        maturity_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(maturity_table);

        nextmaturity_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(nextmaturity_table);

        lbl_maturitynext.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbl_maturitynext.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_maturitynext.setText("Productos a tener en cuenta:");

        lbl_maturity.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbl_maturity.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_maturity.setText("Productos a vencer/Productos vencidos:");

        javax.swing.GroupLayout information_panelLayout = new javax.swing.GroupLayout(information_panel);
        information_panel.setLayout(information_panelLayout);
        information_panelLayout.setHorizontalGroup(
            information_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(information_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(information_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addComponent(lbl_maturitynext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_maturity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        information_panelLayout.setVerticalGroup(
            information_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(information_panelLayout.createSequentialGroup()
                .addComponent(lbl_maturity, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_maturitynext, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "Hora", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 24))); // NOI18N

        time_label.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        time_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        time_label.setText("00:00:00");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(time_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(time_label, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "Opciones de administrador", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        add_user_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/new_user_button.png"))); // NOI18N
        add_user_button.setBorderPainted(false);
        add_user_button.setContentAreaFilled(false);
        add_user_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add_user_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/new_user_button_ro.png"))); // NOI18N
        add_user_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_user_buttonActionPerformed(evt);
            }
        });

        delete_user_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/delete_user_button.png"))); // NOI18N
        delete_user_button.setBorderPainted(false);
        delete_user_button.setContentAreaFilled(false);
        delete_user_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        delete_user_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/delete_user_button_ro.png"))); // NOI18N
        delete_user_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_user_buttonActionPerformed(evt);
            }
        });

        modify_user_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/modfiy_user_button.png"))); // NOI18N
        modify_user_button.setBorderPainted(false);
        modify_user_button.setContentAreaFilled(false);
        modify_user_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        modify_user_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/modfiy_user_button_ro.png"))); // NOI18N
        modify_user_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modify_user_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(add_user_button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(delete_user_button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(modify_user_button, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(add_user_button, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 22, Short.MAX_VALUE)
                .addComponent(modify_user_button, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(delete_user_button)
                .addContainerGap())
        );

        javax.swing.GroupLayout section_menuLayout = new javax.swing.GroupLayout(section_menu);
        section_menu.setLayout(section_menuLayout);
        section_menuLayout.setHorizontalGroup(
            section_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(section_menuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(section_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator4)
                    .addGroup(section_menuLayout.createSequentialGroup()
                        .addComponent(information_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(section_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        section_menuLayout.setVerticalGroup(
            section_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(section_menuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_title, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(section_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(section_menuLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(85, 85, 85)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(information_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        toc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        toc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/img_login_icon.png"))); // NOI18N

        start_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/start_button_selected.png"))); // NOI18N
        start_button.setBorderPainted(false);
        start_button.setContentAreaFilled(false);
        start_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

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

        employee_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/employee_button.png"))); // NOI18N
        employee_button.setBorderPainted(false);
        employee_button.setContentAreaFilled(false);
        employee_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        employee_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/employee_button_ro.png"))); // NOI18N
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
            Logger.getLogger(Start_window.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_products_buttonActionPerformed

    private void clients_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clients_buttonActionPerformed
        Clients_window ventana = new Clients_window();
        ventana.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_clients_buttonActionPerformed

    private void employee_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employee_buttonActionPerformed
        Employee_window ventana = new Employee_window();
        ventana.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_employee_buttonActionPerformed

    private void add_user_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_user_buttonActionPerformed
        Add_modify_user_window ventana = new Add_modify_user_window(0, this);
        ventana.setVisible(true);
    }//GEN-LAST:event_add_user_buttonActionPerformed

    private void modify_user_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modify_user_buttonActionPerformed
        Add_modify_user_window ventana = new Add_modify_user_window(1, this);
        ventana.setVisible(true);
    }//GEN-LAST:event_modify_user_buttonActionPerformed

    private void delete_user_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_user_buttonActionPerformed
        Ud_table_window ventana;
        try {
            ventana = new Ud_table_window(3, this, 1);
            ventana.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Start_window.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_delete_user_buttonActionPerformed

    private void help_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_help_buttonActionPerformed
        Clases.PDF.abrirPDF("/Recursos/ManualInicio.pdf");
    }//GEN-LAST:event_help_buttonActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Start_window().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Start_window.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add_user_button;
    private javax.swing.JPanel background;
    private javax.swing.JButton clients_button;
    private javax.swing.JButton configuration_button;
    private javax.swing.JButton delete_user_button;
    private javax.swing.JButton employee_button;
    private javax.swing.JButton help_button;
    private javax.swing.JPanel information_panel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lbl_maturity;
    private javax.swing.JLabel lbl_maturitynext;
    private javax.swing.JLabel lbl_title;
    private javax.swing.JButton logout_button;
    private javax.swing.JTable maturity_table;
    private javax.swing.JButton modify_user_button;
    private javax.swing.JButton new_file_button;
    private javax.swing.JTable nextmaturity_table;
    private javax.swing.JButton products_button;
    private javax.swing.JPanel section_menu;
    private javax.swing.JButton start_button;
    private javax.swing.JLabel time_label;
    private javax.swing.JLabel toc;
    // End of variables declaration//GEN-END:variables
}
