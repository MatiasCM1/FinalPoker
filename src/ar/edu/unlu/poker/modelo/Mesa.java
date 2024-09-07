package ar.edu.unlu.poker.modelo;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import ar.edu.unlu.poker.comunes.Observado;
import ar.edu.unlu.poker.comunes.Observer;

public class Mesa implements Observado{
	
	private List<Jugador> jugadoresMesa = new LinkedList<Jugador>();
	private List<Observer> listaObservadores = new LinkedList<Observer>();
	private static final HashMap<String, Integer> valorCarta = new HashMap<String, Integer>();
	private int posJugadorMano;
	private int apuestaMayor;
	
	private int apuestaMaxima;
	private List<Jugador> jugadoresEnJuego;
	
	public int getApuestaMayor() {
		return apuestaMayor;
	}
	public void setApuestaMayor(int apuestaMayor) {
		this.apuestaMayor = apuestaMayor;
	}

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
	
	/*@Override
	public void notificarObservers(Informe informe, Jugador jugador) {
		this.listaObservadores.forEach(t -> t.update(this, informe, jugador));
	}*/
	
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
			dealer.repartirCartasRonda(this.jugadoresMesa, this.posJugadorMano);
			this.notificarObservers(Informe.CARTAS_REPARTIDAS);
			
			//this.comenzarAGestionarApuestas();
			
			this.notificarObservers(Informe.DEVOLVER_GANADOR);
			this.jugadoresMesa.forEach(Jugador::resetearCartas);
		} else {
			this.notificarObservers(Informe.CANT_JUGADORES_INSUFICIENTES);
		}
	}
	
	/*public void iniciarRondaApuestas() {
		this.apuestaMaxima = 0;
		this.jugadoresEnJuego = new LinkedList<Jugador>(this.jugadoresMesa);
	}
	
	public void procesarApuesta(Jugador jugador, int cantidad) {
		if (cantidad > 0) {
			jugador.envidar(cantidad);
			this.apuestaMaxima = Math.max(this.apuestaMaxima, cantidad);
		} else {
			jugador.pasar();
			this.jugadoresEnJuego.remove(jugador);
		}
		notificarObservers(Informe.APUESTA_REALIZADA);
	}
	
	public boolean rondaApuestasTerminada() {
		for (Jugador jugador : jugadoresEnJuego) {
			if (jugador.isEnJuego() && jugador.getApuesta() < this.apuestaMaxima) {
				return false;
			}
		}
		return true;
	}
	
	public void finalizarRonda() {
		if (this.jugadoresEnJuego.size() == 1) {
			notificarObservers(Informe.DEVOLVER_GANADOR);
		}
	}*/
	
	/*public void comenzarAGestionarApuestas() {
		this.setApuestaMayor(0);
		this.notificarObservers(Informe.REALIZAR_APUESTAS);
	}
	
	public void igualarApuestas(int opcion) {
		boolean apuestasIgualadas;
		do{
			apuestasIgualadas = true;
			for (Jugador jugador : jugadoresMesa){
				if (jugador.isHapasado() || jugador.getApuesta() == apuestaMayor) {
					continue; //No se le solicita la apuesta si el jugador ha pasado
				}
				switch(opcion) {
					case 1:	
						
						this.notificarObservers(Informe.JUGADOR_DECIDE_APOSTAR, jugador);
						
					break;
					case 2:
						
						jugador.pasar();
						
					break;
				}
				//Verificar que las apuestas esten igualadas
				for (Jugador j : jugadoresMesa) {
					if (!j.isHapasado() && j.getApuesta() != apuestaMayor) {
						apuestasIgualadas = false;
						break;
					}
				}
			}
		} while (!apuestasIgualadas);
	}
	
	
	public void jugadorDecideApostar(int apuesta, Jugador jugador) {
		if (apuesta > jugador.getFondo() || apuesta < this.getApuestaMayor()) { //Como se a que jugador le tengo que asignar
			this.notificarObservers(Informe.APUESTA_INSUFICIENTE);
		}
	}
	
	
	public void obtenerApuesta(int apuesta, Jugador jugador) {
		if (apuesta > jugador.getFondo() || apuesta < this.getApuestaMayor()) {
			
			this.notificarObservers(Informe.FONDO_INSUFICIENTE);
			
		} else if (apuesta < this.getApuestaMayor()) {
			
			this.notificarObservers(Informe.APUESTA_INSUFICIENTE);
			
			} else {
				
				jugador.setApuesta(apuesta);
				if (apuesta > this.getApuestaMayor()) {
					this.setApuestaMayor(apuesta);
				}
				
		}
	}*/
	
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
