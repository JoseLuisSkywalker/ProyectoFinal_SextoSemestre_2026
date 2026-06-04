package conexion;

import funciones.FuncionCalcularEdad;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBD {

    private Connection conexion;
    
    private static ConexionBD instancia;

    
    private final String URL = "jdbc:sqlite:wellmeadows_hospital.db";
    
    
    //------------------------- PATRON DE DISENO SINGELTON -------------
    private ConexionBD() {

    }
    
    public static ConexionBD getInstance() {

        if (instancia == null) {

            instancia = new ConexionBD();

        }

    return instancia;

    }

    
    public Connection abrirConexion() {

        try {
            Class.forName("org.sqlite.JDBC");

            
            if (conexion == null || conexion.isClosed()) {

                conexion = DriverManager.getConnection(URL);
                
                FuncionCalcularEdad.registrar(conexion);
                

                // Activar Foreign Keys
                Statement stmt = conexion.createStatement();
                stmt.execute("PRAGMA foreign_keys = ON");
                stmt.close();

                System.out.println("Conexión exitosa a SQLite.");
            }

        } catch (ClassNotFoundException e) {

            System.out.println("Error: No se encontró el driver JDBC de SQLite.");
            e.printStackTrace();

        } catch (SQLException e) {

            System.out.println("Error al conectar con la base de datos.");
            e.printStackTrace();
        }
        
        System.out.println(
            "BD: " +
            new java.io.File("wellmeadows_hospital.db").getAbsolutePath()
        );

        return conexion;
    }

    
    public void cerrarConexion() {

        if (conexion != null) {

            try {

                if (!conexion.isClosed()) {
                    conexion.close();
                    System.out.println("Conexión cerrada correctamente.");
                }

            } catch (SQLException e) {

                System.out.println("Error al cerrar la conexión.");
                e.printStackTrace();
            }
        }
    }

    
//-----------------------------------------–––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––    
//-----------------------------------------–––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––
//----------------------------------------- TRANSACCION EN EL LMD  –––––––––––––––––––––––––––––––––––––––––
//-----------------------------------------–––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––
//-----------------------------------------–––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––
//-----------------------------------------–––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––    

    
    public boolean ejecutarInstruccionLMD(String sql, Object... datos) {

        try {

            if (conexion == null || conexion.isClosed()) {

                abrirConexion();
            }

            conexion.setAutoCommit(false);

            try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {

                for (int i = 0; i < datos.length; i++) {

                    pstmt.setObject(i + 1, datos[i]);
                }

                int filasAfectadas = pstmt.executeUpdate();
                
                conexion.commit();

                return filasAfectadas > 0;

            } catch (SQLException e) {

                conexion.rollback();
                System.out.println(

                        "La Transacción fue cancelada, se hará un ROLLBACK!!!"

                );

                e.printStackTrace();

                return false;

            } finally {

                conexion.setAutoCommit(true);

            }

        } catch (SQLException e) {

            e.printStackTrace();

            return false;

        }

    }

    
    public ResultSet ejecutarConsultaSQL(String sql, Object... datos) {

        try {

            if (conexion == null || conexion.isClosed()) {

                abrirConexion();

            }

            PreparedStatement pstmt = conexion.prepareStatement(sql);

            for (int i = 0; i < datos.length; i++) {

                pstmt.setObject(i + 1, datos[i]);

            }

            return pstmt.executeQuery();

        } catch (SQLException e) {

            System.out.println("Error al ejecutar la consulta SQL");

            e.printStackTrace();

            return null;

        }

    }

    public Connection getConexion() {
        return conexion;
    }
}