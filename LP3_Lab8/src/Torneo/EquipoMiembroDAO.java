/*************************************************************************************
ARCHIVO	: EquipoMiembroDAO.java
FECHA	: 23/10/2025
*************************************************************************************/

package Torneo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipoMiembroDAO {

    public void insertarMiembro(EquipoMiembro miembro) {
        String sql = "INSERT INTO Equipo_Miembros (id_equipo, id_jugador) VALUES (?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, miembro.getIdEquipo());
            pstmt.setInt(2, miembro.getIdJugador());

            pstmt.executeUpdate();
            System.out.println("Jugador ID " + miembro.getIdJugador() + " añadido a Equipo ID " + miembro.getIdEquipo());

        } catch (SQLException e) {
            System.err.println("Error al insertar miembro de equipo: " + e.getMessage());
            System.err.println("Causa probable: El jugador o el equipo no existen, o el jugador ya está en ese equipo.");
        }
    }

    public void eliminarMiembro(int idEquipo, int idJugador) {
        String sql = "DELETE FROM Equipo_Miembros WHERE id_equipo = ? AND id_jugador = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEquipo);
            pstmt.setInt(2, idJugador);

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Jugador ID " + idJugador + " eliminado de Equipo ID " + idEquipo);
            } else {
                System.out.println("No se encontró la membresía para eliminar.");
            }

        } catch (SQLException e) {
            System.err.println("Error al eliminar miembro: " + e.getMessage());
        }
    }

    public List<Jugador> obtenerMiembrosPorEquipo(int idEquipo) {
        List<Jugador> miembros = new ArrayList<>();
        String sql = "SELECT J.* FROM Jugadores J " +
                     "JOIN Equipo_Miembros EM ON J.id_jugador = EM.id_jugador " +
                     "WHERE EM.id_equipo = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idEquipo);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Jugador jugador = new Jugador();
                    jugador.setIdJugador(rs.getInt("id_jugador"));
                    jugador.setNombreCompleto(rs.getString("nombre_completo"));
                    jugador.setNickname(rs.getString("nickname"));
                    jugador.setEmail(rs.getString("email"));
                    miembros.add(jugador);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener miembros de equipo: " + e.getMessage());
        }
        return miembros;
    }

    public List<Equipo> obtenerEquiposPorJugador(int idJugador) {
        List<Equipo> equipos = new ArrayList<>();
        String sql = "SELECT E.* FROM Equipos E " +
                     "JOIN Equipo_Miembros EM ON E.id_equipo = EM.id_equipo " +
                     "WHERE EM.id_jugador = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idJugador);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Equipo equipo = new Equipo();
                    equipo.setIdEquipo(rs.getInt("id_equipo"));
                    equipo.setNombreEquipo(rs.getString("nombre_equipo"));
                    equipo.setFechaCreacion(rs.getString("fecha_creacion"));
                    equipos.add(equipo);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener equipos de jugador: " + e.getMessage());
        }
        return equipos;
    }
}