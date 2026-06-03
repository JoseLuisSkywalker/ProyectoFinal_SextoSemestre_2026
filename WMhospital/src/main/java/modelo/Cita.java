package modelo;

/**

 *

 * @author josesanchez

 */

public class Cita {

    private int numCita;

    private int idPaciente;

    private int idMedico;

    private String fechaCita;

    private String horaCita;

    private int numHabitacion;

    public Cita() {

    }

    public Cita(

            int numCita,

            int idPaciente,

            int idMedico,

            String fechaCita,

            String horaCita,

            int numHabitacion

    ) {

        this.numCita = numCita;

        this.idPaciente = idPaciente;

        this.idMedico = idMedico;

        this.fechaCita = fechaCita;

        this.horaCita = horaCita;

        this.numHabitacion = numHabitacion;

    }

    public int getNumCita() {

        return numCita;

    }

    public void setNumCita(int numCita) {

        this.numCita = numCita;

    }

    public int getIdPaciente() {

        return idPaciente;

    }

    public void setIdPaciente(int idPaciente) {

        this.idPaciente = idPaciente;

    }

    public int getIdMedico() {

        return idMedico;

    }

    public void setIdMedico(int idMedico) {

        this.idMedico = idMedico;

    }

    public String getFechaCita() {

        return fechaCita;

    }

    public void setFechaCita(String fechaCita) {

        this.fechaCita = fechaCita;

    }

    public String getHoraCita() {

        return horaCita;

    }

    public void setHoraCita(String horaCita) {

        this.horaCita = horaCita;

    }

    public int getNumHabitacion() {

        return numHabitacion;

    }

    public void setNumHabitacion(int numHabitacion) {

        this.numHabitacion = numHabitacion;

    }

}