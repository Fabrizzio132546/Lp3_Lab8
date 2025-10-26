/*************************************************************************************
ARCHIVO	: JugadorDAO.java
FECHA	: 23/10/2025
*************************************************************************************/
package Torneo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class JugadorDAO {
    public void insertarJugador(Jugador jugador) {

        String sql = "INSERT INTO Jugadores (nombre_completo, nickname, email) VALUES (?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, jugador.getNombreCompleto());
            pstmt.setString(2, jugador.getNickname());
            pstmt.setString(3, jugador.getEmail());
            pstmt.executeUpdate();
            System.out.println("Jugador '" + jugador.getNickname() + "' insertado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al insertar jugador: " + e.getMessage());
        }
    }
    public List<Jugador> obtenerTodos() {
        List<Jugador> jugadores = new ArrayList<>();
        String sql = "SELECT * FROM Jugadores ORDER BY id_jugador";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Jugador jugador = new Jugador();
                jugador.setIdJugador(rs.getInt("id_jugador"));
                jugador.setNombreCompleto(rs.getString("nombre_completo"));
                jugador.setNickname(rs.getString("nickname"));
                jugador.setEmail(rs.getString("email"));
                jugadores.add(jugador);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener jugadores: " + e.getMessage());
        }
        return jugadores;
    }
    
    public Jugador obtenerPorId(int id) {
        String sql = "SELECT * FROM Jugadores WHERE id_jugador = ?";
        Jugador jugador = null;
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    jugador = new Jugador();
                    jugador.setIdJugador(rs.getInt("id_jugador"));
                    jugador.setNombreCompleto(rs.getString("nombre_completo"));
                    jugador.setNickname(rs.getString("nickname"));
                    jugador.setEmail(rs.getString("email"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener jugador por ID: " + e.getMessage());
        }
        return jugador;
    }
    public void actualizarJugador(Jugador jugador) {
        String sql = "UPDATE Jugadores SET nombre_completo = ?, nickname = ?, email = ? WHERE id_jugador = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, jugador.getNombreCompleto());
            pstmt.setString(2, jugador.getNickname());
            pstmt.setString(3, jugador.getEmail());
            pstmt.setInt(4, jugador.getIdJugador());
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Jugador ID " + jugador.getIdJugador() + " actualizado.");
            } else {
                System.out.println("No se encontró jugador con ID " + jugador.getIdJugador() + " para actualizar.");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar jugador: " + e.getMessage());
        }
    }
    public void eliminarJugador(int id) {
        String sql = "DELETE FROM Jugadores WHERE id_jugador = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Jugador ID " + id + " eliminado.");
            } else {
                System.out.println("No se encontró jugador con ID " + id + " para eliminar.");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar jugador: " + e.getMessage());
            System.err.println("Posible causa: El jugador está inscrito en un equipo o torneo y no se puede borrar (restricción de FK).");
        }
    }
}