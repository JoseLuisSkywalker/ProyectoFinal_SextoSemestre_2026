package controlador;

import conexion.ConexionBD;
import intefaces.InterfazCitaDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Cita;

/**
 *
 * @author josesanchez
 */
public class CitaDAO implements InterfazCitaDAO {

    private final ConexionBD conexionBD;

    public CitaDAO() {

        conexionBD = ConexionBD.getInstance();

        conexionBD.abrirConexion();

    }

    @Override
    public boolean insertar(Cita cita) {

        String sql =

                "INSERT INTO citas " +
                "(id_paciente, id_medico, fecha_cita, hora_cita, num_habitacion) " +
                "VALUES (?, ?, ?, ?, ?)";

        return conexionBD.ejecutarInstruccionLMD(

                sql,
                cita.getIdPaciente(),
                cita.getIdMedico(),
                cita.getFechaCita(),
                cita.getHoraCita(),
                cita.getNumHabitacion()

        );

    }

    @Override
    public boolean actualizar(Cita cita) {

        String sql =

                "UPDATE citas " +
                "SET id_paciente = ?, " +
                "id_medico = ?, " +
                "fecha_cita = ?, " +
                "hora_cita = ?, " +
                "num_habitacion = ? " +
                "WHERE num_cita = ?";

        return conexionBD.ejecutarInstruccionLMD(

                sql,
                cita.getIdPaciente(),
                cita.getIdMedico(),
                cita.getFechaCita(),
                cita.getHoraCita(),
                cita.getNumHabitacion(),
                cita.getNumCita()

        );

    }

    @Override
    public boolean eliminar(int numCita) {

        String sql =

                "DELETE FROM citas " +
                "WHERE num_cita = ?";

        return conexionBD.ejecutarInstruccionLMD(

                sql,

                numCita

        );

    }

    @Override
    public Cita buscarPorId(int numCita) {

        String sql =

                "SELECT * " +
                "FROM citas " +
                "WHERE num_cita = ?";

        ResultSet rs =
                conexionBD.ejecutarConsultaSQL(
                        sql,
                        numCita
                );

        try {

            if (rs != null && rs.next()) {

                return new Cita(

                        rs.getInt("num_cita"),
                        rs.getInt("id_paciente"),
                        rs.getInt("id_medico"),
                        rs.getString("fecha_cita"),
                        rs.getString("hora_cita"),
                        rs.getInt("num_habitacion")

                );

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return null;

    }

    @Override
    public List<Cita> obtenerTodos() {

        List<Cita> lista = new ArrayList<>();

        String sql =

                "SELECT * " +
                "FROM citas " +
                "ORDER BY num_cita";

        ResultSet rs =
                conexionBD.ejecutarConsultaSQL(sql);
        try {

            while (rs != null && rs.next()) {

                lista.add(

                        new Cita(

                                rs.getInt("num_cita"),
                                rs.getInt("id_paciente"),
                                rs.getInt("id_medico"),
                                rs.getString("fecha_cita"),
                                rs.getString("hora_cita"),
                                rs.getInt("num_habitacion")

                        )

                );

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }
        return lista;
    }

    @Override
    public List<Cita> buscar(String texto) {

        List<Cita> lista = new ArrayList<>();

        String sql =

                "SELECT * " +
                "FROM citas " +
                "WHERE CAST(num_cita AS TEXT) LIKE ? " +
                "OR CAST(id_paciente AS TEXT) LIKE ? " +
                "OR CAST(id_medico AS TEXT) LIKE ? " +
                "OR fecha_cita LIKE ? " +
                "OR hora_cita LIKE ? " +
                "OR CAST(num_habitacion AS TEXT) LIKE ? " +
                "ORDER BY num_cita";

        String filtro = "%" + texto + "%";

        ResultSet rs =
                conexionBD.ejecutarConsultaSQL(
                        sql,
                        filtro,
                        filtro,
                        filtro,
                        filtro,
                        filtro,
                        filtro

                );

        try {

            while (rs != null && rs.next()) {
                lista.add(
                        new Cita(
                                rs.getInt("num_cita"),
                                rs.getInt("id_paciente"),
                                rs.getInt("id_medico"),
                                rs.getString("fecha_cita"),
                                rs.getString("hora_cita"),
                                rs.getInt("num_habitacion")
                        )
                );

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }
        return lista;
    }

    public void cerrarConexion() {

        conexionBD.cerrarConexion();

    }

}