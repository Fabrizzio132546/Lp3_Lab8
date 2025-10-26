/*************************************************************************************
ARCHIVO	: Torneo.java
FECHA	: 23/10/2025
*************************************************************************************/

package Torneo;

public class Torneo {

    private int idTorneo;
    private String nombre;
    private String tipoTorneo; 
    private String estado;    
    private String fechaInicio;
    
    private Integer idGanadorParticipante;

    public Torneo() {
    }

    public Torneo(int idTorneo, String nombre, String tipoTorneo, String estado, String fechaInicio, Integer idGanadorParticipante) {
        this.idTorneo = idTorneo;
        this.nombre = nombre;
        this.tipoTorneo = tipoTorneo;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.idGanadorParticipante = idGanadorParticipante;
    }

    public int getIdTorneo() { return idTorneo; }
    public void setIdTorneo(int idTorneo) {this.idTorneo = idTorneo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTipoTorneo() { return tipoTorneo;}
    public void setTipoTorneo(String tipoTorneo) { this.tipoTorneo = tipoTorneo;}
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }
    public Integer getIdGanadorParticipante() { return idGanadorParticipante; }
    public void setIdGanadorParticipante(Integer idGanadorParticipante) { this.idGanadorParticipante = idGanadorParticipante; }
    @Override
    public String toString() {
        return "Torneo{" +
                "idTorneo=" + idTorneo +
                ", nombre='" + nombre + '\'' +
                ", tipoTorneo='" + tipoTorneo + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaInicio='" + fechaInicio + '\'' +
                ", idGanadorParticipante=" + idGanadorParticipante +
                '}';
    }
}