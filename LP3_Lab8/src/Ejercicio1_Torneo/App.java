/*************************************************************************************
ARCHIVO	: App.java
FECHA	: 23/10/2025
*************************************************************************************/
package Ejercicio1_Torneo;
import java.util.Scanner;
public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestorTorneos gestor = new GestorTorneos();
        System.out.println("--- BIENVENIDO AL SISTEMA DE GESTION DE TORNEOS ---");
        boolean salir = false;
        while (!salir) {
            mostrarMenuPrincipal();
            String opcion = scanner.nextLine();
            
            switch (opcion) {
                case "1":
                    menuGestionJugadores(gestor, scanner);
                    break;
                case "2":
                    gestor.gestionarEquipos(scanner);
                    break;
                case "3":
                    gestor.gestionarTorneos(scanner);
                    break;
                case "0":
                    salir = true;
                    System.out.println("Saliendo");
                    break;
                default:
                    System.out.println("La opcion no es valida, intenta otra vez");
            }
            System.out.println();
        }
        ConexionBD.closeConnection();
        scanner.close();
        System.out.println("Programa finalizado.");
    }
    private static void mostrarMenuPrincipal() {
        System.out.println("--- MENU PRINCIPAL ---");
        System.out.println("1. Gestionar Jugadores");
        System.out.println("2. Gestionar Equipos");
        System.out.println("3. Ver Torneos y Partidos");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }
    private static void menuGestionJugadores(GestorTorneos gestor, Scanner scanner) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTION DE JUGADORES ---");
            System.out.println("1. Crear Jugador");
            System.out.println("2. Listar Todos los Jugadores");
            System.out.println("3. Actualizar Jugador");
            System.out.println("4. Eliminar Jugador"); 
            System.out.println("5. Búsqueda Avanzada");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1":
                    gestor.crearJugador(scanner);
                    break;
                case "2":
                    gestor.listarTodosLosJugadores();
                    break;
                case "3":
                    gestor.actualizarJugador(scanner);
                    break;
                case "4":
                    gestor.eliminarJugadorConConfirmacion(scanner);
                    break;
                case "5":
                    gestor.buscarJugadoresEnJava(scanner);
                    break;
                case "0":
                    volver = true;
                    break;
                default:
                    System.out.println("La opcion no es correcta");
            }
        }
    }
}