/*************************************************************************************
ARCHIVO	: Jugador.java
FECHA	: 23/10/2025
*************************************************************************************/
package Ejercicio1_Torneo;
public class Jugador {
    private int idJugador;
    private String nombreCompleto;
    private String nickname;
    private String email;
    public Jugador() {
    }

    public Jugador(int idJugador, String nombreCompleto, String nickname, String email) {
        this.idJugador = idJugador;
        this.nombreCompleto = nombreCompleto;
        this.nickname = nickname;
        this.email = email;
    }
    
    public int getIdJugador() { return idJugador; }
    public void setIdJugador(int idJugador) { this.idJugador = idJugador; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname;}
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Jugador{" +
                "idJugador=" + idJugador +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}