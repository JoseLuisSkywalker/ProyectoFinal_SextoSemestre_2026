/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

/**
 *
 * @author josesanchez
 */
public class ConexionBDPrueba {
    
    public static void main(String[] args) {
        
        ConexionBD con = ConexionBD.getInstance();
        con.abrirConexion(); 
        
        
    }
}
