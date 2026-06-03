/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validaciones;

import intefaces.ValidadorCita;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import modelo.Cita;

/**
 *
 * @author josesanchez
 */
public class ValidadorCitaImpl implements ValidadorCita {

    @Override
    public void validar(Cita cita) throws Exception {

        if (cita == null) {
            throw new Exception("La cita es nula.");
        }

        if (cita.getIdPaciente() <= 0) {
            throw new Exception("Debe seleccionar un paciente válido.");
        }

        if (cita.getIdMedico() <= 0) {
            throw new Exception("Debe seleccionar un médico válido.");
        }

        if (cita.getFechaCita() == null
                || cita.getFechaCita().trim().isEmpty()) {
            throw new Exception("La fecha de la cita es obligatoria.");
        }

        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu").withResolverStyle(ResolverStyle.STRICT);
            LocalDate fechaCita = LocalDate.parse(cita.getFechaCita(), formatter);

            LocalDate hoy = LocalDate.now();

            if (fechaCita.isBefore(hoy)) {

                throw new Exception("La fecha de la cita no puede ser anterior a la fecha actual.");

            }

        } catch (DateTimeParseException e) {

            throw new Exception("Ingrese una fecha correcta (DD-MM-AAAA)");

        }

        if (cita.getHoraCita() == null

                || cita.getHoraCita().trim().isEmpty()) {

            throw new Exception("La hora de la cita es obligatoria.");

        }

        try {

            String horaCompleta = cita.getHoraCita();

            String[] partes = horaCompleta.split(" ");

            if (partes.length != 2) {

                throw new Exception();

            }

            String[] horaMinuto = partes[0].split(":");

            if (horaMinuto.length != 2) {

                throw new Exception();

            }

            int hora = Integer.parseInt(horaMinuto[0]);

            int minuto = Integer.parseInt(horaMinuto[1]);

            String periodo = partes[1];

            if (hora < 1 || hora > 12) {

                throw new Exception();

            }

            if (minuto < 0 || minuto > 59) {

                throw new Exception();

            }

            if (!periodo.equals("a.m.")
                    
                    && !periodo.equals("p.m.")) {
                throw new Exception();

            }

        } catch (Exception e) {

            throw new Exception("Ingrese una hora válida.");

        }

        if (cita.getNumHabitacion() <= 0) {

            throw new Exception("Debe seleccionar una habitación válida.");
        }
    }

}