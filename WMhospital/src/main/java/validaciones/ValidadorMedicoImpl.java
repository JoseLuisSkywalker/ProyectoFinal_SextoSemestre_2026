/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validaciones;

import intefaces.ValidadorMedico;
import modelo.Medico;

/**
 *
 * @author josesanchez
 */
public class ValidadorMedicoImpl implements ValidadorMedico {

    @Override
    public void validar(Medico medico) throws Exception {

        if (medico == null) {
            throw new Exception("El médico no puede ser nulo.");
        }

        if (medico.getIdMedico() <= 0) {
            throw new Exception("El ID del médico debe ser mayor a cero.");
        }

        if (medico.getNombre() == null || medico.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre del médico es obligatorio.");
        }

        if (medico.getApellido() == null || medico.getApellido().trim().isEmpty()) {
            throw new Exception("El apellido del médico es obligatorio.");
        }

        if (medico.getNumeroDepartamento() <= 0) {
            throw new Exception("El número de departamento debe ser mayor a cero.");
        }

        if (medico.getDireccion() == null || medico.getDireccion().trim().isEmpty()) {
            throw new Exception("La dirección es obligatoria.");
        }

        if (medico.getTelefono() == null || medico.getTelefono().trim().isEmpty()) {
            throw new Exception("El teléfono es obligatorio.");
        }

        String telefonoLimpio = medico.getTelefono().replaceAll("[^0-9]", "");

        if (telefonoLimpio.length() != 10) {
            throw new Exception("El teléfono debe contener exactamente 10 dígitos.");
        }
        
        if (medico.getTelefono().equals("000 - 000 - 0000")) {

            throw new Exception("Ingrese un número de teléfono válido.");

        }
    }
}