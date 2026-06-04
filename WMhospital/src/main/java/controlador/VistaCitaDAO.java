package controlador;

import conexion.ConexionBD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.VistaCita;

public class VistaCitaDAO {

    private final ConexionBD conexionBD;

    public VistaCitaDAO() {

        conexionBD = ConexionBD.getInstance();
        conexionBD.abrirConexion();

    }
    public List<VistaCita> buscarPorFecha(String fecha) {

        List<VistaCita> lista = new ArrayList<>();

        String sql =
                "SELECT * " +
                "FROM vista_citas_completa " +
                "WHERE fecha_cita = ?";
        ResultSet rs =
                conexionBD.ejecutarConsultaSQL(
                        sql,
                        fecha
                );

        try {

            while (rs != null && rs.next()) {

                lista.add(
                        new VistaCita(
                                rs.getString("fecha_cita"),
                                rs.getString("hora_cita"),
                                rs.getString("nombre_paciente"),
                                rs.getString("nombre_medico")
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