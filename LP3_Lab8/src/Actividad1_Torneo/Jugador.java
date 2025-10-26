/*************************************************************************************
ARCHIVO	: JUGADOR.JAVA
FECHA	: 25/10/2025
DESCRIPCION: MODELO DE DATOS PARA LA ENTIDAD JUGADOR (CORREGIDO).
*************************************************************************************/
package Ejercicio5;

public class Jugador implements IParticipante {
    private int id_jugador; 
    private String nombre;
    private String apodo;
    private int victorias;

    public Jugador(String nombre, String apodo) throws ExcepcionDatosInvalidos {
        if (nombre == null || nombre.isBlank() || apodo == null || apodo.isBlank()) {
            // Este es el punto donde se lanza la ExcepcionDatosInvalidos
            throw new ExcepcionDatosInvalidos("nombre y apodo no pueden estar vacíos.");
        }
        this.nombre = nombre.trim();
        this.apodo = apodo.trim();
        this.victorias = 0;
        this.id_jugador = 0; 
    }
    
    // Getters y Setters necesarios para JDBC y DAO

    public int getId_jugador() { return id_jugador; }
    public void setId_jugador(int id_jugador) { this.id_jugador = id_jugador; }
    
    @Override
    public String getNombre() {
        return nombre;
    }
    // Setters necesarios para la función actualizar() en JugadorDAO
    public void setNombre(String nombre) throws ExcepcionDatosInvalidos { 
        if (nombre == null || nombre.isBlank()) {
            throw new ExcepcionDatosInvalidos("el nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim(); 
    }

    @Override
    public String getApodo() {
        return apodo;
    }
    // Setters necesarios para la función actualizar() en JugadorDAO
    public void setApodo(String apodo) throws ExcepcionDatosInvalidos { 
        if (apodo == null || apodo.isBlank()) {
            throw new ExcepcionDatosInvalidos("el apodo no puede estar vacío.");
        }
        this.apodo = apodo.trim(); 
    }

    @Override
    public int getVictorias() {
        return victorias;
    }

    public void setVictorias(int victorias) { this.victorias = victorias; }

    @Override
    public void sumarVictoria() {
        victorias++;
    }

    @Override
    public String toString() {
        return "id: " + id_jugador + " | " + apodo + " - " + nombre + " (victorias: " + victorias + ")";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        return apodo.equalsIgnoreCase(jugador.apodo);
    }
}