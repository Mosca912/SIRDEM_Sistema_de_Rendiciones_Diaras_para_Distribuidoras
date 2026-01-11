/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import static Clases.Add_bills_window.listaValoresGlobal;
import static Clases.Add_bills_window.totalprecio;
import java.awt.Color;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Facuymayriver
 */
public class File_Check_window {

    public static int verification;
    static int idficha = 0;
    static DefaultComboBoxModel<Gastos> model;

    public static class Gastos {

        private final int id;
        private final String nombre;

        public Gastos(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        public int getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }

        @Override
        public String toString() {
            return nombre; // lo que se muestra en el combo
        }
    }

    public static void verification(int x) {
        verification = x;
    }

    public static int returnverification() {
        return verification;
    }

    public static int isListEmpty() {
        // 1. Verificar si la lista es nula (es una buena práctica, aunque ya esté inicializada)
        if (listaValoresGlobal == null) {
            return 1; // Retorna 1 si la lista no existe (es nula)
        }

        if (listaValoresGlobal.isEmpty()) {
            return 1;
        }

        // 3. Si llega hasta aquí, la lista existe Y tiene elementos.
        return 0; // Retorna 0 si la lista tiene datos
    }

    public static void actualizarRendicion(JTextField total, JTextField abonado, JTextField fiado, JLabel resultado, Double sum, JLabel totalr, JLabel totalT, JLabel diferencia, JLabel devolucion, JLabel Producto, JLabel totalg) {
        try {
            double valTotalR = Double.parseDouble(total.getText());
            double valAbonado = Double.parseDouble(abonado.getText());
            double valFiado = Double.parseDouble(fiado.getText());
            double valortotal = Double.parseDouble(totalT.getText());
            double valordev = Double.parseDouble(devolucion.getText());
            double valorprod = Double.parseDouble(Producto.getText());

            double res = valTotalR + valAbonado - valFiado;
            double totalrendicion = sum + res;
            double valortotal2 = valortotal - valordev - valorprod;
            double totalficha = totalrendicion - valortotal2;

            totalg.setText("" + valortotal2);
            resultado.setText("" + res);
            totalr.setText("" + totalrendicion);
            diferencia.setText("" + totalficha);

            // Opcional: color según valor
            if (res < 0) {
                resultado.setForeground(Color.RED);
            } else {
                resultado.setForeground(Color.GREEN);
            }

            if (totalficha < 0) {
                diferencia.setForeground(Color.RED);
            } else {
                diferencia.setForeground(Color.GREEN);
            }
        } catch (NumberFormatException e) {
            resultado.setText("Entrada inválida");
            resultado.setForeground(Color.RED);
        }
    }

