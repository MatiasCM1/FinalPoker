package ar.edu.unlu.poker.controlador;

import java.util.LinkedList;
import java.util.List;

import ar.edu.unlu.poker.comunes.Observado;
import ar.edu.unlu.poker.comunes.Observer;
import ar.edu.unlu.poker.modelo.Informe;
import ar.edu.unlu.poker.modelo.Jugador;
import ar.edu.unlu.poker.modelo.Mesa;
import ar.edu.unlu.poker.vista.VistaConsola;

public class Controlador implements Observer{
	
	private VistaConsola vista;
	private Mesa mesa;
	
	public Controlador(VistaConsola vista, Mesa mesa) {
		this.vista = vista;
		this.mesa = mesa;
	}

	@Override
	public void update(Observado observado, Informe informe) {
		switch(informe) {
			case JUGADOR_MANO:
				vista.mostrarJugadorMano(((Mesa)observado).obtenerJugadorMano());
			break;
			case CARTAS_REPARTIDAS:
				vista.mostrarCartasJugador(((Mesa)observado).getJugadoresMesa());
			break;
			case CANT_JUGADORES_INSUFICIENTES:
				vista.informarJugadoresInsuficientes();
			break;
			case CANT_JUGADORES_EXCEDIDOS:
				vista.informarCantJugadoresExcedidos();
			break;
			case REALIZAR_APUESTAS:
				vista.solicitarApuestas(((Mesa)observado).getJugadoresMesa());
			break;
			case DEVOLVER_GANADOR:
				vista.mostrarGanador(((Mesa)observado).devolverGanador());
			break;
		}
	}

	public void agregarJugador(Jugador j) {
		mesa.agregarJugador(j);
	}
	
	public List<Jugador> getJugadoresMesa(){
		return mesa.getJugadoresMesa();
	}
	
	public List<Jugador> getListaOrdenadaJugadorMano() {
		return mesa.devolverJugadorEntregaCarta();
	}
	
	public List<Jugador> obtenerJugadores(){
		return mesa.getJugadoresMesa();
	}
	
	public void iniciarGame() {
		mesa.iniciarJuego();
	}
	
}
