package Actividad1_Torneo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModeloTorneo {

    public void inscribirJugador(Torneo torneo, Jugador jugador) throws ExcepcionTorneo {
        if (jugador == null) throw new ExcepcionTorneo("Jugador inválido.");
        if (torneo.getInscritos().size() >= torneo.getLimiteJugadores())
            throw new ExcepcionTorneo("Se alcanzó el límite de jugadores.");

        for (Jugador j : torneo.getInscritos()) {
            if (j.getApodo().equalsIgnoreCase(jugador.getApodo())) {
                throw new ExcepcionTorneo("Ya existe un jugador con el apodo '" + jugador.getApodo() + "'.");
            }
        }
        torneo.getInscritos().add(jugador);
    }

    public void generarPrimeraRonda(Torneo torneo) throws ExcepcionTorneo {
        if (torneo.getInscritos().size() < 2) {
            throw new ExcepcionTorneo("No hay suficientes jugadores para iniciar el torneo.");
        }

        List<Jugador> participantes = new ArrayList<>(torneo.getInscritos());
        Collections.shuffle(participantes);

        Ronda ronda = new Ronda(1);
        int numeroPartida = 1;

        for (int i = 0; i < participantes.size(); i += 2) {
            Jugador j1 = participantes.get(i);
            Jugador j2 = (i + 1 < participantes.size()) ? participantes.get(i + 1) : null;
            ronda.agregarPartido(new Partido(j1, j2, numeroPartida++, 1));
        }

        torneo.getRondas().add(ronda);
    }

    public void registrarGanadorPartida(Torneo torneo, int numRonda, int numPartida, String apodoGanador) throws ExcepcionTorneo {
        if (numRonda < 1 || numRonda > torneo.getRondas().size()) {
            throw new ExcepcionTorneo("Número de ronda inválido.");
        }

        Ronda ronda = torneo.getRondas().get(numRonda - 1);
        if (numPartida < 1 || numPartida > ronda.getPartidos().size()) {
            throw new ExcepcionTorneo("Número de partida inválido.");
        }

        Partido p = ronda.getPartidos().get(numPartida - 1);
        Jugador ganador = null;

        if (p.getJugador1().getApodo().equalsIgnoreCase(apodoGanador)) {
            ganador = p.getJugador1();
        } else if (p.getJugador2() != null && p.getJugador2().getApodo().equalsIgnoreCase(apodoGanador)) {
            ganador = p.getJugador2();
        } else {
            throw new ExcepcionTorneo("No se encontró el jugador ganador indicado.");
        }

        p.registrarGanador(ganador);
    }

    public void avanzarRonda(Torneo torneo) throws ExcepcionTorneo {
        if (torneo.getRondas().isEmpty()) throw new ExcepcionTorneo("No hay rondas generadas.");

        Ronda ultima = torneo.getRondas().get(torneo.getRondas().size() - 1);
        if (!ultima.estaCompletada()) {
            throw new ExcepcionTorneo("La ronda actual aún no está completa.");
        }

        List<Jugador> ganadores = ultima.obtenerGanadores();
        if (ganadores.size() == 1) {
            torneo.setGanadorFinal(ganadores.get(0));
            return;
        }

        Ronda nuevaRonda = new Ronda(torneo.getRondas().size() + 1);
        int numPartida = 1;
        for (int i = 0; i < ganadores.size(); i += 2) {
            Jugador j1 = ganadores.get(i);
            Jugador j2 = (i + 1 < ganadores.size()) ? ganadores.get(i + 1) : null;
            nuevaRonda.agregarPartido(new Partido(j1, j2, numPartida++, torneo.getRondas().size() + 1));
        }
        torneo.getRondas().add(nuevaRonda);
    }

    public String obtenerResumen(Torneo torneo) {
        StringBuilder sb = new StringBuilder();
        sb.append("Torneo: ").append(torneo.getNombre())
          .append(" (").append(torneo.getInscritos().size())
          .append("/").append(torneo.getLimiteJugadores()).append(" jugadores)\n");

        for (Ronda r : torneo.getRondas()) {
            sb.append(r.toString());
        }

        if (torneo.getGanadorFinal() != null) {
            sb.append("\nGanador Final: ").append(torneo.getGanadorFinal().getApodo()).append("\n");
        }
        return sb.toString();
    }
}
