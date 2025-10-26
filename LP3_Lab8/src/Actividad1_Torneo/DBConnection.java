/*************************************************************************************
ARCHIVO	: DBCONNECTION.JAVA
FECHA	: 25/10/2025
DESCRIPCION: CLASE DE UTILIDAD PARA GESTIONAR LA CONEXIÓN JDBC A SQLITE.
*************************************************************************************/
package Ejercicio5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private static final String URL = "jdbc:sqlite:TorneoFinal.db"; 
    private static Connection connection = null;

    private static final String SQL_CREATE_TABLE_JUGADORES = 
        "CREATE TABLE IF NOT EXISTS Jugadores (" +
        "id_jugador INTEGER PRIMARY KEY, " + 
        "nombre TEXT UNIQUE NOT NULL, " + 
        "apodo TEXT UNIQUE NOT NULL, " +
        "victorias INTEGER DEFAULT 0)"; 

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC"); 
                
                connection = DriverManager.getConnection(URL); 
                System.out.println("conexión a sqlite establecida."); 
                
                connection.setAutoCommit(false); 
                
                crearEstructura(connection);
                
            } catch (ClassNotFoundException e) {
                System.err.println("error: driver jdbc de sqlite no encontrado. asegúrese de incluir el jar.");
                throw new SQLException("driver sqlite no disponible.", e);
            }
        }
        return connection;
    }

    private static void crearEstructura(Connection con) throws SQLException {
        try (Statement stmt = con.createStatement()) { 
            stmt.execute(SQL_CREATE_TABLE_JUGADORES);
            con.commit(); 
            System.out.println("estructura base de datos verificada/creada.");
        } catch (SQLException e) {
            con.rollback(); 
            System.err.println("error al crear la estructura de la base de datos: " + e.getMessage());
            throw e; 
        }
    }
    
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close(); 
                connection = null;
                System.out.println("conexión a sqlite cerrada.");
            } catch (SQLException e) {
                System.err.println("error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}