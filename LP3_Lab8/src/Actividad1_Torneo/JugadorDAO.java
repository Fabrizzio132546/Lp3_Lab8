/*************************************************************************************
ARCHIVO	: JUGADORDAO.JAVA
FECHA	: 25/10/2025
DESCRIPCION: DAO PARA LA ENTIDAD JUGADOR USANDO JDBC, PREPAREDSTATEMENT Y TRANSACCIONES.
*************************************************************************************/
package Actividad1_Torneo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JugadorDAO {

    private final String CLAVE_CORRECTA = "UCSM-DB"; 
    
    public boolean insertar(Jugador jugador, String claveConfirmacion) throws ExcepcionTorneo {
        int id = listarTodos().size() + 1; 
        String sql = "INSERT INTO Jugadores(id_jugador, nombre, apodo, victorias) VALUES(?, ?, ?, ?)"; 
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) { 
            
            pstmt.setInt(1, id); 
            pstmt.setString(2, jugador.getNombre()); 
            pstmt.setString(3, jugador.getApodo()); 
            pstmt.setInt(4, jugador.getVictorias()); 
            
            int filasAfectadas = pstmt.executeUpdate(); 
            
            if (claveConfirmacion.equals(CLAVE_CORRECTA)) {
                con.commit(); 
                jugador.setId_jugador(id); 
                return filasAfectadas > 0;
            } else {
                con.rollback(); 
                throw new ExcepcionTorneo("transacción revertida: clave de confirmación incorrecta.");
            }
            
        } catch (SQLException e) {
            try {
                DBConnection.getConnection().rollback(); 
            } catch (SQLException ignored) {}
            throw new ExcepcionTorneo("error sql: " + e.getMessage());
        }
    }
    
    public List<Jugador> listarTodos() {
        List<Jugador> lista = new ArrayList<>();
        String sql = "SELECT id_jugador, nombre, apodo, victorias FROM Jugadores ORDER BY id_jugador"; 
        
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) { 
            
            while (rs.next()) { 
                Jugador j = new Jugador(
                    rs.getString("nombre"), 
                    rs.getString("apodo")
                );
                j.setId_jugador(rs.getInt("id_jugador"));
                j.setVictorias(rs.getInt("victorias")); 
                lista.add(j);
            }
            
        } catch (SQLException | ExcepcionDatosInvalidos e) {
            System.err.println("error al listar jugadores: " + e.getMessage());
        }
        return lista;
    }
    
    public Jugador buscarPorApodo(String apodo) {
        String sql = "SELECT id_jugador, nombre, apodo, victorias FROM Jugadores WHERE apodo = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) { 
            
            pstmt.setString(1, apodo);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Jugador j = new Jugador(
                        rs.getString("nombre"), 
                        rs.getString("apodo")
                    );
                    j.setId_jugador(rs.getInt("id_jugador"));
                    j.setVictorias(rs.getInt("victorias"));
                    return j;
                }
            }
        } catch (SQLException | ExcepcionDatosInvalidos e) {
            System.err.println("error al buscar jugador por apodo: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizar(Jugador jugador, String claveConfirmacion) throws ExcepcionTorneo {
        String sql = "UPDATE Jugadores SET nombre = ?, apodo = ?, victorias = ? WHERE id_jugador = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, jugador.getNombre());
            pstmt.setString(2, jugador.getApodo());
            pstmt.setInt(3, jugador.getVictorias());
            pstmt.setInt(4, jugador.getId_jugador());
            
            int filasAfectadas = pstmt.executeUpdate();

            if (claveConfirmacion.equals(CLAVE_CORRECTA)) {
                con.commit(); 
                return filasAfectadas > 0;
            } else {
                con.rollback(); 
                throw new ExcepcionTorneo("transacción revertida: clave de confirmación incorrecta.");
            }

        } catch (SQLException e) {
            try {
                DBConnection.getConnection().rollback();
            } catch (SQLException ignored) {}
            throw new ExcepcionTorneo("error sql al actualizar: " + e.getMessage());
        }
    }
    
    public boolean eliminar(int id_jugador, String claveConfirmacion) throws ExcepcionTorneo {
        String sql = "DELETE FROM Jugadores WHERE id_jugador = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, id_jugador);
            
            int filasAfectadas = pstmt.executeUpdate();

            if (claveConfirmacion.equals(CLAVE_CORRECTA)) {
                con.commit(); 
                return filasAfectadas > 0;
            } else {
                con.rollback(); 
                throw new ExcepcionTorneo("transacción revertida: clave de confirmación incorrecta.");
            }

        } catch (SQLException e) {
            try {
                DBConnection.getConnection().rollback();
            } catch (SQLException ignored) {}
            throw new ExcepcionTorneo("error sql al eliminar: " + e.getMessage());
        }
    }
}