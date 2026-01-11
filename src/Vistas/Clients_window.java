package Vistas;

import Conexion.Conexiones;
import Vistas.ABM.Cud_window;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class Clients_window extends javax.swing.JFrame {

    JComboBox<Clases.Clients_window.Zone> comboZona = new JComboBox<>();
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
    ModeloEditablePorFila clients_tablemod = new ModeloEditablePorFila(new String[]{"Nº", "Nombre", "Apellido", "Domicilio", "Telefono", "Zona"}, 0) {
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
            clients_table.getColumnModel().getColumn(col).setCellEditor(customEditor);
        }
    }

    //Tabla
    public Clients_window() {
        initComponents();
        Clases.General_configurations.Icon(this);
        this.setLocationRelativeTo(null);
        Clases.General_configurations.MaxMinWindow(this);
        Clases.General_configurations.table_configuration(clients_table, clients_tablemod);

        cancel_button.setEnabled(false);
        save_button.setEnabled(false);

        Clases.Clients_window.ShowZone(con, comboZona);
        clients_table.setModel(clients_tablemod);

        TableColumn columnaZona = clients_table.getColumnModel().getColumn(5);
        columnaZona.setCellEditor(new DefaultCellEditor(comboZona));

        int rankvalid = Clases.Menu_window.rank();
        if (rankvalid == 2) {
            zones_button.setEnabled(false);
            delete_button.setEnabled(false);
            modify_button.setEnabled(false);
            add_button.setEnabled(false);
        }

        try {
            Clases.Clients_window.ShowClients(con, clients_tablemod);
            if (clients_tablemod.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "¡No se encontró ningún Cliente!");
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR AL CARGAR TABLA: " + e);
        }

        Clases.General_configurations.colorconfig(section_menu, lbl_title, options_panel, action);
        aplicarFiltroTexto(1, 44, 1);
        aplicarFiltroTexto(1, 44, 2);
        aplicarFiltroTexto(0, 15, 4);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JPanel();
        section_menu = new javax.swing.JPanel();
        lbl_title = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        clients_table = new javax.swing.JTable();
        options_panel = new javax.swing.JPanel();
        delete_button = new javax.swing.JButton();
        add_button = new javax.swing.JButton();
        modify_button = new javax.swing.JButton();
        zones_button = new javax.swing.JButton();
        action = new javax.swing.JPanel();
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
        setMinimumSize(new java.awt.Dimension(1200, 700));
        setUndecorated(true);
        setResizable(false);

        background.setBackground(new java.awt.Color(0, 0, 102));
        background.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        section_menu.setBackground(new java.awt.Color(255, 255, 255));
        section_menu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lbl_title.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lbl_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_title.setText("Control de clientes");

        clients_table.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(clients_table);

        options_panel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true), "Opciones:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N
        options_panel.setOpaque(false);

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

        zones_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/zone_button.png"))); // NOI18N
        zones_button.setBorderPainted(false);
        zones_button.setContentAreaFilled(false);
        zones_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        zones_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/zone_button_ro.png"))); // NOI18N
        zones_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zones_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout options_panelLayout = new javax.swing.GroupLayout(options_panel);
        options_panel.setLayout(options_panelLayout);
        options_panelLayout.setHorizontalGroup(
            options_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(options_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(zones_button, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
                .addComponent(delete_button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(modify_button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(add_button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        options_panelLayout.setVerticalGroup(
            options_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(options_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(zones_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, options_panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(options_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(delete_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(modify_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(add_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19))
        );

        action.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true), "Acciones:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N
        action.setOpaque(false);

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

        javax.swing.GroupLayout actionLayout = new javax.swing.GroupLayout(action);
        action.setLayout(actionLayout);
        actionLayout.setHorizontalGroup(
            actionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cancel_button, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(save_button, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        actionLayout.setVerticalGroup(
            actionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, actionLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(actionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
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
                        .addComponent(options_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(action, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(options_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(action, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        clients_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/clients_button_selected.png"))); // NOI18N
        clients_button.setBorderPainted(false);
        clients_button.setContentAreaFilled(false);
        clients_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
            Logger.getLogger(Clients_window.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_products_buttonActionPerformed

    private void clients_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clients_buttonActionPerformed

    }//GEN-LAST:event_clients_buttonActionPerformed

    private void employee_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employee_buttonActionPerformed
        Employee_window ventana = new Employee_window();
        ventana.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_employee_buttonActionPerformed

    private void start_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_start_buttonActionPerformed
        Start_window ventana;
        try {
            ventana = new Start_window();
            ventana.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Clients_window.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_start_buttonActionPerformed

    private void zones_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zones_buttonActionPerformed
        Cud_window ventana = new Cud_window(0, this);
        ventana.setVisible(true);
        comboZona.removeAllItems();
        Clases.Clients_window.ShowZone(con, comboZona);
        try {
            clients_tablemod.setRowCount(0);
            Clases.Clients_window.ShowClients(con, clients_tablemod);
            if (clients_tablemod.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "¡No se encontró ningún Cliente!");
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR AL CARGAR TABLA: " + e);
        }
    }//GEN-LAST:event_zones_buttonActionPerformed

    private void cancel_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_buttonActionPerformed
        if (band == 0) {
            for (int x = 0; x < cont; x++) {
                int ultimaFila = clients_tablemod.getRowCount() - 1;

                if (ultimaFila >= 0) {
                    if (clients_table.isEditing()) {
                        clients_table.getCellEditor().stopCellEditing();
                    }
                    Object valor = clients_tablemod.getValueAt(ultimaFila, 0); // columna Cod

                    if (valor == null || valor.toString().isEmpty()) {
                        clients_tablemod.removeRow(ultimaFila);
                        clients_table.clearSelection(); // <-- esta línea es clave para "descongelar"
                        clients_table.repaint();
                    } else {
                    }
                }
            }
        } else if (band == 1) {
            if (clients_table.isEditing()) {
                clients_table.getCellEditor().stopCellEditing();
            }
            clients_table.clearSelection();
            clients_tablemod.bloquearEdicion();
            filasEditadas.clear();
            clients_tablemod.setRowCount(0);
            try {
                Clases.Clients_window.ShowClients(con, clients_tablemod);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "ERROR AL CARGAR TABLA: " + ex);
            }
        }
        cancel_button.setEnabled(false);
        save_button.setEnabled(false);

        delete_button.setEnabled(true);
        add_button.setEnabled(true);
        modify_button.setEnabled(true);
        zones_button.setEnabled(true);

        start_button.setEnabled(true);
        new_file_button.setEnabled(true);
        products_button.setEnabled(true);
        clients_button.setEnabled(true);
        employee_button.setEnabled(true);
        configuration_button.setEnabled(true);
        logout_button.setEnabled(true);
        cont = 0;
    }//GEN-LAST:event_cancel_buttonActionPerformed

    private void add_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_buttonActionPerformed
        int verificationzone = Clases.Clients_window.VeriZone(con);

        if (verificationzone == 1) {
            return;
        }

        cancel_button.setEnabled(true);
        save_button.setEnabled(true);

        delete_button.setEnabled(false);
        modify_button.setEnabled(false);
        zones_button.setEnabled(false);

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
        clients_tablemod.addRow(nuevaFila);
        // Obtener el índice de la nueva fila
        int nuevaFilaIndex = clients_tablemod.getRowCount() - 1;

        // Hacerla editable
        clients_tablemod.setEditableRow(nuevaFilaIndex);

        // Seleccionar automáticamente
        clients_table.setRowSelectionInterval(nuevaFilaIndex, nuevaFilaIndex);
        clients_table.editCellAt(nuevaFilaIndex, 1); // enfocar en la primera celda editable
        clients_table.requestFocus();
    }//GEN-LAST:event_add_buttonActionPerformed

    private void modify_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modify_buttonActionPerformed
        int verificationzone = Clases.Clients_window.VeriZone(con);

        if (verificationzone == 1) {
            return;
        }

        band = 1;
        cancel_button.setEnabled(true);
        save_button.setEnabled(true);

        delete_button.setEnabled(false);
        add_button.setEnabled(false);
        zones_button.setEnabled(false);

        start_button.setEnabled(false);
        new_file_button.setEnabled(false);
        products_button.setEnabled(false);
        clients_button.setEnabled(false);
        employee_button.setEnabled(false);
        configuration_button.setEnabled(false);
        logout_button.setEnabled(false);

        filaSeleccionada = clients_table.getSelectedRow();
        if (filaSeleccionada != -1) {
            clients_tablemod.setEditableRow(filaSeleccionada);
            filasEditadas.add(filaSeleccionada);
        } else {
            JOptionPane.showMessageDialog(null, "¡Seleccione una fila primero!");
            cancel_button.setEnabled(false);
            save_button.setEnabled(false);

            delete_button.setEnabled(true);
            add_button.setEnabled(true);
            zones_button.setEnabled(true);

            start_button.setEnabled(true);
            new_file_button.setEnabled(true);
            products_button.setEnabled(true);
            clients_button.setEnabled(true);
            employee_button.setEnabled(true);
            configuration_button.setEnabled(true);
            logout_button.setEnabled(true);
        }
    }//GEN-LAST:event_modify_buttonActionPerformed

    private void delete_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_buttonActionPerformed
        filaSeleccionada = clients_table.getSelectedRow();
        if (filaSeleccionada != -1) {
            int opcion = JOptionPane.showConfirmDialog(
                    null,
                    "¿Desea Eliminar?",
                    "Confirmar acción",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (opcion == JOptionPane.OK_OPTION) {
                int fila = clients_table.getSelectedRow();
                int cod = Integer.parseInt(clients_table.getValueAt(fila, 0).toString());
                int borrado = 1;
                try {
                    Clases.Clients_window.DeleteClients(con, cod, borrado);
                    JOptionPane.showMessageDialog(null, "¡Cliente eliminado correctamente!");
                    clients_tablemod.setRowCount(0);
                    Clases.Clients_window.ShowClients(con, clients_tablemod);
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

    private void save_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_buttonActionPerformed
        if (band == 0) {
            for (int i = 0; i < clients_table.getRowCount(); i++) {
                Object idObj = clients_table.getValueAt(i, 0);
                if (idObj == null || idObj.toString().trim().isEmpty()) {
                    boolean filaCompleta = true;
                    for (int j = 1; j < clients_tablemod.getColumnCount(); j++) {
                        Object valor = clients_tablemod.getValueAt(i, j);
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

                String tel = clients_tablemod.getValueAt(i, 4).toString();
                try {
                    Double.parseDouble(tel);
                } catch (NumberFormatException e) {
                    i = i + 1;
                    JOptionPane.showMessageDialog(null, "¡Ha ingresado un valor NO numerico en la fila " + i + "! Porfavor, revise");
                    return;
                }
            }
            //validacion

            //validacion
            if (clients_table.isEditing()) {
                clients_table.getCellEditor().stopCellEditing();
            }
            //Insert
            for (int i = 0; i < clients_table.getRowCount(); i++) {
                Object idObj = clients_table.getValueAt(i, 0);
                if (idObj == null || idObj.toString().trim().isEmpty()) {
                    Object cellValue = clients_table.getValueAt(i, 5);
                    int idZona = 0; // Valor por defecto

                    if (cellValue instanceof Clases.Clients_window.Zone) {
                        Clases.Clients_window.Zone zonaSeleccionada = (Clases.Clients_window.Zone) cellValue;
                        idZona = zonaSeleccionada.getId();
                        if (idZona == 0) {
                            JOptionPane.showMessageDialog(null, "Por favor, seleccione una zona válida en la fila " + (i + 1));
                            return;
                        }
                    }
                }
            }

            for (int i = 0; i < clients_table.getRowCount(); i++) {
                Object idObj = clients_table.getValueAt(i, 0);
                if (idObj == null || idObj.toString().trim().isEmpty()) {
                    String Nombre = clients_table.getValueAt(i, 1).toString();
                    String Apellido = clients_table.getValueAt(i, 2).toString();
                    String Domicilio = clients_table.getValueAt(i, 3).toString();
                    int borrado = 0;
                    String Telefono = clients_table.getValueAt(i, 4).toString();
                    Object cellValue = clients_table.getValueAt(i, 5);
                    int idZona = 0; // Valor por defecto

                    if (cellValue instanceof Clases.Clients_window.Zone) {
                        Clases.Clients_window.Zone zonaSeleccionada = (Clases.Clients_window.Zone) cellValue;
                        idZona = zonaSeleccionada.getId();
                    }
                    try {
                        Clases.Clients_window.AddClients(con, Nombre, Apellido, Domicilio, Telefono, idZona, borrado);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                }
            }
            //Insert

            JOptionPane.showMessageDialog(null, "¡Cliente/s registrado correctamente!");
            cancel_button.setEnabled(false);
            save_button.setEnabled(false);

            delete_button.setEnabled(true);
            add_button.setEnabled(true);
            modify_button.setEnabled(true);
            zones_button.setEnabled(true);

            start_button.setEnabled(true);
            new_file_button.setEnabled(true);
            products_button.setEnabled(true);
            clients_button.setEnabled(true);
            employee_button.setEnabled(true);
            configuration_button.setEnabled(true);
            logout_button.setEnabled(true);

            clients_table.clearSelection();
            clients_tablemod.bloquearEdicion();
            try {
                clients_tablemod.setRowCount(0);
                Clases.Clients_window.ShowClients(con, clients_tablemod);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }

        } else if (band == 1) { //Editar

            for (int i : filasEditadas) {
                boolean filaCompleta = true;
                for (int j = 1; j < clients_tablemod.getColumnCount(); j++) {
                    Object valor = clients_tablemod.getValueAt(i, j);
                    if (valor == null || valor.toString().trim().isEmpty()) {
                        filaCompleta = false;
                        break;
                    }
                }

                if (!filaCompleta) {
                    JOptionPane.showMessageDialog(null, "Hay filas nuevas con campos vacíos. Completá todos los datos antes de guardar.");
                    return; // Cancela el proceso de guardado
                }

                String tel = clients_tablemod.getValueAt(i, 4).toString();
                try {
                    Double.parseDouble(tel);
                } catch (NumberFormatException e) {
                    i = i + 1;
                    JOptionPane.showMessageDialog(null, "¡Ha ingresado un valor NO numerico en la fila " + i + "! Porfavor, revise");
                    return;
                }
            }

            for (int i : filasEditadas) {
                Object cellValue = clients_table.getValueAt(i, 5);
                int idZona = 0;
                if (cellValue instanceof Clases.Clients_window.Zone) {
                    Clases.Clients_window.Zone zonaSeleccionada = (Clases.Clients_window.Zone) cellValue;
                    idZona = zonaSeleccionada.getId();
                    if (idZona == 0) {
                        JOptionPane.showMessageDialog(null, "Por favor, seleccione una zona válida en la fila " + (i + 1));
                        return;
                    }
                }
            }

            for (int i : filasEditadas) {
                int cod = Integer.parseInt(clients_table.getValueAt(i, 0).toString());
                String Nombre = clients_table.getValueAt(i, 1).toString();
                String Apellido = clients_table.getValueAt(i, 2).toString();
                String Domicilio = clients_table.getValueAt(i, 3).toString();
                int borrado = 0;
                String Telefono = clients_table.getValueAt(i, 4).toString();
                Object cellValue = clients_table.getValueAt(i, 5);
                int idZona = 0; // Valor por defecto
                if (cellValue instanceof Clases.Clients_window.Zone) {
                    Clases.Clients_window.Zone zonaSeleccionada = (Clases.Clients_window.Zone) cellValue;
                    idZona = zonaSeleccionada.getId();
                }
                try {
                    Clases.Clients_window.UpdateClients(con, cod, Nombre, Apellido, Domicilio, Telefono, idZona, borrado);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "ERROR AL ACTUALIZAR CLIENTE:" + ex);
                }
            }

            cancel_button.setEnabled(false);
            save_button.setEnabled(false);

            delete_button.setEnabled(true);
            add_button.setEnabled(true);
            modify_button.setEnabled(true);
            zones_button.setEnabled(true);

            start_button.setEnabled(true);
            new_file_button.setEnabled(true);
            products_button.setEnabled(true);
            clients_button.setEnabled(true);
            employee_button.setEnabled(true);
            configuration_button.setEnabled(true);
            logout_button.setEnabled(true);

            JOptionPane.showMessageDialog(null, "¡Cliente/s actualizado correctamente!");
            filasEditadas.clear();
            clients_table.clearSelection();
            clients_tablemod.bloquearEdicion();
            try {
                clients_tablemod.setRowCount(0);
                Clases.Clients_window.ShowClients(con, clients_tablemod);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }//GEN-LAST:event_save_buttonActionPerformed

    private void help_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_help_buttonActionPerformed
        Clases.PDF.abrirPDF("/Recursos/ManualABM.pdf");
    }//GEN-LAST:event_help_buttonActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Clients_window().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel action;
    private javax.swing.JButton add_button;
    private javax.swing.JPanel background;
    private javax.swing.JButton cancel_button;
    private javax.swing.JButton clients_button;
    private javax.swing.JTable clients_table;
    private javax.swing.JButton configuration_button;
    private javax.swing.JButton delete_button;
    private javax.swing.JButton employee_button;
    private javax.swing.JButton help_button;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lbl_title;
    private javax.swing.JButton logout_button;
    private javax.swing.JButton modify_button;
    private javax.swing.JButton new_file_button;
    private javax.swing.JPanel options_panel;
    private javax.swing.JButton products_button;
    private javax.swing.JButton save_button;
    private javax.swing.JPanel section_menu;
    private javax.swing.JButton start_button;
    private javax.swing.JLabel toc;
    private javax.swing.JButton zones_button;
    // End of variables declaration//GEN-END:variables
}
