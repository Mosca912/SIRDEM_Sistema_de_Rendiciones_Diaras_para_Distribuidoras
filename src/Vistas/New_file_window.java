package Vistas;

import Clases.Clients_window.Zone;
import Clases.Employee_window.Employee;
import Clases.New_file_window.Cliente;
import Clases.New_file_window.Producto;
import Conexion.Conexiones;
import Vistas.ABM.File_check_window;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class New_file_window extends javax.swing.JFrame {

    static int empId, zoneId;
    static String empName, zoneName;
    int cont = 0, x = 0, contcan = 0, pv = 0;
    Connection con = Conexiones.Conexion();
    Date hoy = new Date();
    Double fiado = 0.0, transferencia = 0.0, devolucion = 0.0, productodevuelto = 0.0;
    JComboBox<String> comboCod = new JComboBox<>();
    JComboBox<Cliente> comboCliente = new JComboBox<>();
    JComboBox<Producto> comboDes = new JComboBox<>();

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

    //Metodos para editar
    public class ModeloEditablePorFila extends DefaultTableModel {

        private int editableRow = -1;

        public ModeloEditablePorFila(Object[] columnNames, int rowCount) {
            super(columnNames, rowCount);
        }

        @Override
        public boolean isCellEditable(int row, int column) {

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
            newfile_table.getColumnModel().getColumn(col).setCellEditor(customEditor);
        }
    }

    //Tabla
    ModeloEditablePorFila trusted_table = new ModeloEditablePorFila(new String[]{"Cliente", "Saldo"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return true; // Sin condiciones, siempre permite editar
        }
    };

    ModeloEditablePorFila transfers_table = new ModeloEditablePorFila(new String[]{"Cliente", "Saldo", "Estado"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return true; // Sin condiciones, siempre permite editar
        }
    };

    ModeloEditablePorFila returns_table = new ModeloEditablePorFila(new String[]{"Codigo", "Cliente", "Saldo"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            // Ignoramos "cod == null" y "editableRow". Solo bloqueamos la col 1.
            return column != 1;
        }
    };

    ModeloEditablePorFila prodret_table = new ModeloEditablePorFila(
            new String[]{"Cantidad", "Descripcion", "Precio Unitario", "Total"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            // Bloqueamos Precio y Total, permitimos Cantidad y Descripción SIEMPRE.
            return (column == 0 || column == 1 || column == 2);
        }
    };
    //Tabla

    // Método que centraliza la verificación
    private void verificarCampos() {
        String tot = total_txt.getText().trim();
        String p = pdv_txt.getText().trim();

        boolean fechaOk = calender_date.getDate() != null;
        boolean camposOk = !tot.isEmpty() && !p.isEmpty();

        boolean ok = camposOk && fechaOk;

        minus_button.setEnabled(ok);
        add_button.setEnabled(ok);
        next_button.setEnabled(ok);
        newfile_table.setEnabled(ok);
        if (x != 0) {
            back_button.setEnabled(ok);
        }
    }

    private void agregarVerificacion() {
        DocumentListener listener = new DocumentListener() {
            private void changed() {
                verificarCampos();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                changed();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changed();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changed();
            }
        };

        // Se lo agrego a los dos JTextFields
        total_txt.getDocument().addDocumentListener(listener);
        pdv_txt.getDocument().addDocumentListener(listener);

// Para el JDateChooser, accedemos al editor de texto interno:
        javax.swing.JTextField editor = (javax.swing.JTextField) calender_date.getDateEditor().getUiComponent();
        editor.getDocument().addDocumentListener(listener);
    }

    private void setModelo(JTable tabla, TableModel modelo, int nuevoX) {
        x = nuevoX;
        tabla.setModel(modelo);

        tabla.getModel().addTableModelListener(e -> {
            if (x == 3) {
                int fila = e.getFirstRow();
                int columna = e.getColumn();

                // Evitar index -1 o cambios en otras columnas
                if (fila >= 0 && (columna == 0 || columna == 2)) { // col 0 = Cantidad, col 2 = Precio
                    try {
                        double cantidad = Double.parseDouble(tabla.getValueAt(fila, 0).toString());
                        double precio = Double.parseDouble(tabla.getValueAt(fila, 2).toString());
                        double total = cantidad * precio;
                        tabla.setValueAt(total, fila, 3);
                    } catch (NumberFormatException ex) {
                        tabla.setValueAt("", fila, 3);
                    }
                }
            }
        });
    }

    private void refrescarComboCliente(int columnaIndex) {
        // 1. Limpiar la selección previa
        comboCliente.setSelectedIndex(0);

        // 2. Crear un NUEVO editor para evitar que arrastre estados de la tabla anterior
        DefaultCellEditor nuevoEditor = new DefaultCellEditor(comboCliente);

        // 3. Asignarlo a la columna de la tabla actual
        TableColumn columna = newfile_table.getColumnModel().getColumn(columnaIndex);
        columna.setCellEditor(nuevoEditor);
    }

    public New_file_window() {
        initComponents();
        agregarVerificacion();
        newfile_table.setModel(trusted_table);
        aplicarFiltroTexto(0, 9, 1);
        Clases.General_configurations.Icon(this);
        this.setLocationRelativeTo(null);
        Clases.General_configurations.MaxMinWindow(this);
        Clases.Employee_window.Employee_cb(con, employee_cb);
        Clases.Clients_window.ShowZone(con, zone_cb);

        calender_date.getJCalendar().setMaxSelectableDate(hoy);
        calender_date.setDate(hoy);

        zone_cb.setEnabled(false);
        calender_date.setEnabled(false);
        total_txt.setEnabled(false);
        pdv_txt.setEnabled(false);
        back_button.setEnabled(false);
        minus_button.setEnabled(false);
        add_button.setEnabled(false);
        next_button.setEnabled(false);
        newfile_table.setEnabled(false);

        Clases.New_file_window.Combo_clients(con, comboCliente);
        Clases.New_file_window.Combo_code(con, comboCod);
        Clases.New_file_window.Combo_products(con, comboDes);

        TableColumn columnaCliente = newfile_table.getColumnModel().getColumn(0);
        columnaCliente.setCellEditor(new DefaultCellEditor(comboCliente));

        //Tabla devoluciones
        comboCod.addActionListener(e -> {
            int filaSeleccionada = newfile_table.getSelectedRow();
            if (filaSeleccionada != -1) {
                Object valor = newfile_table.getValueAt(filaSeleccionada, 0);
                if (valor != null) {
                    String cod = valor.toString().trim();
                    if (!cod.isEmpty()) {
                        String nya = Clases.New_file_window.ClientAut(con, cod);
                        newfile_table.setValueAt(nya, filaSeleccionada, 1);
                    }
                }
            }
        });
        //Tabla devoluciones

        //Tabla ProdcutoDevuelto
        comboDes.addActionListener(e -> {
            int filaSeleccionada = newfile_table.getSelectedRow();
            if (filaSeleccionada != -1) {
                Object valorCantidad = newfile_table.getValueAt(filaSeleccionada, 0); // cantidad
                Object valorCodigo = newfile_table.getValueAt(filaSeleccionada, 1);   // código

                // Validar cantidad
                if (valorCantidad == null || valorCantidad.toString().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese primero la cantidad");
                    return; // salir del listener
                }

                // Validar que sea número
                double cantidad;
                try {
                    cantidad = Double.parseDouble(valorCantidad.toString().trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "La cantidad debe ser un número válido");
                    return;
                }

                // Validar código
                if (valorCodigo != null && !valorCodigo.toString().trim().isEmpty()) {
                    String cod = valorCodigo.toString().trim();
                    String pr = Clases.New_file_window.DescAut(con, cod);
                    newfile_table.setValueAt(pr, filaSeleccionada, 2);

                    try {
                        double pr2 = Double.parseDouble(pr);
                        double totalpd = pr2 * cantidad;
                        newfile_table.setValueAt(totalpd, filaSeleccionada, 3);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "El precio no es numérico");
                    }
                }
            }
        });
        //Tabla ProdcutoDevuelto
        Clases.General_configurations.table_configurationFile(newfile_table);
        Clases.General_configurations.colorconfig5(jLabel5, lbl_title, employee_cb, zone_cb, total_txt, pdv_txt, section_menu, calender_date);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JPanel();
        section_menu = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        newfile_table = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        lbl_trusted = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lbl_transfers = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lbl_returns = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        lbl_prodret = new javax.swing.JLabel();
        back_button = new javax.swing.JButton();
        next_button = new javax.swing.JButton();
        minus_button = new javax.swing.JButton();
        add_button = new javax.swing.JButton();
        lbl_title = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        calender_date = new com.toedter.calendar.JDateChooser();
        total_txt = new javax.swing.JTextField();
        pdv_txt = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        employee_cb = new javax.swing.JComboBox<>();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        zone_cb = new javax.swing.JComboBox<>();
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
        setPreferredSize(new java.awt.Dimension(1200, 700));
        setResizable(false);

        background.setBackground(new java.awt.Color(0, 0, 102));
        background.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        section_menu.setBackground(new java.awt.Color(255, 255, 255));
        section_menu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        newfile_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(newfile_table);

        jPanel1.setBackground(new java.awt.Color(51, 51, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true), "Fiados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        lbl_trusted.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbl_trusted.setForeground(new java.awt.Color(255, 255, 255));
        lbl_trusted.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_trusted.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_trusted, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_trusted, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(51, 51, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true), "Transferencias", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        lbl_transfers.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbl_transfers.setForeground(new java.awt.Color(255, 255, 255));
        lbl_transfers.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_transfers.setText("0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_transfers, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_transfers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(51, 51, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true), "Devoluciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        lbl_returns.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbl_returns.setForeground(new java.awt.Color(255, 255, 255));
        lbl_returns.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_returns.setText("0");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_returns, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_returns, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(51, 51, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true), "Prod. Devueltos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        lbl_prodret.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbl_prodret.setForeground(new java.awt.Color(255, 255, 255));
        lbl_prodret.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_prodret.setText("0");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_prodret, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_prodret, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

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

        next_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/next_button.png"))); // NOI18N
        next_button.setBorderPainted(false);
        next_button.setContentAreaFilled(false);
        next_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        next_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/next_button_ro.png"))); // NOI18N
        next_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                next_buttonActionPerformed(evt);
            }
        });

        minus_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/delete_button.png"))); // NOI18N
        minus_button.setBorderPainted(false);
        minus_button.setContentAreaFilled(false);
        minus_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        minus_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/delete_button_ro.png"))); // NOI18N
        minus_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minus_buttonActionPerformed(evt);
            }
        });

        add_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/add_button.png"))); // NOI18N
        add_button.setBorderPainted(false);
        add_button.setContentAreaFilled(false);
        add_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/add_button_ro.png"))); // NOI18N
        add_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_buttonActionPerformed(evt);
            }
        });

        lbl_title.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_title.setText("Tabla: Fiados");

        calender_date.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fecha:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 20))); // NOI18N
        calender_date.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        calender_date.setOpaque(false);

        total_txt.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        total_txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        total_txt.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Total:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 20))); // NOI18N
        total_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                total_txtKeyTyped(evt);
            }
        });

        pdv_txt.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        pdv_txt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pdv_txt.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Puntos de venta:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 20))); // NOI18N
        pdv_txt.setOpaque(false);
        pdv_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                pdv_txtKeyTyped(evt);
            }
        });

        employee_cb.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        employee_cb.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Preventista", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 20))); // NOI18N
        employee_cb.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        employee_cb.setOpaque(false);
        employee_cb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employee_cbActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Registrar nueva ficha");

        zone_cb.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        zone_cb.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Zona", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 20))); // NOI18N
        zone_cb.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        zone_cb.setOpaque(false);
        zone_cb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zone_cbActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout section_menuLayout = new javax.swing.GroupLayout(section_menu);
        section_menu.setLayout(section_menuLayout);
        section_menuLayout.setHorizontalGroup(
            section_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(section_menuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(section_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator5)
                    .addComponent(lbl_title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(section_menuLayout.createSequentialGroup()
                        .addComponent(back_button, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(minus_button, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(add_button, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(next_button, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator4)
                    .addGroup(section_menuLayout.createSequentialGroup()
                        .addComponent(calender_date, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(total_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pdv_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator6)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, section_menuLayout.createSequentialGroup()
                        .addComponent(employee_cb, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(zone_cb, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        section_menuLayout.setVerticalGroup(
            section_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(section_menuLayout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(section_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(employee_cb)
                    .addComponent(zone_cb))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(section_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(calender_date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(total_txt, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                    .addComponent(pdv_txt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_title, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(section_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(next_button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(section_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(add_button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(minus_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(back_button, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        new_file_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/new_file_button_selected.png"))); // NOI18N
        new_file_button.setBorderPainted(false);
        new_file_button.setContentAreaFilled(false);
        new_file_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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

    }//GEN-LAST:event_new_file_buttonActionPerformed

    private void products_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_products_buttonActionPerformed
        Products_window ventana;
        try {
            ventana = new Products_window();
            ventana.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(New_file_window.class.getName()).log(Level.SEVERE, null, ex);
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

    private void start_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_start_buttonActionPerformed
        Start_window ventana;
        try {
            ventana = new Start_window();
            ventana.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(New_file_window.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_start_buttonActionPerformed

    private void next_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_next_buttonActionPerformed
        switch (x) {
            case 0:
                fiado = 0.0;
                for (int i = 0; i < newfile_table.getRowCount(); i++) {
                    Object idObj = newfile_table.getValueAt(i, 0);
                    // Si es una fila nueva (sin código), la validamos
                    if (idObj == null || idObj.toString().trim().isEmpty()) {
                        boolean filaCompleta = true;
                        // Recorremos todas las columnas
                        for (int j = 0; j < trusted_table.getColumnCount(); j++) {
                            Object valor = trusted_table.getValueAt(i, j);
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
                    String fi = newfile_table.getValueAt(i, 1).toString();
                    try {
                        double fi2 = Double.parseDouble(fi);
                        fiado = fiado + fi2;
                    } catch (NumberFormatException e) {
                        i = i + 1;
                        JOptionPane.showMessageDialog(null, "¡Ha ingresado un valor NO numerico en la fila " + i + "! Porfavor, revise");
                        fiado = 0.0;
                        return;
                    }
                }
                //validacion

                x++;
                lbl_trusted.setText("" + fiado);
                newfile_table.setModel(transfers_table);
                aplicarFiltroTexto(0, 9, 1); // Saldo: Números (tipo 0, col 1)
                lbl_title.setText("Tabla: Transferencia");

                refrescarComboCliente(0);
                JComboBox<String> comboEstado = new JComboBox<>();

                comboEstado.addItem("Aprobado");
                comboEstado.addItem("Pendiente");

                TableColumn columnaEstado = newfile_table.getColumnModel().getColumn(2);
                columnaEstado.setCellEditor(new DefaultCellEditor(comboEstado));
                back_button.setEnabled(true);
                break;

            case 1:
                transferencia = 0.0;
                for (int i = 0; i < newfile_table.getRowCount(); i++) {
                    Object idObj = newfile_table.getValueAt(i, 0);
                    // Si es una fila nueva (sin código), la validamos
                    if (idObj == null || idObj.toString().trim().isEmpty()) {
                        boolean filaCompleta = true;
                        // Recorremos todas las columnas
                        for (int j = 0; j < transfers_table.getColumnCount(); j++) {
                            Object valor = transfers_table.getValueAt(i, j);
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
                    String tr = newfile_table.getValueAt(i, 1).toString();
                    try {
                        double tr2 = Double.parseDouble(tr);
                        transferencia = transferencia + tr2;
                    } catch (NumberFormatException e) {
                        i = i + 1;
                        JOptionPane.showMessageDialog(null, "¡Ha ingresado un valor NO numerico en la fila " + i + "! Porfavor, revise");
                        transferencia = 0.0;
                        return;
                    }
                }
                //validacion
                x++;
                lbl_transfers.setText("" + transferencia);
                newfile_table.setModel(returns_table);
                aplicarFiltroTexto(0, 9, 2);
                lbl_title.setText("Tabla: Devolución");

                TableColumn columnaCod = newfile_table.getColumnModel().getColumn(0);
                columnaCod.setCellEditor(new DefaultCellEditor(comboCod));

                break;
            case 2:
                devolucion = 0.0;
                pv = 0;
                for (int i = 0; i < newfile_table.getRowCount(); i++) {
                    pv++;
                    Object idObj = newfile_table.getValueAt(i, 0);
                    // Si es una fila nueva (sin código), la validamos
                    if (idObj == null || idObj.toString().trim().isEmpty()) {
                        boolean filaCompleta = true;
                        // Recorremos todas las columnas
                        for (int j = 0; j < returns_table.getColumnCount(); j++) {
                            Object valor = returns_table.getValueAt(i, j);
                            if (valor == null || valor.toString().trim().isEmpty()) {
                                filaCompleta = false;
                                break;
                            }
                        }

                        if (!filaCompleta) {
                            JOptionPane.showMessageDialog(null, "Hay filas nuevas con campos vacíos. Completá todos los datos antes de guardar.");
                            pv = 0;
                            return; // Cancela el proceso de guardado
                        }
                    }

                    // Obtener los datos actualizados desde la tabla
                    String de = newfile_table.getValueAt(i, 2).toString();
                    try {
                        double de2 = Double.parseDouble(de);
                        devolucion = devolucion + de2;
                    } catch (NumberFormatException e) {
                        i = i + 1;
                        JOptionPane.showMessageDialog(null, "¡Ha ingresado un valor NO numerico en la fila " + i + "! Porfavor, revise");
                        devolucion = 0.0;
                        pv = 0;
                        return;
                    }
                }
                //validacion

                x++;
                lbl_returns.setText("" + devolucion);
                newfile_table.setModel(prodret_table);
                aplicarFiltroTexto(0, 9, 0); // Cliente: Letras (tipo 1, col 0)
                aplicarFiltroTexto(0, 9, 2);
                lbl_title.setText("Tabla: Producto devuelto");

                Icon normal = new ImageIcon(getClass().getResource("/Recursos/finish_button.png"));
                Icon encima = new ImageIcon(getClass().getResource("/Recursos/finish_button_ro.png"));

                next_button.setIcon(normal);
                next_button.setRolloverIcon(encima);

                TableColumn columnaDes = newfile_table.getColumnModel().getColumn(1);
                columnaDes.setCellEditor(new DefaultCellEditor(comboDes));
                setModelo(newfile_table, prodret_table, 3);
                break;
            case 3:
                productodevuelto = 0.0;
                //validacion
                for (int i = 0; i < newfile_table.getRowCount(); i++) {
                    Object idObj = newfile_table.getValueAt(i, 0);
                    // Si es una fila nueva (sin código), la validamos
                    if (idObj == null || idObj.toString().trim().isEmpty()) {
                        boolean filaCompleta = true;
                        // Recorremos todas las columnas
                        for (int j = 0; j < prodret_table.getColumnCount(); j++) {
                            Object valor = prodret_table.getValueAt(i, j);
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
                    String pd = newfile_table.getValueAt(i, 3).toString();
                    try {
                        double pd2 = Double.parseDouble(pd);
                        productodevuelto = productodevuelto + pd2;
                    } catch (NumberFormatException e) {
                        i = i + 1;
                        JOptionPane.showMessageDialog(null, "¡Ha ingresado un valor NO numerico en la fila " + i + "! Porfavor, revise");
                        productodevuelto = 0.0;
                        return;
                    }
                }
                //validacion
                lbl_prodret.setText("" + productodevuelto);
                int opcion = JOptionPane.showConfirmDialog(
                        null,
                        "¿Deseás Terminar?",
                        "Confirmar acción",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (opcion == JOptionPane.OK_OPTION) {
                    String Tot = total_txt.getText();
                    int pvt = Integer.parseInt(pdv_txt.getText());
                    int resta = pvt - pv;
                    String puntos = String.valueOf(resta);
                    Date fecha1 = calender_date.getDate();
                    if (fecha1 == null) {
                        JOptionPane.showMessageDialog(null, "¡HAY CAMPOS VACIOS! Por favor, revise");
                        return;
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String fe = sdf.format(fecha1);
                    System.out.println("Antes: " + empId);
                    File_check_window ventana = new File_check_window(this, fiado, transferencia, devolucion, productodevuelto, trusted_table, transfers_table, returns_table, prodret_table, Tot, puntos, empId, zoneId, pvt, fe);
                    ventana.setVisible(true);
                    int verification = Clases.File_Check_window.returnverification();
                    if (verification == 1) {
                        total_txt.setText("");
                        pdv_txt.setText("");
                        zone_cb.setEnabled(false);
                        total_txt.setEnabled(false);
                        pdv_txt.setEnabled(false);
                        calender_date.setEnabled(false);
                        back_button.setEnabled(false);
                        minus_button.setEnabled(false);
                        add_button.setEnabled(false);
                        next_button.setEnabled(false);

                        start_button.setEnabled(true);
                        products_button.setEnabled(true);
                        clients_button.setEnabled(true);
                        employee_button.setEnabled(true);
                        configuration_button.setEnabled(true);
                        logout_button.setEnabled(true);

                        trusted_table.setRowCount(0);
                        transfers_table.setRowCount(0);
                        returns_table.setRowCount(0);
                        prodret_table.setRowCount(0);

                        lbl_trusted.setText("0");
                        lbl_transfers.setText("0");
                        lbl_returns.setText("0");
                        lbl_prodret.setText("0");
                    }
                    //this.dispose();
                } else if (opcion == JOptionPane.CANCEL_OPTION || opcion == JOptionPane.CLOSED_OPTION) {
                    System.out.println("Cancelado");
                    productodevuelto = 0.0;
                }
        }

    }//GEN-LAST:event_next_buttonActionPerformed

    private void employee_cbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employee_cbActionPerformed
        Object value = employee_cb.getSelectedItem();
        if (value instanceof Employee) {
            Employee emp = (Employee) value;
            empId = emp.getId();
            empName = emp.getNombre();
            zone_cb.setEnabled(true);
            if (!empName.equals("Opciones")) {
                zone_cb.setEnabled(true);
            } else {
                total_txt.setText("");
                pdv_txt.setText("");
                zone_cb.setEnabled(false);
                total_txt.setEnabled(false);
                pdv_txt.setEnabled(false);
                calender_date.setEnabled(false);
                back_button.setEnabled(false);
                minus_button.setEnabled(false);
                add_button.setEnabled(false);
                next_button.setEnabled(false);

                start_button.setEnabled(true);
                products_button.setEnabled(true);
                clients_button.setEnabled(true);
                employee_button.setEnabled(true);
                configuration_button.setEnabled(true);
                logout_button.setEnabled(true);

                trusted_table.setRowCount(0);
                transfers_table.setRowCount(0);
                returns_table.setRowCount(0);
                prodret_table.setRowCount(0);

                lbl_trusted.setText("0");
                lbl_transfers.setText("0");
                lbl_returns.setText("0");
                lbl_prodret.setText("0");
            }
        }
    }//GEN-LAST:event_employee_cbActionPerformed

    private void zone_cbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zone_cbActionPerformed
        Object value = zone_cb.getSelectedItem();
        if (value instanceof Zone) {
            Zone zn = (Zone) value;
            zoneId = zn.getId();
            zoneName = zn.getNombre();
            if (!zoneName.equals("Opciones")) {
                calender_date.setEnabled(true);
                total_txt.setEnabled(true);
                pdv_txt.setEnabled(true);
                start_button.setEnabled(false);
                products_button.setEnabled(false);
                clients_button.setEnabled(false);
                employee_button.setEnabled(false);
                configuration_button.setEnabled(false);
                logout_button.setEnabled(false);
            } else {
                total_txt.setText("");
                pdv_txt.setText("");
                total_txt.setEnabled(false);
                pdv_txt.setEnabled(false);
                calender_date.setEnabled(false);

                start_button.setEnabled(true);
                products_button.setEnabled(true);
                clients_button.setEnabled(true);
                employee_button.setEnabled(true);
                configuration_button.setEnabled(true);
                logout_button.setEnabled(true);

                trusted_table.setRowCount(0);
                transfers_table.setRowCount(0);
                returns_table.setRowCount(0);
                prodret_table.setRowCount(0);

                lbl_trusted.setText("0");
                lbl_transfers.setText("0");
                lbl_returns.setText("0");
                lbl_prodret.setText("0");
            }
        }
    }//GEN-LAST:event_zone_cbActionPerformed

    private void total_txtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_total_txtKeyTyped
        char r = evt.getKeyChar();
        if (Character.isISOControl(r)) {
            return; // permite borrar, mover, etc.
        }
        if (!Character.isDigit(r)) {
            getToolkit().beep();
            evt.consume();
        }
        if (total_txt.getText().length() >= 12) {
            evt.consume();
        }
    }//GEN-LAST:event_total_txtKeyTyped

    private void pdv_txtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pdv_txtKeyTyped
        char r = evt.getKeyChar();
        if (Character.isISOControl(r)) {
            return; // permite borrar, mover, etc.
        }
        if (!Character.isDigit(r)) {
            getToolkit().beep();
            evt.consume();
        }
        if (pdv_txt.getText().length() >= 3) {
            evt.consume();
        }
    }//GEN-LAST:event_pdv_txtKeyTyped

    private void add_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_buttonActionPerformed
        switch (x) {

            case 0:
                Object[] nuevaFila = {"", ""};
                trusted_table.addRow(nuevaFila);

                // Obtener el índice de la nueva fila
                int nuevaFilaIndex = trusted_table.getRowCount() - 1;

                // Hacerla editable
                trusted_table.setEditableRow(nuevaFilaIndex);

                // Seleccionar automáticamente
                newfile_table.setRowSelectionInterval(nuevaFilaIndex, nuevaFilaIndex);
                newfile_table.requestFocus();

                contcan++;
                break;

            case 1:
                Object[] nuevaFila2 = {"", "", ""};
                transfers_table.addRow(nuevaFila2);

                // Obtener el índice de la nueva fila
                int nuevaFilaIndex2 = transfers_table.getRowCount() - 1;

                // Hacerla editable
                transfers_table.setEditableRow(nuevaFilaIndex2);

                // Seleccionar automáticamente
                newfile_table.setRowSelectionInterval(nuevaFilaIndex2, nuevaFilaIndex2);
                newfile_table.requestFocus();

                contcan++;
                break;

            case 2:
                Object[] nuevaFila3 = {"", "", ""};
                returns_table.addRow(nuevaFila3);

                // Obtener el índice de la nueva fila
                int nuevaFilaIndex3 = returns_table.getRowCount() - 1;

                // Hacerla editable
                returns_table.setEditableRow(nuevaFilaIndex3);

                // Seleccionar automáticamente
                newfile_table.setRowSelectionInterval(nuevaFilaIndex3, nuevaFilaIndex3);
                newfile_table.requestFocus();

                contcan++;
                break;
            case 3:
                Object[] nuevaFila4 = {"", "", "", ""};
                prodret_table.addRow(nuevaFila4);

                // Obtener el índice de la nueva fila
                int nuevaFilaIndex4 = prodret_table.getRowCount() - 1;

                // Hacerla editable
                prodret_table.setEditableRow(nuevaFilaIndex4);

                // Seleccionar automáticamente
                newfile_table.setRowSelectionInterval(nuevaFilaIndex4, nuevaFilaIndex4);
                newfile_table.requestFocus();

                contcan++;
                break;
        }
    }//GEN-LAST:event_add_buttonActionPerformed

    private void minus_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minus_buttonActionPerformed
        switch (x) {

            case 0:
                int totalFilas = trusted_table.getRowCount();
                if (totalFilas > 0) {
                    trusted_table.removeRow(totalFilas - 1); // elimina la última
                } else {
                    JOptionPane.showMessageDialog(null, "La tabla está vacía.");
                }

                contcan--;
                break;

            case 1:
                int totalFilas2 = transfers_table.getRowCount();
                if (totalFilas2 > 0) {
                    transfers_table.removeRow(totalFilas2 - 1);
                } else {
                    JOptionPane.showMessageDialog(null, "La tabla está vacía.");
                }
                break;

            case 2:
                int totalFilas3 = returns_table.getRowCount();
                if (totalFilas3 > 0) {
                    returns_table.removeRow(totalFilas3 - 1);
                } else {
                    JOptionPane.showMessageDialog(null, "La tabla está vacía.");
                }

                contcan--;
                break;
            case 3:
                int totalFilas4 = prodret_table.getRowCount();
                if (totalFilas4 > 0) {
                    prodret_table.removeRow(totalFilas4 - 1);
                } else {
                    JOptionPane.showMessageDialog(null, "La tabla está vacía.");
                }

                contcan--;
                break;
        }
    }//GEN-LAST:event_minus_buttonActionPerformed

    private void back_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_back_buttonActionPerformed
        switch (x) {
            case 3:
                x--;
                Icon normal = new ImageIcon(getClass().getResource("/Recursos/next_button.png"));
                Icon encima = new ImageIcon(getClass().getResource("/Recursos/next_button_ro.png"));

                next_button.setIcon(normal);
                next_button.setRolloverIcon(encima);
                newfile_table.setModel(returns_table);
                lbl_title.setText("Titulo: Devolución");
                aplicarFiltroTexto(0, 9, 2);

                TableColumn columnaCod = newfile_table.getColumnModel().getColumn(0);
                columnaCod.setCellEditor(new DefaultCellEditor(comboCod));
                devolucion = 0.0;
                break;
            case 2:
                x--;
                newfile_table.setModel(transfers_table);
                lbl_title.setText("Titulo: Transferencia");

                refrescarComboCliente(0);

                JComboBox<String> comboEstado = new JComboBox<>();
                aplicarFiltroTexto(0, 9, 1);

                comboEstado.addItem("Aprobado");
                comboEstado.addItem("Pendiente");

                TableColumn columnaEstado = newfile_table.getColumnModel().getColumn(2);
                columnaEstado.setCellEditor(new DefaultCellEditor(comboEstado));
                transferencia = 0.0;
                break;
            case 1:
                x--;
                newfile_table.setModel(trusted_table);
                lbl_title.setText("Titulo: Fiado");
                aplicarFiltroTexto(0, 9, 1);
                refrescarComboCliente(0);
                fiado = 0.0;
                back_button.setEnabled(false);
                break;
        }
    }//GEN-LAST:event_back_buttonActionPerformed

    private void help_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_help_buttonActionPerformed
        Clases.PDF.abrirPDF("/Recursos/ManualFicha.pdf");
    }//GEN-LAST:event_help_buttonActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new New_file_window().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add_button;
    private javax.swing.JButton back_button;
    private javax.swing.JPanel background;
    private com.toedter.calendar.JDateChooser calender_date;
    private javax.swing.JButton clients_button;
    private javax.swing.JButton configuration_button;
    private javax.swing.JButton employee_button;
    private javax.swing.JComboBox<Employee> employee_cb;
    private javax.swing.JButton help_button;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JLabel lbl_prodret;
    private javax.swing.JLabel lbl_returns;
    private javax.swing.JLabel lbl_title;
    private javax.swing.JLabel lbl_transfers;
    private javax.swing.JLabel lbl_trusted;
    private javax.swing.JButton logout_button;
    private javax.swing.JButton minus_button;
    private javax.swing.JButton new_file_button;
    private javax.swing.JTable newfile_table;
    private javax.swing.JButton next_button;
    private javax.swing.JTextField pdv_txt;
    private javax.swing.JButton products_button;
    private javax.swing.JPanel section_menu;
    private javax.swing.JButton start_button;
    private javax.swing.JLabel toc;
    private javax.swing.JTextField total_txt;
    private javax.swing.JComboBox<Zone> zone_cb;
    // End of variables declaration//GEN-END:variables
}
