package modelo;

public class VistaCita {

    private String fechaCita;
    private String horaCita;
    private String nombrePaciente;
    private String nombreMedico;

    public VistaCita(String fechaCita,
                     String horaCita,
                     String nombrePaciente,
                     String nombreMedico) {

        this.fechaCita = fechaCita;
        this.horaCita = horaCita;
        this.nombrePaciente = nombrePaciente;
        this.nombreMedico = nombreMedico;
    }

    public String getFechaCita() {
        return fechaCita;
    }

    public String getHoraCita() {
        return horaCita;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }
}