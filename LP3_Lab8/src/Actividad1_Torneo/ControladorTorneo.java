/*************************************************************************************
ARCHIVO	: CONTROLADORTORNEO.JAVA
FECHA	: 25/10/2025
DESCRIPCION: CONTROLADOR PARA LA GESTIÓN DE TORNEOS CON PERSISTENCIA JDBC EN JUGADORES.
*************************************************************************************/
package Ejercicio5;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class ControladorTorneo {

    private VistaTorneo vista;
    private ModeloTorneo modeloTorneo;
    private JugadorDAO jugadorDAO; 
    
    private List<Torneo> torneos;
    private boolean ejecutando;

    public ControladorTorneo(VistaTorneo vista, ModeloTorneo modelo) {
        this.vista = vista;
        this.modeloTorneo = modelo;
        this.jugadorDAO = new JugadorDAO();
        
        this.torneos = new ArrayList<>();
        this.ejecutando = true;
        
        Runtime.getRuntime().addShutdownHook(new Thread(DBConnection::closeConnection));
    }


    public void iniciar() {
        vista.mostrarMensaje("sistema de torneos con persistencia jdbc");

        while (ejecutando) {
            try {
                int opcion = vista.mostrarMenuPrincipal();
                switch (opcion) {
                    case 1 -> menuAdministrador();
                    case 2 -> menuJugador();
                    case 3 -> salir();
                    default -> vista.mostrarMensaje("opcion no valida.");
                }
            } catch (ExcepcionTorneo e) {
                vista.mostrarMensaje("error: " + e.getMessage());
            } catch (Exception e) {
                vista.mostrarMensaje("error inesperado: " + e.getMessage());
            }
        }
    }
    private void menuAdministrador() throws ExcepcionTorneo {
        boolean continuar = true;

        while (continuar) {
            int opcion = vista.mostrarMenuAdministrador();
            switch (opcion) {
                case 1 -> registrarJugador();
                case 2 -> crearTorneo();
                case 3 -> inscribirJugadorEnTorneo();
                case 4 -> generarPrimeraRonda();
                case 5 -> registrarResultado();
                case 6 -> vista.mostrarJugadores(jugadorDAO.listarTodos());
                case 7 -> mostrarTorneos();
                case 8 -> modificarJugador(); // NUEVO UPDATE
                case 9 -> eliminarJugador();  // NUEVO DELETE
                case 10 -> mostrarRankingGlobal();
                case 11 -> continuar = false;
                default -> vista.mostrarMensaje("opción inválida.");
            }
        }
    }
    private void menuJugador() throws ExcepcionTorneo {
        boolean continuar = true;

        while (continuar) {
            int opcion = vista.mostrarMenuJugador();
            switch (opcion) {
                case 1 -> vista.mostrarJugadores(jugadorDAO.listarTodos());
                case 2 -> mostrarTorneos();
                case 3 -> continuar = false;
                default -> vista.mostrarMensaje("opción inválida.");
            }
        }
    }
    
    // ====================================================================
    // CRUD IMPLEMENTADO CON JDBC/DAO
    // ====================================================================

    private void registrarJugador() throws ExcepcionDatosInvalidos {
        try {
            String nombre = vista.leerTexto("ingrese el nombre completo: ");
            String apodo = vista.leerTexto("ingrese el apodo (único): ");
            String clave = vista.leerTexto("ingrese la clave de confirmación para insert (ucsm-db): ");

            if (jugadorDAO.buscarPorApodo(apodo) != null) {
                throw new ExcepcionTorneo("ya existe un jugador con ese apodo. operación cancelada.");
            }
            
            Jugador nuevo = new Jugador(nombre, apodo);
            
            if (jugadorDAO.insertar(nuevo, clave)) {
                vista.mostrarMensaje("jugador registrado con éxito y transacción confirmada. id: " + nuevo.getId_jugador());
            }

        } catch (ExcepcionTorneo e) {
            vista.mostrarMensaje("error: " + e.getMessage());
        }
    }
    
    private void modificarJugador() {
        try {
            String apodo = vista.leerTexto("ingrese el apodo del jugador a modificar: ");
            Jugador jugadorExistente = jugadorDAO.buscarPorApodo(apodo);
            
            if (jugadorExistente == null) {
                vista.mostrarMensaje("no se encontró ningún jugador con ese apodo.");
                return;
            }
            
            vista.mostrarMensaje("modificando jugador con id: " + jugadorExistente.getId_jugador() + " y apodo: " + jugadorExistente.getApodo());
            String nuevoNombre = vista.leerTexto("ingrese el nuevo nombre completo: ");
            String nuevoApodo = vista.leerTexto("ingrese el nuevo apodo (deje vacío para no cambiar): ");
            String clave = vista.leerTexto("ingrese la clave de confirmación para update (ucsm-db): ");
            
            if (!nuevoNombre.isBlank()) jugadorExistente.setNombre(nuevoNombre);
            if (!nuevoApodo.isBlank()) jugadorExistente.setApodo(nuevoApodo); // El dao manejará si es duplicado
            
            if (jugadorDAO.actualizar(jugadorExistente, clave)) {
                vista.mostrarMensaje("jugador actualizado con éxito y transacción confirmada.");
            }
            
        } catch (ExcepcionTorneo e) {
            vista.mostrarMensaje("error: " + e.getMessage());
        }
    }
    
    private void eliminarJugador() {
        try {
            String apodo = vista.leerTexto("ingrese el apodo del jugador a eliminar: ");
            Jugador j = jugadorDAO.buscarPorApodo(apodo);

            if (j == null) {
                vista.mostrarMensaje("no se encontró ningún jugador con ese apodo.");
                return;
            }
            
            String clave = vista.leerTexto("ingrese la clave de confirmación para delete (ucsm-db): ");
            
            if (jugadorDAO.eliminar(j.getId_jugador(), clave)) {
                vista.mostrarMensaje("jugador eliminado correctamente y transacción confirmada.");
            }
            
        } catch (ExcepcionTorneo e) {
            vista.mostrarMensaje("error: " + e.getMessage());
        }
    }
    
    private void crearTorneo() {
        try {
            String nombre = vista.leerTexto("ingrese el nombre del torneo: ");
            int limite = vista.leerEntero("ingrese el número máximo de jugadores: ");

            if (nombre.isBlank()) throw new ExcepcionTorneo("el nombre no puede estar vacío.");
            if (limite < 2) throw new ExcepcionTorneo("el torneo debe tener al menos 2 jugadores.");

            torneos.add(new Torneo(nombre, limite));
            vista.mostrarMensaje("torneo creado correctamente.");

        } catch (ExcepcionTorneo e) {
            vista.mostrarMensaje(" " + e.getMessage());
        }
    }

    private void inscribirJugadorEnTorneo() {
        try {
            List<Jugador> jugadoresDB = jugadorDAO.listarTodos();
            
            if (torneos.isEmpty()) throw new ExcepcionTorneo("no hay torneos disponibles.");
            if (jugadoresDB.isEmpty()) throw new ExcepcionTorneo("no hay jugadores registrados.");

            vista.mostrarTorneos(torneos);
            String nombreTorneo = vista.leerTexto("ingrese el nombre del torneo: ");
            Torneo torneo = buscarTorneo(nombreTorneo);
            if (torneo == null) throw new ExcepcionTorneo("torneo no encontrado.");

            vista.mostrarJugadores(jugadoresDB);
            String apodo = vista.leerTexto("ingrese el apodo del jugador a inscribir: ");
            Jugador jugador = jugadorDAO.buscarPorApodo(apodo);
            if (jugador == null) throw new ExcepcionTorneo("jugador no encontrado en la base de datos.");

            modeloTorneo.inscribirJugador(torneo, jugador);
            vista.mostrarMensaje("jugador inscrito correctamente en memoria.");

        } catch (ExcepcionTorneo e) {
            vista.mostrarMensaje("" + e.getMessage());
        }
    }

    private void generarPrimeraRonda() {
        try {
            if (torneos.isEmpty()) throw new ExcepcionTorneo("no hay torneos registrados.");
            vista.mostrarTorneos(torneos);

            String nombreTorneo = vista.leerTexto("ingrese el nombre del torneo: ");
            Torneo torneo = buscarTorneo(nombreTorneo);
            if (torneo == null) throw new ExcepcionTorneo("torneo no encontrado.");

            modeloTorneo.generarPrimeraRonda(torneo);
            vista.mostrarMensaje("primera ronda generada correctamente.");
            vista.mostrarMensaje(modeloTorneo.obtenerResumen(torneo));

        } catch (ExcepcionTorneo e) {
            vista.mostrarMensaje("" + e.getMessage());
        }
    }

    private void registrarResultado() {
        try {
            if (torneos.isEmpty()) throw new ExcepcionTorneo("no hay torneos registrados.");
            vista.mostrarTorneos(torneos);

            String nombreTorneo = vista.leerTexto("ingrese el nombre del torneo: ");
            Torneo torneo = buscarTorneo(nombreTorneo);
            if (torneo == null) throw new ExcepcionTorneo("torneo no encontrado.");

            vista.mostrarMensaje(modeloTorneo.obtenerResumen(torneo));

            int numRonda = vista.leerEntero("ingrese el número de ronda: ");
            int numPartida = vista.leerEntero("ingrese el número de partida: ");
            String apodoGanador = vista.leerTexto("ingrese el apodo del ganador: ");

            // Se registra el resultado en memoria (modeloTorneo)
            modeloTorneo.registrarGanadorPartida(torneo, numRonda, numPartida, apodoGanador);
            vista.mostrarMensaje("resultado registrado correctamente en memoria.");

            Ronda ultima = torneo.getRondas().get(torneo.getRondas().size() - 1);
            if (ultima.estaCompletada()) {
                modeloTorneo.avanzarRonda(torneo);
            }
            
            // Lógica para actualizar las victorias en la base de datos
            Partido p = ultima.getPartidos().get(numPartida - 1);
            Jugador ganadorActualizado = p.getGanador();

            if (ganadorActualizado != null) {
                // Se busca el jugador por apodo en la DB para obtener su ID y victorias reales
                Jugador jugadorDB = jugadorDAO.buscarPorApodo(ganadorActualizado.getApodo());
                if(jugadorDB != null) {
                     // El objeto Jugador en memoria ya tiene la nueva victoria. Se usa para actualizar la DB
                     jugadorDB.sumarVictoria(); // Esto es redundante si modeloTorneo.registrarGanadorPartida ya lo hizo
                     
                     String clave = vista.leerTexto("ingrese clave para confirmar victoria en la db (ucsm-db): ");
                     if(jugadorDAO.actualizar(ganadorActualizado, clave)) {
                          vista.mostrarMensaje("la victoria ha sido actualizada en la base de datos.");
                     }
                }
            }

            vista.mostrarMensaje(modeloTorneo.obtenerResumen(torneo));

        } catch (ExcepcionTorneo e) {
            vista.mostrarMensaje("" + e.getMessage());
        }
    }

    private void mostrarTorneos() {
        if (torneos.isEmpty()) {
            vista.mostrarMensaje("no hay torneos registrados.");
        } else {
            vista.mostrarTorneos(torneos);
        }
    }

    private void mostrarRankingGlobal() {
        List<Jugador> jugadores = jugadorDAO.listarTodos();
        if (jugadores.isEmpty()) {
            vista.mostrarMensaje("no hay jugadores registrados.");
            return;
        }

        jugadores.sort(Comparator.comparingInt(Jugador::getVictorias).reversed());
        vista.mostrarMensaje("\n=== ranking global ===");
        for (int i = 0; i < jugadores.size(); i++) {
            Jugador j = jugadores.get(i);
            vista.mostrarMensaje((i + 1) + ". " + j.toString());
        }
    }

    private void salir() {
        vista.mostrarMensaje("saliendo, bye bye");
        vista.cerrarScanner();
        DBConnection.closeConnection();
        ejecutando = false;
    }

    private Torneo buscarTorneo(String nombre) {
        for (Torneo t : torneos) {
            if (t.getNombre().equalsIgnoreCase(nombre)) {
                return t;
            }
        }
        return null;
    }
}