package modelo;

import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResultSetTableModel extends AbstractTableModel {

    private Connection conexion;
    private ResultSetMetaData metaDatos;

    private List<Object[]> datos;
    private int numeroDeFilas;

    private boolean conectadoALaBaseDeDatos = false;

    // Constructor consulta normal
    public ResultSetTableModel(String controlador, String url, String consulta)
            throws SQLException, ClassNotFoundException {

        Class.forName(controlador);

        conexion = DriverManager.getConnection(url);

        conectadoALaBaseDeDatos = true;

        establecerConsulta(consulta);
    }

    // Constructor consulta con parámetros
    public ResultSetTableModel(String controlador, String url,
                               String consulta, Object... params)
            throws SQLException, ClassNotFoundException {

        Class.forName(controlador);

        conexion = DriverManager.getConnection(url);

        conectadoALaBaseDeDatos = true;

        establecerConsultaConParametros(consulta, params);
    }

    @Override
    public Class<?> getColumnClass(int columna) {

        if (!conectadoALaBaseDeDatos) {
            throw new IllegalStateException("No hay conexión a la base de datos");
        }

        try {

            String nombreClase = metaDatos.getColumnClassName(columna + 1);

            switch (nombreClase) {
                case "java.lang.Integer":
                    return Integer.class;

                case "java.lang.Double":
                    return Double.class;

                default:
                    return String.class;
            }

        } catch (SQLException e) {
            return Object.class;
        }
    }

    @Override
    public int getColumnCount() {

        if (!conectadoALaBaseDeDatos) {
            throw new IllegalStateException("No hay conexión a la base de datos");
        }

        try {
            return metaDatos.getColumnCount();
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public String getColumnName(int columna) {

        if (!conectadoALaBaseDeDatos) {
            throw new IllegalStateException("No hay conexión a la base de datos");
        }

        try {
            return metaDatos.getColumnName(columna + 1);
        } catch (SQLException e) {
            return "";
        }
    }

    @Override
    public int getRowCount() {

        if (!conectadoALaBaseDeDatos) {
            throw new IllegalStateException("No hay conexión a la base de datos");
        }

        return numeroDeFilas;
    }

    @Override
    public Object getValueAt(int fila, int columna) {

        if (!conectadoALaBaseDeDatos) {
            throw new IllegalStateException("No hay conexión a la base de datos");
        }

        return datos.get(fila)[columna];
    }

    // Consulta normal
    public void establecerConsulta(String consulta) throws SQLException {

        Statement stmt = conexion.createStatement();

        ResultSet rs = stmt.executeQuery(consulta);

        cargarDatos(rs);

        rs.close();
        stmt.close();
    }

    // Consulta con parámetros
    public void establecerConsultaConParametros(String consulta,
                                                Object... params)
            throws SQLException {

        PreparedStatement ps = conexion.prepareStatement(consulta);

        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }

        ResultSet rs = ps.executeQuery();

        cargarDatos(rs);

        rs.close();
        ps.close();
    }

    // Cargar resultados en memoria
    private void cargarDatos(ResultSet rs) throws SQLException {

        metaDatos = rs.getMetaData();

        datos = new ArrayList<>();

        int columnas = metaDatos.getColumnCount();

        while (rs.next()) {

            Object[] fila = new Object[columnas];

            for (int i = 0; i < columnas; i++) {
                fila[i] = rs.getObject(i + 1);
            }

            datos.add(fila);
        }

        numeroDeFilas = datos.size();

        fireTableStructureChanged();
    }

    // Cerrar conexión
    public void desconectarDeLaBaseDeDatos() {

        try {

            if (conexion != null) {
                conexion.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        conectadoALaBaseDeDatos = false;
    }
}