/*************************************************************************************
ARCHIVO	: Participante.java
FECHA	: 23/10/2025
*************************************************************************************/
package Torneo;
public class Participante {

    private int idParticipante;
    private int idTorneo;
    private Integer idJugador; 
    private Integer idEquipo;
    private String fechaInscripcion; 

    public Participante() {
    }

    public Participante(int idParticipante, int idTorneo, Integer idJugador, Integer idEquipo, String fechaInscripcion) {
        this.idParticipante = idParticipante;
        this.idTorneo = idTorneo;
        this.idJugador = idJugador;
        this.idEquipo = idEquipo;
        this.fechaInscripcion = fechaInscripcion;
    }

    public int getIdParticipante() { return idParticipante; }
    public void setIdParticipante(int idParticipante) { this.idParticipante = idParticipante; }
    public int getIdTorneo() { return idTorneo; }
    public void setIdTorneo(int idTorneo) {  this.idTorneo = idTorneo; }
    public Integer getIdJugador() { return idJugador;}
    public void setIdJugador(Integer idJugador) { this.idJugador = idJugador; }
    public Integer getIdEquipo() { return idEquipo;}
    public void setIdEquipo(Integer idEquipo) {  this.idEquipo = idEquipo; }
    public String getFechaInscripcion() { return fechaInscripcion; }
    public void setFechaInscripcion(String fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }

    @Override
    public String toString() {
        return "Participante{" +
                "idParticipante=" + idParticipante +
                ", idTorneo=" + idTorneo +
                ", idJugador=" + idJugador +
                ", idEquipo=" + idEquipo +
                ", fechaInscripcion='" + fechaInscripcion + '\'' +
                '}';
    }
}