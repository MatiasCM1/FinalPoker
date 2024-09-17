package ar.edu.unlu.poker.todoapp;

import ar.edu.unlu.poker.controlador.Controlador;
import ar.edu.unlu.poker.modelo.Mesa;
import ar.edu.unlu.poker.vista.consola.VistaConsolaSwing;

public class Main {

	public static void main(String[] args) {
        VistaConsolaSwing vista = new VistaConsolaSwing();
        Mesa mesa = new Mesa();
        Controlador controlador = new Controlador(vista/*, mesa*/);
        //mesa.agregarObservador(controlador);
        vista.setControlador(controlador);
        vista.setVisible(true);
    }

}
