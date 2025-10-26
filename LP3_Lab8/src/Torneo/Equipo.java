/*************************************************************************************
ARCHIVO	: Equipo.java
FECHA	: 23/10/2025
*************************************************************************************/

package Torneo;

public class Equipo {

    private int idEquipo;
    private String nombreEquipo;
    private String fechaCreacion; 

    public Equipo() {
    }

    public Equipo(int idEquipo, String nombreEquipo, String fechaCreacion) {
        this.idEquipo = idEquipo;
        this.nombreEquipo = nombreEquipo;
        this.fechaCreacion = fechaCreacion;
    }

    public int getIdEquipo() { return idEquipo; }
    public void setIdEquipo(int idEquipo) {  this.idEquipo = idEquipo; }
    public String getNombreEquipo() { return nombreEquipo; }
    public void setNombreEquipo(String nombreEquipo) { this.nombreEquipo = nombreEquipo; }
    public String getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(String fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    @Override
    public String toString() {
        return "Equipo{" +
                "idEquipo=" + idEquipo +
                ", nombreEquipo='" + nombreEquipo + '\'' +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                '}';
    }
}