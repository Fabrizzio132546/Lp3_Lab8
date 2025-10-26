/*************************************************************************************
ARCHIVO	: Partido.java
FECHA	: 23/10/2025
*************************************************************************************/
package Ejercicio1_Torneo;
public class Partido {
    private int idPartido;
    private int idRonda;
    private int idParticipante1;
    private int idParticipante2;
    private int resultadoP1;
    private int resultadoP2;
   
    private Integer idGanador; 
    
    private String estado; 
    private String fechaPartido; 

    public Partido() {
    }

    public Partido(int idPartido, int idRonda, int idParticipante1, int idParticipante2, int resultadoP1, int resultadoP2, Integer idGanador, String estado, String fechaPartido) {
        this.idPartido = idPartido;
        this.idRonda = idRonda;
        this.idParticipante1 = idParticipante1;
        this.idParticipante2 = idParticipante2;
        this.resultadoP1 = resultadoP1;
        this.resultadoP2 = resultadoP2;
        this.idGanador = idGanador;
        this.estado = estado;
        this.fechaPartido = fechaPartido;
    }

    public int getIdPartido() { return idPartido; }
    public void setIdPartido(int idPartido) {  this.idPartido = idPartido; }
    public int getIdRonda() {  return idRonda; }
    public void setIdRonda(int idRonda) { this.idRonda = idRonda; }
    public int getIdParticipante1() {  return idParticipante1; }
    public void setIdParticipante1(int idParticipante1) {  this.idParticipante1 = idParticipante1; }
    public int getIdParticipante2() {  return idParticipante2; }
    public void setIdParticipante2(int idParticipante2) {  this.idParticipante2 = idParticipante2; }
    public int getResultadoP1() { return resultadoP1;  }
    public void setResultadoP1(int resultadoP1) { this.resultadoP1 = resultadoP1; }
    public int getResultadoP2() { return resultadoP2;}
    public void setResultadoP2(int resultadoP2) {this.resultadoP2 = resultadoP2; }
    public Integer getIdGanador() { return idGanador; }
    public void setIdGanador(Integer idGanador) { this.idGanador = idGanador; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) {  this.estado = estado; }
    public String getFechaPartido() {  return fechaPartido;}
    public void setFechaPartido(String fechaPartido) { this.fechaPartido = fechaPartido; }

    @Override
    public String toString() {
        return "Partido{" +
                "idPartido=" + idPartido +
                ", idRonda=" + idRonda +
                ", idParticipante1=" + idParticipante1 +
                ", idParticipante2=" + idParticipante2 +
                ", resultadoP1=" + resultadoP1 +
                ", resultadoP2=" + resultadoP2 +
                ", idGanador=" + idGanador +
                ", estado='" + estado + '\'' +
                ", fechaPartido='" + fechaPartido + '\'' +
                '}';
    }
}