/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import conexion.ConexionBD;
import intefaces.InterfazMedicoDAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Medico;

/**
 *
 * @author josesanchez
 */
public class MedicoDAO implements InterfazMedicoDAO {

    private final ConexionBD conexionBD;

    public MedicoDAO() {

        conexionBD = new ConexionBD();

        conexionBD.abrirConexion();

    }

    @Override

    public boolean insertar(Medico medico) {

        String sql =

                "INSERT INTO medicos_cabecera " +

                "(id_medico, nombre, apellido, numero_departamento, direccion, telefono) " +

                "VALUES (?, ?, ?, ?, ?, ?)";

        return conexionBD.ejecutarInstruccionLMD(

                sql,
                
                medico.getIdMedico(),

                medico.getNombre(),

                medico.getApellido(),

                medico.getNumeroDepartamento(),

                medico.getDireccion(),

                medico.getTelefono()

        );

    }

    @Override

    public boolean actualizar(Medico medico) {

        String sql =

                "UPDATE medicos_cabecera " +

                "SET nombre = ?, " +

                "apellido = ?, " +

                "numero_departamento = ?, " +

                "direccion = ?, " +

                "telefono = ? " +

                "WHERE id_medico = ?";

        return conexionBD.ejecutarInstruccionLMD(

                sql,

                medico.getNombre(),

                medico.getApellido(),

                medico.getNumeroDepartamento(),

                medico.getDireccion(),

                medico.getTelefono(),

                medico.getIdMedico()

        );

    }

    @Override

    public boolean eliminar(int idMedico) {

        String sql =

                "DELETE FROM medicos_cabecera " +

                "WHERE id_medico = ?";

        return conexionBD.ejecutarInstruccionLMD(

                sql,

                idMedico

        );

    }

    @Override

    public Medico buscarPorId(int idMedico) {

        String sql =

                "SELECT * " +

                "FROM medicos_cabecera " +

                "WHERE id_medico = ?";

        ResultSet rs = conexionBD.ejecutarConsultaSQL(sql, idMedico);

        try {

            if (rs != null && rs.next()) {

                return new Medico(

                        rs.getInt("id_medico"),

                        rs.getString("nombre"),

                        rs.getString("apellido"),

                        rs.getInt("numero_departamento"),

                        rs.getString("direccion"),

                        rs.getString("telefono")

                );

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return null;

    }

    @Override

    public List<Medico> obtenerTodos() {

        List<Medico> lista = new ArrayList<>();

        String sql =

                "SELECT * " +

                "FROM medicos_cabecera " +

                "ORDER BY id_medico";

        ResultSet rs = conexionBD.ejecutarConsultaSQL(sql);

        try {

            while (rs != null && rs.next()) {

                lista.add(

                        new Medico(

                                rs.getInt("id_medico"),

                                rs.getString("nombre"),

                                rs.getString("apellido"),

                                rs.getInt("numero_departamento"),

                                rs.getString("direccion"),

                                rs.getString("telefono")

                        )

                );

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return lista;

    }

    @Override

    public List<Medico> buscar(String texto) {

        List<Medico> lista = new ArrayList<>();

        String sql =

                "SELECT * " +

                "FROM medicos_cabecera " +

                "WHERE CAST(id_medico AS TEXT) LIKE ? " +

                "OR nombre LIKE ? " +

                "OR apellido LIKE ? " +

                "OR CAST(numero_departamento AS TEXT) LIKE ? " +

                "OR direccion LIKE ? " +

                "OR telefono LIKE ? " +

                "ORDER BY id_medico";

        String filtro = "%" + texto + "%";

        ResultSet rs = conexionBD.ejecutarConsultaSQL(

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

                        new Medico(

                                rs.getInt("id_medico"),

                                rs.getString("nombre"),

                                rs.getString("apellido"),

                                rs.getInt("numero_departamento"),

                                rs.getString("direccion"),

                                rs.getString("telefono")

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