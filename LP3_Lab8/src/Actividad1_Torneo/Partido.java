package Ejercicio5;

public class Partido {
    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador ganador;
    private int numeroPartida;
    private int numeroRonda;

    public Partido(Jugador jugador1, Jugador jugador2, int numeroPartida, int numeroRonda) throws ExcepcionTorneo {
        if (jugador1 == null) {
            throw new ExcepcionTorneo("El jugador 1 no puede ser nulo.");
        }

        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.numeroPartida = numeroPartida;
        this.numeroRonda = numeroRonda;

        // Si hay "bye" (jugador2 nulo), gana automáticamente el jugador1
        if (jugador2 == null) {
            this.ganador = jugador1;
            jugador1.sumarVictoria();
        } else {
            this.ganador = null;
        }
    }

    public void registrarGanador(Jugador ganador) throws ExcepcionTorneo {
        if (ganador == null) throw new ExcepcionTorneo("El ganador no puede ser nulo.");
        if (!ganador.equals(jugador1) && !ganador.equals(jugador2))
            throw new ExcepcionTorneo("El ganador no pertenece a este partido.");

        this.ganador = ganador;
        ganador.sumarVictoria();
    }

    public boolean estaFinalizada() {
        return ganador != null;
    }

    public Jugador getGanador() {
        return ganador;
    }

    public Jugador getJugador1() {
        return jugador1;
    }

    public Jugador getJugador2() {
        return jugador2;
    }

    public String mostrarPartido() {
        String info = "Partida " + numeroPartida + " (Ronda " + numeroRonda + "): ";
        if (jugador2 == null) {
            info += jugador1.getApodo() + " pasa automáticamente (bye)";
        } else {
            info += jugador1.getApodo() + " vs " + jugador2.getApodo();
            if (ganador != null) {
                info += " → Ganador: " + ganador.getApodo();
            }
        }
        return info;
    }
}
