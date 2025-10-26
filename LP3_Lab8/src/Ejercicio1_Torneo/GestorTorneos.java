/*************************************************************************************
ARCHIVO	: GestionTorneos.java
FECHA	: 23/10/2025
*************************************************************************************/
package Ejercicio1_Torneo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GestorTorneos {
    private final String CLAVE_SECRETA = "123";
    private JugadorDAO jugadorDAO;
    private EquipoDAO equipoDAO;
    private EquipoMiembroDAO equipoMiembroDAO;
    private TorneoDAO torneoDAO;
    private ParticipanteDAO participanteDAO;
    private RondaDAO rondaDAO;
    private PartidoDAO partidoDAO;
    public GestorTorneos() {
        this.jugadorDAO = new JugadorDAO();
        this.equipoDAO = new EquipoDAO();
        this.equipoMiembroDAO = new EquipoMiembroDAO();
        this.torneoDAO = new TorneoDAO();
        this.participanteDAO = new ParticipanteDAO();
        this.rondaDAO = new RondaDAO();
        this.partidoDAO = new PartidoDAO();
    }
    public void crearJugador(Scanner scanner) {
        try {
            System.out.print("Ingrese nombre completo: ");
            String nombre = scanner.nextLine();
            System.out.print("Ingrese nickname: ");
            String nickname = scanner.nextLine();
            System.out.print("Ingrese email: ");
            String email = scanner.nextLine();
            Jugador j = new Jugador(0, nombre, nickname, email);
            jugadorDAO.insertarJugador(j);
        } catch (Exception e) {
            System.err.println("Error al crear jugador: " + e.getMessage());
        }
    }

    public void listarTodosLosJugadores() {
        List<Jugador> jugadores = jugadorDAO.obtenerTodos();
        if (jugadores.isEmpty()) {
            System.out.println("No hay jugadores registrados");
            return;
        }
        for (Jugador j : jugadores) {
            System.out.println(j.toString());
        }
    }

    public void actualizarJugador(Scanner scanner) {
        try {
            System.out.print("Ingrese el ID del jugador a actualizar: ");
            int id = Integer.parseInt(scanner.nextLine());
            Jugador j = jugadorDAO.obtenerPorId(id);
            if (j == null) {
                System.out.println("Jugador no encontrado.");
                return;
            }
            System.out.println("Datos actuales: " + j);
            System.out.print("Nuevo nombre completo, deje en blanco para no cambiar: ");
            String nombre = scanner.nextLine();
            System.out.print("Nuevo nickname, deje en blanco para no cambiar: ");
            String nickname = scanner.nextLine();
            System.out.print("Nuevo email, deje en blanco para no cambiar: ");
            String email = scanner.nextLine();
            if (!nombre.isEmpty()) j.setNombreCompleto(nombre);
            if (!nickname.isEmpty()) j.setNickname(nickname);
            if (!email.isEmpty()) j.setEmail(email);
            jugadorDAO.actualizarJugador(j);
        } catch (NumberFormatException e) {
            System.err.println("ID invalido.");
        } catch (Exception e) {
            System.err.println("Error al actualizar jugador: " + e.getMessage());
        }
    }

    public void eliminarJugadorConConfirmacion(Scanner scanner) {
        Connection conn = null;
        try {
            System.out.print("Ingrese el ID del jugador a eliminar: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Esta accion es irreversible. Ingrese la clave para confirmar: ");
            String clave = scanner.nextLine();
            if (!clave.equals(CLAVE_SECRETA)) {
                System.out.println("Clave incorrecta. Operacion cancelada.");
                return;
            }
            conn = ConexionBD.getConnection();
            conn.setAutoCommit(false); 
            String sql = "DELETE FROM Jugadores WHERE id_jugador = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                int filasAfectadas = pstmt.executeUpdate();
                if (filasAfectadas > 0) {
                    conn.commit();
                    System.out.println("Jugador ID " + id + " eliminado exitosamente.");
                } else {
                    System.out.println("No se encontró jugador con ID " + id + ".");
                    conn.rollback(); 
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("ID inválido. Operación cancelada.");
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback(); 
            } catch (SQLException se) {
                System.err.println("Error en rollback: " + se.getMessage());
            }
            System.err.println("Error al eliminar jugador: " + e.getMessage());
            System.err.println("Causa probable: El jugador está en un equipo o torneo. Operación revertida.");
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Error al restaurar auto-commit: " + e.getMessage());
            }
        }
    }

    public void buscarJugadoresEnJava(Scanner scanner) {
        System.out.println("--- Busqueda Avanzada de Jugadores---");
        List<Jugador> jugadores = jugadorDAO.obtenerTodos();
        try {
            System.out.print("Filtrar por nickname (contiene, deje en blanco para no filtrar): ");
            String filtroNick = scanner.nextLine().toLowerCase();
            System.out.print("Ordenar por (1: ID, 2: Nombre, 3: Nickname) [1]: ");
            String ordenInput = scanner.nextLine();
            int orden = ordenInput.isEmpty() ? 1 : Integer.parseInt(ordenInput);
            System.out.print("Orden (1: ASC, 2: DESC) [1]: ");
            String ascDescInput = scanner.nextLine();
            boolean esDesc = ascDescInput.equals("2");
            System.out.print("Limitar cantidad de resultados (0 para todos) [0]: ");
            String limitInput = scanner.nextLine();
            int limite = limitInput.isEmpty() ? 0 : Integer.parseInt(limitInput);
            Comparator<Jugador> comparador;
            switch (orden) {
                case 2:
                    comparador = Comparator.comparing(Jugador::getNombreCompleto);
                    break;
                case 3:
                    comparador = Comparator.comparing(Jugador::getNickname);
                    break;
                default:
                    comparador = Comparator.comparingInt(Jugador::getIdJugador);
                    break;
            }
            if (esDesc) {
                comparador = comparador.reversed();
            }
            List<Jugador> resultados = jugadores.stream()
                .filter(j -> filtroNick.isEmpty() || j.getNickname().toLowerCase().contains(filtroNick)) 
                .sorted(comparador) 
                .collect(Collectors.toList());

            if (limite > 0) {
                resultados = resultados.stream().limit(limite).collect(Collectors.toList()); 
            }
            if (resultados.isEmpty()) {
                System.out.println("No se encontraron resultados.");
            } else {
                System.out.println("--- Resultados ---");
                for (Jugador j : resultados) {
                    System.out.println(j.toString());
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("No se hizo una entrada correcta");
        }
    }
    
    public void gestionarEquipos(Scanner scanner) {
        System.out.println("--- GESTIÓN DE EQUIPOS ---");
        System.out.println("1. Crear Equipo");
        System.out.println("2. Listar Todos los Equipos");
        System.out.println("3. Actualizar Equipo");
        System.out.println("4. Eliminar Equipo (con confirmación)");
        System.out.println("5. Asignar Jugador a Equipo");
        System.out.println("0. Volver al Menu Principal");
        System.out.print("Seleccione una opción: ");

        String opcion = scanner.nextLine();
        switch (opcion) {
            case "1":
                crearEquipo(scanner);
                break;
            case "2":
                listarTodosLosEquipos();
                break;
            case "3":
                actualizarEquipo(scanner);
                break;
            case "4":
                eliminarEquipoConConfirmacion(scanner);
                break;
            case "5":
                asignarJugadorAEquipo(scanner);
                break;
            case "0":
                return;
            default:
                System.out.println("La opcion no es correcta");
        }
    }

    public void crearEquipo(Scanner scanner) {
        try {
           System.out.print("Ingrese nombre del equipo: ");
           String nombre = scanner.nextLine();
           System.out.print("Ingrese fecha creación (YYYY-MM-DD): ");
           String fecha = scanner.nextLine();
           Equipo e = new Equipo(0, nombre, fecha);
           equipoDAO.insertarEquipo(e);

       } catch (Exception e) {
           System.err.println("Error al crear equipo: " + e.getMessage());
       }
   }

    public void gestionarTorneos(Scanner scanner) {
        System.out.println("--- GESTION DE TORNEOS ---");
        System.out.println("1. Listar Todos los Torneos");
        System.out.println("2. Listar Rondas de un Torneo");
        System.out.println("3. Listar Partidos de una Ronda");
        System.out.println("0. Volver al Menu Principal");
        System.out.print("Seleccione una opcion: ");
        String opcion = scanner.nextLine();
        switch (opcion) {
            case "1":
                listarTodosLosTorneos();
                break;
            case "2":
                listarRondasPorTorneo(scanner);
                break;
            case "3":
                listarPartidosPorRonda(scanner);
                break;
            case "0":
                return;
            default:
                System.out.println("La opcion no es valida");
        }
    }
    
    public void listarTodosLosTorneos() {
        List<Torneo> torneos = torneoDAO.obtenerTodos();
        if (torneos.isEmpty()) {
            System.out.println("No hay torneos registrados.");
            return;
        }
        for (Torneo t : torneos) {
            System.out.println(t.toString());
        }
    }
    
    public void listarRondasPorTorneo(Scanner scanner) {
        try {
            System.out.print("Ingrese ID del Torneo: ");
            int idTorneo = Integer.parseInt(scanner.nextLine());
            List<Ronda> rondas = rondaDAO.obtenerPorTorneo(idTorneo);
            if (rondas.isEmpty()) {
                System.out.println("No hay rondas para ese torneo.");
                return;
            }
            for (Ronda r : rondas) {
                System.out.println(r.toString());
            }
        } catch (NumberFormatException e) {
            System.err.println("ID invalido.");
        }
    }
    
    public void listarTodosLosEquipos() {
        List<Equipo> equipos = equipoDAO.obtenerTodos();
        if (equipos.isEmpty()) {
            System.out.println("No hay equipos registrados.");
            return;
        }
        for (Equipo e : equipos) {
            System.out.println(e.toString());
        }
    }

    public void actualizarEquipo(Scanner scanner) {
        try {
            System.out.print("Ingrese el ID del equipo a actualizar: ");
            int id = Integer.parseInt(scanner.nextLine());
            Equipo equipo = equipoDAO.obtenerPorId(id);
            if (equipo == null) {
                System.out.println("Equipo no encontrado.");
                return;
            }
            System.out.println("Datos actuales: " + equipo);
            System.out.print("Nuevo nombre (deje en blanco para no cambiar): ");
            String nombre = scanner.nextLine();
            System.out.print("Nueva fecha (YYYY-MM-DD): ");
            String fecha = scanner.nextLine();
            if (!nombre.isEmpty()) equipo.setNombreEquipo(nombre);
            if (!fecha.isEmpty()) equipo.setFechaCreacion(fecha);
            equipoDAO.actualizarEquipo(equipo);

        } catch (NumberFormatException e) {
            System.err.println("ID invalido.");
        } catch (Exception e) {
            System.err.println("Error al actualizar equipo: " + e.getMessage());
        }
    }

    public void eliminarEquipoConConfirmacion(Scanner scanner) {
        Connection conn = null;
        try {
            System.out.print("Ingrese el ID del equipo a eliminar: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Esta acción es irreversible y eliminara miembros asociados. Ingrese la clave para confirmar: ");
            String clave = scanner.nextLine();

            if (!clave.equals(CLAVE_SECRETA)) {
                System.out.println("Clave incorrecta. Operacion cancelada.");
                return;
            }
            conn = ConexionBD.getConnection();
            conn.setAutoCommit(false);
            String sql = "DELETE FROM Equipos WHERE id_equipo = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                int filasAfectadas = pstmt.executeUpdate();

                if (filasAfectadas > 0) {
                    conn.commit(); 
                    System.out.println("Equipo ID " + id + " eliminado exitosamente.");
                } else {
                    System.out.println("No se encontro equipo con ID " + id + ".");
                    conn.rollback(); 
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("ID inválido. Operación cancelada.");
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback(); 
            } catch (SQLException se) {
                System.err.println("Error en rollback: " + se.getMessage());
            }
            System.err.println("Error al eliminar equipo: " + e.getMessage());
            System.err.println("Causa probable: El equipo está inscrito en un torneo. Operacion revertida.");
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true); 
            } catch (SQLException e) {
                System.err.println("Error al restaurar auto-commit: " + e.getMessage());
            }
        }
    }

    public void asignarJugadorAEquipo(Scanner scanner) {
        try {
            System.out.print("Ingrese el ID del Jugador: ");
            int idJugador = Integer.parseInt(scanner.nextLine());
            System.out.print("Ingrese el ID del Equipo al que se unira: ");
            int idEquipo = Integer.parseInt(scanner.nextLine());
            if (jugadorDAO.obtenerPorId(idJugador) == null) {
                System.out.println("Error: No se encontro el jugador con ID " + idJugador);
                return;
            }
            if (equipoDAO.obtenerPorId(idEquipo) == null) {
                System.out.println("Error: No se encontro el equipo con ID " + idEquipo);
                return;
            }
            EquipoMiembro miembro = new EquipoMiembro(idEquipo, idJugador);
            equipoMiembroDAO.insertarMiembro(miembro);
        } catch (NumberFormatException e) {
            System.err.println("ID invalido.");
        } catch (Exception e) {
            System.err.println("Error al asignar jugador: " + e.getMessage());
        }
    }

    public void listarPartidosPorRonda(Scanner scanner) {
         try {
            System.out.print("Ingrese ID de la Ronda: ");
            int idRonda = Integer.parseInt(scanner.nextLine());
            List<Partido> partidos = partidoDAO.obtenerPorRonda(idRonda);
            if (partidos.isEmpty()) {
                System.out.println("No hay partidos para esa ronda.");
                return;
            }
            for (Partido p : partidos) {
                System.out.println(p.toString());
            }
        } catch (NumberFormatException e) {
            System.err.println("ID es invalido.");
        }
    }
}