package Vistas;

import Conexion.Conexiones;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Menu_window extends javax.swing.JFrame {

    private final char caracterEchoPredeterminado;
    Connection con = Conexiones.Conexion();
    static int valid1;

    public Menu_window() {
        initComponents();
        Clases.General_configurations.Icon(this);
        this.setLocationRelativeTo(null);

        String textoLargo = "<html>Ingrese las credenciales registradas en cada campo para iniciar la sesión. Si no lo recuerda, consulte con su superior para la consulta y/o cambio de lo registrado</html>";
        description_label_txt.setText(textoLargo);

        caracterEchoPredeterminado = password_text.getEchoChar();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        dni_text = new javax.swing.JTextField();
        password_text = new javax.swing.JPasswordField();
        view_password_button = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        login_button = new javax.swing.JButton();
        exit_button = new javax.swing.JButton();
        logo_lbl = new javax.swing.JLabel();
        lbl_login_txt = new javax.swing.JLabel();
        description_label_txt = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("menu_window");
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 0, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        dni_text.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dni_text.setToolTipText("Ingrese el DNI registrado");
        dni_text.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "D.N.I", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N
        dni_text.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dni_textKeyTyped(evt);
            }
        });

        password_text.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        password_text.setToolTipText("Ingrese la contraseña registrada");
        password_text.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Contraseña", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N
        password_text.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                password_textKeyPressed(evt);
            }
        });

        view_password_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/view_pass.png"))); // NOI18N
        view_password_button.setToolTipText("Mirar/Ocultar contraseña");
        view_password_button.setBorderPainted(false);
        view_password_button.setContentAreaFilled(false);
        view_password_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        view_password_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view_password_buttonActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("Bienvenido/a!");

        login_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/login_button.png"))); // NOI18N
        login_button.setToolTipText("Ingresar al programa");
        login_button.setBorderPainted(false);
        login_button.setContentAreaFilled(false);
        login_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        login_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/login_button_ro.png"))); // NOI18N
        login_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                login_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dni_text, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(password_text, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(view_password_button, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(login_button, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(37, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(142, 142, 142)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dni_text, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(password_text, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(view_password_button))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addComponent(login_button, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );

        exit_button.setBackground(new java.awt.Color(255, 102, 102));
        exit_button.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        exit_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/exit_button.png"))); // NOI18N
        exit_button.setToolTipText("Cerrar el programa :(");
        exit_button.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 102, 102), 2, true));
        exit_button.setBorderPainted(false);
        exit_button.setContentAreaFilled(false);
        exit_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exit_button.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/exit_button_ro.png"))); // NOI18N
        exit_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exit_buttonActionPerformed(evt);
            }
        });

        logo_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logo_lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/img_login_icon.png"))); // NOI18N

        lbl_login_txt.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lbl_login_txt.setForeground(new java.awt.Color(255, 255, 255));
        lbl_login_txt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_login_txt.setText("Iniciar Sesión");

        description_label_txt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        description_label_txt.setForeground(new java.awt.Color(255, 255, 255));
        description_label_txt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(description_label_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_login_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(20, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(logo_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(68, 68, 68))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(exit_button, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(65, 65, 65))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logo_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_login_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(description_label_txt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exit_button, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
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

    private void login_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_login_buttonActionPerformed
        String dni = dni_text.getText();
        String cont = password_text.getText();
        if (!dni.trim().isEmpty() && !cont.trim().isEmpty()) {
            try {
                valid1 = Clases.Menu_window.Login(con, dni, cont);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al iniciar sesión: " + ex);
            }
            if (valid1 == 1) {
                Start_window ventana;
                try {
                    ventana = new Start_window();
                    ventana.setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Menu_window.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.dispose();
            }
        } else {
            JOptionPane.showMessageDialog(null, "¡HAY CAMPOS VACIOS! Por favor, revise");
        }

    }//GEN-LAST:event_login_buttonActionPerformed

    private void exit_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exit_buttonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exit_buttonActionPerformed

    private void view_password_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view_password_buttonActionPerformed
        ImageIcon icon1 = new ImageIcon(getClass().getResource("/Recursos/view_pass2.png"));
        ImageIcon icon2 = new ImageIcon(getClass().getResource("/Recursos/view_pass.png"));
        char caracterActual = password_text.getEchoChar();
        if (caracterActual != (char) 0) {
            password_text.setEchoChar((char) 0);
            view_password_button.setIcon(icon1);
        } else {
            password_text.setEchoChar(caracterEchoPredeterminado);
            view_password_button.setIcon(icon2);
        }
    }//GEN-LAST:event_view_password_buttonActionPerformed

    private void password_textKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_password_textKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            login_button.doClick();
        }
    }//GEN-LAST:event_password_textKeyPressed

    private void dni_textKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dni_textKeyTyped
        char r = evt.getKeyChar();
        if (Character.isISOControl(r)) {
            return; // permite borrar, mover, etc.
        }
        if (!Character.isDigit(r)) {
            getToolkit().beep();
            evt.consume();
        }
        if (dni_text.getText().length() >= 9) {
            evt.consume();
        }
    }//GEN-LAST:event_dni_textKeyTyped
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Menu_window().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel description_label_txt;
    private javax.swing.JTextField dni_text;
    private javax.swing.JButton exit_button;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lbl_login_txt;
    private javax.swing.JButton login_button;
    private javax.swing.JLabel logo_lbl;
    private javax.swing.JPasswordField password_text;
    private javax.swing.JButton view_password_button;
    // End of variables declaration//GEN-END:variables
}
