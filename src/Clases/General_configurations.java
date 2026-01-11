package Clases;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import static java.awt.Frame.MAXIMIZED_BOTH;
import static java.awt.Frame.NORMAL;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

public class General_configurations {

    //Icono del programa
    static String rutaIcono = "/Recursos/icon_toc.png";
    static int ventana = 0, mode = 0;

    public static int Color() {
        return mode;
    }

    public static void ColorOpc(int flag) {
        mode = flag;
    }

    public static void ColorConfigurationWindow(JPanel panel1, JPanel panel2, JPanel panel3, JPanel panel4, JLabel label) {
        if (mode == 1) {
            panel1.setBackground(Color.BLACK);
            label.setForeground(Color.WHITE);

            panel2.setBackground(Color.BLACK);
            TitledBorder borde = (TitledBorder) panel2.getBorder();

            borde.setBorder(new LineBorder(Color.WHITE, 3));
            borde.setTitleColor(Color.WHITE);

            panel3.setBackground(Color.BLACK);
            TitledBorder borde2 = (TitledBorder) panel3.getBorder();

            borde2.setBorder(new LineBorder(Color.WHITE, 3));
            borde2.setTitleColor(Color.WHITE);

            panel4.setBackground(Color.BLACK);
            TitledBorder borde3 = (TitledBorder) panel4.getBorder();

            borde3.setBorder(new LineBorder(Color.WHITE, 3));
            borde3.setTitleColor(Color.WHITE);
        } else if (mode == 0) {
            panel1.setBackground(Color.WHITE);
            label.setForeground(Color.BLACK);

            panel2.setBackground(Color.WHITE);
            TitledBorder borde = (TitledBorder) panel2.getBorder();

            borde.setBorder(new LineBorder(Color.BLACK, 3));
            borde.setTitleColor(Color.BLACK);

            panel3.setBackground(Color.WHITE);
            TitledBorder borde2 = (TitledBorder) panel3.getBorder();

            borde2.setBorder(new LineBorder(Color.BLACK, 3));
            borde2.setTitleColor(Color.BLACK);

            panel4.setBackground(Color.WHITE);
            TitledBorder borde3 = (TitledBorder) panel4.getBorder();

            borde3.setBorder(new LineBorder(Color.BLACK, 3));
            borde3.setTitleColor(Color.BLACK);
        }
    }

    public static int Ventana() {
        return ventana;
    }

    public static void VentanaOpc(int flag) {
        ventana = flag;
    }

    public static void MaxMinWindow(JFrame window) {
        if (ventana == 1) {
            window.setExtendedState(NORMAL);
        } else if (ventana == 0) {
            window.setExtendedState(MAXIMIZED_BOTH);
        }
    }

    public static void table_configuration(JTable table, DefaultTableModel tablemodel) {
        table.setModel(tablemodel);

        Font fuenteGrande = new Font("Arial", Font.PLAIN, 15);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
        table.getTableHeader().setPreferredSize(new Dimension(0, 35));
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(0).setMinWidth(40);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
        table.setFont(fuenteGrande);

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setRowHeight(40);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        JTextField editorTextField = new JTextField();
        editorTextField.setHorizontalAlignment(JTextField.CENTER);

        DefaultCellEditor centerEditor = new DefaultCellEditor(editorTextField);
        table.setDefaultEditor(Object.class, centerEditor);
    }

    public static void table_configuration12(JTable table, DefaultTableModel tablemodel) {
        table.setModel(tablemodel);

        Font fuenteGrande = new Font("Arial", Font.PLAIN, 15);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
        table.getTableHeader().setPreferredSize(new Dimension(0, 35));
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(0).setMinWidth(40);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
        table.setFont(fuenteGrande);

        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter("##-##-####");
        } catch (ParseException ex) {

        }
        formatter.setPlaceholderCharacter('0');
        JFormattedTextField horaField = new JFormattedTextField(formatter);

        DefaultCellEditor editor = new DefaultCellEditor(horaField);
        table.getColumnModel().getColumn(5).setCellEditor(editor);

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setRowHeight(40);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        JTextField editorTextField = new JTextField();
        editorTextField.setHorizontalAlignment(JTextField.CENTER);

        DefaultCellEditor centerEditor = new DefaultCellEditor(editorTextField);
        table.setDefaultEditor(Object.class, centerEditor);

        JFormattedTextField fechaField = new JFormattedTextField(formatter);
        fechaField.setHorizontalAlignment(SwingConstants.CENTER);
        fechaField.setFont(fuenteGrande);

