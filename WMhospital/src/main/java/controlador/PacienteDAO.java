/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;


import conexion.ConexionBD;
import intefaces.InterfazPacienteDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Paciente;
/**
 *
 * @author josesanchez
 */
public class PacienteDAO implements InterfazPacienteDAO{
    
    private final ConexionBD conexionBD;


    public PacienteDAO() {
        conexionBD = ConexionBD.getInstance();
        conexionBD.abrirConexion();
    }
    
    @Override
    public boolean insertar(Paciente paciente) {
        String sql = "INSERT INTO pacientes " + "(nombre, apellido, telefono, fecha_nacimiento, sexo, estado_civil, id_medico) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        return conexionBD.ejecutarInstruccionLMD(sql, paciente.getNombre(), paciente.getApellido(), paciente.getTelefono(),
                paciente.getFechaNacimiento(), paciente.getSexo(), paciente.getEstadoCivil(),paciente.getIdMedico()
        );        
    }

    @Override
    public boolean actualizar(Paciente paciente) {
        
        String sql = "UPDATE pacientes " + "SET nombre = ?, " + "apellido = ?, " + "telefono = ?, " +
                "fecha_nacimiento = ?, " + "sexo = ?, " +  "estado_civil = ?, " + "id_medico = ? " +
                "WHERE id_paciente = ?";

        return conexionBD.ejecutarInstruccionLMD(sql, paciente.getNombre(), paciente.getApellido(), paciente.getTelefono(),
                paciente.getFechaNacimiento(), paciente.getSexo(), paciente.getEstadoCivil(),paciente.getIdMedico(), paciente.getIdPaciente()
        ); 
    }

    @Override
    public boolean eliminar(int idPaciente) {
        String sql = "DELETE FROM pacientes " + "WHERE id_paciente = ?";

        return conexionBD.ejecutarInstruccionLMD(sql, idPaciente);
    }

    @Override
    public Paciente buscarPorId(int idPaciente) {
        String sql = "SELECT * " + "FROM pacientes " + "WHERE id_paciente = ?";

        ResultSet rs = conexionBD.ejecutarConsultaSQL(sql, idPaciente);

        try {
            if (rs != null && rs.next()) {
                return new Paciente(rs.getInt("id_paciente"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("telefono"),
                        rs.getString("fecha_nacimiento"), rs.getString("sexo"), rs.getString("estado_civil"), rs.getString("fecha_registro"),
                        rs.getInt("id_medico")
                );

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;        
        
    }

    @Override
    public List<Paciente> obtenerTodos() {
        List<Paciente> lista = new ArrayList<>();

        String sql = "SELECT * " + "FROM pacientes " + "ORDER BY id_paciente";

        ResultSet rs = conexionBD.ejecutarConsultaSQL(sql);

        try {

            while (rs != null && rs.next()) {
                lista.add(new Paciente(rs.getInt("id_paciente"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("telefono"),
                                rs.getString("fecha_nacimiento"), rs.getString("sexo"), rs.getString("estado_civil"), rs.getString("fecha_registro"),
                                rs.getInt("id_medico"))
                );

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }
        return lista;        
    }

    @Override
    public List<Paciente> buscar(String texto) {
        List<Paciente> lista = new ArrayList<>();

        String filtro = "%" + texto + "%";

        String sql =
                "SELECT * " +
                "FROM pacientes " +
                "WHERE CAST(id_paciente AS TEXT) LIKE ? " +
                "OR nombre LIKE ? " +
                "OR apellido LIKE ? " +
                "OR telefono LIKE ? " +
                "OR fecha_nacimiento LIKE ? " +
                "OR sexo LIKE ? " +
                "OR estado_civil LIKE ? " +
                "OR CAST(id_medico AS TEXT) LIKE ? " +
                "ORDER BY id_paciente";

        ResultSet rs = conexionBD.ejecutarConsultaSQL(sql, filtro, filtro, filtro, filtro, filtro, filtro, filtro, filtro);

        try {
            while (rs != null && rs.next()) {
                lista.add(new Paciente(rs.getInt("id_paciente"), rs.getString("nombre"), rs.getString("apellido"),
                                rs.getString("telefono"), rs.getString("fecha_nacimiento"), rs.getString("sexo"),
                                rs.getString("estado_civil"), rs.getString("fecha_registro"), rs.getInt("id_medico")));

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
