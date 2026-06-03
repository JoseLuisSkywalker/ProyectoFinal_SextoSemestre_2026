package validaciones;

import intefaces.ValidadorPaciente;
import intefaces.ValidadorPaciente;
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

        if (paciente.getFechaNacimiento() == null
                || paciente.getFechaNacimiento().trim().isEmpty()) {

            throw new Exception("La fecha de nacimiento es obligatoria.");
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