package Vistas.Preventistas;

import Conexion.Conexiones;
import Vistas.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.MaskFormatter;
import javax.swing.text.PlainDocument;

public class Add_employee_window extends javax.swing.JFrame {

    Connection con = Conexiones.Conexion();
    private int band = 0;
    ResultSet rs;
    int cont = 0;
    int filaSeleccionada;
    private java.util.Set<Integer> filasEditadas = new java.util.HashSet<>();
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
    ModeloEditablePorFila employee_tablemod = new ModeloEditablePorFila(new String[]{"Nº", "Nombre", "Apellido", "DNI", "Direccion", "Ingreso"}, 0) {
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
            employee_table.getColumnModel().getColumn(col).setCellEditor(customEditor);
        }
    }

    //Tabla
    public Add_employee_window() {
        initComponents();
        Clases.General_configurations.Icon(this);
        this.setLocationRelativeTo(null);
        Clases.General_configurations.MaxMinWindow(this);
        Clases.General_configurations.table_configuration12(employee_table, employee_tablemod);

        cancel_button.setEnabled(false);
        save_button.setEnabled(false);

        try {
            Clases.Add_employee_window.ShowEmployee(con, employee_tablemod);
            if (employee_table.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "¡No se encontró ningún Preventista!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR");
        }

        Clases.General_configurations.colorconfig(section_menu, lbl_title, jPanel1, jPanel2);

        int rankvalid = Clases.Menu_window.rank();
        if (rankvalid == 2) {
            delete_button.setEnabled(false);
            modify_button.setEnabled(false);
            add_button.setEnabled(false);
        }

        aplicarFiltroTexto(1, 44, 1);
        aplicarFiltroTexto(1, 44, 2);

        aplicarFiltroTexto(0, 10, 3);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JPanel();
        section_menu = new javax.swing.JPanel();
        lbl_title = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        employee_table = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        delete_button = new javax.swing.JButton();
        add_button = new javax.swing.JButton();
        modify_button = new javax.swing.JButton();
        back_button = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        cancel_button = new javax.swing.JButton();
        save_button = new javax.swing.JButton();
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
        lbl_title.setText("Control de preventista");

        employee_table.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(employee_table);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true), "Opciones:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N
        jPanel1.setOpaque(false);

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(back_button, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
                .addComponent(delete_button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(modify_button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(add_button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(back_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(delete_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(modify_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(add_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true), "Acciones:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N
        jPanel2.setOpaque(false);

        cancel_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/cancel_button.png"))); // NOI18N
        cancel_button.setBorderPainted(false);
        cancel_button.setContentAreaFilled(false);
        cancel_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cancel_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/cancel_button_ro.png"))); // NOI18N
        cancel_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_buttonActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cancel_button, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(save_button, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(save_button, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancel_button, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout section_menuLayout = new javax.swing.GroupLayout(section_menu);
        section_menu.setLayout(section_menuLayout);
        section_menuLayout.setHorizontalGroup(
            section_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(section_menuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(section_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, section_menuLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_title, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
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
            Logger.getLogger(Add_employee_window.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Add_employee_window.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_start_buttonActionPerformed

    private void back_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_back_buttonActionPerformed
        Employee_window ventana = new Employee_window();
        ventana.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_back_buttonActionPerformed

    private void add_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_buttonActionPerformed
        cancel_button.setEnabled(true);
        save_button.setEnabled(true);

        delete_button.setEnabled(false);
        modify_button.setEnabled(false);
        back_button.setEnabled(false);

        start_button.setEnabled(false);
        new_file_button.setEnabled(false);
        products_button.setEnabled(false);
        clients_button.setEnabled(false);
        employee_button.setEnabled(false);
        configuration_button.setEnabled(false);
        logout_button.setEnabled(false);

        cont++;
        band = 0;
        Object[] nuevaFila = {"", "", "", "", "", ""};
        employee_tablemod.addRow(nuevaFila);
        // Obtener el índice de la nueva fila
        int nuevaFilaIndex = employee_tablemod.getRowCount() - 1;

        // Hacerla editable
        employee_tablemod.setEditableRow(nuevaFilaIndex);

        // Seleccionar automáticamente
        employee_table.setRowSelectionInterval(nuevaFilaIndex, nuevaFilaIndex);
        employee_table.editCellAt(nuevaFilaIndex, 1); // enfocar en la primera celda editable
        employee_table.requestFocus();
    }//GEN-LAST:event_add_buttonActionPerformed

    private void modify_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modify_buttonActionPerformed
        band = 1;
        cancel_button.setEnabled(true);
        save_button.setEnabled(true);

        delete_button.setEnabled(false);
        add_button.setEnabled(false);
        back_button.setEnabled(false);

        start_button.setEnabled(false);
        new_file_button.setEnabled(false);
        products_button.setEnabled(false);
        clients_button.setEnabled(false);
        employee_button.setEnabled(false);
        configuration_button.setEnabled(false);
        logout_button.setEnabled(false);

        filaSeleccionada = employee_table.getSelectedRow();
        if (filaSeleccionada != -1) {
            employee_tablemod.setEditableRow(filaSeleccionada);
            filasEditadas.add(filaSeleccionada);
        } else {
            JOptionPane.showMessageDialog(null, "¡Seleccione una fila primero!");
            cancel_button.setEnabled(false);
            save_button.setEnabled(false);

            delete_button.setEnabled(true);
            add_button.setEnabled(true);
            back_button.setEnabled(true);

            start_button.setEnabled(true);
            new_file_button.setEnabled(true);
            products_button.setEnabled(true);
            clients_button.setEnabled(true);
            employee_button.setEnabled(true);
            configuration_button.setEnabled(true);
            logout_button.setEnabled(true);
        }
    }//GEN-LAST:event_modify_buttonActionPerformed

    private void cancel_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_buttonActionPerformed
        if (band == 0) {
            for (int x = 0; x < cont; x++) {
                int ultimaFila = employee_tablemod.getRowCount() - 1;

                if (ultimaFila >= 0) {
                    if (employee_table.isEditing()) {
                        employee_table.getCellEditor().stopCellEditing();
                    }
                    Object valor = employee_tablemod.getValueAt(ultimaFila, 0); // columna Cod

                    if (valor == null || valor.toString().isEmpty()) {
                        employee_tablemod.removeRow(ultimaFila);
                        employee_table.clearSelection(); // <-- esta línea es clave para "descongelar"
                        employee_table.repaint();
                    } else {
                    }
                }
            }
        } else if (band == 1) {
            if (employee_table.isEditing()) {
                employee_table.getCellEditor().stopCellEditing();
            }
            employee_table.clearSelection();
            employee_tablemod.bloquearEdicion();

            employee_tablemod.setRowCount(0);
            try {
                Clases.Add_employee_window.ShowEmployee(con, employee_tablemod);
            } catch (SQLException ex) {
                Logger.getLogger(Add_employee_window.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        cancel_button.setEnabled(false);
        save_button.setEnabled(false);

        delete_button.setEnabled(true);
        add_button.setEnabled(true);
        modify_button.setEnabled(true);
        back_button.setEnabled(true);

        start_button.setEnabled(true);
        new_file_button.setEnabled(true);
        products_button.setEnabled(true);
        clients_button.setEnabled(true);
        employee_button.setEnabled(true);
        configuration_button.setEnabled(true);
        logout_button.setEnabled(true);
        cont = 0;
    }//GEN-LAST:event_cancel_buttonActionPerformed

    private void save_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_buttonActionPerformed
        if (band == 0) {
            for (int i = 0; i < employee_table.getRowCount(); i++) {
                Object idObj = employee_table.getValueAt(i, 0);

                // 1. Validar si la fila es nueva (ID vacío) y si está completa
                if (idObj == null || idObj.toString().trim().isEmpty()) {
                    boolean filaCompleta = true;
                    for (int j = 1; j < employee_tablemod.getColumnCount(); j++) {
                        Object valor = employee_tablemod.getValueAt(i, j);
                        if (valor == null || valor.toString().trim().isEmpty()) {
                            filaCompleta = false;
                            break;
                        }
                    }

                    if (!filaCompleta) {
                        JOptionPane.showMessageDialog(null, "Hay filas nuevas con campos vacíos en la fila " + (i + 1) + ". Completá todos los datos antes de guardar.");
                        return;
                    }
                }

                // 2. Validar que el DNI sea numérico
                String dni = employee_table.getValueAt(i, 3).toString();
                try {
                    Double.parseDouble(dni);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "¡Ha ingresado un valor NO numérico en el DNI de la fila " + (i + 1) + "! Por favor, revise.");
                    return;
                }

                String fecha = employee_table.getValueAt(i, 5).toString();

                if (fecha.equals("00-00-0000") || fecha.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "La fecha en la fila " + (i + 1) + " no puede estar vacía.");
                    return;
                }

                if (!Clases.Products_window.esFechaValida(fecha)) {
                    JOptionPane.showMessageDialog(null, "La fecha '" + fecha + "' en la fila " + (i + 1) + " es inválida. Verifique día y mes.");
                    return;
                }

                try {
                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate fechaIngresada = LocalDate.parse(fecha, formato);
                    LocalDate hoy = LocalDate.now();

                    if (fechaIngresada.isAfter(hoy)) {
                        JOptionPane.showMessageDialog(null, "Error en la fila " + (i + 1) + ":\nLa fecha " + fecha + " no puede ser posterior a hoy (" + hoy.format(formato) + ").");
                        return;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error al procesar la fecha en la fila " + (i + 1));
                    return;
                }
            }
            //validacion

            //validacion
            if (employee_table.isEditing()) {
                employee_table.getCellEditor().stopCellEditing();
            }
            //Insert
            for (int i = 0; i < employee_table.getRowCount(); i++) {
                Object idObj = employee_table.getValueAt(i, 0);
                if (idObj == null || idObj.toString().trim().isEmpty()) {
                    String Nombre = employee_table.getValueAt(i, 1).toString();
                    String Apellido = employee_table.getValueAt(i, 2).toString();
                    String DNI = employee_table.getValueAt(i, 3).toString();
                    int borrado = 0;
                    String Direccion = employee_table.getValueAt(i, 4).toString();
                    String Fecha = employee_table.getValueAt(i, 5).toString();
                    try {
                        Clases.Add_employee_window.AddEmployee(con, Nombre, Apellido, borrado, DNI, Direccion, Fecha);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                }
            }
            //Insert

            JOptionPane.showMessageDialog(null, "¡Preventista registrado correctamente!");
            cancel_button.setEnabled(false);
            save_button.setEnabled(false);

            delete_button.setEnabled(true);
            add_button.setEnabled(true);
            modify_button.setEnabled(true);
            back_button.setEnabled(true);

            start_button.setEnabled(true);
            new_file_button.setEnabled(true);
            products_button.setEnabled(true);
            clients_button.setEnabled(true);
            employee_button.setEnabled(true);
            configuration_button.setEnabled(true);
            logout_button.setEnabled(true);

            employee_table.clearSelection();
            employee_tablemod.bloquearEdicion();
            try {
                employee_tablemod.setRowCount(0);
                Clases.Add_employee_window.ShowEmployee(con, employee_tablemod);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }

        } else if (band == 1) { //Editar

            for (int i : filasEditadas) {
                boolean filaCompleta = true;
                for (int j = 1; j < employee_tablemod.getColumnCount(); j++) {
                    Object valor = employee_tablemod.getValueAt(i, j);
                    if (valor == null || valor.toString().trim().isEmpty()) {
                        filaCompleta = false;
                        break;
                    }
                }

                if (!filaCompleta) {
                    JOptionPane.showMessageDialog(null, "Hay filas nuevas con campos vacíos en la fila " + (i + 1) + ". Completá todos los datos antes de guardar.");
                    return;
                }

                // 2. Validar que el DNI sea numérico
                String dni = employee_table.getValueAt(i, 3).toString();
                try {
                    Double.parseDouble(dni);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "¡Ha ingresado un valor NO numérico en el DNI de la fila " + (i + 1) + "! Por favor, revise.");
                    return;
                }

                String fecha = employee_table.getValueAt(i, 5).toString();

                if (fecha.equals("00-00-0000") || fecha.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "La fecha en la fila " + (i + 1) + " no puede estar vacía.");
                    return;
                }

                if (!Clases.Products_window.esFechaValida(fecha)) {
                    JOptionPane.showMessageDialog(null, "La fecha '" + fecha + "' en la fila " + (i + 1) + " es inválida. Verifique día y mes.");
                    return;
                }

                try {
                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate fechaIngresada = LocalDate.parse(fecha, formato);
                    LocalDate hoy = LocalDate.now();

                    if (fechaIngresada.isAfter(hoy)) {
                        JOptionPane.showMessageDialog(null, "Error en la fila " + (i + 1) + ":\nLa fecha " + fecha + " no puede ser posterior a hoy (" + hoy.format(formato) + ").");
                        return;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error al procesar la fecha en la fila " + (i + 1));
                    return;
                }
            }

            filasEditadas.forEach((i) -> {
                int cod = Integer.parseInt(employee_table.getValueAt(i, 0).toString());
                String Nombre = employee_table.getValueAt(i, 1).toString();
                String Apellido = employee_table.getValueAt(i, 2).toString();
                String DNI = employee_table.getValueAt(i, 3).toString();
                int borrado = 0;
                String Direccion = employee_table.getValueAt(i, 4).toString();
                String Fecha = employee_table.getValueAt(i, 5).toString();
                try {
                    Clases.Add_employee_window.UpdateEmployee(con, cod, Nombre, Apellido, borrado, DNI, Direccion, Fecha);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "ERROR" + ex);
                }
            });

            cancel_button.setEnabled(false);
            save_button.setEnabled(false);

            delete_button.setEnabled(true);
            add_button.setEnabled(true);
            modify_button.setEnabled(true);
            back_button.setEnabled(true);

            start_button.setEnabled(true);
            new_file_button.setEnabled(true);
            products_button.setEnabled(true);
            clients_button.setEnabled(true);
            employee_button.setEnabled(true);
            configuration_button.setEnabled(true);
            logout_button.setEnabled(true);

            JOptionPane.showMessageDialog(null, "¡Preventista actualizado correctamente!");
            filasEditadas.clear();
            employee_table.clearSelection();
            employee_tablemod.bloquearEdicion();
            try {
                employee_tablemod.setRowCount(0);
                Clases.Add_employee_window.ShowEmployee(con, employee_tablemod);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }//GEN-LAST:event_save_buttonActionPerformed

    private void delete_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_buttonActionPerformed
        filaSeleccionada = employee_table.getSelectedRow();
        if (filaSeleccionada != -1) {
            int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea Eliminar?",
                    "Confirmar acción",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (opcion == JOptionPane.OK_OPTION) {
                int fila = employee_table.getSelectedRow();
                int cod = Integer.parseInt(employee_table.getValueAt(fila, 0).toString());
                int borrado = 1;
                try {
                    Clases.Add_employee_window.DeleteEmployee(con, cod, borrado);
                    JOptionPane.showMessageDialog(null, "¡Preventista eliminado correctamente!");
                    employee_tablemod.setRowCount(0);
                    Clases.Add_employee_window.ShowEmployee(con, employee_tablemod);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "ERROR" + ex);
                }
            } else if (opcion == JOptionPane.CANCEL_OPTION || opcion == JOptionPane.CLOSED_OPTION) {
                System.out.println("Cancelado");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila primero.");
        }
    }//GEN-LAST:event_delete_buttonActionPerformed

    private void help_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_help_buttonActionPerformed
        Clases.PDF.abrirPDF("/Recursos/ManualABM.pdf");
    }//GEN-LAST:event_help_buttonActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Add_employee_window().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add_button;
    private javax.swing.JButton back_button;
    private javax.swing.JPanel background;
    private javax.swing.JButton cancel_button;
    private javax.swing.JButton clients_button;
    private javax.swing.JButton configuration_button;
    private javax.swing.JButton delete_button;
    private javax.swing.JButton employee_button;
    private javax.swing.JTable employee_table;
    private javax.swing.JButton help_button;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lbl_title;
    private javax.swing.JButton logout_button;
    private javax.swing.JButton modify_button;
    private javax.swing.JButton new_file_button;
    private javax.swing.JButton products_button;
    private javax.swing.JButton save_button;
    private javax.swing.JPanel section_menu;
    private javax.swing.JButton start_button;
    private javax.swing.JLabel toc;
    // End of variables declaration//GEN-END:variables
}
