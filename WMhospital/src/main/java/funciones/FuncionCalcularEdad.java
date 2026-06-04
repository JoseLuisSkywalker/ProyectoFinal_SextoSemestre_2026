package funciones;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author josesanchez
 */


import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import org.sqlite.Function;



public class FuncionCalcularEdad extends Function {
    
    public static void registrar(Connection conexion) throws SQLException {
       
        Function.create(conexion, "CALCULAR_EDAD", new FuncionCalcularEdad());
    }

    
    @Override
    protected void xFunc() throws SQLException {
        
        String fechaNacimiento = value_text(0);

        if (fechaNacimiento == null || fechaNacimiento.isBlank()) {
            result(0);
            return;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate nacimiento = LocalDate.parse(fechaNacimiento, formatter);

            int edad = Period.between(nacimiento, LocalDate.now()).getYears();

            
            result(edad);

        } catch (Exception e) {
            
            throw new SQLException("Error al parsear la fecha de nacimiento: " + fechaNacimiento, e);
        }
    }
}