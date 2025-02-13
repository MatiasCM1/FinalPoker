package ar.edu.unlu.poker.modelo;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Dealer {
	
	private IMesa mesa;
	
	public Dealer(IMesa mesa) {
		this.mesa = mesa;
	}
	
	public void determinarJugadorMano() throws RemoteException {
		if (this.isPrimeraRonda()) {
			this.seleccionarJugadorRandom();
		}
	}

	private boolean isPrimeraRonda() throws RemoteException {
		return mesa.getPrimeraRonda();
	}

	private void seleccionarJugadorRandom() throws RemoteException {
		List<Jugador> jugadoresMezclados = new LinkedList<Jugador>(mesa.getJugadoresMesa());
		Collections.shuffle(jugadoresMezclados);
		mesa.borrarJugadoresMesa();
		mesa.setearJugadoresMezclados(jugadoresMezclados);

	}

	public void limpiarJugadores() throws RemoteException {
		this.mesa.getJugadoresMesa().clear();
	}

}
