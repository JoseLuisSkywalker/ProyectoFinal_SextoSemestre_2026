/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package intefaces;

import java.util.List;
import modelo.Medico;

/**
 *
 * @author josesanchez
 */
public interface InterfazMedicoDAO {
    
    boolean insertar(Medico medico);

    boolean actualizar(Medico medico);

    boolean eliminar(int idMedico);

    Medico buscarPorId(int idMedico);

    List<Medico> obtenerTodos();

    List<Medico> buscar(String texto);
    
}
