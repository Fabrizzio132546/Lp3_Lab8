/*************************************************************************************
ARCHIVO	: MAIN.JAVA
FECHA	: 25/10/2025
DESCRIPCION: CLASE PRINCIPAL.
*************************************************************************************/
package Ejercicio5;

public class Main {
    public static void main(String[] args) {
    	VistaTorneo vista = new VistaTorneo();
    	ModeloTorneo modelo = new ModeloTorneo();
    	
        ControladorTorneo controlador = new ControladorTorneo(vista, modelo);
        controlador.iniciar();
    }
}