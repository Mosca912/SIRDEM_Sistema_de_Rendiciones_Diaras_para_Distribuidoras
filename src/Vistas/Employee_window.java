package Vistas;

import Vistas.Preventistas.*;
import Clases.Employee_window.*;
import Conexion.Conexiones;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Employee_window extends javax.swing.JFrame {

    static int empId;
    static String empName;
    Connection con = Conexiones.Conexion();

    public Employee_window() {
        initComponents();
        Clases.General_configurations.Icon(this);
        this.setLocationRelativeTo(null);
        Clases.General_configurations.MaxMinWindow(this);
        String textoLargo = "<html>Seleccione/Escriba el preventista que usted desee para poder ingresar a los botones que puede observar en la parte izquierda</html>";
        description_lbl.setText(textoLargo);
        Clases.Employee_window.set_Textlbl(lbl_addemployee, lbl_trusted, lbl_sales, lbl_bills, lbl_returns, lbl_differences, lbl_transfers);
        Clases.Employee_window.Employee_cb(con, employee_cb);
        empId = Clases.Employee_window.getIdEmployee();
        empName = Clases.Employee_window.getEmpName();

        if (empId == 0) {
            trusted_button.setEnabled(false);
            sales_button.setEnabled(false);
            bills_button.setEnabled(false);
            returns_button.setEnabled(false);
            differences_button.setEnabled(false);
            transfers_button.setEnabled(false);
        } else {
            int index = Clases.Employee_window.getContEmployee();
            employee_cb.setSelectedIndex(index);
        }

        Clases.General_configurations.colorconfig5(titlepr_lbl, employee_cb, section_menu);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JPanel();
        section_menu = new javax.swing.JPanel();
        titlepr_lbl = new javax.swing.JLabel();
        section_info = new javax.swing.JPanel();
        title_lbl = new javax.swing.JLabel();
        description_lbl = new javax.swing.JLabel();
        employee_cb = new javax.swing.JComboBox<>();
        section_button = new javax.swing.JPanel();
        add_employee = new javax.swing.JButton();
        trusted_button = new javax.swing.JButton();
        sales_button = new javax.swing.JButton();
        transfers_button = new javax.swing.JButton();
        differences_button = new javax.swing.JButton();
        returns_button = new javax.swing.JButton();
        bills_button = new javax.swing.JButton();
        lbl_transfers = new javax.swing.JLabel();
        lbl_differences = new javax.swing.JLabel();
        lbl_addemployee = new javax.swing.JLabel();
        lbl_trusted = new javax.swing.JLabel();
        lbl_sales = new javax.swing.JLabel();
        lbl_bills = new javax.swing.JLabel();
        lbl_returns = new javax.swing.JLabel();
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

        titlepr_lbl.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        titlepr_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titlepr_lbl.setText("Menú de Preventista");

        section_info.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        title_lbl.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        title_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title_lbl.setText("Selección del Preventista");

        description_lbl.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        employee_cb.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        employee_cb.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Preventista:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 24))); // NOI18N
        employee_cb.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        employee_cb.setOpaque(false);
        employee_cb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employee_cbActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout section_infoLayout = new javax.swing.GroupLayout(section_info);
        section_info.setLayout(section_infoLayout);
        section_infoLayout.setHorizontalGroup(
            section_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(section_infoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(section_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, section_infoLayout.createSequentialGroup()
                        .addComponent(title_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, section_infoLayout.createSequentialGroup()
                        .addGap(0, 10, Short.MAX_VALUE)
                        .addGroup(section_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, section_infoLayout.createSequentialGroup()
                                .addComponent(description_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, section_infoLayout.createSequentialGroup()
                                .addComponent(employee_cb, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34))))))
        );
        section_infoLayout.setVerticalGroup(
            section_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(section_infoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(title_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(description_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(employee_cb, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        section_button.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        add_employee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/add_employee_button.png"))); // NOI18N
        add_employee.setBorderPainted(false);
        add_employee.setContentAreaFilled(false);
        add_employee.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add_employee.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/add_employee_button_ro.png"))); // NOI18N
        add_employee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_employeeActionPerformed(evt);
            }
        });

        trusted_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/trusted_button.png"))); // NOI18N
        trusted_button.setBorderPainted(false);
        trusted_button.setContentAreaFilled(false);
        trusted_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        trusted_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/trusted_button_ro.png"))); // NOI18N
        trusted_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trusted_buttonActionPerformed(evt);
            }
        });

        sales_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/sales_button.png"))); // NOI18N
        sales_button.setBorderPainted(false);
        sales_button.setContentAreaFilled(false);
        sales_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sales_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/sales_button_ro.png"))); // NOI18N
        sales_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sales_buttonActionPerformed(evt);
            }
        });

        transfers_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/transfers_button.png"))); // NOI18N
        transfers_button.setBorderPainted(false);
        transfers_button.setContentAreaFilled(false);
        transfers_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        transfers_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/transfers_button_ro.png"))); // NOI18N
        transfers_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transfers_buttonActionPerformed(evt);
            }
        });

        differences_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/differences_button.png"))); // NOI18N
        differences_button.setBorderPainted(false);
        differences_button.setContentAreaFilled(false);
        differences_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        differences_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/differences_button_ro.png"))); // NOI18N
        differences_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                differences_buttonActionPerformed(evt);
            }
        });

        returns_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/returns_button.png"))); // NOI18N
        returns_button.setBorderPainted(false);
        returns_button.setContentAreaFilled(false);
        returns_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        returns_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/returns_button_ro.png"))); // NOI18N
        returns_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returns_buttonActionPerformed(evt);
            }
        });

        bills_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/bills_button.png"))); // NOI18N
        bills_button.setBorderPainted(false);
        bills_button.setContentAreaFilled(false);
        bills_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bills_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/bills_button_ro.png"))); // NOI18N
        bills_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bills_buttonActionPerformed(evt);
            }
        });

        lbl_transfers.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_transfers.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lbl_differences.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_differences.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lbl_addemployee.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_addemployee.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lbl_trusted.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_trusted.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lbl_sales.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_sales.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lbl_bills.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_bills.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lbl_returns.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_returns.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout section_buttonLayout = new javax.swing.GroupLayout(section_button);
        section_button.setLayout(section_buttonLayout);
        section_buttonLayout.setHorizontalGroup(
            section_buttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(section_buttonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(section_buttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(section_buttonLayout.createSequentialGroup()
                        .addComponent(transfers_button, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_transfers, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE))
                    .addGroup(section_buttonLayout.createSequentialGroup()
                        .addComponent(add_employee, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_addemployee, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(section_buttonLayout.createSequentialGroup()
                        .addComponent(trusted_button, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_trusted, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(section_buttonLayout.createSequentialGroup()
                        .addComponent(sales_button, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_sales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(section_buttonLayout.createSequentialGroup()
                        .addComponent(bills_button, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_bills, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(section_buttonLayout.createSequentialGroup()
                        .addGroup(section_buttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(returns_button, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(differences_button, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(section_buttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_differences, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_returns, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        section_buttonLayout.setVerticalGroup(
            section_buttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(section_buttonLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(section_buttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_addemployee, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(add_employee, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(section_buttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_trusted, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(trusted_button, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(section_buttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_sales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sales_button, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(section_buttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_bills, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bills_button, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(section_buttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(returns_button, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_returns, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(section_buttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_differences, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(differences_button, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(section_buttonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_transfers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(transfers_button, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout section_menuLayout = new javax.swing.GroupLayout(section_menu);
        section_menu.setLayout(section_menuLayout);
        section_menuLayout.setHorizontalGroup(
            section_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(section_menuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(section_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titlepr_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(section_menuLayout.createSequentialGroup()
                        .addComponent(section_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(section_info, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        section_menuLayout.setVerticalGroup(
            section_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(section_menuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titlepr_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(section_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(section_info, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(section_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            Logger.getLogger(Employee_window.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Employee_window.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_start_buttonActionPerformed

    private void trusted_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trusted_buttonActionPerformed
        Trusted_window ventana;
        try {
            ventana = new Trusted_window(empId, empName);
            ventana.setVisible(true);
            this.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(Employee_window.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_trusted_buttonActionPerformed

    private void add_employeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_employeeActionPerformed
        Add_employee_window ventana = new Add_employee_window();
        ventana.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_add_employeeActionPerformed

    private void transfers_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transfers_buttonActionPerformed
        Transfers_window ventana;
        try {
            ventana = new Transfers_window(empId, empName);
            ventana.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Employee_window.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_transfers_buttonActionPerformed

    private void returns_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returns_buttonActionPerformed
        Returns_window ventana;
        try {
            ventana = new Returns_window(empId);
            ventana.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Employee_window.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_returns_buttonActionPerformed

    private void differences_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_differences_buttonActionPerformed
        Differences_window ventana;
        try {
            ventana = new Differences_window(empId);
            ventana.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Employee_window.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_differences_buttonActionPerformed

    private void bills_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bills_buttonActionPerformed
        Bills_window ventana;
        try {
            ventana = new Bills_window(empId);
            ventana.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Employee_window.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_bills_buttonActionPerformed

    private void sales_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sales_buttonActionPerformed
        Sales_window ventana = new Sales_window(empId);
        ventana.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_sales_buttonActionPerformed

    private void employee_cbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employee_cbActionPerformed
        Object value = employee_cb.getSelectedItem();
        if (value instanceof Employee) {
            Employee emp = (Employee) value;
            empId = emp.getId();
            int empcont = emp.getCont();
            empName = emp.getNombre();
            Clases.Employee_window.saveemployee(empId, empName, empcont);
            if (empId != 0) {
                trusted_button.setEnabled(true);
                sales_button.setEnabled(true);
                int rankvalid = Clases.Menu_window.rank();
                if (rankvalid == 2) {
                    sales_button.setEnabled(false);
                } else {
                    sales_button.setEnabled(true);
                }
                bills_button.setEnabled(true);
                returns_button.setEnabled(true);
                differences_button.setEnabled(true);
                transfers_button.setEnabled(true);
            } else {
                trusted_button.setEnabled(false);
                sales_button.setEnabled(false);
                bills_button.setEnabled(false);
                returns_button.setEnabled(false);
                differences_button.setEnabled(false);
                transfers_button.setEnabled(false);
            }
        }
    }//GEN-LAST:event_employee_cbActionPerformed

    private void help_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_help_buttonActionPerformed
        Clases.PDF.abrirPDF("/Recursos/ManualPreventista.pdf");
    }//GEN-LAST:event_help_buttonActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Employee_window().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add_employee;
    private javax.swing.JPanel background;
    private javax.swing.JButton bills_button;
    private javax.swing.JButton clients_button;
    private javax.swing.JButton configuration_button;
    private javax.swing.JLabel description_lbl;
    private javax.swing.JButton differences_button;
    private javax.swing.JButton employee_button;
    private javax.swing.JComboBox<Employee> employee_cb;
    private javax.swing.JButton help_button;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lbl_addemployee;
    private javax.swing.JLabel lbl_bills;
    private javax.swing.JLabel lbl_differences;
    private javax.swing.JLabel lbl_returns;
    private javax.swing.JLabel lbl_sales;
    private javax.swing.JLabel lbl_transfers;
    private javax.swing.JLabel lbl_trusted;
    private javax.swing.JButton logout_button;
    private javax.swing.JButton new_file_button;
    private javax.swing.JButton products_button;
    private javax.swing.JButton returns_button;
    private javax.swing.JButton sales_button;
    private javax.swing.JPanel section_button;
    private javax.swing.JPanel section_info;
    private javax.swing.JPanel section_menu;
    private javax.swing.JButton start_button;
    private javax.swing.JLabel title_lbl;
    private javax.swing.JLabel titlepr_lbl;
    private javax.swing.JLabel toc;
    private javax.swing.JButton transfers_button;
    private javax.swing.JButton trusted_button;
    // End of variables declaration//GEN-END:variables
}
