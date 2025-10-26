/*************************************************************************************
ARCHIVO	: EquipoMiembro.java
FECHA	: 23/10/2025
*************************************************************************************/
package Ejercicio1_Torneo;
public class EquipoMiembro {

    private int idEquipo;
    private int idJugador;

    public EquipoMiembro() { }
    public EquipoMiembro(int idEquipo, int idJugador) {
        this.idEquipo = idEquipo;
        this.idJugador = idJugador;
    }

    public int getIdEquipo() {  return idEquipo; }
    public void setIdEquipo(int idEquipo) {  this.idEquipo = idEquipo; }
    public int getIdJugador() {  return idJugador; }
    public void setIdJugador(int idJugador) {  this.idJugador = idJugador; }

    @Override
    public String toString() {
        return "EquipoMiembro{" +
                "idEquipo=" + idEquipo +
                ", idJugador=" + idJugador +
                '}';
    }
}