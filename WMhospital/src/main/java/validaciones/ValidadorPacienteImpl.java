package validaciones;

import intefaces.ValidadorPaciente;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import modelo.Paciente;

public class ValidadorPacienteImpl implements ValidadorPaciente {

    @Override
    public void validar(Paciente paciente) throws Exception {

        if (paciente == null) {
            throw new Exception("El paciente es nulo.");
        }

        if (paciente.getNombre() == null
                || paciente.getNombre().trim().isEmpty()) {

            throw new Exception("El nombre es obligatorio.");
        }

        if (paciente.getApellido() == null
                || paciente.getApellido().trim().isEmpty()) {

            throw new Exception("El apellido es obligatorio.");
        }

        if (paciente.getTelefono() == null
                || paciente.getTelefono().trim().isEmpty()) {

            throw new Exception("El teléfono es obligatorio.");
        }
        if (paciente.getTelefono().equals("000 - 000 - 0000")) {

            throw new Exception("Ingrese un número de teléfono válido.");

        }

        if (paciente.getFechaNacimiento() == null || paciente.getFechaNacimiento().trim().isEmpty()) {

        throw new Exception("La fecha de nacimiento es obligatoria.");

        }

        try {
            DateTimeFormatter formatter =

            DateTimeFormatter.ofPattern("dd-MM-uuuu")

                             .withResolverStyle(ResolverStyle.STRICT);

            LocalDate fechaNacimiento = LocalDate.parse( paciente.getFechaNacimiento(), formatter);

            LocalDate hoy = LocalDate.now();

            if (fechaNacimiento.isAfter(hoy)) {

                throw new Exception("La fecha de nacimiento no puede ser posterior a la fecha actual.");

            }

            if (fechaNacimiento.isBefore(hoy.minusYears(120))) {
            
                throw new Exception("La fecha de nacimiento no es válida porque paciente supera expectativas de edad.");

            }

        } catch (DateTimeParseException e) {

            throw new Exception("Ingrese una fecha correcta (DD-MM-AAAA)");

        }

        if (paciente.getSexo() == null
                || paciente.getSexo().trim().isEmpty()) {

            throw new Exception("El sexo es obligatorio.");
        }

        if (paciente.getEstadoCivil() == null
                || paciente.getEstadoCivil().trim().isEmpty()) {

            throw new Exception("El estado civil es obligatorio.");
        }

        if (paciente.getIdMedico() <= 0) {

            throw new Exception("Debe seleccionar un médico válido.");
        }
    }
}