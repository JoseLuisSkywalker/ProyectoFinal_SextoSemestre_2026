/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author josesanchez
 */
public class ButtonEditor extends AbstractCellEditor implements TableCellEditor{
    private JButton boton; 
    private String accion;
    
    public ButtonEditor(ImageIcon icono, Color color, ActionListener listener){
        boton = new JButton(); 
        boton.setIcon(icono); 
        boton.setForeground(Color.WHITE);
        boton.setOpaque(true); 
        boton.setBorderPainted(false); 
        boton.setFocusPainted(false);
        
        boton.addActionListener(e -> {
            fireEditingStopped();
            listener.actionPerformed(e);
        });
    }
    

    @Override
    public Object getCellEditorValue() {
        
     return accion; 
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        
        accion = (value != null) ? value.toString() : ""; 
        return boton; 
    }
    
    
}
