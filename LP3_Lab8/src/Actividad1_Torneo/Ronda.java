package Actividad1_Torneo;

import java.util.ArrayList;
import java.util.List;

public class Ronda {
    private int numeroRonda;
    private List<Partido> partidos;

    public Ronda(int numeroRonda) {
        this.numeroRonda = numeroRonda;
        this.partidos = new ArrayList<>();
    }

    public void agregarPartido(Partido partido) {
        if (partido != null) {
            partidos.add(partido);
        }
    }

    public boolean estaCompletada() {
        for (Partido p : partidos) {
            if (!p.estaFinalizada()) return false;
        }
        return true;
    }

    public List<Jugador> obtenerGanadores() {
        List<Jugador> ganadores = new ArrayList<>();
        for (Partido p : partidos) {
            if (p.getGanador() != null) {
                ganadores.add(p.getGanador());
            }
        }
        return ganadores;
    }

    public int getNumeroRonda() {
        return numeroRonda;
    }

    public List<Partido> getPartidos() {
        return partidos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== Ronda ").append(numeroRonda).append(" ===\n");
        for (Partido p : partidos) {
            sb.append(p.mostrarPartido()).append("\n");
        }
        return sb.toString();
    }
}
