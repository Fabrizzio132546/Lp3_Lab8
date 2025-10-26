/*************************************************************************************
ARCHIVO	: RondaDAO.java
FECHA	: 23/10/2025
*************************************************************************************/

package Ejercicio1_Torneo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RondaDAO {

    public void insertarRonda(Ronda ronda) {
        String sql = "INSERT INTO Rondas (id_torneo, nombre_ronda, numero_ronda) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ronda.getIdTorneo());
            pstmt.setString(2, ronda.getNombreRonda());
            pstmt.setInt(3, ronda.getNumeroRonda());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al insertar ronda: " + e.getMessage());
        }
    }

    public List<Ronda> obtenerTodos() {
        List<Ronda> rondas = new ArrayList<>();
        String sql = "SELECT * FROM Rondas";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                rondas.add(mapearRonda(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener rondas: " + e.getMessage());
        }
        return rondas;
    }

    public Ronda obtenerPorId(int id) {
        String sql = "SELECT * FROM Rondas WHERE id_ronda = ?";
        Ronda ronda = null;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ronda = mapearRonda(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ronda por ID: " + e.getMessage());
        }
        return ronda;
    }

    public List<Ronda> obtenerPorTorneo(int idTorneo) {
        List<Ronda> rondas = new ArrayList<>();
        String sql = "SELECT * FROM Rondas WHERE id_torneo = ? ORDER BY numero_ronda";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idTorneo);

            try(ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    rondas.add(mapearRonda(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener rondas por torneo: " + e.getMessage());
        }
        return rondas;
    }

    public void actualizarRonda(Ronda ronda) {
        String sql = "UPDATE Rondas SET id_torneo = ?, nombre_ronda = ?, numero_ronda = ? WHERE id_ronda = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ronda.getIdTorneo());
            pstmt.setString(2, ronda.getNombreRonda());
            pstmt.setInt(3, ronda.getNumeroRonda());
            pstmt.setInt(4, ronda.getIdRonda());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al actualizar ronda: " + e.getMessage());
        }
    }

    public void eliminarRonda(int id) {
        String sql = "DELETE FROM Rondas WHERE id_ronda = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al eliminar ronda: " + e.getMessage());
            System.err.println("Causa probable: La ronda tiene partidos asociados (restricción de FK).");
        }
    }

    private Ronda mapearRonda(ResultSet rs) throws SQLException {
        Ronda r = new Ronda();
        r.setIdRonda(rs.getInt("id_ronda"));
        r.setIdTorneo(rs.getInt("id_torneo"));
        r.setNombreRonda(rs.getString("nombre_ronda"));
        r.setNumeroRonda(rs.getInt("numero_ronda"));
        return r;
    }
}