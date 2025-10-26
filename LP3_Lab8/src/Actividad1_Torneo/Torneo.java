package Actividad1_Torneo;

import java.util.ArrayList;
import java.util.List;

public class Torneo {
    private String nombre;
    private int limiteJugadores;
    private List<Jugador> inscritos;
    private List<Ronda> rondas;
    private Jugador ganadorFinal;

    public Torneo(String nombre, int limiteJugadores) {
        this.nombre = nombre;
        this.limiteJugadores = limiteJugadores;
        this.inscritos = new ArrayList<>();
        this.rondas = new ArrayList<>();
        this.ganadorFinal = null;
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getLimiteJugadores() {
        return limiteJugadores;
    }

    public void setLimiteJugadores(int limiteJugadores) {
        this.limiteJugadores = limiteJugadores;
    }

    public List<Jugador> getInscritos() {
        return inscritos;
    }

    public void setInscritos(List<Jugador> inscritos) {
        this.inscritos = inscritos;
    }

    public List<Ronda> getRondas() {
        return rondas;
    }

    public void setRondas(List<Ronda> rondas) {
        this.rondas = rondas;
    }

    public Jugador getGanadorFinal() {
        return ganadorFinal;
    }

    public void setGanadorFinal(Jugador ganadorFinal) {
        this.ganadorFinal = ganadorFinal;
    }
}

