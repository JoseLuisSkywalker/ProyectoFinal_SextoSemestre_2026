package modelo;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author josesanchez
 */
public class EstadisticaCita {

    private int anio;
    private int mes;
    private int totalCitas;
    public EstadisticaCita(int anio, int mes, int totalCitas) {

        this.anio = anio;
        this.mes = mes;
        this.totalCitas = totalCitas;

    }

    public int getAnio() {
        return anio;
    }

    public int getMes() {
        return mes;
    }

    public int getTotalCitas() {
        return totalCitas;
    }

}