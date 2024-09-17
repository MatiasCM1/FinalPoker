package ar.edu.unlu.poker.modelo;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Dealer {

	private LinkedList<Carta> cartasTotales = new LinkedList<Carta>();
	private LinkedList<Carta> cartas;
	private Random aleatorio = new Random();
	
	public Dealer() throws RemoteException {
		this.setearCartasTotales();
		this.setearCartasRonda();
	}
	
	private Carta repartirCarta() {
		int indice = aleatorio.nextInt(this.cartas.size());
		return this.cartas.remove(indice);
	}
	
	public void repartirCartasRonda(Queue<Jugador> jugadoresMesa) throws RemoteException {
	    for (int j = 0; j < 5; j++) {
	        for (int i = 0; i < jugadoresMesa.size(); i++) {
	            Jugador jugadorActual = jugadoresMesa.poll();
	            if (jugadorActual.getCartas().size() < 5) {
	                jugadorActual.recibirCarta(this.repartirCarta());
	            }
	            jugadoresMesa.add(jugadorActual);
	        }
	    }
	}
	
	public LinkedList<Jugador> primerJugadorRepartir(LinkedList<Jugador> jugadoresAux) throws RemoteException{
		int indice = aleatorio.nextInt(jugadoresAux.size());
		Collections.rotate(jugadoresAux, jugadoresAux.size() - indice);
		return jugadoresAux;
	}
	
	public void setearCartasRonda() throws RemoteException{
		this.cartas = new LinkedList<Carta>(this.cartasTotales);
	}
	
	private void setearCartasTotales() {
		String[] palos = {"PICA", "CORAZON", "DIAMANTE", "TREBOL"};
		String[] valores = {"AS", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
		for (String palo : palos) {
			for (String valor : valores) {
				this.cartasTotales.add(new Carta(valor, palo));
			}
		}
	}
	
	public LinkedList<Carta> getCartas() throws RemoteException{
		return cartas;
	}
	
}
