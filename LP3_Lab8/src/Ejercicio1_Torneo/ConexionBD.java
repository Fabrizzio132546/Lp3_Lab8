/*************************************************************************************
ARCHIVO	: ConexionBD.java
FECHA	: 23/10/2025
*************************************************************************************/

package Ejercicio1_Torneo; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = "jdbc:sqlite:torneos.db";
    private static Connection conexion = null;
    private ConexionBD() {
    }
    public static Connection getConnection() {
        try {
            if (conexion == null || conexion.isClosed()) {
                try {
                    Class.forName("org.sqlite.JDBC");
                } catch (ClassNotFoundException e) {
                    System.err.println("Error: No se encontró el driver de SQLite (sqlite-jdbc.jar).");
                    e.printStackTrace();
                }
                System.out.println("Creando nueva conexión a la base de datos...");
                conexion = DriverManager.getConnection(URL);
                conexion.createStatement().execute("PRAGMA foreign_keys = ON;");
                System.out.println("¡Conexión exitosa!");
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
        return conexion;
    }
    public static void closeConnection() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión cerrada.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }
}