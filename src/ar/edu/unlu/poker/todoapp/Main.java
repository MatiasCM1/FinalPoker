package ar.edu.unlu.poker.todoapp;

import ar.edu.unlu.poker.controlador.Controlador;
import ar.edu.unlu.poker.modelo.Mesa;
import ar.edu.unlu.poker.vista.VistaConsola;

public class Main {

	public static void main(String[] args) {
		VistaConsola vista = new VistaConsola();
		Mesa mesa = new Mesa();
		Controlador controlador = new Controlador(vista, mesa);
		mesa.agregarObservador(controlador);
		vista.setControlador(controlador);
		vista.inicioJuego();
		
		
	}

}