        DefaultCellEditor fechaEditor = new DefaultCellEditor(fechaField);
        table.getColumnModel().getColumn(5).setCellEditor(fechaEditor);
    }

    public static void table_configuration3(JTable table, DefaultTableModel tablemodel) {
        table.setModel(tablemodel);

        Font fuenteGrande = new Font("Arial", Font.PLAIN, 15);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
        table.getTableHeader().setPreferredSize(new Dimension(0, 35));
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(0).setMinWidth(40);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
        table.setFont(fuenteGrande);

        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter("##-##-####");
        } catch (ParseException ex) {

        }
        formatter.setPlaceholderCharacter('0');
        JFormattedTextField horaField = new JFormattedTextField(formatter);

        DefaultCellEditor editor = new DefaultCellEditor(horaField);
        table.getColumnModel().getColumn(5).setCellEditor(editor);

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setRowHeight(40);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        JTextField editorTextField = new JTextField();
        editorTextField.setHorizontalAlignment(JTextField.CENTER);

        DefaultCellEditor centerEditor = new DefaultCellEditor(editorTextField);
        table.setDefaultEditor(Object.class, centerEditor);

        JFormattedTextField fechaField = new JFormattedTextField(formatter);
        fechaField.setHorizontalAlignment(SwingConstants.CENTER);
        fechaField.setFont(fuenteGrande);

        DefaultCellEditor fechaEditor = new DefaultCellEditor(fechaField);
        table.getColumnModel().getColumn(5).setCellEditor(fechaEditor);
    }

    public static void table_configurationBills(JTable table, DefaultTableModel tablemodel) {
        table.setModel(tablemodel);

        Font fuenteGrande = new Font("Arial", Font.PLAIN, 15);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
        table.getTableHeader().setPreferredSize(new Dimension(0, 35));
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.setFont(fuenteGrande);

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setRowHeight(40);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        JTextField editorTextField = new JTextField();
        editorTextField.setHorizontalAlignment(JTextField.CENTER);

        DefaultCellEditor centerEditor = new DefaultCellEditor(editorTextField);
        table.setDefaultEditor(Object.class, centerEditor);
    }

    public static void table_configurationFile(JTable table) {

        Font fuenteGrande = new Font("Arial", Font.PLAIN, 15);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
        table.getTableHeader().setPreferredSize(new Dimension(0, 35));
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.setFont(fuenteGrande);

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setRowHeight(40);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        JTextField editorTextField = new JTextField();
        editorTextField.setHorizontalAlignment(JTextField.CENTER);

        DefaultCellEditor centerEditor = new DefaultCellEditor(editorTextField);
        table.setDefaultEditor(Object.class, centerEditor);
    }

    public static void table_configurationUD(JTable table) {
        table.getColumnModel().getColumn(0).setMinWidth(40);
        table.getColumnModel().getColumn(0).setMaxWidth(40);

        Font fuenteGrande = new Font("Arial", Font.PLAIN, 20);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
        table.getTableHeader().setPreferredSize(new Dimension(0, 35));
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.setFont(fuenteGrande);

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setRowHeight(40);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        JTextField editorTextField = new JTextField();
        editorTextField.setHorizontalAlignment(JTextField.CENTER);

        DefaultCellEditor centerEditor = new DefaultCellEditor(editorTextField);
        table.setDefaultEditor(Object.class, centerEditor);
    }

    public static void Icon(JFrame ventana) {
        try {
            Image icono = new ImageIcon(General_configurations.class.getResource(rutaIcono)).getImage();
            ventana.setIconImage(icono);
        } catch (Exception e) {
            System.err.println("Error al cargar el ícono: " + e.getMessage());
        }
    }

    public static int VerificationFile(Connection conexion) {
        int valid = 0;
        String sql3 = "SELECT id_Preventista, Nombre, Apellido FROM preventista WHERE borrado=0 ORDER BY id_Preventista";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql3);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "Por favor! Cargue preventistas para el ingreso de nuevas fichas");
                valid = 1;
                return valid;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
        }

        String sql4 = "SELECT id_Zonas, Nombre FROM Zonas WHERE borrado=0 ORDER BY id_Zonas";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql4);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "Por favor! Cargue zonas para el ingreso de nuevas fichas");
                valid = 1;
                return valid;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR AL MOSTRAR ZONA: " + ex.getMessage());
        }

        String sql = "SELECT id_Clientes, nombre, apellido FROM clientes WHERE borrado=0 ORDER BY id_Clientes";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "Por favor! Cargue clientes para el ingreso de nuevas fichas");
                valid = 1;
                return valid;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
        }

        String sql2 = "SELECT id_Productos, Descripcion FROM productos WHERE borrado=0 ORDER BY id_Productos";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql2);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "Por favor! Cargue productos para el ingreso de nuevas fichas");
                valid = 1;
                return valid;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
        }
        return valid;
    }

    public static void colorconfig(JPanel panel, JLabel title, JPanel opciones, JPanel accion) {
        if (mode == 1) {
            panel.setBackground(Color.BLACK);
            title.setForeground(Color.WHITE);

            TitledBorder borde = (TitledBorder) opciones.getBorder();

            borde.setBorder(new LineBorder(Color.WHITE, 3));
            borde.setTitleColor(Color.WHITE);

            TitledBorder borde2 = (TitledBorder) accion.getBorder();

            borde2.setBorder(new LineBorder(Color.WHITE, 3));
            borde2.setTitleColor(Color.WHITE);
        }
    }

    public static void colorconfig2(JPanel panel, JLabel title) {
        if (mode == 1) {
            panel.setBackground(Color.BLACK);
            title.setForeground(Color.WHITE);

            TitledBorder borde = (TitledBorder) panel.getBorder();

            borde.setBorder(new LineBorder(Color.WHITE, 3));
            borde.setTitleColor(Color.WHITE);
        }
    }

    public static void colorconfig3(JPanel panel, JLabel title, JTextField text) {
        if (mode == 1) {
            panel.setBackground(Color.BLACK);
            title.setForeground(Color.WHITE);

            TitledBorder borde = (TitledBorder) panel.getBorder();

            borde.setBorder(new LineBorder(Color.WHITE, 3));
            borde.setTitleColor(Color.WHITE);

            text.setBackground(Color.BLACK);
            text.setForeground(Color.WHITE);

            TitledBorder borde2 = (TitledBorder) text.getBorder();

            borde2.setBorder(new LineBorder(Color.WHITE, 3));
            borde2.setTitleColor(Color.WHITE);
        }
    }

    public static void colorconfig4(JPanel panel, JLabel title, JPanel opciones, JPanel accion, JLabel label1, JComboBox mes) {
        if (mode == 1) {
            panel.setBackground(Color.BLACK);
            title.setForeground(Color.WHITE);

            ((TitledBorder) opciones.getBorder()).setBorder(new LineBorder(Color.WHITE, 3));
            ((TitledBorder) opciones.getBorder()).setTitleColor(Color.WHITE);

            accion.setBackground(Color.BLACK);
            label1.setForeground(Color.WHITE);
            ((TitledBorder) accion.getBorder()).setBorder(new LineBorder(Color.WHITE, 3));
            ((TitledBorder) accion.getBorder()).setTitleColor(Color.WHITE);

            mes.setBackground(Color.BLACK);
            mes.setForeground(Color.WHITE);

            mes.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    JLabel c = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (isSelected) {
                        c.setBackground(Color.DARK_GRAY);
                        c.setForeground(Color.WHITE);
                    } else {
                        c.setBackground(Color.BLACK);
                        c.setForeground(Color.WHITE);
                    }
                    return c;
                }
            });

            if (mes.getEditor() != null) {
                Component editor = mes.getEditor().getEditorComponent();
                editor.setBackground(Color.BLACK);
                editor.setForeground(Color.WHITE);
            }

            TitledBorder borde3 = (TitledBorder) mes.getBorder();
            if (borde3 != null) {
                borde3.setBorder(new LineBorder(Color.WHITE, 3));
                borde3.setTitleColor(Color.WHITE);
            }
        }
    }

    public static void colorconfig5(JComponent... componentes) {
        if (mode == 1) {
            for (JComponent c : componentes) {
                if (c == null) {
                    continue;
                }

                boolean esCalendario = c.getClass().getName().contains("JCalendar")
                        || c.getClass().getName().contains("JDateChooser");

                if (esCalendario) {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                    actualizarComponentesInternos(c, Color.WHITE, Color.BLACK);
                } else {
                    c.setBackground(Color.BLACK);
                    c.setForeground(Color.WHITE);
                }

                c.setOpaque(true);

                if (c.getBorder() instanceof TitledBorder) {
                    TitledBorder borde = (TitledBorder) c.getBorder();
                    Color colorContraste = esCalendario ? Color.BLACK : Color.WHITE;
                    borde.setBorder(new LineBorder(colorContraste, 1));
                    borde.setTitleColor(colorContraste);
                }

                if (c instanceof JTextField && !esCalendario) {
                    JTextField texto = (JTextField) c;
                    texto.setCaretColor(Color.WHITE);
                    texto.setDisabledTextColor(Color.WHITE);
                }

                if (c instanceof JComboBox) {
                    JComboBox combo = (JComboBox) c;
                    combo.setRenderer(new DefaultListCellRenderer() {
                        @Override
                        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                            if (!list.isEnabled()) {
                                label.setBackground(Color.BLACK);
                                label.setForeground(Color.WHITE);
                            } else {
                                label.setBackground(isSelected ? Color.DARK_GRAY : Color.BLACK);
                                label.setForeground(Color.WHITE);
                            }
                            return label;
                        }
                    });
                }

                if (c instanceof JTable) {
                    JTable tabla = (JTable) c;
                    tabla.setFillsViewportHeight(true);
                    tabla.getTableHeader().setBackground(Color.BLACK);
                    tabla.getTableHeader().setForeground(Color.WHITE);
                }
            }
        }
    }

    private static void actualizarComponentesInternos(Component comp, Color fondo, Color texto) {
        comp.setBackground(fondo);
        comp.setForeground(texto);

        if (comp instanceof javax.swing.text.JTextComponent) {
            javax.swing.text.JTextComponent editor = (javax.swing.text.JTextComponent) comp;
            editor.setBackground(fondo);
            editor.setForeground(texto);
            editor.setDisabledTextColor(Color.DARK_GRAY);
            editor.setOpaque(true);
        }

        if (comp instanceof JButton || comp instanceof JLabel) {
            comp.setForeground(texto);
        }

        if (comp instanceof Container) {
            for (Component hijo : ((Container) comp).getComponents()) {
                actualizarComponentesInternos(hijo, fondo, texto);
            }
        }
    }
}
