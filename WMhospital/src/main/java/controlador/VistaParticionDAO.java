/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 *
 * @author josesanchez
 */

import conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.VistaParticion;

public class VistaParticionDAO {

    private Connection conexion;

    public VistaParticionDAO() {

        ConexionBD bd = ConexionBD.getInstance();
        conexion = bd.abrirConexion();
    }

    public List<VistaParticion> obtenerTodos() {

        List<VistaParticion> lista = new ArrayList<>();

        String sql = "SELECT * FROM vista_particion_citas";

        try {

            PreparedStatement ps =
                    conexion.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                VistaParticion vista =
                        new VistaParticion();

                vista.setNumCita(
                        rs.getInt("num_cita")
                );

                vista.setIdPaciente(
                        rs.getInt("id_paciente")
                );

                vista.setIdMedico(
                        rs.getInt("id_medico")
                );

                vista.setFechaCita(
                        rs.getString("fecha_cita")
                );

                vista.setHoraCita(
                        rs.getString("hora_cita")
                );

                vista.setNumHabitacion(
                        rs.getInt("num_habitacion")
                );

                vista.setTipo(
                        rs.getString("tipo")
                );

                lista.add(vista);
            }

            rs.close();
            ps.close();

        } catch (Exception e) {

            System.out.println(
                    "Error al obtener datos de la partición"
            );

            e.printStackTrace();
        }

        return lista;
    }

    public void cerrarConexion() {

        try {

            if (conexion != null) {

                conexion.close();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}