
package com.mycompany.wmhospital;


import conexion.ConexionBD;
import javax.swing.SwingUtilities;
import vista.Inicio;

public class WMhospital {

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                new Inicio(); 
                
                ConexionBD conexion = new ConexionBD();
                conexion.abrirConexion();

            }
        });
    }
}
