/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author josesanchez
 */
public class ButtonRenderer extends JButton implements TableCellRenderer {
    
    public ButtonRenderer(ImageIcon icono, Color color) {

        setIcon(icono);
        setBackground(color);
        
        setOpaque(true);
        setBorderPainted(false);
        setFocusPainted(false);
       

        setHorizontalAlignment(CENTER);

        setVerticalAlignment(CENTER);
        
        setPreferredSize(new java.awt.Dimension(28, 28));

    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
       return this;
    }
    
}
