/*************************************************************************************
ARCHIVO	: Torneo.java
FECHA	: 23/10/2025
*************************************************************************************/

package Torneo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class TorneoDAO {

    public void insertarTorneo(Torneo torneo) {

        String sql = "INSERT INTO Torneos (nombre, tipo_torneo, estado, fecha_inicio) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, torneo.getNombre());
            pstmt.setString(2, torneo.getTipoTorneo());
            pstmt.setString(3, torneo.getEstado());
            pstmt.setString(4, torneo.getFechaInicio());

            pstmt.executeUpdate();
            System.out.println("Torneo '" + torneo.getNombre() + "' insertado correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al insertar torneo: " + e.getMessage());
        }
    }

    public List<Torneo> obtenerTodos() {
        List<Torneo> torneos = new ArrayList<>();
        String sql = "SELECT * FROM Torneos ORDER BY fecha_inicio DESC";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Torneo torneo = new Torneo();
                torneo.setIdTorneo(rs.getInt("id_torneo"));
                torneo.setNombre(rs.getString("nombre"));
                torneo.setTipoTorneo(rs.getString("tipo_torneo"));
                torneo.setEstado(rs.getString("estado"));
                torneo.setFechaInicio(rs.getString("fecha_inicio"));

                int idGanador = rs.getInt("id_ganador_participante");
                if (rs.wasNull()) {
                    torneo.setIdGanadorParticipante(null);
                } else {
                    torneo.setIdGanadorParticipante(idGanador);
                }

                torneos.add(torneo);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener torneos: " + e.getMessage());
        }
        return torneos;
    }

    public Torneo obtenerPorId(int id) {
        String sql = "SELECT * FROM Torneos WHERE id_torneo = ?";
        Torneo torneo = null;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    torneo = new Torneo();
                    torneo.setIdTorneo(rs.getInt("id_torneo"));
                    torneo.setNombre(rs.getString("nombre"));
                    torneo.setTipoTorneo(rs.getString("tipo_torneo"));
                    torneo.setEstado(rs.getString("estado"));
                    torneo.setFechaInicio(rs.getString("fecha_inicio"));
                    
                    int idGanador = rs.getInt("id_ganador_participante");
                    if (rs.wasNull()) {
                        torneo.setIdGanadorParticipante(null);
                    } else {
                        torneo.setIdGanadorParticipante(idGanador);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener torneo por ID: " + e.getMessage());
        }
        return torneo;
    }

    public void actualizarTorneo(Torneo torneo) {
        String sql = "UPDATE Torneos SET nombre = ?, tipo_torneo = ?, estado = ?, " +
                     "fecha_inicio = ?, id_ganador_participante = ? WHERE id_torneo = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, torneo.getNombre());
            pstmt.setString(2, torneo.getTipoTorneo());
            pstmt.setString(3, torneo.getEstado());
            pstmt.setString(4, torneo.getFechaInicio());

            if (torneo.getIdGanadorParticipante() != null) {
                pstmt.setInt(5, torneo.getIdGanadorParticipante());
            } else {
                pstmt.setNull(5, Types.INTEGER);
            }
            
            pstmt.setInt(6, torneo.getIdTorneo());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Torneo ID " + torneo.getIdTorneo() + " actualizado.");
            } else {
                System.out.println("No se encontró torneo con ID " + torneo.getIdTorneo() + " para actualizar.");
            }

        } catch (SQLException e) {
            System.err.println("Error al actualizar torneo: " + e.getMessage());
        }
    }

    public void eliminarTorneo(int id) {
        String sql = "DELETE FROM Torneos WHERE id_torneo = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Torneo ID " + id + " eliminado (junto con sus rondas, partidos y participantes asociados).");
            } else {
                System.out.println("No se encontró torneo con ID " + id + " para eliminar.");
            }

        } catch (SQLException e) {
            System.err.println("Error al eliminar torneo: " + e.getMessage());
        }
    }
}