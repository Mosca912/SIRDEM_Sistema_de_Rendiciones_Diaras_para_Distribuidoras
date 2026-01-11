package Vistas.Preventistas;

import Conexion.Conexiones;
import Vistas.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Sales_window extends javax.swing.JFrame {

    Icon normalcancel = new ImageIcon(getClass().getResource("/Recursos/cancel_button_sales.png"));
    Icon encimacancel = new ImageIcon(getClass().getResource("/Recursos/cancel_button_salesro.png"));

    Icon normal = new ImageIcon(getClass().getResource("/Recursos/modify_button.png"));
    Icon encima = new ImageIcon(getClass().getResource("/Recursos/modify_button_ro.png"));
    static int empId;
    Connection con = Conexiones.Conexion();
    ResultSet rs;
    int cont = 0, crid = 0;
    String prveri, crveri = "Opciones";
    int cantmes, indice = 0, cantsig, verificacionobjetivo = 0;

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

    //Tabla
    ModeloEditablePorFila sales_tablemod = new ModeloEditablePorFila(new String[]{"Semana", "Total", "PDV", "Dias"}, 0) {
        private final int editableRow = -1;
    };
    //Tabla

    public Sales_window(int empId) {
        initComponents();
        Sales_window.empId = empId;
        Clases.General_configurations.Icon(this);
        this.setLocationRelativeTo(null);
        Clases.General_configurations.MaxMinWindow(this);

        add_button.setEnabled(false);
        modify_button.setEnabled(false);

        total_txt.setEnabled(false);
        pdv_txt.setEnabled(false);
        days_txt.setEnabled(false);
        salary_button.setEnabled(false);

        sales_table.setModel(sales_tablemod);

        Clases.General_configurations.table_configurationFile(sales_table);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        sales_table.setDefaultRenderer(Object.class, centerRenderer);

        // Definir el listener
        DocumentListener universalListener = new DocumentListener() {
            private void dispatch() {
                Clases.Sales_window.updateAll(
                        total_txt, pdv_txt, days_txt, // Tus 3 JTextFields
                        lbl_total_general, lbl_pdv, lbl_days, // Bloque 1
                        lbl_total_to, lbl_pdv_to, lbl_days_to, // Bloque 2
                        lbl_total_dt, lbl_pdv_dt, // Bloque 3
                        lbl_total_p, lbl_difference_p, lbl_scope_p// Bloque 4
                );
            }
//

            @Override
            public void insertUpdate(DocumentEvent e) {
                dispatch();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                dispatch();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                dispatch();
            }
        };

// Asignar a los tres campos
        total_txt.getDocument().addDocumentListener(universalListener);
        pdv_txt.getDocument().addDocumentListener(universalListener);
        days_txt.getDocument().addDocumentListener(universalListener);

        Clases.General_configurations.colorconfig5(month_cb, days_txt, pdv_txt, total_txt, lbl_title, lbl_total_general, lbl_pdv, lbl_days, lbl_total_to, lbl_pdv_to, lbl_days_to, lbl_total_dt, lbl_pdv_dt, lbl_total_p, lbl_difference_p, lbl_scope_p, section_menu, option_panel, target_panel, weeks_panel, general_total_panel, target_subtracion_panel, daily_target_panel, proyection_panel, total_gt_panel, pdv_gt_panel, days_gt_panel, total_to_panel, pdv_to_panel, days_to_panel, total_td_panel, pdv_td_panel, total_p_panel, differencel_p_panel, scope_p_panel);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JPanel();
        section_menu = new javax.swing.JPanel();
        lbl_title = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        option_panel = new javax.swing.JPanel();
        back_button = new javax.swing.JButton();
        month_cb = new javax.swing.JComboBox<>();
        target_panel = new javax.swing.JPanel();
        modify_button = new javax.swing.JButton();
        add_button = new javax.swing.JButton();
        days_txt = new javax.swing.JTextField();
        pdv_txt = new javax.swing.JTextField();
        total_txt = new javax.swing.JTextField();
        weeks_panel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        sales_table = new javax.swing.JTable();
        salary_button = new javax.swing.JButton();
        general_total_panel = new javax.swing.JPanel();
        total_gt_panel = new javax.swing.JPanel();
        lbl_total_general = new javax.swing.JLabel();
        pdv_gt_panel = new javax.swing.JPanel();
        lbl_pdv = new javax.swing.JLabel();
        days_gt_panel = new javax.swing.JPanel();
        lbl_days = new javax.swing.JLabel();
        proyection_panel = new javax.swing.JPanel();
        total_p_panel = new javax.swing.JPanel();
        lbl_total_p = new javax.swing.JLabel();
        differencel_p_panel = new javax.swing.JPanel();
        lbl_difference_p = new javax.swing.JLabel();
        scope_p_panel = new javax.swing.JPanel();
        lbl_scope_p = new javax.swing.JLabel();
        daily_target_panel = new javax.swing.JPanel();
        total_td_panel = new javax.swing.JPanel();
        lbl_total_dt = new javax.swing.JLabel();
        pdv_td_panel = new javax.swing.JPanel();
        lbl_pdv_dt = new javax.swing.JLabel();
        target_subtracion_panel = new javax.swing.JPanel();
        total_to_panel = new javax.swing.JPanel();
        lbl_total_to = new javax.swing.JLabel();
        pdv_to_panel = new javax.swing.JPanel();
        lbl_pdv_to = new javax.swing.JLabel();
        days_to_panel = new javax.swing.JPanel();
        lbl_days_to = new javax.swing.JLabel();
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
        lbl_title.setText("VENTAS");

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
                .addComponent(month_cb, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67))
        );
        option_panelLayout.setVerticalGroup(
            option_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(option_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(option_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(month_cb)
                    .addComponent(back_button, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        target_panel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true), "Objetivo:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N
        target_panel.setOpaque(false);

        modify_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/modify_button.png"))); // NOI18N
        modify_button.setToolTipText("Selecciona un preventista para modificarlo");
        modify_button.setBorderPainted(false);
        modify_button.setContentAreaFilled(false);
        modify_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        modify_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/modify_button_ro.png"))); // NOI18N
        modify_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modify_buttonActionPerformed(evt);
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

        days_txt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        days_txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        days_txt.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Días", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N
        days_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                days_txtActionPerformed(evt);
            }
        });

        pdv_txt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        pdv_txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pdv_txt.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "P.D.V", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N
        pdv_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pdv_txtActionPerformed(evt);
            }
        });

        total_txt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        total_txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        total_txt.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Total", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        javax.swing.GroupLayout target_panelLayout = new javax.swing.GroupLayout(target_panel);
        target_panel.setLayout(target_panelLayout);
        target_panelLayout.setHorizontalGroup(
            target_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(target_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(days_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pdv_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(total_txt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(modify_button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(add_button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        target_panelLayout.setVerticalGroup(
            target_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(target_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(target_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(days_txt, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(pdv_txt)
                    .addComponent(total_txt)
                    .addComponent(modify_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(add_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        weeks_panel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true), "Semanas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N
        weeks_panel.setOpaque(false);

        sales_table.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(sales_table);

        salary_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/salary_button.png"))); // NOI18N
        salary_button.setBorderPainted(false);
        salary_button.setContentAreaFilled(false);
        salary_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        salary_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/salary_button_ro.png"))); // NOI18N
        salary_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salary_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout weeks_panelLayout = new javax.swing.GroupLayout(weeks_panel);
        weeks_panel.setLayout(weeks_panelLayout);
        weeks_panelLayout.setHorizontalGroup(
            weeks_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(weeks_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(weeks_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 922, Short.MAX_VALUE)
                    .addGroup(weeks_panelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(salary_button, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        weeks_panelLayout.setVerticalGroup(
            weeks_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(weeks_panelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(salary_button, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        general_total_panel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true), "Total general:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N
        general_total_panel.setOpaque(false);

        total_gt_panel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Total", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        total_gt_panel.setOpaque(false);

        lbl_total_general.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_total_general.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_total_general.setText("0");

        javax.swing.GroupLayout total_gt_panelLayout = new javax.swing.GroupLayout(total_gt_panel);
        total_gt_panel.setLayout(total_gt_panelLayout);
        total_gt_panelLayout.setHorizontalGroup(
            total_gt_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_total_general, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
        );
        total_gt_panelLayout.setVerticalGroup(
            total_gt_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_total_general, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pdv_gt_panel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "P.D.V", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        pdv_gt_panel.setOpaque(false);

        lbl_pdv.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_pdv.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_pdv.setText("0");

        javax.swing.GroupLayout pdv_gt_panelLayout = new javax.swing.GroupLayout(pdv_gt_panel);
        pdv_gt_panel.setLayout(pdv_gt_panelLayout);
        pdv_gt_panelLayout.setHorizontalGroup(
            pdv_gt_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_pdv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pdv_gt_panelLayout.setVerticalGroup(
            pdv_gt_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_pdv, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        days_gt_panel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Días", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        days_gt_panel.setOpaque(false);

        lbl_days.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_days.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_days.setText("0");

        javax.swing.GroupLayout days_gt_panelLayout = new javax.swing.GroupLayout(days_gt_panel);
        days_gt_panel.setLayout(days_gt_panelLayout);
        days_gt_panelLayout.setHorizontalGroup(
            days_gt_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_days, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        days_gt_panelLayout.setVerticalGroup(
            days_gt_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_days, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout general_total_panelLayout = new javax.swing.GroupLayout(general_total_panel);
        general_total_panel.setLayout(general_total_panelLayout);
        general_total_panelLayout.setHorizontalGroup(
            general_total_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(general_total_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(general_total_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(total_gt_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pdv_gt_panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(days_gt_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        general_total_panelLayout.setVerticalGroup(
            general_total_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(general_total_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(total_gt_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(pdv_gt_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(days_gt_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        proyection_panel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true), "Proyección", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N
        proyection_panel.setOpaque(false);

        total_p_panel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Total", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        total_p_panel.setOpaque(false);

        lbl_total_p.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_total_p.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_total_p.setText("0");

        javax.swing.GroupLayout total_p_panelLayout = new javax.swing.GroupLayout(total_p_panel);
        total_p_panel.setLayout(total_p_panelLayout);
        total_p_panelLayout.setHorizontalGroup(
            total_p_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_total_p, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
        );
        total_p_panelLayout.setVerticalGroup(
            total_p_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_total_p, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );

        differencel_p_panel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Diferencia", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        differencel_p_panel.setOpaque(false);

        lbl_difference_p.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_difference_p.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_difference_p.setText("0");

        javax.swing.GroupLayout differencel_p_panelLayout = new javax.swing.GroupLayout(differencel_p_panel);
        differencel_p_panel.setLayout(differencel_p_panelLayout);
        differencel_p_panelLayout.setHorizontalGroup(
            differencel_p_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_difference_p, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        differencel_p_panelLayout.setVerticalGroup(
            differencel_p_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_difference_p, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );

        scope_p_panel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Alcance", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        scope_p_panel.setOpaque(false);

        lbl_scope_p.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_scope_p.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_scope_p.setText("0");

        javax.swing.GroupLayout scope_p_panelLayout = new javax.swing.GroupLayout(scope_p_panel);
        scope_p_panel.setLayout(scope_p_panelLayout);
        scope_p_panelLayout.setHorizontalGroup(
            scope_p_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_scope_p, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        scope_p_panelLayout.setVerticalGroup(
            scope_p_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_scope_p, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout proyection_panelLayout = new javax.swing.GroupLayout(proyection_panel);
        proyection_panel.setLayout(proyection_panelLayout);
        proyection_panelLayout.setHorizontalGroup(
            proyection_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proyection_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(proyection_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(proyection_panelLayout.createSequentialGroup()
                        .addComponent(total_p_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(14, 14, 14))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, proyection_panelLayout.createSequentialGroup()
                        .addGroup(proyection_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(scope_p_panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(differencel_p_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        proyection_panelLayout.setVerticalGroup(
            proyection_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proyection_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(total_p_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(differencel_p_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(scope_p_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        daily_target_panel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true), "Objetivo diario:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N
        daily_target_panel.setOpaque(false);

        total_td_panel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Total", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        total_td_panel.setOpaque(false);

        lbl_total_dt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_total_dt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_total_dt.setText("0");

        javax.swing.GroupLayout total_td_panelLayout = new javax.swing.GroupLayout(total_td_panel);
        total_td_panel.setLayout(total_td_panelLayout);
        total_td_panelLayout.setHorizontalGroup(
            total_td_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_total_dt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        total_td_panelLayout.setVerticalGroup(
            total_td_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_total_dt, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );

        pdv_td_panel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "P.D.V", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        pdv_td_panel.setOpaque(false);

        lbl_pdv_dt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_pdv_dt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_pdv_dt.setText("0");

        javax.swing.GroupLayout pdv_td_panelLayout = new javax.swing.GroupLayout(pdv_td_panel);
        pdv_td_panel.setLayout(pdv_td_panelLayout);
        pdv_td_panelLayout.setHorizontalGroup(
            pdv_td_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_pdv_dt, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
        );
        pdv_td_panelLayout.setVerticalGroup(
            pdv_td_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_pdv_dt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout daily_target_panelLayout = new javax.swing.GroupLayout(daily_target_panel);
        daily_target_panel.setLayout(daily_target_panelLayout);
        daily_target_panelLayout.setHorizontalGroup(
            daily_target_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(daily_target_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(daily_target_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(daily_target_panelLayout.createSequentialGroup()
                        .addComponent(total_td_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(8, 8, 8))
                    .addGroup(daily_target_panelLayout.createSequentialGroup()
                        .addComponent(pdv_td_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        daily_target_panelLayout.setVerticalGroup(
            daily_target_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(daily_target_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(total_td_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82)
                .addComponent(pdv_td_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        target_subtracion_panel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true), "Resta objetivo:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N
        target_subtracion_panel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        target_subtracion_panel.setOpaque(false);

        total_to_panel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Total", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        total_to_panel.setOpaque(false);

        lbl_total_to.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_total_to.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_total_to.setText("0");

        javax.swing.GroupLayout total_to_panelLayout = new javax.swing.GroupLayout(total_to_panel);
        total_to_panel.setLayout(total_to_panelLayout);
        total_to_panelLayout.setHorizontalGroup(
            total_to_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_total_to, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
        );
        total_to_panelLayout.setVerticalGroup(
            total_to_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_total_to, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );

        pdv_to_panel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "P.D.V", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        pdv_to_panel.setOpaque(false);

        lbl_pdv_to.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_pdv_to.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_pdv_to.setText("0");

        javax.swing.GroupLayout pdv_to_panelLayout = new javax.swing.GroupLayout(pdv_to_panel);
        pdv_to_panel.setLayout(pdv_to_panelLayout);
        pdv_to_panelLayout.setHorizontalGroup(
            pdv_to_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_pdv_to, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pdv_to_panelLayout.setVerticalGroup(
            pdv_to_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_pdv_to, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        days_to_panel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Días", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        days_to_panel.setOpaque(false);

        lbl_days_to.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_days_to.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_days_to.setText("0");

        javax.swing.GroupLayout days_to_panelLayout = new javax.swing.GroupLayout(days_to_panel);
        days_to_panel.setLayout(days_to_panelLayout);
        days_to_panelLayout.setHorizontalGroup(
            days_to_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_days_to, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        days_to_panelLayout.setVerticalGroup(
            days_to_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_days_to, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout target_subtracion_panelLayout = new javax.swing.GroupLayout(target_subtracion_panel);
        target_subtracion_panel.setLayout(target_subtracion_panelLayout);
        target_subtracion_panelLayout.setHorizontalGroup(
            target_subtracion_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(target_subtracion_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(target_subtracion_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(total_to_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pdv_to_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(days_to_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        target_subtracion_panelLayout.setVerticalGroup(
            target_subtracion_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(target_subtracion_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(total_to_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pdv_to_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(days_to_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout section_menuLayout = new javax.swing.GroupLayout(section_menu);
        section_menu.setLayout(section_menuLayout);
        section_menuLayout.setHorizontalGroup(
            section_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, section_menuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(section_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, section_menuLayout.createSequentialGroup()
                        .addComponent(option_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(target_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(weeks_panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, section_menuLayout.createSequentialGroup()
                        .addComponent(general_total_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addComponent(target_subtracion_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 11, Short.MAX_VALUE)
                        .addComponent(daily_target_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 11, Short.MAX_VALUE)
                        .addComponent(proyection_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addGroup(section_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(option_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(target_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(weeks_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(section_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(general_total_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(proyection_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(daily_target_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(target_subtracion_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            Logger.getLogger(Sales_window.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Sales_window.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_start_buttonActionPerformed

    private void pdv_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pdv_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pdv_txtActionPerformed

    private void back_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_back_buttonActionPerformed
        Employee_window ventana = new Employee_window();
        ventana.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_back_buttonActionPerformed

    private void days_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_days_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_days_txtActionPerformed

    private void salary_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salary_buttonActionPerformed
        if (total_txt.getText() == null || total_txt.getText().trim().isEmpty() || pdv_txt.getText() == null || pdv_txt.getText().trim().isEmpty() || days_txt.getText() == null || days_txt.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Campos vacio. Por favor, complete!");
            return;
        }

        String totobj = total_txt.getText();
        String pdvobj = pdv_txt.getText();
        String diasobj = days_txt.getText();
        String totgen = lbl_total_general.getText();
        String pdvgen = lbl_pdv.getText();
        String dgen = lbl_days.getText();
        Salary_window ventana;
        try {
            ventana = new Salary_window(0, this, totobj, pdvobj, diasobj, totgen, pdvgen, dgen, empId, indice);
            ventana.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Sales_window.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_salary_buttonActionPerformed

    private void month_cbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_month_cbActionPerformed
        indice = month_cb.getSelectedIndex();
        if (indice != 0) {
            sales_tablemod.setRowCount(0);
            total_txt.setEnabled(true);
            total_txt.setText("");
            salary_button.setEnabled(true);
            pdv_txt.setEnabled(true);
            pdv_txt.setText("");

            days_txt.setEnabled(true);
            days_txt.setText("");
            Clases.Sales_window.ShowTableSales(con, cantsig, sales_tablemod, empId, indice);
            Clases.Sales_window.ShowLabels(con, empId, lbl_total_general, lbl_pdv, lbl_days, indice);
            Clases.Sales_window.LoadTarget(con, empId, days_txt, pdv_txt, total_txt, indice, add_button, modify_button, salary_button);
        } else {
            sales_tablemod.setRowCount(0);
            salary_button.setEnabled(false);
            total_txt.setEnabled(false);
            total_txt.setText("");

            pdv_txt.setEnabled(false);
            pdv_txt.setText("");

            days_txt.setEnabled(false);
            days_txt.setText("");
        }
    }//GEN-LAST:event_month_cbActionPerformed

    private void modify_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modify_buttonActionPerformed

        if (verificacionobjetivo == 0) {
            modify_button.setIcon(normalcancel);
            modify_button.setRolloverIcon(encimacancel);
            add_button.setEnabled(true);
            days_txt.setEnabled(true);
            pdv_txt.setEnabled(true);
            total_txt.setEnabled(true);
            verificacionobjetivo = 1;
        } else if (verificacionobjetivo == 1) {
            modify_button.setIcon(normal);
            modify_button.setRolloverIcon(encima);
            verificacionobjetivo = 0;
            add_button.setEnabled(false);
            days_txt.setEnabled(false);
            pdv_txt.setEnabled(false);
            total_txt.setEnabled(false);
        }
    }//GEN-LAST:event_modify_buttonActionPerformed

    private void add_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_buttonActionPerformed
        if (verificacionobjetivo == 0) {
            String day = days_txt.getText();
            String pdvf = pdv_txt.getText();
            String totalobj = total_txt.getText();

            if (!day.trim().isEmpty() && !pdvf.trim().isEmpty() && !totalobj.trim().isEmpty()) {
                try {
                    Clases.Sales_window.InsertUpdTarget(con, empId, day, pdvf, totalobj, indice, add_button, modify_button, verificacionobjetivo);
                    add_button.setEnabled(false);
                    modify_button.setEnabled(true);
                    days_txt.setEnabled(false);
                    pdv_txt.setEnabled(false);
                    total_txt.setEnabled(false);
                    salary_button.setEnabled(true);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "ERROR AL CARGAR NUEVO OBJETIVO: " + ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "¡HAY CAMPOS VACIOS! Por favor, revise");
            }
        } else {
            String day = days_txt.getText();
            String pdvf = pdv_txt.getText();
            String totalobj = total_txt.getText();

            if (!day.trim().isEmpty() && !pdvf.trim().isEmpty() && !totalobj.trim().isEmpty()) {
                try {
                    Clases.Sales_window.InsertUpdTarget(con, empId, day, pdvf, totalobj, indice, add_button, modify_button, verificacionobjetivo);
                    add_button.setEnabled(false);
                    modify_button.setEnabled(true);
                    days_txt.setEnabled(false);
                    pdv_txt.setEnabled(false);
                    total_txt.setEnabled(false);
                    modify_button.setIcon(normal);
                    modify_button.setRolloverIcon(encima);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "ERROR AL CARGAR NUEVO OBJETIVO: " + ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "¡HAY CAMPOS VACIOS! Por favor, revise");
            }
        }
    }//GEN-LAST:event_add_buttonActionPerformed

    private void help_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_help_buttonActionPerformed
        Clases.PDF.abrirPDF("/Recursos/ManualPreventista.pdf");
    }//GEN-LAST:event_help_buttonActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Sales_window(0).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add_button;
    private javax.swing.JButton back_button;
    private javax.swing.JPanel background;
    private javax.swing.JButton clients_button;
    private javax.swing.JButton configuration_button;
    private javax.swing.JPanel daily_target_panel;
    private javax.swing.JPanel days_gt_panel;
    private javax.swing.JPanel days_to_panel;
    private javax.swing.JTextField days_txt;
    private javax.swing.JPanel differencel_p_panel;
    private javax.swing.JButton employee_button;
    private javax.swing.JPanel general_total_panel;
    private javax.swing.JButton help_button;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lbl_days;
    private javax.swing.JLabel lbl_days_to;
    private javax.swing.JLabel lbl_difference_p;
    private javax.swing.JLabel lbl_pdv;
    private javax.swing.JLabel lbl_pdv_dt;
    private javax.swing.JLabel lbl_pdv_to;
    private javax.swing.JLabel lbl_scope_p;
    private javax.swing.JLabel lbl_title;
    private javax.swing.JLabel lbl_total_dt;
    private javax.swing.JLabel lbl_total_general;
    private javax.swing.JLabel lbl_total_p;
    private javax.swing.JLabel lbl_total_to;
    private javax.swing.JButton logout_button;
    private javax.swing.JButton modify_button;
    private javax.swing.JComboBox<String> month_cb;
    private javax.swing.JButton new_file_button;
    private javax.swing.JPanel option_panel;
    private javax.swing.JPanel pdv_gt_panel;
    private javax.swing.JPanel pdv_td_panel;
    private javax.swing.JPanel pdv_to_panel;
    private javax.swing.JTextField pdv_txt;
    private javax.swing.JButton products_button;
    private javax.swing.JPanel proyection_panel;
    private javax.swing.JButton salary_button;
    private javax.swing.JTable sales_table;
    private javax.swing.JPanel scope_p_panel;
    private javax.swing.JPanel section_menu;
    private javax.swing.JButton start_button;
    private javax.swing.JPanel target_panel;
    private javax.swing.JPanel target_subtracion_panel;
    private javax.swing.JLabel toc;
    private javax.swing.JPanel total_gt_panel;
    private javax.swing.JPanel total_p_panel;
    private javax.swing.JPanel total_td_panel;
    private javax.swing.JPanel total_to_panel;
    private javax.swing.JTextField total_txt;
    private javax.swing.JPanel weeks_panel;
    // End of variables declaration//GEN-END:variables
}
