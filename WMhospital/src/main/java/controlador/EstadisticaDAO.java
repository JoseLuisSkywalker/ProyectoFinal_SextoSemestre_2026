/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import conexion.ConexionBD;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.EstadisticaCita;

public class EstadisticaDAO {

    private final ConexionBD conexionBD;

    public EstadisticaDAO() {

        conexionBD = new ConexionBD();

    }

    public List<EstadisticaCita> obtenerCitasPorMes() {

        List<EstadisticaCita> lista = new ArrayList<>();

        String sql =
                "SELECT anio, mes, total_citas " +
                "FROM estadisticas_citas " +
                "ORDER BY anio, mes";

        try {

            conexionBD.abrirConexion();
            ResultSet rs = conexionBD.ejecutarConsultaSQL(sql);

            while (rs.next()) {
                
                lista.add(new EstadisticaCita(rs.getInt("anio"),rs.getInt("mes"),rs.getInt("total_citas")));

            }

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {

            conexionBD.cerrarConexion();

        }

        return lista;

    }

}