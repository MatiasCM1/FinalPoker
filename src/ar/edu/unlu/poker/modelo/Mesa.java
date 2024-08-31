package ar.edu.unlu.poker.modelo;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import ar.edu.unlu.poker.comunes.Observado;
import ar.edu.unlu.poker.comunes.Observer;

public class Mesa implements Observado{
	
	private List<Jugador> jugadoresMesa = new LinkedList<Jugador>();
	private List<Observer> listaObservadores = new LinkedList<Observer>();
	private static final HashMap<String, Integer> valorCarta = new HashMap<String, Integer>();
	private int posJugadorMano;
	
	static {
		valorCarta.put("2", 2);
		valorCarta.put("3", 3);
		valorCarta.put("4", 4);
		valorCarta.put("5", 5);
		valorCarta.put("6", 6);
		valorCarta.put("7", 7);
		valorCarta.put("8", 8);
		valorCarta.put("9", 9);
		valorCarta.put("10", 10);
		valorCarta.put("J", 11);
		valorCarta.put("Q", 12);
		valorCarta.put("K", 13);
		valorCarta.put("AS", 14);
	}
	
	@Override
	public void agregarObservador(Observer o) {
		this.listaObservadores.add(o);
	}
	@Override
	public void notificarObservers(Informe informe) {
		this.listaObservadores.forEach(t -> t.update(this, informe));
	}
	
	public void agregarJugador(Jugador jugador) {
		if (this.jugadoresMesa.size() < 8) {
			this.jugadoresMesa.add(jugador);
		} else {
			this.notificarObservers(Informe.CANT_JUGADORES_EXCEDIDOS);
		}
	}
	
	public Jugador obtenerJugadorMano() {
		return this.jugadoresMesa.get(this.posJugadorMano);
	}
	
	public void iniciarJuego() {
		if (this.jugadoresMesa.size() > 1) {

			Dealer dealer = new Dealer();
			dealer.setearCartasRonda();
			this.posJugadorMano = this.seleccionarJugadorRandom();
			this.notificarObservers(Informe.JUGADOR_MANO);
			//this.setearFondoApuestas();
			dealer.repartirCartasRonda(this.jugadoresMesa, this.posJugadorMano);
			this.notificarObservers(Informe.CARTAS_REPARTIDAS);
			this.notificarObservers(Informe.DEVOLVER_GANADOR);
			this.jugadoresMesa.forEach(Jugador::resetearCartas);
		} else {
			this.notificarObservers(Informe.CANT_JUGADORES_INSUFICIENTES);
		}
	}
	
	
	public List<Jugador> getJugadoresMesa() {
		return jugadoresMesa;
	}
	
	private int seleccionarJugadorRandom() {
		Random random = new Random();
		return random.nextInt(this.jugadoresMesa.size());
	}
	
	
	public int getPosJugadorMano() {
		return posJugadorMano;
	}
	
	public List<Jugador> devolverJugadorEntregaCarta() {
		
		List<Jugador> listaOrdenada = new LinkedList<Jugador>();
		int jugManoAux = this.posJugadorMano;
		for (int i = 0; i < this.jugadoresMesa.size(); i++) {
			listaOrdenada.add(this.jugadoresMesa.get(jugManoAux));
			jugManoAux = (jugManoAux + 1) % this.jugadoresMesa.size();
		}
		return listaOrdenada;		
	}
	
	private void calcularResultadoJugadores() {
		this.jugadoresMesa.forEach(Jugador::calcularValorCartas);
	}
	
	public List<Jugador> devolverGanador(){
		calcularResultadoJugadores();
		List<Jugador> ganador = new LinkedList<Jugador>();
		ganador.add(this.jugadoresMesa.get(0));
		for (int i = 1; i < this.jugadoresMesa.size(); i++) {
			Jugador jugadorActual = this.jugadoresMesa.get(i);
			Jugador jugadorGanador = ganador.get(0);
			if (jugadorActual.getResultadoValoresCartas().ordinal() > jugadorGanador.getResultadoValoresCartas().ordinal()) {
				ganador.clear();
				ganador.add(jugadorActual);
			} else if (jugadorActual.getResultadoValoresCartas().ordinal() == jugadorGanador.getResultadoValoresCartas().ordinal()) {
				Jugador jugadorConCartaMayor = buscarCartaMayor(jugadorGanador, jugadorActual);
				if (jugadorConCartaMayor.equals(jugadorActual)) {
					ganador.clear();
					ganador.add(jugadorActual);
				} else if (jugadorConCartaMayor.equals(jugadorGanador)) {
					ganador.clear();
					ganador.add(jugadorGanador);
				}
			}
		}
		return ganador;
	}
	
	private Jugador buscarCartaMayor(Jugador jugador1, Jugador jugador2) {
		Carta carta1 = jugador1.getCartasOrdenadas().getLast();
		Carta carta2 = jugador2.getCartasOrdenadas().getLast();
		LinkedList <Carta> cartasAlta = new LinkedList<Carta>();
		cartasAlta.add(carta1);
		cartasAlta.add(carta2);
		ResultadoJugadaJugador resultado = new ResultadoJugadaJugador();
		Carta cartaMasAlta = resultado.cartaMasAlta(cartasAlta);
		if (cartaMasAlta.equals(carta1)) {
			return jugador1;
		} else if (cartaMasAlta.equals(carta2)){
			return jugador2;
		} 
		return null;
	}
	
	

}
