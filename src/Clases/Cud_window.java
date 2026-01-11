/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import javax.swing.JLabel;
/**
 *
 * @author Facuymayriver
 */
public class Cud_window {
    public static void CUDInfo(int flag, JLabel info) {
        switch (flag){
            case 0:
                info.setText("Zonas");
                return;
            case 1:
                info.setText("Categorias");
                return;
            case 2:
                info.setText("Gastos");
        }
    }
}
