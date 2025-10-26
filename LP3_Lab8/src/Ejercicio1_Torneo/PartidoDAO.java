/*************************************************************************************
ARCHIVO	: PartidoDAO.java
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

public class PartidoDAO {

    public void insertarPartido(Partido partido) {
        String sql = "INSERT INTO Partidos (id_ronda, id_participante1, id_participante2, resultado_p1, " +
                     "resultado_p2, id_ganador, estado, fecha_partido) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, partido.getIdRonda());
            pstmt.setInt(2, partido.getIdParticipante1());
            pstmt.setInt(3, partido.getIdParticipante2());
            pstmt.setInt(4, partido.getResultadoP1());
            pstmt.setInt(5, partido.getResultadoP2());

            if (partido.getIdGanador() != null) {
                pstmt.setInt(6, partido.getIdGanador());
            } else {
                pstmt.setNull(6, Types.INTEGER);
            }

            pstmt.setString(7, partido.getEstado());
            pstmt.setString(8, partido.getFechaPartido());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al insertar partido: " + e.getMessage());
        }
    }

    public List<Partido> obtenerTodos() {
        List<Partido> partidos = new ArrayList<>();
        String sql = "SELECT * FROM Partidos";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                partidos.add(mapearPartido(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener partidos: " + e.getMessage());
        }
        return partidos;
    }

    public Partido obtenerPorId(int id) {
        String sql = "SELECT * FROM Partidos WHERE id_partido = ?";
        Partido partido = null;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    partido = mapearPartido(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener partido por ID: " + e.getMessage());
        }
        return partido;
    }

    public List<Partido> obtenerPorRonda(int idRonda) {
        List<Partido> partidos = new ArrayList<>();
        String sql = "SELECT * FROM Partidos WHERE id_ronda = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idRonda);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    partidos.add(mapearPartido(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener partidos por ronda: " + e.getMessage());
        }
        return partidos;
    }

    public void actualizarPartido(Partido partido) {
        String sql = "UPDATE Partidos SET id_ronda = ?, id_participante1 = ?, id_participante2 = ?, " +
                     "resultado_p1 = ?, resultado_p2 = ?, id_ganador = ?, estado = ?, fecha_partido = ? " +
                     "WHERE id_partido = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, partido.getIdRonda());
            pstmt.setInt(2, partido.getIdParticipante1());
            pstmt.setInt(3, partido.getIdParticipante2());
            pstmt.setInt(4, partido.getResultadoP1());
            pstmt.setInt(5, partido.getResultadoP2());

            if (partido.getIdGanador() != null) {
                pstmt.setInt(6, partido.getIdGanador());
            } else {
                pstmt.setNull(6, Types.INTEGER);
            }

            pstmt.setString(7, partido.getEstado());
            pstmt.setString(8, partido.getFechaPartido());
            pstmt.setInt(9, partido.getIdPartido());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al actualizar partido: " + e.getMessage());
        }
    }

    public void eliminarPartido(int id) {
        String sql = "DELETE FROM Partidos WHERE id_partido = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al eliminar partido: " + e.getMessage());
        }
    }

    private Partido mapearPartido(ResultSet rs) throws SQLException {
        Partido p = new Partido();
        p.setIdPartido(rs.getInt("id_partido"));
        p.setIdRonda(rs.getInt("id_ronda"));
        p.setIdParticipante1(rs.getInt("id_participante1"));
        p.setIdParticipante2(rs.getInt("id_participante2"));
        p.setResultadoP1(rs.getInt("resultado_p1"));
        p.setResultadoP2(rs.getInt("resultado_p2"));
        
        int idGanador = rs.getInt("id_ganador");
        p.setIdGanador(rs.wasNull() ? null : idGanador);
        
        p.setEstado(rs.getString("estado"));
        p.setFechaPartido(rs.getString("fecha_partido"));
        return p;
    }
}