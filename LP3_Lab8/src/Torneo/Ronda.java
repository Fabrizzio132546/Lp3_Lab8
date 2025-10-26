/*************************************************************************************
ARCHIVO	: Ronda.java
FECHA	: 23/10/2025
*************************************************************************************/

package Torneo;

public class Ronda {

    private int idRonda;
    private int idTorneo;
    private String nombreRonda;
    private int numeroRonda;

    public Ronda() {
    }

    public Ronda(int idRonda, int idTorneo, String nombreRonda, int numeroRonda) {
        this.idRonda = idRonda;
        this.idTorneo = idTorneo;
        this.nombreRonda = nombreRonda;
        this.numeroRonda = numeroRonda;
    }

    public int getIdRonda() {  return idRonda; }
    public void setIdRonda(int idRonda) {  this.idRonda = idRonda; }
    public int getIdTorneo() {  return idTorneo; }
    public void setIdTorneo(int idTorneo) { this.idTorneo = idTorneo; }
    public String getNombreRonda() {  return nombreRonda; }
    public void setNombreRonda(String nombreRonda) {  this.nombreRonda = nombreRonda; }
    public int getNumeroRonda() { return numeroRonda; }
    public void setNumeroRonda(int numeroRonda) { this.numeroRonda = numeroRonda; }

    @Override
    public String toString() {
        return "Ronda{" +
                "idRonda=" + idRonda +
                ", idTorneo=" + idTorneo +
                ", nombreRonda='" + nombreRonda + '\'' +
                ", numeroRonda=" + numeroRonda +
                '}';
    }
}