/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author josesanchez
 */

public class VistaParticion {

    private int numCita;
    private int idPaciente;
    private int idMedico;
    private String fechaCita;
    private String horaCita;
    private int numHabitacion;
    private String tipo;

    public VistaParticion() {
        
    }

    public VistaParticion(int numCita,int idPaciente,int idMedico,String fechaCita,String horaCita,int numHabitacion,String tipo) {

        this.numCita = numCita;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.fechaCita = fechaCita;
        this.horaCita = horaCita;
        this.numHabitacion = numHabitacion;
        this.tipo = tipo;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}