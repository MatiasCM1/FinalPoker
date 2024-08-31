package ar.edu.unlu.poker.modelo;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Dealer {

	private LinkedList<Carta> cartasTotales = new LinkedList<Carta>();
	private LinkedList<Carta> cartas;
	private Random aleatorio = new Random();
	
	public Dealer() {
		this.setearCartasTotales();
		this.setearCartasRonda();
	}
	
	private Carta repartirCarta() {
		int indice = aleatorio.nextInt(this.cartas.size());
		return this.cartas.remove(indice);
	}
	
	public void repartirCartasRonda(List<Jugador> jugadoresMesa, int posJugadorMano) {
		for (int j = 0; j < 5; j++) {
			for (int i = 0; i < jugadoresMesa.size(); i++) {
				Jugador jugadorActual = jugadoresMesa.get(posJugadorMano);
				if (jugadorActual.getCartas().size() < 5) {
					jugadorActual.recibirCarta(this.repartirCarta());
				}
				posJugadorMano = (posJugadorMano + 1) % jugadoresMesa.size();
			}
		}
	}
	
	public LinkedList<Jugador> primerJugadorRepartir(LinkedList<Jugador> jugadoresAux) {
		int indice = aleatorio.nextInt(jugadoresAux.size());
		Collections.rotate(jugadoresAux, jugadoresAux.size() - indice);
		return jugadoresAux;
	}
	
	public void setearCartasRonda() {
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
	
	public LinkedList<Carta> getCartas() {
		return cartas;
	}
	
}
