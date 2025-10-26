/*************************************************************************************
ARCHIVO	: ParticipanteDAO.java
FECHA	: 23/10/2025
*************************************************************************************/

package Ejercicio1_Torneo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ParticipanteDAO {

    public void insertarParticipante(Participante participante) {
        String sql = "INSERT INTO Participantes (id_torneo, id_jugador, id_equipo, fecha_inscripcion) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, participante.getIdTorneo());

            if (participante.getIdJugador() != null) {
                pstmt.setInt(2, participante.getIdJugador());
            } else {
                pstmt.setNull(2, Types.INTEGER);
            }

            if (participante.getIdEquipo() != null) {
                pstmt.setInt(3, participante.getIdEquipo());
            } else {
                pstmt.setNull(3, Types.INTEGER);
            }

            pstmt.setString(4, participante.getFechaInscripcion());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al insertar participante: " + e.getMessage());
        }
    }

    public List<Participante> obtenerTodos() {
        List<Participante> participantes = new ArrayList<>();
        String sql = "SELECT * FROM Participantes";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                participantes.add(mapearParticipante(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener participantes: " + e.getMessage());
        }
        return participantes;
    }

    public Participante obtenerPorId(int id) {
        String sql = "SELECT * FROM Participantes WHERE id_participante = ?";
        Participante participante = null;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    participante = mapearParticipante(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener participante por ID: " + e.getMessage());
        }
        return participante;
    }

    public List<Participante> obtenerPorTorneo(int idTorneo) {
        List<Participante> participantes = new ArrayList<>();
        String sql = "SELECT * FROM Participantes WHERE id_torneo = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idTorneo);
            
            try(ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    participantes.add(mapearParticipante(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener participantes por torneo: " + e.getMessage());
        }
        return participantes;
    }


    public void actualizarParticipante(Participante participante) {
        String sql = "UPDATE Participantes SET id_torneo = ?, id_jugador = ?, id_equipo = ?, fecha_inscripcion = ? " +
                     "WHERE id_participante = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, participante.getIdTorneo());

            if (participante.getIdJugador() != null) {
                pstmt.setInt(2, participante.getIdJugador());
            } else {
                pstmt.setNull(2, Types.INTEGER);
            }

            if (participante.getIdEquipo() != null) {
                pstmt.setInt(3, participante.getIdEquipo());
            } else {
                pstmt.setNull(3, Types.INTEGER);
            }

            pstmt.setString(4, participante.getFechaInscripcion());
            pstmt.setInt(5, participante.getIdParticipante());
            
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al actualizar participante: " + e.getMessage());
        }
    }

    public void eliminarParticipante(int id) {
        String sql = "DELETE FROM Participantes WHERE id_participante = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al eliminar participante: " + e.getMessage());
            System.err.println("Causa probable: El participante tiene partidos asociados (restricción de FK).");
        }
    }

    private Participante mapearParticipante(ResultSet rs) throws SQLException {
        Participante p = new Participante();
        p.setIdParticipante(rs.getInt("id_participante"));
        p.setIdTorneo(rs.getInt("id_torneo"));
        
        int idJugador = rs.getInt("id_jugador");
        p.setIdJugador(rs.wasNull() ? null : idJugador);
        
        int idEquipo = rs.getInt("id_equipo");
        p.setIdEquipo(rs.wasNull() ? null : idEquipo);
        
        p.setFechaInscripcion(rs.getString("fecha_inscripcion"));
        return p;
    }
}