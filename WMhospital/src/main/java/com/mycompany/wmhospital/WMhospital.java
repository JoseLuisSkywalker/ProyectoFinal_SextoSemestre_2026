
package com.mycompany.wmhospital;

import javax.swing.SwingUtilities;
import vista.Inicio;

public class WMhospital {

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                new Inicio(); 

            }
        });
    }
}
