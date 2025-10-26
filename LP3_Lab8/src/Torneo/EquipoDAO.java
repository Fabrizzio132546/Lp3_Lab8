/*************************************************************************************
ARCHIVO	: EquipoDAO.java
FECHA	: 23/10/2025
*************************************************************************************/

package Torneo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {

    public void insertarEquipo(Equipo equipo) {
        String sql = "INSERT INTO Equipos (nombre_equipo, fecha_creacion) VALUES (?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, equipo.getNombreEquipo());
            pstmt.setString(2, equipo.getFechaCreacion()); 

            pstmt.executeUpdate();
            System.out.println("Equipo '" + equipo.getNombreEquipo() + "' insertado correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al insertar equipo: " + e.getMessage());
        }
    }

    public List<Equipo> obtenerTodos() {
        List<Equipo> equipos = new ArrayList<>();
        String sql = "SELECT * FROM Equipos ORDER BY nombre_equipo";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Equipo equipo = new Equipo();
                equipo.setIdEquipo(rs.getInt("id_equipo"));
                equipo.setNombreEquipo(rs.getString("nombre_equipo"));
                equipo.setFechaCreacion(rs.getString("fecha_creacion"));

                equipos.add(equipo);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener equipos: " + e.getMessage());
        }
        return equipos;
    }

    public Equipo obtenerPorId(int id) {
        String sql = "SELECT * FROM Equipos WHERE id_equipo = ?";
        Equipo equipo = null;

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    equipo = new Equipo();
                    equipo.setIdEquipo(rs.getInt("id_equipo"));
                    equipo.setNombreEquipo(rs.getString("nombre_equipo"));
                    equipo.setFechaCreacion(rs.getString("fecha_creacion"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener equipo por ID: " + e.getMessage());
        }
        return equipo;
    }

    public void actualizarEquipo(Equipo equipo) {
        String sql = "UPDATE Equipos SET nombre_equipo = ?, fecha_creacion = ? WHERE id_equipo = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, equipo.getNombreEquipo());
            pstmt.setString(2, equipo.getFechaCreacion());
            pstmt.setInt(3, equipo.getIdEquipo());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Equipo ID " + equipo.getIdEquipo() + " actualizado.");
            } else {
                System.out.println("No se encontró equipo con ID " + equipo.getIdEquipo() + " para actualizar.");
            }

        } catch (SQLException e) {
            System.err.println("Error al actualizar equipo: " + e.getMessage());
        }
    }

    public void eliminarEquipo(int id) {
        String sql = "DELETE FROM Equipos WHERE id_equipo = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Equipo ID " + id + " eliminado.");
            } else {
                System.out.println("No se encontró equipo con ID " + id + " para eliminar.");
            }

        } catch (SQLException e) {
            System.err.println("Error al eliminar equipo: " + e.getMessage());
            System.err.println("Causa probable: El equipo tiene miembros o está inscrito en un torneo (restricción de FK).");
        }
    }
}