    public static void InsertFicha(Connection conexion, int semana, String fechaActual, int preventista, int zona, int total, String puntos) {
        int id = 0;
        int id2 = 0;
        int x = 0;

        //semana
        while (x == 0) {
            String sql = "SELECT idSemana FROM semana WHERE semana=?";
            try {
                PreparedStatement ps = conexion.prepareStatement(sql);
                ps.setInt(1, semana);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    id = rs.getInt("idSemana");
                    x = 1;
                } else {
                    String sql2 = "INSERT INTO semana (semana) VALUES (?)";
                    PreparedStatement ps2 = conexion.prepareStatement(sql2);
                    ps2.setInt(1, semana);
                    try {
                        ps2.execute();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "ERROR12");
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
            }
        }

        x = 0;
        while (x == 0) {
            String sql = "SELECT id_Fecha FROM fecha WHERE Fecha=? AND idSemana= ?";
            try {
                PreparedStatement ps = conexion.prepareStatement(sql);
                ps.setString(1, fechaActual);
                ps.setInt(2, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    id2 = rs.getInt("id_Fecha");
                    x = 1;
                } else {
                    String sql2 = "INSERT INTO fecha (Fecha, idSemana) VALUES (?,?)";
                    PreparedStatement ps2 = conexion.prepareStatement(sql2);
                    ps2.setString(1, fechaActual);
                    ps2.setInt(2, id);
                    try {
                        ps2.execute();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "ERROR12");
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
            }
        }

        String sql = "INSERT INTO ficha (id_Fecha, id_Zona, total, PuntosVentas, id_preventista, id_Usuario) VALUES (?,?,?,?,?,1)";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id2);
            ps.setInt(2, zona);
            ps.setInt(3, total);
            ps.setString(4, puntos);
            ps.setInt(5, preventista);
            int filas = ps.executeUpdate();

            if (filas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idficha = rs.getInt(1); // 👈 este es el id autoincremental de ficha
                }
            }
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public static void Fiado(Connection conexion, int cliente, int saldo) {

        String sql = "INSERT into fiado (id_Cliente, saldo, id_Ficha) VALUES (?,?,?) ";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, cliente);
            ps.setInt(2, saldo);
            ps.setInt(3, idficha);
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public static void TipoGasto(Connection conexion, String tipo) {

        String sql = "INSERT into tipos (tipo) VALUES (?) ";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, tipo);
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public static void Transferencia(Connection conexion, int cliente, int saldo, int estado) {

        String sql = "INSERT into transferencia (id_Cliente, saldo, id_Estado, id_Ficha) VALUES (?,?,?,?) ";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, cliente);
            ps.setInt(2, saldo);
            ps.setInt(3, estado);
            ps.setInt(4, idficha);
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public static void Devolucion(Connection conexion, int cliente, int saldo) {

        String sql = "INSERT into devolucion (id_Cliente, saldo, id_Ficha) VALUES (?,?,?) ";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, cliente);
            ps.setInt(2, saldo);
            ps.setInt(3, idficha);
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public static void ProductoDevuelto(Connection conexion, int Producto, int PU, Double Total, int cant) {

        String sql = "INSERT into productodevuelto (Cantidad, PrecioUn, Total, id_Producto, id_Ficha) VALUES (?,?,?,?,?) ";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, cant);
            ps.setInt(2, PU);
            ps.setDouble(3, Total);
            ps.setInt(4, Producto);
            ps.setInt(5, idficha);
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public static void Rendicion(Connection conexion, int Efectivo, int Gastos, int Cobranzas, Double fiado, Double transferencia, Double devolucion, Double productodevuelto, Double totalGeneral, Double totaldiferencia) {
        String sql3 = "INSERT INTO gastos (fecha) VALUES (?)";
        int idGastos = -1; // Variable para almacenar el ID
        try (
                PreparedStatement ps = conexion.prepareStatement(sql3, Statement.RETURN_GENERATED_KEYS);) {
            LocalDateTime ahora = LocalDateTime.now();
            Timestamp tiempoActual = Timestamp.valueOf(ahora);
            ps.setTimestamp(1, tiempoActual);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGastos = rs.getInt(1);
                    }
                }
            }

            // Aquí puedes usar 'idGenerado' para la siguiente operación (ej: guardarlo en tu lista de Object[])
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar o recuperar ID: " + e.getMessage());
        }

        String sql4 = "INSERT INTO listagastos (idgastos,idtipos,precio) VALUES (?,?,?)";
        // Utilizamos try-with-resources para asegurar el cierre automático del PreparedStatement
        try (PreparedStatement ps = conexion.prepareStatement(sql4)) {

            for (Object[] registro : listaValoresGlobal) {
                int valorInfo = (int) registro[1];
                int pre = (int) registro[3];
                ps.setInt(1, idGastos);
                ps.setInt(2, valorInfo);
                ps.setInt(3, pre);
                ps.execute();
            }
            listaValoresGlobal.clear();
            totalprecio = 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar los datos: " + e.getMessage());
        }

        String sql = "INSERT into rendicion (Efectivo, Gastos, id_gasto, Cobranzas, id_ficha) VALUES (?,?,?,?,?) ";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, Efectivo);
            ps.setInt(2, Gastos);
            ps.setInt(3, idGastos);
            ps.setInt(4, Cobranzas);
            ps.setInt(5, idficha);
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        String sql2 = "INSERT into total (idficha, totalfiado, totaltransferencia, totaldev, totalpd, totalgeneral, totaldiferencia) VALUES (?,?,?,?,?,?,?) ";
        try {
            PreparedStatement ps = conexion.prepareStatement(sql2);
            ps.setInt(1, idficha);
            ps.setDouble(2, fiado);
            ps.setDouble(3, transferencia);
            ps.setDouble(4, devolucion);
            ps.setDouble(5, productodevuelto);
            ps.setDouble(6, totalGeneral);
            ps.setDouble(7, totaldiferencia);
            ps.execute();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
