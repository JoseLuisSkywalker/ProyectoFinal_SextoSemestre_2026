package intefaces;

import java.util.List;
import modelo.Paciente;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author josesanchez
 */
public interface InterfazPacienteDAO {
    
    boolean insertar(Paciente paciente);
    boolean actualizar(Paciente paciente);
    boolean eliminar(int idPaciente);
    Paciente buscarPorId(int idPaciente);
    List<Paciente> obtenerTodos();
    List<Paciente> buscar(String texto);
    
}
