
package com.mycompany.wmhospital;


import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import conexion.ConexionBD;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import vista.Inicio;

public class WMhospital {

    public static void main(String[] args) {

        FlatMacDarkLaf.setup();

        java.awt.EventQueue.invokeLater(() -> {

            new Inicio().setVisible(true);

        });

    }

}