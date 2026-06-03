package intefaces;

import java.util.List;
import modelo.Cita;

/**
 *
 * @author josesanchez
 */
public interface InterfazCitaDAO {

    boolean insertar(Cita cita);

    boolean actualizar(Cita cita);

    boolean eliminar(int numCita);

    Cita buscarPorId(int numCita);

    List<Cita> obtenerTodos();

    List<Cita> buscar(String texto);

}