/*************************************************************************************
ARCHIVO	: VISTATORNEO.JAVA
FECHA	: 25/10/2025
DESCRIPCION: INTERFAZ DE USUARIO PARA LA GESTIÓN DE TORNEOS Y JUGADORES.
*************************************************************************************/
package Actividad1_Torneo;

import java.util.List;
import java.util.Scanner;

public class VistaTorneo {
    private Scanner scanner;
    public VistaTorneo() {
        this.scanner = new Scanner(System.in);
    }
    public int mostrarMenuPrincipal() {
        System.out.println("\n==============================");
        System.out.println("   menú principal del torneo");
        System.out.println("1. modo administrador");
        System.out.println("2. modo jugador");
        System.out.println("3. salir");
        return leerEntero("seleccione una opción: ");
    }
    public int mostrarMenuAdministrador() {
        System.out.println("\n==============================");
        System.out.println("     menú administrador");
        System.out.println("1. registrar jugador (create)");
        System.out.println("2. crear torneo");
        System.out.println("3. inscribir jugador en torneo");
        System.out.println("4. generar primera ronda");
        System.out.println("5. registrar resultado");
        System.out.println("6. mostrar jugadores (read all)");
        System.out.println("7. mostrar torneos");
        System.out.println("8. modificar jugador (update)"); 
        System.out.println("9. eliminar jugador (delete)"); 
        System.out.println("10. mostrar ranking global");
        System.out.println("11. volver al menú principal");
        return leerEntero("seleccione una opción: ");
    }

    public int mostrarMenuJugador() {
        System.out.println("\n==============================");
        System.out.println("      menú jugador");
        System.out.println("1. ver jugadores");
        System.out.println("2. ver torneos");
        System.out.println("3. volver al menú principal");
        return leerEntero("seleccione una opción: ");
    }

    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    public int leerEntero(String mensaje) {
        int valor = -1;
        boolean valido = false;
        while (!valido) {
            try {
                System.out.print(mensaje);
                valor = Integer.parseInt(scanner.nextLine().trim());
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("ingrese un número válido.");
            }
        }
        return valor;
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarJugadores(List<Jugador> jugadores) {
        System.out.println("\n=== lista de jugadores ===");
        if (jugadores.isEmpty()) {
            System.out.println("no hay jugadores registrados.");
            return;
        }

        for (int i = 0; i < jugadores.size(); i++) {
            Jugador j = jugadores.get(i);
            System.out.println((i + 1) + ". " + j.toString());
        }
    }

    public void mostrarTorneos(List<Torneo> torneos) {
        System.out.println("\n=== lista de torneos ===");
        if (torneos.isEmpty()) {
            System.out.println("no hay torneos creados.");
            return;
        }

        for (int i = 0; i < torneos.size(); i++) {
            Torneo t = torneos.get(i);
            System.out.println((i + 1) + ". " + t.getNombre() 
                + " - jugadores inscritos: " + t.getInscritos().size());
        }
    }

    public void cerrarScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}